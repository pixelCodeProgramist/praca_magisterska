package com.karol.offerservice.offerMenager.api.response.generalInfoPackage;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BikeForSearchView {
    private Long id;
    private String name;
    private byte[] image;
    private String type;
    private Boolean active;
}
