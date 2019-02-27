package com.w3r1.eventLocator.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.w3r1.eventLocator.create.client.EventfulClient;
import com.w3r1.eventLocator.create.client.domain.EventsResponse;
import com.w3r1.eventLocator.create.request.EventsWebRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.w3r1.eventLocator.create.client.EventfulClient.SEARCH_EVENTS_PATH;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:testapplication.properties")
public class EventfulClientIntegrationTest {

    private static final String BASE_PATH = EventfulClientIntegrationTest.class.getResource("../../../..").getPath();
    private static final String JSON_RESPONSE_FILE = BASE_PATH + "eventsExampleResponse.json";

    @Autowired
    private EventfulClient eventfulClient;

    @Value("${eventful.server.host}")
    private String eventfulServerHost;

    @Value("${eventful.server.port}")
    private int eventfulServerPort;

    @Value("${eventful.server.auth.appkey}")
    private String eventfulAppkey;

    @Value("${hystrix.command.getEventfulEvents.execution.isolation.thread.timeoutInMilliseconds}")
    private int hystrixTimeout;

    private WireMockServer wireMockServer;

    @Before
    public void setup() {
        wireMockServer = new WireMockServer(wireMockConfig().port(eventfulServerPort));
        wireMockServer.start();
        WireMock.configureFor(eventfulServerHost, eventfulServerPort);
    }

    @After
    public void cleanup() {
        wireMockServer.stop();
    }

    @Test
    public void parseEventsJsonSuccessfully() throws IOException {

        stubEventfulEventsCall();

        EventsWebRequest londonEventsRequest = EventsWebRequest.builder().location("London").build();
        Optional<EventsResponse> eventsResponseOptional = eventfulClient.getEventfulEvents(londonEventsRequest, 1, 2);
        EventsResponse eventsResponse = eventsResponseOptional.get();

        assertThat(eventsResponse.getPageNumber(), equalTo(1));
        assertThat(eventsResponse.getPageSize(), equalTo(2));
        assertThat(eventsResponse.getEvents().getEvent(), hasSize(2));
        assertThat(eventsResponse.getEvents().getEvent(),
                hasItem(
                        hasProperty("id", equalTo("E0-001-122804325-0"))
                ));
        assertThat(eventsResponse.getEvents().getEvent(),
                hasItem(
                        hasProperty("id", equalTo("E0-001-123723417-3"))
                ));
    }

    @Test
    public void circuitBreakerJumpsInAndHandlesByFallback() throws IOException {

        WireMock.setGlobalFixedDelay(hystrixTimeout + 100);
        stubEventfulEventsCall();

        EventsWebRequest londonEventsRequest = EventsWebRequest.builder().location("London").build();
        Optional<EventsResponse> eventsResponseOptional = eventfulClient.getEventfulEvents(londonEventsRequest, 1, 2);

        assertFalse(eventsResponseOptional.isPresent());
    }

    private void stubEventfulEventsCall() throws IOException {

        String url = SEARCH_EVENTS_PATH
                + "?app_key=" + eventfulAppkey
                + "&location=London"
                + "&page_number=1"
                + "&page_size=2";

        stubFor(get(urlEqualTo(url))
                .willReturn(aResponse()
                        // Original URL doesn't return application/json
                        .withHeader("Content-Type", "text/javascript; charset=utf-8")
                        .withStatus(OK.value())
                        .withBody(Files.readAllBytes(Paths.get(JSON_RESPONSE_FILE)))
                ));
    }
}