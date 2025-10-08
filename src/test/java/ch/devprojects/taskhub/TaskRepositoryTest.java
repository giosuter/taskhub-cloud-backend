package ch.devprojects.taskhub;

import ch.devprojects.taskhub.domain.Task;
import ch.devprojects.taskhub.repo.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
class TaskRepositoryTest {

	@Autowired
	private TaskRepository repo;

	@Test
	void saveAndFind() {
		Task t = new Task();
		t.setTitle("Repo test");
		t.setStatus(Task.Status.NEW);
		t = repo.save(t);

		assertThat(t.getId()).isNotBlank();
		assertThat(repo.findById(t.getId())).isPresent();
	}
}