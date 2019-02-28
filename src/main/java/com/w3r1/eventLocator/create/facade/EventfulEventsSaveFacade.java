package com.w3r1.eventLocator.create.facade;

import com.w3r1.eventLocator.create.request.EventfulSaveWebRequest;

public interface EventfulEventsSaveFacade {

    void queryFromEventfulAndSaveToDatastore(EventfulSaveWebRequest request);
}
