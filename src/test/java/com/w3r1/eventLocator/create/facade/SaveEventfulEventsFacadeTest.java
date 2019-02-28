package com.w3r1.eventLocator.create.facade;

import com.w3r1.eventLocator.create.request.EventsWebRequest;
import com.w3r1.eventLocator.create.service.EventfulEventsService;
import com.w3r1.eventLocator.create.service.domain.EventLocationsPage;
import com.w3r1.eventLocator.model.EventLocation;
import com.w3r1.eventLocator.service.EventLocationService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SaveEventfulEventsFacadeTest {

    @Mock
    private EventfulEventsService eventfulEventsService;

    @Mock
    private EventLocationService eventLocationService;

    @Captor
    private ArgumentCaptor<List<EventLocation>> eventLocationsCaptor;

    private SaveEventfulEventsFacade saveEventfulEventsFacade;

    @Before
    public void setup() {
        saveEventfulEventsFacade = new SaveEventfulEventsFacadeImpl(eventfulEventsService, eventLocationService);
    }

    @Test
    public void queryFromEventfulAndSaveToDatastore() {

        EventLocation eventLocationA = EventLocation.builder().title("A").build();
        EventLocationsPage eventLocationsPage1 = EventLocationsPage.builder()
                .pageNumber(1).pageCount(3).eventLocations(Arrays.asList(eventLocationA)).build();

        EventLocation eventLocationB = EventLocation.builder().title("B").build();
        EventLocation eventLocationC = EventLocation.builder().title("C").build();
        EventLocationsPage eventLocationsPage2 = EventLocationsPage.builder()
                .pageNumber(2).pageCount(3).eventLocations(Arrays.asList(eventLocationB, eventLocationC)).build();

        EventLocation eventLocationD = EventLocation.builder().title("D").build();
        EventLocationsPage eventLocationsPage3 = EventLocationsPage.builder()
                .pageNumber(3).pageCount(3).eventLocations(Arrays.asList(eventLocationD)).build();

        given(eventfulEventsService.getEventfulEvents(any(), eq(1), anyInt()))
                .willReturn(eventLocationsPage1);
        given(eventfulEventsService.getEventfulEvents(any(), eq(2), anyInt()))
                .willReturn(eventLocationsPage2);
        given(eventfulEventsService.getEventfulEvents(any(), eq(3), anyInt()))
                .willReturn(eventLocationsPage3);

        doAnswer(answer -> null).when(eventLocationService).saveAll(eventLocationsCaptor.capture());

        saveEventfulEventsFacade.queryFromEventfulAndSaveToDatastore(EventsWebRequest.builder().build());

        verify(eventfulEventsService, times(3))
                .getEventfulEvents(any(), anyInt(), anyInt());
        verify(eventLocationService, times(3))
                .saveAll(anyList());

        List<EventLocation> list = eventLocationsCaptor.getAllValues()
                .stream()
                .flatMap(l -> l.stream())
                .collect(Collectors.toList());
        assertThat(list, hasSize(4));
        assertThat(list, Matchers.contains(eventLocationA, eventLocationB, eventLocationC, eventLocationD));
    }

    @Test
    public void noNullPointersOnNullParams() {

        given(eventfulEventsService.getEventfulEvents(any(), anyInt(), anyInt()))
                .willReturn(EventLocationsPage.builder().pageCount(0).pageNumber(0).build());
        saveEventfulEventsFacade.queryFromEventfulAndSaveToDatastore(null);
    }
}