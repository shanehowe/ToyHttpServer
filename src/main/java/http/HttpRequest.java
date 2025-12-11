package http;

import java.util.Map;

public record HttpRequest(HttpMethod method, String path, String version, HttpHeaders headers) {
}
