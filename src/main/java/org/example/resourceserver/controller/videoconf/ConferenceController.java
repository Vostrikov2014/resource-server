package org.example.resourceserver.controller.videoconf;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.resourceserver.entity.ConferenceEntity;
import org.example.resourceserver.service.videoconf.ConferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ConferenceController {

    private final ConferenceService conferenceService;
    //private final AuditorAware<String> auditorAware; // пример - это можно удалить
    private final HttpSession session;

    @GetMapping("/conferences")
    //@PreAuthorize("hasAuthority('SCOPE_read')") // Ограничиваем доступ для scope 'read'
    public ResponseEntity<List<ConferenceEntity>> getConferences(Authentication authentication) {

        if (authentication != null) {
            return ResponseEntity.ok(conferenceService.findAllByHostUsername(authentication.getName()));
        } else {
            return ResponseEntity.ok(conferenceService.findAll());
        }

        //String username = jwt.getClaimAsString("username");
        //String authorities = jwt.getClaimAsString("authorities");

        // получаем данные пользователя
        //UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @GetMapping("/conference/{id}")
    public ResponseEntity<ConferenceEntity> getConference(@PathVariable Long id) {
        Optional<ConferenceEntity> conference = conferenceService.getConference(id);
        return conference.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/conferences")
    public ResponseEntity<ConferenceEntity> createConference(Authentication authentication, @RequestBody ConferenceEntity conference) {
        String hostUsername = null;
        if (authentication != null && authentication.isAuthenticated()) {
            hostUsername =  authentication.getName();
        }

        ConferenceEntity createdConference = conferenceService.createConference(conference, hostUsername);
        return ResponseEntity.ok(createdConference);
    }

    @PutMapping("/conferences")
    public ResponseEntity<ConferenceEntity> updateConference(@RequestBody ConferenceEntity conference) {
        ConferenceEntity savedConference = conferenceService.updateConference(conference);
        return ResponseEntity.ok(savedConference);
    }

    @DeleteMapping("conference/{id}")
    public ResponseEntity<Void> deleteConference(@PathVariable Long id) {
        conferenceService.deleteConference(id);
        return ResponseEntity.noContent().build();
    }
}
