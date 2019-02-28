package com.w3r1.eventLocator.retrieve.facade;

import com.w3r1.eventLocator.model.EventLocation;
import com.w3r1.eventLocator.service.EventLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventRetrievalFacadeImpl implements EventRetrievalFacade {

    private EventLocationService eventLocationService;

    @Autowired
    public EventRetrievalFacadeImpl(EventLocationService eventLocationService) {
        this.eventLocationService = eventLocationService;
    }

    @Override
    public List<EventLocation> getEventLocations(String searchPhrase, Pageable page) {

        return eventLocationService.searchThroughTitleDescriptionCategoryAndCity(searchPhrase, page)
                .stream()
                .collect(Collectors.toList());
    }
}
