package ch.devprojects.taskhub.specs;

import ch.devprojects.taskhub.domain.Task;
import org.springframework.data.jpa.domain.Specification;
import java.time.Instant;
import java.util.Set;

public final class TaskSpecs {
	private TaskSpecs() {
	}

	public static Specification<Task> statusIn(Set<Task.Status> statuses) {
		return (root, q, cb) -> (statuses == null || statuses.isEmpty()) ? null : root.get("status").in(statuses);
	}

	public static Specification<Task> owner(String ownerId) {
		return (root, q, cb) -> (ownerId == null || ownerId.isBlank()) ? null : cb.equal(root.get("ownerId"), ownerId);
	}

	public static Specification<Task> text(String qtext) {
		return (root, q, cb) -> {
			if (qtext == null || qtext.isBlank())
				return null;
			String like = "%" + qtext.toLowerCase() + "%";
			return cb.or(cb.like(cb.lower(root.get("title")), like), cb.like(cb.lower(root.get("description")), like));
		};
	}

	public static Specification<Task> updatedFrom(Instant from) {
		return (root, q, cb) -> (from == null) ? null : cb.greaterThanOrEqualTo(root.get("updatedAt"), from);
	}

	public static Specification<Task> updatedTo(Instant to) {
		return (root, q, cb) -> (to == null) ? null : cb.lessThanOrEqualTo(root.get("updatedAt"), to);
	}
}