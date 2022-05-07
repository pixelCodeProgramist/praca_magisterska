package com.karol.offerservice.offerMenager.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClassicBikeFrameInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private int availableNumber;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "frame_id", nullable = false)
    private Frame frame;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "classic_bike_id", nullable = false)
    private ClassicBike classicBike;



}
