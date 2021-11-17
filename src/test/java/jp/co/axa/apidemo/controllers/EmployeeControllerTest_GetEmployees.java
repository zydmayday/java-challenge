package jp.co.axa.apidemo.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jp.co.axa.apidemo.extension.H2EmployeeDataSetupExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(value = "test")
@DisplayName("Test GET /api/v1/employees")
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest_GetEmployees {

  @Autowired @RegisterExtension H2EmployeeDataSetupExtension h2EmployeeExtension;

  @Autowired private MockMvc mockMvc;

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

  @DisplayName("Fetch single employee with exists id")
  @Test
  public void testGetEmployee_existsId() throws Exception {
    mockMvc
        .perform(get("/api/v1/employees/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("name-1")));
  }

  @DisplayName("Fetch single employee with non-exists id")
  @Test
  public void testGetEmployee_nonExistsId() throws Exception {
    mockMvc
        .perform(get("/api/v1/employees/999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$", is("The employee try to get does not exists, id: 999")));
  }
}
