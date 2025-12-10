package http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HttpRequestReader {

  private final InputStream stream;

  public HttpRequestReader(InputStream stream) {
    this.stream = stream;
  }

  public HttpRequest readRequest()  throws IOException {
    RequestLine requestLine = parseRequestLine();
    return new HttpRequest(requestLine.method(), requestLine.path(), requestLine.version());
  }

  private RequestLine parseRequestLine() throws IOException {
    String requestLineString = readAsciiLine().trim();
    String[] requestLineSplit = requestLineString.split(" ");
    return RequestLine.newBuilder()
        .method(requestLineSplit[0])
        .path(requestLineSplit[1])
        .version(requestLineSplit[2])
        .build();
  }

  private String readAsciiLine() throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int previous = -1;
    int current;
    while ((current = stream.read()) != -1) {
      if (previous == '\r' && current == '\n') {
        outputStream.write(current);
        break;
      }
      outputStream.write(current);
      previous = current;
    }
    return outputStream.toString(StandardCharsets.US_ASCII);
  }

  public record RequestLine(HttpMethod method, String path, String version) {

    public static Builder newBuilder() {
      return new Builder();
    }

    public static class Builder {
      private HttpMethod method;
      private String path;
      private String version;

      public Builder method(String method) {
        this.method = HttpMethod.caseInsensitiveValueOf(method);
        return this;
      }

      public Builder path(String path) {
        this.path = path;
        return this;
      }

      public Builder version(String version) {
        this.version = version;
        return this;
      }

      public RequestLine build() {
        return new RequestLine(method, path, version);
      }
    }
  }
}
