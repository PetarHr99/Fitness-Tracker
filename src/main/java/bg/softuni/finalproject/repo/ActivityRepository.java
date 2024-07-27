package bg.softuni.finalproject.repo;

import bg.softuni.finalproject.Entity.Activity;
import bg.softuni.finalproject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Activity findTopByAddedByUserOrderByDateOfActivityDesc(User user);

    List<Activity> findByAddedByUser(User addedByUser);
}
