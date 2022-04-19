package com.karol.offerservice.offerMenager.business.service.detailBikeFactory;

import com.karol.offerservice.offerMenager.business.service.generalBikeFactory.ClassicGeneralBikeGeneralOffer;
import com.karol.offerservice.offerMenager.business.service.generalBikeFactory.ElectricGeneralBikeGeneralOffer;
import com.karol.offerservice.offerMenager.business.service.generalBikeFactory.IGeneralBikeGeneralOffer;
import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import com.karol.offerservice.offerMenager.data.entity.ElectricBike;
import com.karol.offerservice.offerMenager.data.repository.*;

public class DetailBikeFactory {
    private ClassicBike classicBike;

    private BikeTypeRepo bikeTypeRepo;

    private ElectricBike electricBike;
    private ElectricBikePriceRepo electricBikePriceRepo;

    private ServiceRepo serviceRepo;


    public DetailBikeFactory(ClassicBike classicBike, BikeTypeRepo bikeTypeRepo, ServiceRepo serviceRepo) {
        this.classicBike = classicBike;
        this.bikeTypeRepo = bikeTypeRepo;
        this.serviceRepo = serviceRepo;
    }

    public DetailBikeFactory(ElectricBike electricBike, ElectricBikePriceRepo electricBikePriceRepo, ServiceRepo serviceRepo) {
        this.electricBike = electricBike;
        this.electricBikePriceRepo = electricBikePriceRepo;
        this.serviceRepo = serviceRepo;
    }

    public IDetailBikeDetailOffer getBike(String bikeType) {
        IDetailBikeDetailOffer bikeDetailView = null;

        if("classic".equalsIgnoreCase(bikeType)) bikeDetailView = new ClassicDetailBikeDetailOffer(classicBike, bikeTypeRepo, serviceRepo);

        if("electric".equalsIgnoreCase(bikeType)) bikeDetailView = new ElectricDetailBikeDetailOffer(electricBike, electricBikePriceRepo, serviceRepo);
        return bikeDetailView;
    }
}
