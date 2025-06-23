package com.strava.event.events;

import com.strava.event.infrastructures.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreated implements Event {
    private int id;
    private int eventId;
    private String name;
    private Date timestamp;
    private String type;

    @Override
    public String getType() {
        return "EmployeeCreated";
    }
}
