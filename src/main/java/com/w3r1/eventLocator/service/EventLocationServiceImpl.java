package com.w3r1.eventLocator.service;

import com.w3r1.eventLocator.model.EventLocation;
import com.w3r1.eventLocator.repository.EventLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class EventLocationServiceImpl implements EventLocationService {

    private EventLocationRepository eventLocationRepository;

    @Autowired
    public EventLocationServiceImpl(EventLocationRepository eventLocationRepository) {
        this.eventLocationRepository = eventLocationRepository;
    }

    @Override
    public Page<EventLocation> searchCrossThroughTitleDescriptionCategoryAndCity(String crossfieldSearch, Pageable page) {
        return eventLocationRepository.searchCrossThroughTitleDescriptionCategoryAndCity(crossfieldSearch, page);
    }

    @Override
    public void save(EventLocation eventLocation) {
        eventLocationRepository.save(eventLocation);
    }

    @Override
    public void saveAll(List<EventLocation> eventLocations) {
        eventLocationRepository.saveAll(eventLocations);
    }
}
