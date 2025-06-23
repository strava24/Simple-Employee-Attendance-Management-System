package com.strava.event.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.strava.event.DTO.PushRequest;
import com.strava.event.services.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventController {

    private EventService eventService;

    @PostMapping
    public ResponseEntity<String> push(@RequestBody PushRequest request) throws JsonProcessingException {
        boolean isSuccessful = eventService.push(request);

        if (isSuccessful) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

}
