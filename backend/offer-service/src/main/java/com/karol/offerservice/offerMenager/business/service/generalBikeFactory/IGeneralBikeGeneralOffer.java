package com.karol.offerservice.offerMenager.business.service.generalBikeFactory;

import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.BikeGeneralOfferResponseView;
import com.karol.offerservice.offerMenager.business.service.ImageService;

public interface IGeneralBikeGeneralOffer {
    int MAX_PRODUCT_ON_PAGE = 9;

    BikeGeneralOfferResponseView getGeneralOfferView(ImageService imageService, int pageNr, String section);
}
