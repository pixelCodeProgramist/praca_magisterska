package com.karol.offerservice.offerMenager.api.mapper;

import com.karol.offerservice.business.request.FrameRequest;
import com.karol.offerservice.offerMenager.api.dto.TimePriceDto;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoView;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoViewBuilder;
import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import com.karol.offerservice.offerMenager.data.entity.ElectricBike;
import com.karol.offerservice.offerMenager.data.entity.ElectricBikeFrameInventory;
import com.karol.offerservice.offerMenager.data.entity.Frame;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ElectricBikeInventoryMapper {
    public ElectricBikeFrameInventory mapDataToResponse(FrameRequest frame, Frame frameFromDb){
       return ElectricBikeFrameInventory.builder()
               .availableNumber(frame.getQuantity())
               .frame(frameFromDb)
               .build();
    }


}
