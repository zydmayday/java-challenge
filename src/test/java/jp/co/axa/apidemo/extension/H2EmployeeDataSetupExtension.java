package jp.co.axa.apidemo.extension;

import java.util.Arrays;
import javax.persistence.EntityManager;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Register this extension when you want to write test case for employee. This extension will help
 * you to initialize employee data and delete them after test.
 */
@Component
public class H2EmployeeDataSetupExtension implements BeforeEachCallback, AfterEachCallback {

  @Autowired private EmployeeRepository employeeRepository;
  @Autowired private EntityManager entityManager;
  @Autowired private TransactionTemplate transactionTemplate;

  @Override
  public void beforeEach(ExtensionContext extensionContext) {
    Employee employee1 = new Employee(1L, "number-1", "name-1", 100, "dept-1");
    Employee employee2 = new Employee(2L, "number-2", "name-2", 200, "dept-2");
    Employee employee3 = new Employee(3L, "number-3", "name-3", 300, "dept-3");
    Employee employee4 = new Employee(4L, "number-4", "name-4", 400, "dept-4");
    employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3, employee4));
  }

  @Override
  public void afterEach(ExtensionContext extensionContext) {
    employeeRepository.deleteAll();
    transactionTemplate.execute(
        transactionStatus -> {
          entityManager
              .createNativeQuery("ALTER TABLE EMPLOYEE ALTER ID RESTART WITH 1")
              .executeUpdate();
          transactionStatus.flush();
          return null;
        });
  }
}
