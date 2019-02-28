package com.w3r1.eventLocator.service;

import com.w3r1.eventLocator.model.EventLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventLocationService {

    Page<EventLocation> searchThroughTitleDescriptionCategoryAndCity(String searchPhrase, Pageable page);

    void save(EventLocation eventLocation);

    void saveAll(List<EventLocation> eventLocations);
}
