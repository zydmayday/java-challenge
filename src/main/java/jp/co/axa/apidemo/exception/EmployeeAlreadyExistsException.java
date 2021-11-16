package jp.co.axa.apidemo.exception;

public class EmployeeAlreadyExistsException extends RuntimeException{

  public EmployeeAlreadyExistsException(String number) {
    super("The employee try to save already exists, number: " + number);
  }
}
