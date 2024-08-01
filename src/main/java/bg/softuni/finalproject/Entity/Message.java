package bg.softuni.finalproject.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

    @Entity
    @Table(name = "messages")
    public class Message {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false, columnDefinition = "TEXT")
        private String message;

        @Column(nullable = false)
        private LocalDateTime sentAt;

        private boolean answered;

        public Message() {}

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public LocalDateTime getSentAt() {
            return sentAt;
        }

        public void setSentAt(LocalDateTime sentAt) {
            this.sentAt = sentAt;
        }

        public boolean isAnswered() {
            return answered;
        }

        public void setAnswered(boolean answered) {
            this.answered = answered;
        }
    }

