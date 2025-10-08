package ch.devprojects.taskhub.api;

import ch.devprojects.taskhub.domain.Task;
import ch.devprojects.taskhub.dto.TaskCatalogDto;
import ch.devprojects.taskhub.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	private final TaskService service;

	public TaskController(TaskService service) {
		this.service = service;
	}

	@GetMapping("/catalog")
	public TaskCatalogDto catalog() {
		return service.catalog();
	}

	@GetMapping
	public Page<Task> list(@RequestParam(required = false) List<Task.Status> status,
			@RequestParam(required = false) String ownerId,
			@RequestParam(required = false, name = "q") String queryText,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant updatedFrom,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant updatedTo,
			@PageableDefault(size = 20) Pageable pageable) {
		Set<Task.Status> statuses = (status == null) ? new HashSet<>() : new HashSet<>(status);
		return service.search(statuses, ownerId, queryText, updatedFrom, updatedTo, pageable);
	}

	@PostMapping
	public ResponseEntity<Task> create(@RequestBody Task task) {
		Task saved = service.create(task);
		return ResponseEntity.created(URI.create("/api/tasks/" + saved.getId())).body(saved);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Task> get(@PathVariable String id) {
		return service.get(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Task> update(@PathVariable String id, @RequestBody Task patch) {
		return service.update(id, patch).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}