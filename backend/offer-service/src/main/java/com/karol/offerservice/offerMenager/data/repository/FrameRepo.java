package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.Frame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FrameRepo extends JpaRepository<Frame, Long> {
    Optional<Frame> findByName(String name);

}
