package bg.softuni.finalproject.init;

import bg.softuni.finalproject.Entity.Quote;
import bg.softuni.finalproject.repo.QuoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase(QuoteRepository quoteRepository) {
        return args -> {
            List<String> quotes = Arrays.asList(
                    "Believe you can and you're halfway there.",
                    "The only way to do great work is to love what you do.",
                    "You are never too old to set another goal or to dream a new dream.",
                    "The only limit to our realization of tomorrow is our doubts of today.",
                    "The future belongs to those who believe in the beauty of their dreams.",
                    "It does not matter how slowly you go as long as you do not stop.",
                    "Your time is limited, don't waste it living someone else's life.",
                    "The best way to predict the future is to invent it.",
                    "Don't watch the clock; do what it does. Keep going.",
                    "Success is not the key to happiness. Happiness is the key to success.",
                    "The harder you work for something, the greater you’ll feel when you achieve it.",
                    "Dream bigger. Do bigger.",
                    "Don’t stop when you’re tired. Stop when you’re done.",
                    "Wake up with determination. Go to bed with satisfaction.",
                    "Do something today that your future self will thank you for.",
                    "Little things make big days.",
                    "It’s going to be hard, but hard does not mean impossible.",
                    "Don’t wait for opportunity. Create it.",
                    "Sometimes we’re tested not to show our weaknesses, but to discover our strengths.",
                    "The key to success is to focus on goals, not obstacles.",
                    "Dream it. Believe it. Build it.",
                    "Start where you are. Use what you have. Do what you can.",
                    "The way to get started is to quit talking and begin doing.",
                    "Don’t let yesterday take up too much of today.",
                    "It’s not whether you get knocked down, it’s whether you get up.",
                    "People who are crazy enough to think they can change the world, are the ones who do.",
                    "We may encounter many defeats but we must not be defeated.",
                    "Knowing is not enough; we must apply. Wishing is not enough; we must do.",
                    "Imagine your life is perfect in every respect; what would it look like?",
                    "We generate fears while we sit. We overcome them by action."
            );
            if (quoteRepository.findAll().isEmpty()) {
                quotes.forEach(quote -> quoteRepository.save(new Quote(quote)));
            }
        };
    }
}
