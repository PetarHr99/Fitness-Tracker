package bg.softuni.finalproject.repo;

import bg.softuni.finalproject.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m ORDER BY m.answered ASC, m.sentAt DESC")
    List<Message> findAllOrderByAnswered();

}
