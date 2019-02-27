package com.w3r1.eventLocator.create.service;

import com.w3r1.eventLocator.create.request.EventsWebRequest;
import com.w3r1.eventLocator.create.service.domain.EventLocationsPage;

public interface EventfulEventsService {

    EventLocationsPage getEventfulEvents(EventsWebRequest request, int pageNo, int pageSize);
}
