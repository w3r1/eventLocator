
package com.w3r1.eventLocator.create.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Events {

    @JsonProperty
    private List<Event> event;
}
