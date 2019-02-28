package com.w3r1.eventLocator.integration;

import com.w3r1.eventLocator.model.EventLocation;
import com.w3r1.eventLocator.repository.EventLocationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:testapplication.properties")
public class EventLocationRepositoryTest {

    @Autowired
    private EventLocationRepository repository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(EventLocation.class);
        elasticsearchTemplate.createIndex(EventLocation.class);
        elasticsearchTemplate.putMapping(EventLocation.class);
        elasticsearchTemplate.refresh(EventLocation.class);
    }

    @Test
    public void searchForEventsMostLikelyResultFirst() {

        EventLocation eventLocation1 = EventLocation.builder()
                .title("Bremen")
                .description("Music in Bremen")
                .category("music")
                .cityName("Bremen")
                .build();

        EventLocation eventLocation2 = EventLocation.builder()
                .title("Hamburg")
                .description("Music in Hamburg")
                .category("music")
                .cityName("Hamburg")
                .build();

        EventLocation eventLocation3 = EventLocation.builder()
                .title("Hamburg Bremen")
                .description("Music in Hamburg Bremen")
                .category("music")
                .cityName("Hamburg Bremen")
                .build();

        repository.saveAll(Arrays.asList(eventLocation1, eventLocation2, eventLocation3));

        Page<EventLocation> eventLocations =
                repository.searchThroughTitleDescriptionCategoryAndCity("Music Hamburg", Pageable.unpaged());
        List<EventLocation> eventLocationList = eventLocations.stream().collect(Collectors.toList());

        assertThat(eventLocationList, hasSize(3));
        assertThat(eventLocationList.get(0).getTitle(), equalTo(eventLocation2.getTitle()));
        assertThat(eventLocationList.get(1).getTitle(), equalTo(eventLocation3.getTitle()));
        assertThat(eventLocationList.get(2).getTitle(), equalTo(eventLocation1.getTitle()));
    }

    @Test
    public void searchForEventsFilteredForJustOne() {

        EventLocation eventLocation1 = EventLocation.builder()
                .title("Bremen")
                .description("Music in Bremen")
                .category("music")
                .cityName("Bremen")
                .build();

        EventLocation eventLocation2 = EventLocation.builder()
                .title("Bremen")
                .description("Gaming in Bremen")
                .category("GAMING")
                .cityName("Bremen")
                .build();

        EventLocation eventLocation3 = EventLocation.builder()
                .title("Bremen")
                .description("Meeting in Bremen")
                .category("meeting")
                .cityName("Bremen")
                .build();

        repository.saveAll(Arrays.asList(eventLocation1, eventLocation2, eventLocation3));

        Page<EventLocation> eventLocations =
                repository.searchThroughTitleDescriptionCategoryAndCity("gaming", Pageable.unpaged());
        List<EventLocation> eventLocationList = eventLocations.stream().collect(Collectors.toList());

        assertThat(eventLocationList, hasSize(1));
        assertThat(eventLocationList.get(0).getTitle(), equalTo(eventLocation2.getTitle()));
    }

    @Test
    public void emptyResult() {

        EventLocation eventLocation1 = EventLocation.builder()
                .title("Bremen")
                .description("Music in Bremen")
                .category("music")
                .cityName("Bremen")
                .build();

        EventLocation eventLocation2 = EventLocation.builder()
                .title("Bremen")
                .description("Gaming in Bremen")
                .category("GAMING")
                .cityName("Bremen")
                .build();

        EventLocation eventLocation3 = EventLocation.builder()
                .title("Bremen")
                .description("Meeting in Bremen")
                .category("meeting")
                .cityName("Bremen")
                .build();

        repository.saveAll(Arrays.asList(eventLocation1, eventLocation2, eventLocation3));

        Page<EventLocation> eventLocations =
                repository.searchThroughTitleDescriptionCategoryAndCity("Jazz", Pageable.unpaged());
        assertThat(eventLocations.getTotalElements(), equalTo(0L));
    }
}