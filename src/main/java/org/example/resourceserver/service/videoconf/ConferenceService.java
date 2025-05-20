package org.example.resourceserver.service.videoconf;

import lombok.RequiredArgsConstructor;
import org.example.resourceserver.entity.ConferenceEntity;
import org.example.resourceserver.repository.ConferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConferenceService {

    private final ConferenceRepository conferenceRepository;

    public ConferenceEntity createConference(ConferenceEntity conference, String hostUsername) {
        conference.setHostUsername(hostUsername);
        conference.setConferenceUrl(generateUniqueUrl(conference.getConferenceName()));
        return conferenceRepository.save(conference);
    }

    public ConferenceEntity updateConference(ConferenceEntity updatedConference) {
        return conferenceRepository.save(updatedConference);  // Сохраняет обновленные данные конференции
    }

    public Optional<ConferenceEntity> getConference(Long id) {
        return conferenceRepository.findById(id);
    }

    // Поиск конференции по id
    public ConferenceEntity findConferenceById(Long id) {
        return conferenceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Конференция не найдена"));
    }

    public List<ConferenceEntity> findAllByHostUsername(String hostUsername) {
        return conferenceRepository.findAllByHostUsername(hostUsername);
    }

    public Optional<ConferenceEntity> findConferenceByName(String conferenceName) {
        return conferenceRepository.findByConferenceName(conferenceName);
    }

    public void deleteConference(Long id) {
        conferenceRepository.deleteById(id);
    }

    public List<ConferenceEntity> findAll() {
        return conferenceRepository.findAll();
    }

    private String generateUniqueUrl(String conferenceName) {
        // Generate a unique URL for the conference
        return "https://online3.spa.msu.ru/" + UUID.randomUUID() + "-" + conferenceName;
    }
}
