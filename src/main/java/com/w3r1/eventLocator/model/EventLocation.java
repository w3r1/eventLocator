package com.w3r1.eventLocator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Data
@Builder
@Entity
@Document(indexName = "eventlocation_index", type = "eventlocation")
public class EventLocation implements Serializable {

    private static final long serialVersionUID = -2654654765387231199L;

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @JsonProperty
    private String eventId;

    @JsonProperty
    private String title;

    @JsonProperty
    private String description;

    @JsonProperty
    private String category;

    @JsonProperty
    private String venueId;

    @JsonProperty
    private String venueName;

    @JsonProperty
    private String venueUrl;

    @JsonProperty
    private String venueAddress;

    @JsonProperty
    private String cityName;

    @JsonProperty
    private String regionName;

    @JsonProperty
    private String countryName;

    @JsonProperty
    private String url;

    @JsonProperty
    private Date startTime;

    @JsonProperty
    private Date created;

    @JsonProperty
    private Date modified;

    @JsonProperty
    private String imgUrl;

    @JsonProperty
    private Integer imgWidth;

    @JsonProperty
    private Integer imgHeight;

    public EventLocation() {
        // FIXME: ID generator must work through @Id and/or @GeneratedValue; ID generation bug inside of spring-data-elastic, please read FIXME in root folder
        this.id = new Random().nextLong();
    }

    public EventLocation(Long id, String eventId, String title, String description, String category, String venueId, String venueName, String venueUrl, String venueAddress, String cityName, String regionName, String countryName, String url, Date startTime, Date created, Date modified, String imgUrl, Integer imgWidth, Integer imgHeight) {
        this.id = new Random().nextLong();
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.venueId = venueId;
        this.venueName = venueName;
        this.venueUrl = venueUrl;
        this.venueAddress = venueAddress;
        this.cityName = cityName;
        this.regionName = regionName;
        this.countryName = countryName;
        this.url = url;
        this.startTime = startTime;
        this.created = created;
        this.modified = modified;
        this.imgUrl = imgUrl;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventLocation that = (EventLocation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(category, that.category) &&
                Objects.equals(venueId, that.venueId) &&
                Objects.equals(venueName, that.venueName) &&
                Objects.equals(venueUrl, that.venueUrl) &&
                Objects.equals(venueAddress, that.venueAddress) &&
                Objects.equals(cityName, that.cityName) &&
                Objects.equals(regionName, that.regionName) &&
                Objects.equals(countryName, that.countryName) &&
                Objects.equals(url, that.url) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(created, that.created) &&
                Objects.equals(modified, that.modified) &&
                Objects.equals(imgUrl, that.imgUrl) &&
                Objects.equals(imgWidth, that.imgWidth) &&
                Objects.equals(imgHeight, that.imgHeight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventId, title, description, category, venueId, venueName, venueUrl, venueAddress, cityName, regionName, countryName, url, startTime, created, modified, imgUrl, imgWidth, imgHeight);
    }
}