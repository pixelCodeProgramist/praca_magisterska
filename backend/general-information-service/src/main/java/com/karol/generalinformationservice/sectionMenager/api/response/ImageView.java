package com.karol.generalinformationservice.sectionMenager.api.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ImageView {
    Long imageId;
    String url;
    String fileName;
    byte[] image;
}
