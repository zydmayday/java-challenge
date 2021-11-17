package jp.co.axa.apidemo.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jp.co.axa.apidemo.extension.H2EmployeeDataSetupExtension;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("Test DELETE /api/v1/employees/{employeeId}")
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest_DeleteEmployees {

  @Autowired @RegisterExtension H2EmployeeDataSetupExtension h2EmployeeExtension;

  @Autowired private MockMvc mockMvc;
  @Autowired private EmployeeRepository employeeRepository;

  @DisplayName("Delete an existing employee")
  @Test
  public void testDeleteEmployee_existsId() throws Exception {
    mockMvc.perform(delete("/api/v1/employees/1")).andExpect(status().isNoContent());
  }

  @DisplayName("Delete an non-existing employee")
  @Test
  public void testGetEmployees_nonExistsId() throws Exception {
    mockMvc
        .perform(delete("/api/v1/employees/999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$", is("The employee try to get does not exists, id: 999")));
  }
}
