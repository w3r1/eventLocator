package com.w3r1.eventLocator.retrieve;

import com.w3r1.eventLocator.model.EventLocation;
import com.w3r1.eventLocator.retrieve.facade.EventRetrievalFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/eventlocator")
public class EventRetrievalController {

    private EventRetrievalFacade eventRetrievalFacade;

    @Autowired
    public EventRetrievalController(EventRetrievalFacade eventRetrievalFacade) {
        this.eventRetrievalFacade = eventRetrievalFacade;
    }

    @RequestMapping(value = "/events", method = GET)
    public ResponseEntity<List<EventLocation>> getEvents(
            @RequestParam(value = "searchPhrase") String searchPhrase,
            Pageable page) {

        List<EventLocation> eventLocations = eventRetrievalFacade.getEventLocations(searchPhrase, page);

        return new ResponseEntity<>(eventLocations, OK);
    }
}
