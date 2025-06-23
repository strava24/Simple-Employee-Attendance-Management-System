package com.strava.event.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceEntity {
    @Id
    private int id;
    private String state;
    private Date timestamp;
}
