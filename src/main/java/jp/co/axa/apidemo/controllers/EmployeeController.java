package jp.co.axa.apidemo.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

  Logger logger = LoggerFactory.getLogger(EmployeeController.class);

  private EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/employees")
  public List<EmployeeDto> getEmployees(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size) {
    SecurityContextHolder.getContext().getAuthentication();
    List<Employee> employees = employeeService.getEmployees(page, size);
    return employees.stream().map(EmployeeDto::fromEmployee).collect(Collectors.toList());
  }

  @GetMapping("/employees/{employeeId}")
  public EmployeeDto getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
    Employee employee = employeeService.getEmployee(employeeId);
    return EmployeeDto.fromEmployee(employee);
  }

  @PostMapping("/employees")
  @ResponseStatus(HttpStatus.CREATED)
  public EmployeeDto saveEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
    logger.info("Start Employee Saved Successfully");
    Employee savedEmployee = employeeService.saveEmployee(employeeDto.toEmployee());
    logger.info("Finish Employee Saved Successfully");
    return EmployeeDto.fromEmployee(savedEmployee);
  }

  @DeleteMapping("/employees/{employeeId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
    employeeService.deleteEmployee(employeeId);
    logger.info("Employee Deleted Successfully");
  }

  @PutMapping("/employees/{employeeId}")
  public EmployeeDto updateEmployee(
      @RequestBody EmployeeDto employeeDto, @PathVariable(name = "employeeId") Long employeeId) {
    Employee employee = employeeService.updateEmployee(employeeId, employeeDto.toEmployee());
    logger.info("Employee Updated Successfully");
    return EmployeeDto.fromEmployee(employee);
  }
}
