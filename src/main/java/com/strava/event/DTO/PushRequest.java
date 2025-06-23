package com.strava.event.DTO;

import com.strava.event.infrastructures.Event;
import lombok.Data;

import java.util.List;

@Data
public class PushRequest {
    List<Event> events;
}
