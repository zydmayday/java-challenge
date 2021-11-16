package jp.co.axa.apidemo.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("Test GET /api/v1/employees")
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest_GetEmployees {

  private final ObjectMapper objectMapper = new ObjectMapper();
  @Autowired private MockMvc mockMvc;
  @Autowired private EmployeeRepository employeeRepository;

  @BeforeEach
  public void init() {
    // Initialize some data for testing
    Employee employee1 = new Employee(1L, "number-1", "name-1", 100, "dept-1");
    Employee employee2 = new Employee(2L, "number-2", "name-2", 200, "dept-2");
    Employee employee3 = new Employee(3L, "number-3", "name-3", 300, "dept-3");
    Employee employee4 = new Employee(4L, "number-4", "name-4", 400, "dept-4");
    employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3, employee4));
  }

  @AfterEach
  public void tearDown() {
    employeeRepository.deleteAll();
  }

  @DisplayName("Fetch all employees by default, page = 0, size = 10")
  @Test
  public void testGetEmployees_noParam() throws Exception {
    mockMvc
        .perform(get("/api/v1/employees"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(4)));
  }

  @DisplayName("Fetch employees, page = 0, size = 2")
  @Test
  public void testGetEmployees_page0_size2() throws Exception {
    mockMvc
        .perform(get("/api/v1/employees?page=0&size=2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(2)));
  }

  @DisplayName("Fetch employees, page = 1, size = 3")
  @Test
  public void testGetEmployees_page1_size3() throws Exception {
    mockMvc
        .perform(get("/api/v1/employees?page=1&size=3"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(1)))
        .andExpect(jsonPath("$[0].name", is("name-4")));
  }
}
