package jp.co.axa.apidemo.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.extension.H2EmployeeDataSetupExtension;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(value = "test")
@DisplayName("Test PUT /api/v1/employees/{employeeId}")
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest_PutEmployees {

  @Autowired @RegisterExtension H2EmployeeDataSetupExtension h2EmployeeExtension;

  @Autowired private ObjectMapper objectMapper;
  @Autowired private MockMvc mockMvc;
  @Autowired private EmployeeRepository employeeRepository;

  @DisplayName("Update an existing employee")
  @Test
  public void testUpdateEmployee_existsId() throws Exception {
    EmployeeDto employeeDto =
        EmployeeDto.builder()
            .name("changed-name")
            .number("number")
            .department("department")
            .salary(100)
            .build();
    mockMvc
        .perform(
            put("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("changed-name")));
  }

  @DisplayName("Update a non-existing employee")
  @Test
  public void testUpdateEmployee_nonExistsId() throws Exception {
    EmployeeDto employeeDto =
        EmployeeDto.builder()
            .name("changed-name")
            .number("number")
            .department("department")
            .salary(100)
            .build();
    mockMvc
        .perform(
            put("/api/v1/employees/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$", is("The employee try to get does not exists, id: 999")));
  }
}
