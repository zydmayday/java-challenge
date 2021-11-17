package jp.co.axa.apidemo.services;

import java.util.List;
import java.util.Optional;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.EmployeeAlreadyExistsException;
import jp.co.axa.apidemo.exception.EmployeeNotFoundException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private EmployeeRepository employeeRepository;

  @Autowired
  public void setEmployeeRepository(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public List<Employee> getEmployees(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Employee> employeePage = employeeRepository.findAll(pageable);
    return employeePage.getContent();
  }

  public Employee getEmployee(Long employeeId) {
    Optional<Employee> optEmp = employeeRepository.findById(employeeId);
    return optEmp.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
  }

  @Transactional
  @Override
  public Employee saveEmployee(Employee employee) {
    String employeeNumber = employee.getNumber();
    if (employeeRepository.findByNumber(employeeNumber).isPresent()) {
      throw new EmployeeAlreadyExistsException(employeeNumber);
    }
    return employeeRepository.save(employee);
  }

  @Transactional
  @Override
  public void deleteEmployee(Long employeeId) {
    if (employeeRepository.existsById(employeeId)) {
      employeeRepository.deleteById(employeeId);
    } else {
      throw new EmployeeNotFoundException(employeeId);
    }
  }

  @Transactional
  @Override
  public Employee updateEmployee(Long employeeId, Employee employee) {
    if (employeeRepository.existsById(employeeId)) {
      // in case user provide a different id.
      employee.setId(employeeId);
      return employeeRepository.save(employee);
    } else {
      throw new EmployeeNotFoundException(employeeId);
    }
  }
}
