package bg.softuni.finalproject.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "quotes")
public class Quote {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String text;

        public Quote() {}

        public Quote(String text) {
            this.text = text;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
}
