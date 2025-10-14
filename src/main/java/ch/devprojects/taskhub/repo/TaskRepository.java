package ch.devprojects.taskhub.repo;

import ch.devprojects.taskhub.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Simple repository interface for Task entities.
 */
public interface TaskRepository extends JpaRepository<Task, String> {
}