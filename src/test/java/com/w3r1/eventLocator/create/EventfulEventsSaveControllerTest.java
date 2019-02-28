package com.w3r1.eventLocator.create;

import com.w3r1.eventLocator.create.facade.EventfulEventsSaveFacade;
import com.w3r1.eventLocator.create.request.EventfulSaveWebRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EventfulEventsSaveControllerTest {

    @Mock
    private EventfulEventsSaveFacade eventfulEventsSaveFacade;

    private EventfulEventsSaveController eventfulEventsSaveController;

    @Before
    public void setup() {
        eventfulEventsSaveController = new EventfulEventsSaveController(eventfulEventsSaveFacade);
    }

    @Test
    public void transferFromEventfulAndSaveInDatastore() {

        EventfulSaveWebRequest testRequest = EventfulSaveWebRequest.builder().location("London").build();
        eventfulEventsSaveController.transferFromEventfulAndSaveInDatastore(testRequest);

        verify(eventfulEventsSaveFacade, times(1))
                .queryFromEventfulAndSaveToDatastore(eq(testRequest));
    }

    @Test
    public void noNullPointersOnNullParams() {

        eventfulEventsSaveController.transferFromEventfulAndSaveInDatastore(null);
    }
}