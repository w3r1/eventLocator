package com.w3r1.eventLocator.create.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.w3r1.eventLocator.create.client.domain.EventsResponse;
import com.w3r1.eventLocator.create.request.EventsWebRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class EventfulClient {

    public static final String PROTOCOL = "http://";
    public static final String SEARCH_EVENTS_PATH = "/json/events/search";
    public static final String HYSTRIX_GROUP_KEY_GET_EVENTS = "EventsRetrieval";
    public static final String HYSTRIX_POOL_KEY_GET_EVENTS = "getEventfulEvents";
    public static final String HYSTRIX_COMMAND_KEY_GET_EVENTS = "getEventfulEvents";

    private static final Logger LOGGER = LogManager.getLogger();

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    private final String appKey;
    private final String eventfulEventsUrl;

    @Autowired
    public EventfulClient(RestTemplate restTemplate,
                          @Value("${eventful.server.host}") String serverHost,
                          @Value("${eventful.server.port:}") String serverPort,
                          @Value("${eventful.server.auth.appkey}") String appKey) {

        this.restTemplate = restTemplate;
        this.headers = new HttpHeaders();
        this.headers.set("Accept", APPLICATION_JSON_VALUE);

        this.appKey = appKey;

        String path = PROTOCOL + serverHost + (isNotEmpty(serverPort) ? ":" + serverPort : EMPTY);
        this.eventfulEventsUrl = path + SEARCH_EVENTS_PATH;
    }

    @HystrixCommand(
            groupKey = HYSTRIX_GROUP_KEY_GET_EVENTS,
            threadPoolKey = HYSTRIX_POOL_KEY_GET_EVENTS,
            commandKey = HYSTRIX_COMMAND_KEY_GET_EVENTS,
            fallbackMethod = "fallbackGetEventfulEvents"
    )
    public Optional<EventsResponse> getEventfulEvents(EventsWebRequest request, int pageNo, int pageSize) {

        boolean isCategoryGiven = isNotBlank(request.getCategory());
        UriComponentsBuilder eventsUrlBuilder = UriComponentsBuilder.fromHttpUrl(eventfulEventsUrl)
                .queryParam("app_key", appKey)
                .queryParam("location", request.getLocation())
                .queryParam("page_number", pageNo)
                .queryParam("page_size", pageSize);
        if (isCategoryGiven) {
            eventsUrlBuilder.queryParam("category", request.getCategory());
        }

        final ResponseEntity<EventsResponse> responseEntity =
                restTemplate.exchange(eventsUrlBuilder.toUriString(), GET, new HttpEntity<>(headers), EventsResponse.class);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            LOGGER.warn("No result from getEventfulEvents with {}, {}, {} with reason: {}",
                    request, pageNo, pageSize, responseEntity.getStatusCode());
        }

        return Optional.ofNullable(responseEntity.getBody());
    }

    @SuppressWarnings("unused")
    public Optional<EventsResponse> fallbackGetEventfulEvents(EventsWebRequest request, int pageNo, int pageSize, Throwable exception) {

        if(exception instanceof HttpStatusCodeException) {
            HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) exception;
            LOGGER.error("Unsuccessful call of getEventfulEvents with {}, {}, {} with reason: {}",
                    request, pageNo, pageSize, httpStatusCodeException.getResponseBodyAsString());
        } else {
            LOGGER.error("Unsuccessful call of getEventfulEvents with {}, {}, {} with reason: {}",
                    request, pageNo, pageSize, exception);
        }

        return Optional.empty();
    }
}