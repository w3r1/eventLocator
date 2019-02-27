
package com.w3r1.eventLocator.create.client.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import java.util.Date;

@Generated("net.hexar.json2pojo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @JsonProperty("all_day")
    private String allDay;

    @JsonProperty("calendar_count")
    private Object calendarCount;

    @JsonProperty
    private Object calendars;

    @JsonProperty("city_name")
    private String cityName;

    @JsonProperty("comment_count")
    private Object commentCount;

    @JsonProperty("country_abbr")
    private String countryAbbr;

    @JsonProperty("country_abbr2")
    private String countryAbbr2;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    @JsonProperty
    private String description;

    @JsonProperty("geocode_type")
    private String geocodeType;

    @JsonProperty
    private Object going;

    @JsonProperty("going_count")
    private Object goingCount;

    @JsonProperty
    private Object groups;

    @JsonProperty
    private String id;

    @JsonProperty
    private Image image;

    @JsonProperty
    private String latitude;

    @JsonProperty("link_count")
    private Object linkCount;

    @JsonProperty
    private String longitude;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modified;

    @JsonProperty("olson_path")
    private String olsonPath;

    @JsonProperty
    private String owner;

    @JsonProperty
    private Object performers;

    @JsonProperty("postal_code")
    private Object postalCode;

    @JsonProperty
    private String privacy;

    @JsonProperty("recur_string")
    private Object recurString;

    @JsonProperty("region_abbr")
    private String regionAbbr;

    @JsonProperty("region_name")
    private String regionName;

    @JsonProperty("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonProperty("stop_time")
    private Object stopTime;

    @JsonProperty
    private String title;

    @JsonProperty("tz_city")
    private Object tzCity;

    @JsonProperty("tz_country")
    private Object tzCountry;

    @JsonProperty("tz_id")
    private Object tzId;

    @JsonProperty("tz_olson_path")
    private Object tzOlsonPath;

    @JsonProperty
    private String url;

    @JsonProperty("venue_address")
    private String venueAddress;

    @JsonProperty("venue_display")
    private String venueDisplay;

    @JsonProperty("venue_id")
    private String venueId;

    @JsonProperty("venue_name")
    private String venueName;

    @JsonProperty("venue_url")
    private String venueUrl;

    @JsonProperty("watching_count")
    private Object watchingCount;
}
