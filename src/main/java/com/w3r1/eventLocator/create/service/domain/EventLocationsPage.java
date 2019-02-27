package com.w3r1.eventLocator.create.service.domain;

import com.w3r1.eventLocator.entity.EventLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventLocationsPage {

    private Integer pageNumber;

    private Integer pageCount;

    private List<EventLocation> eventLocations;
}
