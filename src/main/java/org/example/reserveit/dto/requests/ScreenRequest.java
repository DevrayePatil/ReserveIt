package org.example.reserveit.dto.requests;
import lombok.Data;
import org.example.reserveit.models.Feature;
import org.example.reserveit.models.ScreenStatus;

import java.util.List;

@Data
public class ScreenRequest {
    private String name;
    private Long theatreId;

    private List<Feature> features;
    private List<Long> seatIds;
    private ScreenStatus status;
}
