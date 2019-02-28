package com.w3r1.eventLocator.retrieve;

import com.w3r1.eventLocator.model.EventLocation;
import com.w3r1.eventLocator.retrieve.facade.EventRetrievalFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class EventRetrievalControllerTest {

    @Mock
    private EventRetrievalFacade eventRetrievalFacade;

    private EventRetrievalController eventRetrievalController;

    @Before
    public void setup() {
        eventRetrievalController = new EventRetrievalController(eventRetrievalFacade);
    }

    @Test
    public void getEvents() {

        List<EventLocation> eventLocations = Arrays.asList(
                EventLocation.builder().id(123L).build(),
                EventLocation.builder().id(234L).build()
        );
        given(eventRetrievalFacade.getEventLocations(any(), any()))
                .willReturn(eventLocations);
        ResponseEntity<List<EventLocation>> result =
                eventRetrievalController.getEvents("search", Pageable.unpaged());

        assertThat(result.getStatusCode(), equalTo(OK));
        assertThat(result.getBody(), hasSize(2));
        assertThat(result.getBody().get(0), equalTo(eventLocations.get(0)));
        assertThat(result.getBody().get(1), equalTo(eventLocations.get(1)));
    }

    @Test
    public void noNullPointersOnNullParams() {

        given(eventRetrievalFacade.getEventLocations(any(), any()))
                .willReturn(new ArrayList<>());
        List<EventLocation> resultList = eventRetrievalFacade.getEventLocations(null, null);

        assertThat(resultList, hasSize(0));
    }
}