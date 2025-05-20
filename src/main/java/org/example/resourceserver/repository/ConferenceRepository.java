package org.example.resourceserver.repository;

import org.example.resourceserver.entity.ConferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConferenceRepository extends JpaRepository<ConferenceEntity, Long> {
    Optional<ConferenceEntity> findByConferenceName(String title);
    List<ConferenceEntity> findAllByHostUsername(String hostUsername);
}
