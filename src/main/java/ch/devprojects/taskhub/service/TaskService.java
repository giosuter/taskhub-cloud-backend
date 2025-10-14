package ch.devprojects.taskhub.service;

import ch.devprojects.taskhub.domain.Task;
import ch.devprojects.taskhub.repo.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Encapsulates all business logic for CRUD operations.
 */
@Service
public class TaskService {

    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public List<Task> list() {
        return repo.findAll();
    }

    public Optional<Task> get(String id) {
        return repo.findById(id);
    }

    public Task create(Task t) {
        // ID and timestamps handled by @PrePersist
        return repo.save(t);
    }

    public Optional<Task> update(String id, Task patch) {
        return repo.findById(id).map(existing -> {
            if (patch.getTitle() != null) existing.setTitle(patch.getTitle());
            if (patch.getDescription() != null) existing.setDescription(patch.getDescription());
            if (patch.getStatus() != null) existing.setStatus(patch.getStatus());
            if (patch.getOwnerId() != null) existing.setOwnerId(patch.getOwnerId());
            if (patch.getDueDate() != null) existing.setDueDate(patch.getDueDate());
            return repo.save(existing);
        });
    }

    public boolean delete(String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}