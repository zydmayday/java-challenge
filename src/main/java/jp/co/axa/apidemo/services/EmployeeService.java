package jp.co.axa.apidemo.services;

import java.util.List;
import jp.co.axa.apidemo.entities.Employee;

public interface EmployeeService {

  /**
   * To have a better performance, we fetch employees using pagination.
   *
   * @param page from which page start to fetch data
   * @param size size per page
   * @return employees fetched based on page
   */
  List<Employee> getEmployees(int page, int size);

  Employee getEmployee(Long employeeId);

  /**
   * If employee already exists, throw {@link
   * jp.co.axa.apidemo.exception.EmployeeAlreadyExistsException}, otherwise save employee and return
   * saved data.
   *
   * <p>We should do null check for all NotNull fields.
   *
   * @param employee data to save
   * @return Employee saved employee data
   */
  Employee saveEmployee(Employee employee);

  void deleteEmployee(Long employeeId);

  void updateEmployee(Employee employee);
}
