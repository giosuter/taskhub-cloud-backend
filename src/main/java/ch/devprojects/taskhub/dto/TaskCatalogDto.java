package ch.devprojects.taskhub.dto;

import ch.devprojects.taskhub.domain.Task;
import java.util.Map;

public record TaskCatalogDto(Map<Task.Status, Long> byStatus, Map<String, Long> byOwner) {
}