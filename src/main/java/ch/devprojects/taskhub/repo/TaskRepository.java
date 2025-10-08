package ch.devprojects.taskhub.repo;

import ch.devprojects.taskhub.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepository extends JpaRepository<Task, String>, JpaSpecificationExecutor<Task> {

	interface OwnerCount {
		String getOwnerId();

		long getCnt();
	}

	@org.springframework.data.jpa.repository.Query("select t.ownerId as ownerId, count(t) as cnt from Task t group by t.ownerId")
	java.util.List<OwnerCount> countByOwner();
}