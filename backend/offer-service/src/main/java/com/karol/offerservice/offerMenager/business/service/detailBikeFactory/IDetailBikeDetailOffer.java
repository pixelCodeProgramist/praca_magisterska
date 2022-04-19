package com.karol.offerservice.offerMenager.business.service.detailBikeFactory;

import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoView;
import com.karol.offerservice.offerMenager.business.service.ImageService;

public interface IDetailBikeDetailOffer {
    DetailBikeInfoView getDetailOfferView(ImageService imageService);
}
