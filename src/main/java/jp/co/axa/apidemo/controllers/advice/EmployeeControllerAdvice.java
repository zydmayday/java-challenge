package jp.co.axa.apidemo.controllers.advice;

import java.util.HashMap;
import java.util.Map;
import jp.co.axa.apidemo.exception.EmployeeAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmployeeControllerAdvice {

  @ResponseBody
  @ExceptionHandler(EmployeeAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String employeeAlreadyExistsHandler(EmployeeAlreadyExistsException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  Map<String, String> employeeValidationHandler(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

}
