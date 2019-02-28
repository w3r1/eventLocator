package com.w3r1.eventLocator.service;

import com.w3r1.eventLocator.model.EventLocation;
import com.w3r1.eventLocator.repository.EventLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventLocationServiceImpl implements EventLocationService {

    private EventLocationRepository eventLocationRepository;

    @Autowired
    public EventLocationServiceImpl(EventLocationRepository eventLocationRepository) {
        this.eventLocationRepository = eventLocationRepository;
    }

    @Override
    public Page<EventLocation> searchThroughTitleDescriptionCategoryAndCity(String searchPhrase, Pageable page) {
        return eventLocationRepository.searchThroughTitleDescriptionCategoryAndCity(searchPhrase, page);
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
