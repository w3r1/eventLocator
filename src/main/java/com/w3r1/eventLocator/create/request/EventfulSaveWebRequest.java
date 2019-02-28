package com.w3r1.eventLocator.create.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EventfulSaveWebRequest {

    @JsonProperty
    private String location;

    @JsonProperty
    private String category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventfulSaveWebRequest that = (EventfulSaveWebRequest) o;
        return Objects.equals(location, that.location) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, category);
    }
}

