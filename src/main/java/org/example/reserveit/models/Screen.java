package org.example.reserveit.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "screens")
public class Screen extends BaseModel {
    private String name;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    @JsonBackReference
    private Theatre theatre;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Feature> features;

    @OneToMany(mappedBy = "screen")
    @JsonManagedReference
    private List<Seat> seats;

    @Enumerated(EnumType.STRING)
    private ScreenStatus status;
}
