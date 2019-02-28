package com.w3r1.eventLocator.repository;

import com.w3r1.eventLocator.model.EventLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EventLocationRepository extends ElasticsearchRepository<EventLocation, Long> {

    @Query("{" +
           "    \"multi_match\": {" +
           "        \"query\": \"?0\"," +
           "        \"fields\": [ \"title\", \"description\", \"category\", \"cityName\" ]," +
           "        \"type\": \"cross_fields\"" +
           "    }" +
           "}")
    Page<EventLocation> searchCrossThroughTitleDescriptionCategoryAndCity(String crossfieldSearch, Pageable page);
}
