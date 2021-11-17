package jp.co.axa.apidemo.dto;

import javax.validation.constraints.NotNull;
import jp.co.axa.apidemo.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class EmployeeDto {

  private Long id;
  @NotNull private String number;
  @NotNull private String name;
  private Integer salary;
  @NotNull private String department;

  public static EmployeeDto fromEmployee(Employee employee) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(employee, EmployeeDto.class);
  }

  public Employee toEmployee() {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(this, Employee.class);
  }
}
