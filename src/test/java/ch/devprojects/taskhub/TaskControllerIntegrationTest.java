package ch.devprojects.taskhub;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class TaskControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Test
	void pingWorks() throws Exception {
		// IMPORTANT: no /taskhub-cloud prefix when using MockMvc
		mvc.perform(get("/api/ping")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("TaskHub Cloud backend is alive"));
	}

	@Test
	void listTasksWorks() throws Exception {
		mvc.perform(get("/api/tasks")).andDo(print()).andExpect(status().isOk());
	}
}