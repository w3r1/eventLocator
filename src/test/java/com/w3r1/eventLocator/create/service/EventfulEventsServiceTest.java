package com.w3r1.eventLocator.create.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w3r1.eventLocator.create.client.EventfulClient;
import com.w3r1.eventLocator.create.client.domain.EventsResponse;
import com.w3r1.eventLocator.create.request.EventsWebRequest;
import com.w3r1.eventLocator.create.service.domain.EventLocationsPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class EventfulEventsServiceTest {

    private static final String BASE_PATH = EventfulEventsServiceTest.class.getResource("../../../../..").getPath();
    private static final String JSON_RESPONSE_FILE = BASE_PATH + "eventsExampleResponse.json";

    @Mock
    private EventfulClient eventfulClient;

    private EventfulEventsService eventfulEventsService;

    @Before
    public void setup() {
        eventfulEventsService = new EventfulEventsServiceImpl(eventfulClient);
    }

    @Test
    public void getEventfulEvents() throws IOException {

        String testLocation = "London";
        String testCategory = "music";
        int testPageNo = 1;
        int testPageSize = 10;

        EventsWebRequest webRequest = EventsWebRequest.builder().location(testLocation).category(testCategory).build();
        given(eventfulClient.getEventfulEvents(eq(webRequest), eq(testPageNo), eq(testPageSize)))
                .willReturn(Optional.of(defaultEventsResponse()));

        EventLocationsPage eventLocationsPage = eventfulEventsService.getEventfulEvents(webRequest, testPageNo, testPageSize);

        assertThat(eventLocationsPage.getPageNumber(), equalTo(1));
        assertThat(eventLocationsPage.getPageCount(), equalTo(4398));
        assertThat(eventLocationsPage.getEventLocations(), hasSize(2));
        assertThat(eventLocationsPage.getEventLocations().get(0).getEventId(), equalTo("E0-001-122804325-0"));
        assertThat(eventLocationsPage.getEventLocations().get(0).getImgUrl(), containsString("friday-eve-parkrun-58.jpeg"));
        assertThat(eventLocationsPage.getEventLocations().get(0).getCategory(), equalTo(testCategory));
        assertThat(eventLocationsPage.getEventLocations().get(1).getEventId(), equalTo("E0-001-123723417-3"));
        assertThat(eventLocationsPage.getEventLocations().get(1).getImgUrl(), containsString("dutty-dancing-dancehall-afrobeats-hip-hop-92.jpeg"));
        assertThat(eventLocationsPage.getEventLocations().get(1).getCategory(), equalTo(testCategory));
    }

    @Test
    public void noNullPointersOnNullParams() {

        given(eventfulClient.getEventfulEvents(any(), anyInt(), anyInt()))
                .willReturn(Optional.empty());
        EventLocationsPage eventfulEvents = eventfulEventsService.getEventfulEvents(null, -1, -1);
        assertThat(eventfulEvents.getPageNumber(), equalTo(0));
        assertThat(eventfulEvents.getPageCount(), equalTo(0));
    }

    private EventsResponse defaultEventsResponse() throws IOException {

        byte[] jsonFile = Files.readAllBytes(Paths.get(JSON_RESPONSE_FILE));
        return new ObjectMapper().readValue(jsonFile, EventsResponse.class);
    }
}