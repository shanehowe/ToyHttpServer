package http.model;

public enum HttpStatusCode {
  OK(200, "OK"),
  CREATED(201, "Created"),
  NO_CONTENT(204, "No Content"),
  BAD_REQUEST(400, "Bad Request"),
  NOT_FOUND(404, "Not Found"),
  INTERNAL_SERVER_ERROR(500, "Internal Server Error");

  private final int code;
  private final String statusText;

  HttpStatusCode(int code, String statusText) {
    this.code = code;
    this.statusText = statusText;
  }

  public int getCode() {
    return code;
  }

  public String getStatusText() {
    return statusText;
  }
}
