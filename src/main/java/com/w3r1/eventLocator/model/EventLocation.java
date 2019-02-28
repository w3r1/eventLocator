package com.w3r1.eventLocator.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

@Data
@Builder
@Document(indexName = "eventlocation_index", type = "eventlocation")
public class EventLocation implements Serializable {

    private static final long serialVersionUID = -2654654765387231199L;

    @Id
    private Long id;

    private String eventId;

    private String title;

    private String description;

    private String category;

    private String venueId;

    private String venueName;

    private String venueUrl;

    private String venueAddress;

    private String cityName;

    private String regionName;

    private String countryName;

    private String url;

    private Date startTime;

    private Date created;

    private Date modified;

    private String imgUrl;

    private Integer imgWidth;

    private Integer imgHeight;

    // FIXME: ID generator must work through @Id and/or @GeneratedValue; ID generation bug inside of spring-data-elastic, please read FIXME in root folder
    public EventLocation() {
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
}