package http.model;

public record HttpRequest(HttpMethod method, String path, String version, HttpHeaders headers) {}
