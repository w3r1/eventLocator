package com.w3r1.eventLocator.create.facade;

import com.w3r1.eventLocator.create.request.EventfulSaveWebRequest;
import com.w3r1.eventLocator.create.service.EventfulEventsService;
import com.w3r1.eventLocator.create.service.domain.EventLocationsPage;
import com.w3r1.eventLocator.service.EventLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventfulEventsSaveFacadeImpl implements EventfulEventsSaveFacade {

    private static final int PAGE_SIZE = 100;

    private EventfulEventsService eventfulEventsService;
    private EventLocationService eventLocationService;

    @Autowired
    public EventfulEventsSaveFacadeImpl(EventfulEventsService eventfulEventsService,
                                        EventLocationService eventLocationService) {
        this.eventfulEventsService = eventfulEventsService;
        this.eventLocationService = eventLocationService;
    }

    @Override
    public void queryFromEventfulAndSaveToDatastore(EventfulSaveWebRequest request) {

        EventLocationsPage locationsPage = eventfulEventsService.getEventfulEvents(request, 1, PAGE_SIZE);
        eventLocationService.saveAll(locationsPage.getEventLocations());

        for (int i = 2; i <= locationsPage.getPageCount(); i++) {

            locationsPage = eventfulEventsService.getEventfulEvents(request, i, PAGE_SIZE);
            eventLocationService.saveAll(locationsPage.getEventLocations());
        }
    }
}
