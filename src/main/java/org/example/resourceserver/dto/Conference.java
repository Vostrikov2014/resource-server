package org.example.resourceserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conference {
    private Long id;
    private String conferenceName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String conferenceUrl;
    private String hostUsername;
}
