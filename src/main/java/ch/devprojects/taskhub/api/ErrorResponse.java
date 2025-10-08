package ch.devprojects.taskhub.api;

public record ErrorResponse(int status, String error, String message, String path, String timestamp) {
}