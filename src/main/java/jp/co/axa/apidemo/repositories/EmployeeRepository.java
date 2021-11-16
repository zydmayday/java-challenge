package jp.co.axa.apidemo.repositories;

import java.util.Optional;
import jp.co.axa.apidemo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findByNumber(String number);

}
