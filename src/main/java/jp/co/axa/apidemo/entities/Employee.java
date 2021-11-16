package jp.co.axa.apidemo.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "EMPLOYEE")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "EMPLOYEE_NUMBER", unique = true)
  private String number;

  @NotNull
  @Column(name = "EMPLOYEE_NAME")
  private String name;

  @Column(name = "EMPLOYEE_SALARY")
  private Integer salary;

  @Column(name = "DEPARTMENT")
  private String department;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Employee employee = (Employee) o;
    return id != null && Objects.equals(id, employee.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
