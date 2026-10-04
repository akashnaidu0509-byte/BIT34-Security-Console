package com.bit34.security_console;

import com.bit34.security_console.model.SecurityEvent;
import com.bit34.security_console.repository.AlertRepository;
import com.bit34.security_console.repository.SecurityEventRepository;
import com.bit34.security_console.service.DetectionService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DetectionServiceTest {

    @Test
    void createsAlertAfterFiveFailedLogins() {

        SecurityEventRepository eventRepository =
                mock(SecurityEventRepository.class);

        AlertRepository alertRepository =
                mock(AlertRepository.class);

        SecurityEvent event = new SecurityEvent();
        event.setEventType("LOGIN_FAILED");
        event.setSourceIp("10.0.0.50");
        event.setUsername("testuser");
        event.setTimestamp(LocalDateTime.now());

        when(eventRepository.findAll())
                .thenReturn(List.of(
                        event, event, event, event, event
                ));

        DetectionService service =
                new DetectionService(eventRepository, alertRepository);

        service.checkEvent(event);

        verify(alertRepository, times(1)).save(any());
    }
}