/**
 * This class is the Event Store where we basically store all of the events in sequential order
 */

package com.strava.event.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {
    @Id
    private int id;
    private String eventType;
    private Date timestamp;
    private String payload;
}
