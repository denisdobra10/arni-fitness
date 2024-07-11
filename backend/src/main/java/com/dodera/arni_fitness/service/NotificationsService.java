package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.mail.MailService;
import com.dodera.arni_fitness.model.Notification;
import com.dodera.arni_fitness.model.Session;
import com.dodera.arni_fitness.repository.NotificationsRepository;
import com.dodera.arni_fitness.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationsService {
    private final NotificationsRepository notificationsRepository;
    private final SessionRepository sessionRepository;
    private final MailService mailService;

    public void checkForNotifications(String userEmail, Long sessionId) {
        // Check for notifications
        Notification notification = notificationsRepository.findByUserEmailAndSessionId(userEmail, sessionId).orElse(null);

        if (notification == null) {
            return;
        }

        Session session = sessionRepository.findById(sessionId).orElse(null);

        if (session == null) {
            return;
        }
        // Send notification
        mailService.sendTextMessage(userEmail, "A aparut un loc disponibil la un antrenament",
                "A aparut un loc disponibil la antrenamentul de la data " + session.getDatetime());
        notificationsRepository.delete(notification);
    }

    public void addNotification(String userEmail, Long sessionId) {
        if (notificationsRepository.findByUserEmailAndSessionId(userEmail, sessionId).isPresent()) {
            return;
        }
        Notification notification = new Notification();
        notification.setUserEmail(userEmail);
        notification.setSessionId(sessionId);
        notificationsRepository.save(notification);
    }
}
