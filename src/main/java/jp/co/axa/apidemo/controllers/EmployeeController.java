package jp.co.axa.apidemo.controllers;

import javax.validation.Valid;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

  Logger logger = LoggerFactory.getLogger(EmployeeController.class);

  @Autowired
  private EmployeeService employeeService;

  public void setEmployeeService(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/employees")
  public List<Employee> getEmployees() {
    return employeeService.retrieveEmployees();
  }

  @GetMapping("/employees/{employeeId}")
  public Employee getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
    return employeeService.getEmployee(employeeId);
  }

  @PostMapping("/employees")
  public Employee saveEmployee(@Valid @RequestBody Employee employee) {
    logger.info("Start Employee Saved Successfully");
    Employee savedEmployee = employeeService.saveEmployee(employee);
    logger.info("Finish Employee Saved Successfully");
    return savedEmployee;
  }

  @DeleteMapping("/employees/{employeeId}")
  public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
    employeeService.deleteEmployee(employeeId);
    logger.info("Employee Deleted Successfully");
  }

  @PutMapping("/employees/{employeeId}")
  public void updateEmployee(@RequestBody Employee employee,
      @PathVariable(name = "employeeId") Long employeeId) {
    Employee emp = employeeService.getEmployee(employeeId);
    if (emp != null) {
      employeeService.updateEmployee(employee);
      logger.info("Employee Updated Successfully");
    }

  }

}
