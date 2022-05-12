package com.karol.offerservice.business.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddBikeRequest {
    private String name;
    private List<FrameRequest> frames;
    private Byte[] image;
    private String selectedProductTypeOption;
    private String selectedBikeOrAccessoryTypeOption;
}
