
package com.w3r1.eventLocator.create.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@Data
public class Image {

    @JsonProperty
    private Object caption;

    @JsonProperty
    private String height;

    @JsonProperty
    private Medium medium;

    @JsonProperty
    private Small small;

    @JsonProperty
    private Thumb thumb;

    @JsonProperty
    private String url;

    @JsonProperty
    private String width;
}
