package com.w3r1.eventLocator.create.client;

import com.w3r1.eventLocator.create.client.domain.Event;
import com.w3r1.eventLocator.create.client.domain.Events;
import com.w3r1.eventLocator.create.client.domain.EventsResponse;
import com.w3r1.eventLocator.create.request.EventsWebRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Optional;

import static com.w3r1.eventLocator.create.client.EventfulClient.PROTOCOL;
import static com.w3r1.eventLocator.create.client.EventfulClient.SEARCH_EVENTS_PATH;
import static org.apache.commons.lang3.StringUtils.join;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.springframework.http.HttpStatus.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LogManager.class)
public class EventfulClientTest {

    private static final String TEST_HOST = "host";
    private static final String TEST_PORT = "80";
    private static final String TEST_APPKEY = "appkey";

    private static Logger mockLOGGER;

    @Mock
    private RestTemplate restTemplate;

    private EventfulClient eventfulClient;

    @BeforeClass
    public static void setupClass() {
        mockStatic(LogManager.class);
        mockLOGGER = mock(Logger.class);
        when(LogManager.getLogger()).thenReturn(mockLOGGER);
    }

    @Before
    public void setup() {
        eventfulClient = new EventfulClient(restTemplate, TEST_HOST, null, TEST_APPKEY);
    }

    @Test
    public void getEvents() {

        String testLocation = "London";
        int testPageNo = 1;
        int testPageSize = 10;

        ResponseEntity<EventsResponse> responseEntity
                = new ResponseEntity<>(defaultEventsResponse(), OK);
        ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);
        given(restTemplate
                .exchange(uriCaptor.capture(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .willReturn(responseEntity);

        EventsWebRequest webRequest = EventsWebRequest.builder().location(testLocation).build();
        EventsResponse eventsResponse =
                eventfulClient.getEventfulEvents(webRequest, testPageNo, testPageSize).get();

        verify(restTemplate, times(1))
                .exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class));
        assertThat(eventsResponse.getPageNumber(), equalTo(1));
        assertThat(eventsResponse.getPageSize(), equalTo(1));
        assertThat(eventsResponse.getPageCount(), equalTo(1));
        assertThat(eventsResponse.getEvents().getEvent(), hasSize(1));
        assertThat(eventsResponse.getEvents().getEvent(),
                hasItem(
                        hasProperty("eventId", equalTo("123-abc-001"))
                ));

        String capturedRequestUri = uriCaptor.getValue();
        assertThat(capturedRequestUri, equalTo(
                join(PROTOCOL, TEST_HOST,
                        SEARCH_EVENTS_PATH,
                        "?app_key=", TEST_APPKEY,
                        "&location=", testLocation,
                        "&page_number=", testPageNo,
                        "&page_size=", testPageSize
                )
        ));
    }

    @Test
    public void getEventsWithCategoryFiltering() {

        String testLocation = "London";
        String testCategory = "music";
        int testPageNo = 1;
        int testPageSize = 10;

        ResponseEntity<EventsResponse> responseEntity
                = new ResponseEntity<>(defaultEventsResponse(), OK);
        ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);
        given(restTemplate
                .exchange(uriCaptor.capture(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .willReturn(responseEntity);

        EventsWebRequest webRequest = EventsWebRequest.builder().location(testLocation).category(testCategory).build();
        EventsResponse eventsResponse =
                eventfulClient.getEventfulEvents(webRequest, testPageNo, testPageSize).get();

        verify(restTemplate, times(1))
                .exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class));
        assertThat(eventsResponse.getPageNumber(), equalTo(1));
        assertThat(eventsResponse.getPageSize(), equalTo(1));
        assertThat(eventsResponse.getPageCount(), equalTo(1));
        assertThat(eventsResponse.getEvents().getEvent(), hasSize(1));
        assertThat(eventsResponse.getEvents().getEvent(),
                hasItem(
                        hasProperty("eventId", equalTo("123-abc-001"))
                ));

        String capturedRequestUri = uriCaptor.getValue();
        assertThat(capturedRequestUri, equalTo(
                join(PROTOCOL, TEST_HOST,
                        SEARCH_EVENTS_PATH,
                        "?app_key=", TEST_APPKEY,
                        "&location=", testLocation,
                        "&page_number=", testPageNo,
                        "&page_size=", testPageSize,
                        "&category=", testCategory
                )
        ));
    }

