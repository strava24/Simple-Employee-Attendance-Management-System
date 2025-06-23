package com.strava.event.infrastructures;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.strava.event.events.AttendanceRecorded;
import com.strava.event.events.EmployeeCreated;
import com.strava.event.events.EmployeeUpdated;

import java.util.Date;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,      // Use the logical name to identify the subtype
        include = JsonTypeInfo.As.PROPERTY, // Include the type info as a property
        property = "type"               // The name of the property in JSON that contains the type info
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmployeeCreated.class, name = "EmployeeCreated"), // Map "EmployeeCreated" string to EmployeeCreated.class
        @JsonSubTypes.Type(value = EmployeeUpdated.class, name = "EmployeeUpdated"), // Map "EmployeeUpdated" string to EmployeeUpdated.class
        @JsonSubTypes.Type(value = AttendanceRecorded.class, name = "AttendanceRecorded") // Map "AttendanceRecorded" string to AttendanceRecorded.class
})
public interface Event {
    int getEventId();
    String getType();
    Date getTimestamp();
}
