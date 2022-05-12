package com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder;

import com.karol.offerservice.offerMenager.api.dto.TimePriceDto;
import com.karol.offerservice.offerMenager.data.entity.Frame;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetailBikeInfoView {
    private Long id;
    private String name;
    private byte[] image;
    private int ratingNumber;
    private BigDecimal rating;
    private String bikeType;
    private String offerType;
    private String bikeOfferType;
    private List<TimePriceDto> timePriceDtoList;
    private Set<FrameView> frames;
    private BigDecimal guidePrice;


    DetailBikeInfoView(DetailBikeInfoViewBuilder builder) {
        this.id = builder.getId();
        this.name = builder.getName();
        this.image = builder.getImage();
        this.ratingNumber = builder.getRatingNumber();
        this.rating = builder.getRating();
        this.bikeType = builder.getBikeType();
        this.offerType = builder.getOfferType();
        this.bikeOfferType = builder.getBikeOfferType();
        this.guidePrice = builder.getGuidePrice();
        this.frames = builder.getFrames();
        this.timePriceDtoList = builder.getTimePriceDtoList();

    }
}

