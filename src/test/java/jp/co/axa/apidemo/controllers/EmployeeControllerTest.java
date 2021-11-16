package jp.co.axa.apidemo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.entities.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

  @Autowired private MockMvc mockMvc;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @DisplayName("Test GET /api/v1/employees")
  @TestMethodOrder(OrderAnnotation.class)
  @Nested
  class GetEmployeesTest {

    @DisplayName("First time make the call will success")
    @Order(100)
    @Test
    public void testGetEmployees_normalRequestBody() throws Exception {
      Employee employee =
          Employee.builder()
              .name("name")
              .number("number")
              .department("department")
              .salary(100)
              .build();
      mockMvc
          .perform(
              post("/api/v1/employees")
                  .content(objectMapper.writeValueAsString(employee))
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.name", is("name")));
    }

    @DisplayName("Save with same employee number will cause error")
    @Order(200)
    @Test
    public void testGetEmployees_saveWithSameEmployee() throws Exception {
      Employee employee =
          Employee.builder()
              .name("name")
              .number("number")
              .department("department")
              .salary(100)
              .build();
      mockMvc
          .perform(
              post("/api/v1/employees")
                  .content(objectMapper.writeValueAsString(employee))
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isBadRequest())
          .andDo(print())
          .andExpect(jsonPath("$", is("The employee try to save already exists, number: number")));
    }

    @DisplayName("Save with employee that missing some fields")
    @Order(300)
    @Test
    public void testGetEmployees_saveEmpoyee_withoutName() throws Exception {
      Employee employee =
          Employee.builder()
              .number("number")
              .department("department")
              .salary(100)
              .build();
      mockMvc
          .perform(
              post("/api/v1/employees")
                  .content(objectMapper.writeValueAsString(employee))
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isBadRequest())
          .andDo(print())
          .andExpect(jsonPath("$.name", is("must not be null")));
    }
  }
}
