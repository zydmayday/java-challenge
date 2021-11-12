package jp.co.axa.apidemo.services;

import java.util.List;
import java.util.Optional;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private EmployeeRepository employeeRepository;

  @Autowired
  public void setEmployeeRepository(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public List<Employee> retrieveEmployees() {
    return employeeRepository.findAll();
  }

  public Employee getEmployee(Long employeeId) {
    Optional<Employee> optEmp = employeeRepository.findById(employeeId);
    // FIXME: Handle if employee is not found
    return optEmp.get();
  }

  public void saveEmployee(Employee employee) {
    employeeRepository.save(employee);
  }

  public void deleteEmployee(Long employeeId) {
    employeeRepository.deleteById(employeeId);
  }

  public void updateEmployee(Employee employee) {
    employeeRepository.save(employee);
  }
}