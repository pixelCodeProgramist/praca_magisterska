package com.karol.generalinformationservice.sectionMenager.api.response;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GeneralInfoView {
    private long generalInformationId;
    private String email;
    private String hourFrom;
    private String hourTo;
    private List<BranchView> branches;
}
