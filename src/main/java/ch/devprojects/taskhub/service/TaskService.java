package ch.devprojects.taskhub.service;

import ch.devprojects.taskhub.domain.Task;
import ch.devprojects.taskhub.dto.TaskCatalogDto;
import ch.devprojects.taskhub.repo.TaskRepository;
import ch.devprojects.taskhub.specs.TaskSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

	private final TaskRepository repo;

	public TaskService(TaskRepository repo) {
		this.repo = repo;
	}

	public Page<Task> search(Set<Task.Status> statuses, String ownerId, String q, Instant updatedFrom,
			Instant updatedTo, Pageable pageable) {
		Specification<Task> spec = Specification.where(TaskSpecs.statusIn(statuses)).and(TaskSpecs.owner(ownerId))
				.and(TaskSpecs.text(q)).and(TaskSpecs.updatedFrom(updatedFrom)).and(TaskSpecs.updatedTo(updatedTo));
		return repo.findAll(spec, pageable);
	}

	public Task create(Task t) {
		return repo.save(t);
	}

	public Optional<Task> get(String id) {
		return repo.findById(id);
	}

	public Optional<Task> update(String id, Task patch) {
		return repo.findById(id).map(existing -> {
			if (patch.getTitle() != null)
				existing.setTitle(patch.getTitle());
			if (patch.getDescription() != null)
				existing.setDescription(patch.getDescription());
			if (patch.getStatus() != null)
				existing.setStatus(patch.getStatus());
			if (patch.getOwnerId() != null)
				existing.setOwnerId(patch.getOwnerId());
			if (patch.getDueDate() != null)
				existing.setDueDate(patch.getDueDate());
			return repo.save(existing);
		});
	}

	public void delete(String id) {
		repo.deleteById(id);
	}

	public TaskCatalogDto catalog() {
		Map<Task.Status, Long> byStatus = Arrays.stream(Task.Status.values())
				.collect(Collectors.toMap(s -> s, s -> repo.count((root, q, cb) -> cb.equal(root.get("status"), s))));
		Map<String, Long> byOwner = new LinkedHashMap<>();
		for (TaskRepository.OwnerCount oc : repo.countByOwner())
			byOwner.put(oc.getOwnerId(), oc.getCnt());
		return new TaskCatalogDto(byStatus, byOwner);
	}
}