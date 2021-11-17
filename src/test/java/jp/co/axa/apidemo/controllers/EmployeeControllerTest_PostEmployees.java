package jp.co.axa.apidemo.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Test POST /api/v1/employees")
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest_PostEmployees {

  private final ObjectMapper objectMapper = new ObjectMapper();
  @Autowired private MockMvc mockMvc;

  @DisplayName("First time make the call will success")
  @Order(100)
  @Test
  public void testPostEmployees_normalRequestBody() throws Exception {
    EmployeeDto employeeDto =
        EmployeeDto.builder()
            .name("name")
            .number("number")
            .department("department")
            .salary(100)
            .build();
    mockMvc
        .perform(
            post("/api/v1/employees")
                .content(objectMapper.writeValueAsString(employeeDto))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is("name")));
  }

  @DisplayName("Save with same employee number will cause error")
  @Order(200)
  @Test
  public void testPostEmployees_saveWithSameEmployee() throws Exception {
    Employee employeeDto =
        Employee.builder()
            .name("name")
            .number("number")
            .department("department")
            .salary(100)
            .build();
    mockMvc
        .perform(
            post("/api/v1/employees")
                .content(objectMapper.writeValueAsString(employeeDto))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andDo(print())
        .andExpect(jsonPath("$", is("The employee try to save already exists, number: number")));
  }

  @DisplayName("Save with employee that missing some fields")
  @Order(300)
  @Test
  public void testPostEmployees_saveEmpoyee_withoutName() throws Exception {
    EmployeeDto employeeDto =
        EmployeeDto.builder().number("number").department("department").salary(100).build();
    mockMvc
        .perform(
            post("/api/v1/employees")
                .content(objectMapper.writeValueAsString(employeeDto))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andDo(print())
        .andExpect(jsonPath("$.name", is("must not be null")));
  }
}
