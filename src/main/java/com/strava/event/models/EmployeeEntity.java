/**
 * We are trying to mimic CQRS design pattern here
 * This database is meant to be a projection
 * i.e the read side database
 */

package com.strava.event.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {
    @Id
    private int id;
    private String name;
    private Date timestamp;
}
