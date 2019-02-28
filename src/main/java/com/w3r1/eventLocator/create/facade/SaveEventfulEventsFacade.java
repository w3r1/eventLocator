package com.w3r1.eventLocator.create.facade;

import com.w3r1.eventLocator.create.request.EventsWebRequest;

public interface SaveEventfulEventsFacade {

    void queryFromEventfulAndSaveToDatastore(EventsWebRequest request);
}
