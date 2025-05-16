package org.example.reserveit.dto.requests;

import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.dto.SeatPrice;
import org.example.reserveit.models.Feature;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UpdateShowRequest {
    private Long userId;
    private Long movieId;
    private Long screenId;
    private Date startTime;
    private Date endTime;
    private List<Feature> features;
    private List<SeatPrice> pricingConfig;
}
