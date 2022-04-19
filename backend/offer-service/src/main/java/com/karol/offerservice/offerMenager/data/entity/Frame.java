package com.karol.offerservice.offerMenager.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Frame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int frameId;

    @NotEmpty
    private String name;

    @ManyToMany(mappedBy = "frames", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ClassicBike> classicBikes = new HashSet<>();

    @ManyToMany(mappedBy = "frames", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ClassicBike> electricBikes = new HashSet<>();
}
