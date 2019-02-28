package com.w3r1.eventLocator.retrieve.facade;

import com.w3r1.eventLocator.model.EventLocation;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventRetrievalFacade {

    List<EventLocation> getEventLocations(String searchPhrase, Pageable page);
}
