package com.karol.offerservice.offerMenager.api.mapper;

import com.karol.offerservice.business.request.FrameRequest;
import com.karol.offerservice.offerMenager.data.entity.ClassicBikeFrameInventory;
import com.karol.offerservice.offerMenager.data.entity.ElectricBikeFrameInventory;
import com.karol.offerservice.offerMenager.data.entity.Frame;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClassicBikeInventoryMapper {
    public ClassicBikeFrameInventory mapDataToResponse(FrameRequest frame, Frame frameFromDb){
       return ClassicBikeFrameInventory.builder()
               .availableNumber(frame.getQuantity())
               .frame(frameFromDb)
               .build();
    }


}
