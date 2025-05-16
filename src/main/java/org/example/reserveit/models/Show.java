package org.example.reserveit.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "shows")
public class Show extends BaseModel {
    @ManyToOne
    private Screen screen;
    @ManyToOne
    private Movie movie;
    private Date startTime;
    private Date endTime;
    @OneToMany(mappedBy = "show")
    private List<ShowSeat> seats;
    @OneToMany(mappedBy = "show")
    private List<ShowSeatType> seatTypes;
    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    private List<Feature> features;
}
