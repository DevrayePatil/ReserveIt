package org.example.reserveit.dto.requests;

import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.models.Feature;
import org.example.reserveit.models.ScreenStatus;

import java.util.List;

@Getter
@Setter
public class UpdateScreenRequest {
    private String name;
    private Long theaterId;
    private List<Feature> features;
    private List<Long> addSeatIds;
    private List<Long> removeSeatIds;
    private ScreenStatus status;
}
