package com.karol.offerservice.offerMenager.business.service.generalBikeFactory;

import com.karol.offerservice.offerMenager.data.repository.BikeTypeRepo;
import com.karol.offerservice.offerMenager.data.repository.ClassicBikeRepo;
import com.karol.offerservice.offerMenager.data.repository.ElectricBikePriceRepo;
import com.karol.offerservice.offerMenager.data.repository.ElectricBikeRepo;

public class GeneralBikeFactory {
    private ClassicBikeRepo classicBikeRepo;
    private ElectricBikeRepo electricBikeRepo;
    private ElectricBikePriceRepo electricBikePriceRepo;
    private BikeTypeRepo bikeTypeRepo;

    public GeneralBikeFactory(ClassicBikeRepo classicBikeRepo, BikeTypeRepo bikeTypeRepo) {
        this.classicBikeRepo = classicBikeRepo;
        this.bikeTypeRepo = bikeTypeRepo;
    }

    public GeneralBikeFactory(ElectricBikeRepo electricBikeRepo, ElectricBikePriceRepo electricBikePriceRepo) {
        this.electricBikeRepo = electricBikeRepo;
        this.electricBikePriceRepo = electricBikePriceRepo;
    }

    public IGeneralBikeGeneralOffer getBike(String bikeType) {
        IGeneralBikeGeneralOffer bikeGeneralOfferView = null;
        if("classic".equalsIgnoreCase(bikeType)) bikeGeneralOfferView = new ClassicGeneralBikeGeneralOffer(classicBikeRepo, bikeTypeRepo);

        if("electric".equalsIgnoreCase(bikeType)) bikeGeneralOfferView = new ElectricGeneralBikeGeneralOffer(electricBikeRepo, electricBikePriceRepo);
        return bikeGeneralOfferView;
    }
}
