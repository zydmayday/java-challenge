package jp.co.axa.apidemo.exception;

public class EmployeeNotFoundException extends RuntimeException {

  public EmployeeNotFoundException(Long id) {
    super("The employee try to get does not exists, id: " + id);
  }
}
