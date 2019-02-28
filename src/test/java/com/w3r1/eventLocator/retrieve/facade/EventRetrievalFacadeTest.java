package com.w3r1.eventLocator.retrieve.facade;

import com.w3r1.eventLocator.model.EventLocation;
import com.w3r1.eventLocator.service.EventLocationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class EventRetrievalFacadeTest {

    @Mock
    private EventLocationService eventLocationService;

    private EventRetrievalFacade eventRetrievalFacade;

    @Before
    public void setup() {
        eventRetrievalFacade = new EventRetrievalFacadeImpl(eventLocationService);
    }

    @Test
    public void getEventLocations() {

        List<EventLocation> eventLocations = Arrays.asList(
                EventLocation.builder().id(123L).build(),
                EventLocation.builder().id(234L).build()
        );
        given(eventLocationService
                .searchThroughTitleDescriptionCategoryAndCity(any(), any()))
                .willReturn(new PageImpl(eventLocations));

        List<EventLocation> resultList = eventRetrievalFacade.getEventLocations("search", Pageable.unpaged());

        assertThat(resultList, hasSize(2));
        assertThat(resultList.get(0), equalTo(eventLocations.get(0)));
        assertThat(resultList.get(1), equalTo(eventLocations.get(1)));
    }

    @Test
    public void noNullPointersOnNullParams() {

        given(eventLocationService
                .searchThroughTitleDescriptionCategoryAndCity(any(), any()))
                .willReturn(Page.empty());
        List<EventLocation> resultList = eventRetrievalFacade.getEventLocations(null, null);

        assertThat(resultList, hasSize(0));
    }
}