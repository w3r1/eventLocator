package com.w3r1.eventLocator.create.service;

import com.w3r1.eventLocator.create.request.EventfulSaveWebRequest;
import com.w3r1.eventLocator.create.service.domain.EventLocationsPage;

public interface EventfulEventsService {

    EventLocationsPage getEventfulEvents(EventfulSaveWebRequest request, int pageNo, int pageSize);
}
