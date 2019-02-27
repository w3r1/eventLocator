package com.w3r1.eventLocator.create.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class EventsWebRequest {

    @JsonProperty
    private String location;

    @JsonProperty
    private String category;
}

