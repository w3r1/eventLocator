package com.w3r1.eventLocator.entity;

import lombok.Data;

import java.util.Date;

@Data
public class EventLocation {

    private String title;

    private String description;

    private String venueId;

    private String venueName;

    private String venueUrl;

    private String venueAddress;

    private String cityName;

    private String regionName;

    private String countryName;

    private String url;

    private String id;

    private Date startTime;

    private Date created;

    private Date modified;

    private String imgUrl;

    private Integer imgWidth;

    private Integer imgHeight;
}