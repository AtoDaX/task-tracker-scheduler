package edu.pet.tasktrackerscheduler.repository;

import edu.pet.tasktrackerscheduler.scheduler.model.Task;
import edu.pet.tasktrackerscheduler.scheduler.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    Integer countTasksByUserAndCompletedAtBetween(User user, Timestamp previous, Timestamp now);
    List<Task> getTasksByUserAndCompletedAtBetween(User user, Timestamp previous, Timestamp now);

    List<Task> getTasksByUserAndCompleted(User user, boolean completed);

}


