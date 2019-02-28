package com.w3r1.eventLocator.create;

import com.w3r1.eventLocator.create.facade.EventfulEventsSaveFacade;
import com.w3r1.eventLocator.create.request.EventfulSaveWebRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/eventlocator")
public class EventfulEventsSaveController {

    private EventfulEventsSaveFacade eventfulEventsSaveFacade;

    @Autowired
    public EventfulEventsSaveController(EventfulEventsSaveFacade eventfulEventsSaveFacade) {
        this.eventfulEventsSaveFacade = eventfulEventsSaveFacade;
    }

    @RequestMapping(value = "/eventful/transfer", method = POST)
    public ResponseEntity transferFromEventfulAndSaveInDatastore(
            @RequestBody EventfulSaveWebRequest request) {

        eventfulEventsSaveFacade.queryFromEventfulAndSaveToDatastore(request);

        return new ResponseEntity<>(OK);
    }
}
