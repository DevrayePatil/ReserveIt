package org.example.reserveit.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "movies")
public class Movie extends BaseModel {
    private String name;
    private String description;
}
