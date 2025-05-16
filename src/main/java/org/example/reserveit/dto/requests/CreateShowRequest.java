package org.example.reserveit.dto.requests;

import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.dto.SeatPrice;
import org.example.reserveit.models.Feature;

import java.util.Date;
import java.util.List;


@Getter
@Setter
public class CreateShowRequest {
    private long userId;
    private long movieId;
    private long screenId;
    private Date startTime;
    private Date endTime;
    private List<SeatPrice> pricingConfig;
    private List<Feature> features;

}
