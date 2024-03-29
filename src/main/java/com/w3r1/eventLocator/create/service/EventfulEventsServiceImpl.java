package com.w3r1.eventLocator.create.service;

import com.w3r1.eventLocator.create.client.EventfulClient;
import com.w3r1.eventLocator.create.client.domain.EventsResponse;
import com.w3r1.eventLocator.create.request.EventfulSaveWebRequest;
import com.w3r1.eventLocator.create.service.domain.EventLocationsPage;
import com.w3r1.eventLocator.model.EventLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.vavr.API.Try;

@Service
public class EventfulEventsServiceImpl implements EventfulEventsService {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final DozerBeanMapper BEAN_MAPPER = new DozerBeanMapper();

    private EventfulClient eventfulClient;

    @Autowired
    public EventfulEventsServiceImpl(EventfulClient eventfulClient) {
        this.eventfulClient = eventfulClient;
    }

    @Override
    public EventLocationsPage getEventfulEvents(final EventfulSaveWebRequest request, int pageNo, int pageSize) {

        final Optional<EventsResponse> eventfulEvents = eventfulClient.getEventfulEvents(request, pageNo, pageSize);
        return eventfulEvents.map(eventsResponse -> {

            EventLocationsPage eventLocationsPage =
                    Try(() -> {
                        EventLocationsPage page = new EventLocationsPage();
                        BEAN_MAPPER.map(eventsResponse, page);
                        return page;
                    })
                    .onFailure(ex -> LOGGER.warn("Bean mapping error eventsResponse->eventLocationsPage with reason: {}", ex))
                    .getOrElse(() -> EventLocationsPage.builder().pageCount(1).pageNumber(1).build());

            List<EventLocation> eventLocations = eventsResponse.getEvents().getEvent()
                    .parallelStream()
                    .map(event -> {

                        EventLocation eventLocation = Try(() -> {
                            EventLocation location = EventLocation.builder().category(request.getCategory()).build();
                            BEAN_MAPPER.map(event, location);
                            if (event.getImage() != null) {
                                BEAN_MAPPER.map(event.getImage(), location);
                            }
                            return location;
                        })
                        .onFailure(ex -> LOGGER.warn("Bean mapping error event->eventLocation with reason: {}", ex))
                        .getOrElse(() -> null);

                        return eventLocation;
                    })
                    .filter(event -> event != null)
                    .collect(Collectors.toList());

            eventLocationsPage.setEventLocations(eventLocations);
            return eventLocationsPage;
        }).orElse(EventLocationsPage.builder().pageCount(0).pageNumber(0).build());
    }
}
