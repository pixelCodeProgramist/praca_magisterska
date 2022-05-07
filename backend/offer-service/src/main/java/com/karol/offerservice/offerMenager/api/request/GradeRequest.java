package com.karol.offerservice.offerMenager.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GradeRequest {
    @NotBlank
    private Long productId;

    @Size(max = 5)
    @Column(precision=10, scale=2)
    private BigDecimal rating;

}
