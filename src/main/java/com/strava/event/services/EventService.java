package com.strava.event.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.strava.event.DTO.PushRequest;
import com.strava.event.events.AttendanceRecorded;
import com.strava.event.events.EmployeeCreated;
import com.strava.event.events.EmployeeUpdated;
import com.strava.event.infrastructures.Event;
import com.strava.event.models.AttendanceEntity;
import com.strava.event.models.EmployeeEntity;
import com.strava.event.models.EventEntity;
import com.strava.event.persistance.AttendanceRepository;
import com.strava.event.persistance.EmployeeRepository;
import com.strava.event.persistance.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventService {

    private EventRepository eventRepository;
    private EmployeeRepository employeeRepository;
    private AttendanceRepository attendanceRepository;
    private ObjectMapper mapper;

    // A request is meant to carry multiple events, so iterating through the events from the request
    public boolean push(PushRequest request) throws JsonProcessingException {
        for (Event event : request.getEvents()) {
            // Creating an entity out of the event we just received
            EventEntity eventEntity = new EventEntity(event.getEventId(), event.getType(), event.getTimestamp(), mapper.writeValueAsString(event));

            // Before we actually save the events to the event store it is mandatory to do the validations
            // In this case for example an employee cannot CLOCK_IN (attendance mark) if he is not registered on the system
            if (event instanceof AttendanceRecorded) {
                AttendanceRecorded attendanceRecorded = (AttendanceRecorded) event;
                // Checking if the employee is already registered
                Optional<EmployeeEntity> emp = findEmployeeById(attendanceRecorded.getId());

                if (emp.isEmpty()) {
                    System.out.println("Employee with Id " + attendanceRecorded.getId() + " does not exist!");
                    return false;
                }

                // Similarly we can also restrict the update function if thr employee is not created yet
            }

            // Saving the events on Event Store
            eventRepository.save(eventEntity);

            // After pushing everything to the write side of the database, we need can manipulate the events to
            if (event instanceof EmployeeUpdated) {
                EmployeeUpdated employeeUpdated = (EmployeeUpdated) event;
                EmployeeEntity emp = new EmployeeEntity(employeeUpdated.getId(), employeeUpdated.getName(), eventEntity.getTimestamp());
                employeeRepository.save(emp);
            } else if (event instanceof EmployeeCreated) {
                EmployeeCreated employeeCreated = (EmployeeCreated) event;
                EmployeeEntity emp = new EmployeeEntity(employeeCreated.getId(), employeeCreated.getName(), eventEntity.getTimestamp());
                employeeRepository.save(emp);
            } else if (event instanceof AttendanceRecorded) {
                AttendanceRecorded attendanceRecorded= (AttendanceRecorded) event;
                AttendanceEntity attendance = new AttendanceEntity(attendanceRecorded.getId(), attendanceRecorded.getState(), attendanceRecorded.getTimestamp());
                attendanceRepository.save(attendance);
            }
        }
        return true;
    }

    public Optional<EmployeeEntity> findEmployeeById(int id) {
        return employeeRepository.findById(id);
    }
}