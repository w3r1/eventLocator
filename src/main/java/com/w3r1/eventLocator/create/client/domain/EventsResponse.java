
package com.w3r1.eventLocator.create.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventsResponse {

    @JsonProperty
    private Events events;

    @JsonProperty("first_item")
    private Object firstItem;

    @JsonProperty("last_item")
    private Object lastItem;

    @JsonProperty("page_count")
    private String pageCount;

    @JsonProperty("page_items")
    private Object pageItems;

    @JsonProperty("page_number")
    private String pageNumber;

    @JsonProperty("page_size")
    private String pageSize;

    @JsonProperty("search_time")
    private String searchTime;

    @JsonProperty("total_items")
    private String totalItems;
}
