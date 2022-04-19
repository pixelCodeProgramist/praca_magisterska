package com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder;

import com.karol.offerservice.offerMenager.api.dto.TimePriceDto;
import com.karol.offerservice.offerMenager.data.entity.Frame;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
public class DetailBikeInfoViewBuilder {

    private int id;
    private String name;
    private byte[] image;
    private int ratingNumber;
    private BigDecimal rating;
    private String bikeType;
    private String offerType;
    private String bikeOfferType;

    private BigDecimal guidePrice;

    private List<TimePriceDto> timePriceDtoList;

    private Set<String> frames;
    public DetailBikeInfoViewBuilder() {
    }

    public DetailBikeInfoViewBuilder frames(Set<String> frames) {
        this.frames = frames;
        return this;
    }

    public DetailBikeInfoViewBuilder id(int id) {
        this.id = id;
        return this;
    }

    public DetailBikeInfoViewBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DetailBikeInfoViewBuilder image(byte[] image) {
        this.image = image;
        return this;
    }

    public DetailBikeInfoViewBuilder ratingNumber(int ratingNumber) {
        this.ratingNumber = ratingNumber;
        return this;
    }

    public DetailBikeInfoViewBuilder rating(BigDecimal rating) {
        this.rating = rating;
        return this;
    }

    public DetailBikeInfoViewBuilder bikeType(String bikeType) {
        this.bikeType = bikeType;
        return this;
    }

    public DetailBikeInfoViewBuilder offerType(String offerType) {
        this.offerType = offerType;
        return this;
    }

    public DetailBikeInfoViewBuilder bikeOfferType(String bikeOfferType) {
        this.bikeOfferType = bikeOfferType;
        return this;
    }

    public DetailBikeInfoViewBuilder timePriceDtoList(List<TimePriceDto> timePriceDtoList) {
        this.timePriceDtoList = timePriceDtoList;
        return this;
    }

    public DetailBikeInfoView build() {

        return new DetailBikeInfoView(this);
    }

    public DetailBikeInfoViewBuilder guidePrice(BigDecimal guidePrice) {
        this.guidePrice = guidePrice;
        return this;
    }
}
