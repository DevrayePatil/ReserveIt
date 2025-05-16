package org.example.reserveit.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "theatres")
public class Theatre extends BaseModel {
    private String name;
    private String address;

    @OneToMany(mappedBy = "theatre")
    @JsonManagedReference
    private List<Screen> screens;
}
