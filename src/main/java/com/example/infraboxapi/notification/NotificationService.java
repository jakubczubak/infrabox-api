package com.example.infraboxapi.notification;

import com.example.infraboxapi.user.User;
import com.example.infraboxapi.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void deleteNotification(Long id) {
        User user = userRepository.findById(getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        user.getNotifications().remove(notification);
        userRepository.save(user);
        notificationRepository.delete(notification);
    }

    //This method is used to update the notification to read or unread
    public void updateNotification(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(!notification.isRead());
        notificationRepository.save(notification);
    }

    public void createAndSendNotification(String description, NotificationDescription notificationDescription) {
        // Pobierz nazwę zalogowanego użytkownika z SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // Pobierz użytkownika, który tworzy powiadomienie
        // Tworzenie nowego powiadomienia
        Notification notification = new Notification();
        notification.setDescription(description);
        notification.setTitle(notificationDescription.getDescription());
        notification.setRead(false);
        notification.setAuthor(currentUser.getFirstName() + " " + currentUser.getLastName()); // Ustaw autora na nazwę zalogowanego użytkownika
        // Pobierz listę wszystkich użytkowników z wyjątkiem autora
        List<User> allUsersExceptAuthor = findAllUsersExceptUserWithId(currentUser.getId());
        // Wyślij powiadomienie do wszystkich użytkowników
        for (User user : allUsersExceptAuthor) {
            notification.setUser(user);
            user.getNotifications().add(notification);
            notificationRepository.save(notification);
            userRepository.save(user);
        }
    }

    public void createAndSendSystemNotification(String description, NotificationDescription notificationDescription) {
        // Tworzenie nowego powiadomienia
        Notification notification = new Notification();
        notification.setDescription(description);
        notification.setTitle(notificationDescription.getDescription());
        notification.setRead(false);
        notification.setAuthor("Infrabox");
        List<User> allUsers = userRepository.findAll();
        // Wyślij powiadomienie do wszystkich użytkowników
        for (User user : allUsers) {
            notification.setUser(user);
            user.getNotifications().add(notification);
            notificationRepository.save(notification);
            userRepository.save(user);
        }
    }

    public void createAndSendQuantityNotification(String description, NotificationDescription notificationDescription) {
        // Pobierz nazwę zalogowanego użytkownika z SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // Pobierz użytkownika, który tworzy powiadomienie
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        // Tworzenie nowego powiadomienia
        Notification notification = new Notification();
        notification.setDescription(description);
        notification.setTitle(notificationDescription.getDescription());
        notification.setRead(false);
        notification.setAuthor(currentUser.getFirstName() + " " + currentUser.getLastName());

        List<User> allUsers = userRepository.findAll();
        // Wyślij powiadomienie do wszystkich użytkowników
        for (User u : allUsers) {
            notification.setUser(u);
            u.getNotifications().add(notification);
            notificationRepository.save(notification);
            userRepository.save(u);
        }

    }
    

    public Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof User user) {
            return user.getId();
        } else {
            return null;
        }
    }

    public List<User> findAllUsersExceptUserWithId(Integer userId) {
        return userRepository.findAllUsersExceptUserWithId(userId);
    }
}
