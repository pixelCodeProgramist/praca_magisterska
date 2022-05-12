package com.karol.offerservice.business.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FrameRequest {
    private String frameSize;
    private Integer quantity;
}