    @Test
    public void buildUriWithPortNumber() {

        String testLocation = "London";
        int testPageNo = 1;
        int testPageSize = 10;

        EventfulClient thisEventfulClient = new EventfulClient(restTemplate, TEST_HOST, TEST_PORT, TEST_APPKEY);

        ResponseEntity<EventsResponse> responseEntity
                = new ResponseEntity<>(defaultEventsResponse(), OK);
        ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);
        given(restTemplate
                .exchange(uriCaptor.capture(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .willReturn(responseEntity);

        EventsWebRequest webRequest = EventsWebRequest.builder().location(testLocation).build();
        thisEventfulClient.getEventfulEvents(webRequest, testPageNo, testPageSize).get();

        verify(restTemplate, times(1))
                .exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class));

        String capturedRequestUri = uriCaptor.getValue();
        assertThat(capturedRequestUri, equalTo(
                join(PROTOCOL, TEST_HOST, ":", TEST_PORT,
                        SEARCH_EVENTS_PATH,
                        "?app_key=", TEST_APPKEY,
                        "&location=", testLocation,
                        "&page_number=", testPageNo,
                        "&page_size=", testPageSize
                )
        ));
    }

    @Test
    public void fallbackWithHttpStatusCodeException() {

        String testErrorBody = "{\"error\":\"some error\"}";
        HttpServerErrorException testException =
                new HttpServerErrorException(INTERNAL_SERVER_ERROR, "Error status", testErrorBody.getBytes(), Charset.defaultCharset());

        EventsWebRequest webRequest = EventsWebRequest.builder().location("London").build();
        Optional<EventsResponse> eventsResponseOptional =
                eventfulClient.fallbackGetEventfulEvents(webRequest, 1, 10, testException);

        assertFalse(eventsResponseOptional.isPresent());

        verify(mockLOGGER, times(1)).error(
                eq("Unsuccessful call of getEventfulEvents with {}, {}, {} with reason: {}"),
                eq(webRequest), eq(1), eq(10), eq(testErrorBody)
        );
    }

    @Test
    public void fallbackWithGeneralException() {

        RuntimeException testException =
                new RuntimeException("Some error");

        EventsWebRequest webRequest = EventsWebRequest.builder().location("London").build();
        Optional<EventsResponse> eventsResponseOptional =
                eventfulClient.fallbackGetEventfulEvents(webRequest, 1, 10, testException);

        assertFalse(eventsResponseOptional.isPresent());

        verify(mockLOGGER, times(1)).error(
                eq("Unsuccessful call of getEventfulEvents with {}, {}, {} with reason: {}"),
                eq(webRequest), eq(1), eq(10), eq(testException)
        );
    }

    @Test
    public void noNullPointersOnNullParams() {

        Optional<EventsResponse> eventsResponseOptional =
                eventfulClient.getEventfulEvents(null, -1, -1);
        assertFalse(eventsResponseOptional.isPresent());
    }

    @Test
    public void unsuccessfulEventsReturn() {

        String testLocation = "London";
        int testPageNo = 1;
        int testPageSize = 10;
        HttpStatus testStatus = BAD_GATEWAY;

        EventsResponse testEventsResponse = null;
        ResponseEntity<EventsResponse> responseEntity
                = new ResponseEntity<>(testEventsResponse, testStatus);
        ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);
        given(restTemplate
                .exchange(uriCaptor.capture(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .willReturn(responseEntity);

        EventsWebRequest webRequest = EventsWebRequest.builder().location(testLocation).build();
        Optional<EventsResponse> eventsResponseOptional =
                eventfulClient.getEventfulEvents(webRequest, testPageNo, testPageSize);

        assertFalse(eventsResponseOptional.isPresent());

        verify(mockLOGGER, times(1)).warn(
                eq("No result from getEventfulEvents with {}, {}, {} with reason: {}"),
                eq(webRequest), eq(testPageNo), eq(testPageSize), eq(testStatus)
        );
    }

    private EventsResponse defaultEventsResponse() {

        Event event = Event.builder()
                .eventId("123-abc-001")
                .build();

        Events events = Events.builder()
                .event(Arrays.asList(event))
                .build();

        return EventsResponse.builder()
                .pageNumber(1)
                .pageSize(1)
                .pageCount(1)
                .events(events)
                .build();
    }
}