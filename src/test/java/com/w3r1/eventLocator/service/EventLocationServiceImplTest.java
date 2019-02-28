package com.w3r1.eventLocator.service;

import com.w3r1.eventLocator.model.EventLocation;
import com.w3r1.eventLocator.repository.EventLocationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.data.domain.PageRequest.of;

@RunWith(MockitoJUnitRunner.class)
public class EventLocationServiceImplTest {

    @Mock
    private EventLocationRepository eventLocationRepository;

    private EventLocationService eventLocationService;

    @Before
    public void setup() {
        eventLocationService = new EventLocationServiceImpl(eventLocationRepository);
    }

    @Test
    public void searchCrossThroughTitleDescriptionCategoryAndCity() {

        given(eventLocationRepository
                .searchThroughTitleDescriptionCategoryAndCity(anyString(), any()))
                .willReturn(Page.empty());

        Page<EventLocation> search = eventLocationService
                .searchThroughTitleDescriptionCategoryAndCity("search", of(1, 1));

        verify(eventLocationRepository, times(1))
                .searchThroughTitleDescriptionCategoryAndCity(eq("search"), eq(of(1, 1)));
        assertThat(search, equalTo(Page.empty()));
    }

    @Test
    public void save() {

        EventLocation testEventLocation = EventLocation.builder().build();
        eventLocationService.save(testEventLocation);

        verify(eventLocationRepository, times(1)).save(eq(testEventLocation));
    }

    @Test
    public void saveAll() {

        List<EventLocation> eventLocations = Arrays.asList(EventLocation.builder().build());
        eventLocationService.saveAll(eventLocations);

        verify(eventLocationRepository, times(1)).saveAll(eq(eventLocations));
    }
}