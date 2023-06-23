package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.service.EmployeeServiceEM;
import com.example.demowithtests.util.mappers.EmployeeMapper;
import com.example.demowithtests.web.EmployeeController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = EmployeeController.class)
@DisplayName("Employee Controller Tests")
public class ControllerTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ObjectMapper mapper;

    @MockBean
    EmployeeService service;

    @MockBean
    EmployeeServiceEM serviceEM;

    @MockBean
    EmployeeMapper employeeMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST API -> /api/users")
    @WithMockUser(roles = "ADMIN")
    public void createPassTest() throws Exception {
        var response = new EmployeeDto();
        response.setId(1);
        response.setName("Mike");
        response.setEmail("mail@mail.com");

        var employee = Employee.builder()
                .id(1)
                .name("Mike")
                .email("mail@mail.com")
                .build();

        when(employeeConverter.toDto(any(Employee.class))).thenReturn(response);
        when(employeeConverter.toEmployee(any(EmployeeDto.class))).thenReturn(employee);
        when(service.create(any(Employee.class))).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name").value("Mike"));

        verify(service).create(any());
    }

    @Test
    @DisplayName("POST API -> /api/users/jpa")
    @WithMockUser(roles = "USER")
    public void testSaveWithJpa() throws Exception {

        var employeeToBeReturn = Employee.builder()
                .id(1)
                .name("Mark")
                .country("France")
                .build();

        doReturn(employeeToBeReturn).when(serviceEM).createWithJpa(any());
        when(this.serviceEM.createWithJpa(any(Employee.class))).thenReturn(employeeToBeReturn);
        // Execute the POST request
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/api/users/jpa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employeeToBeReturn))
                .with(csrf());
        mockMvc
                .perform(builder)
                .andExpect(status().isCreated())
                .andDo(print())
           //   .andExpect(jsonPath("$.id", is(1)))
                .andReturn().getResponse();

        verify(this.serviceEM, times(1)).createWithJpa(any(Employee.class));
        verifyNoMoreInteractions(this.serviceEM);
    }

    @Test
    @DisplayName("GET API -> /api/users/{id}")
    @WithMockUser(roles = "USER")
    public void getPassByIdTest() throws Exception {
        var response = EmployeeReadDto.builder()
                .name("Mike")
                .build();

        var employee = Employee.builder()
                .id(1)
                .name("Mike")
                .build();

        when(employeeConverter.toReadDto(any(Employee.class))).thenReturn(response);
        when(service.getById(1)).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = get("/api/users/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name", is("Mike")));

        verify(service).getById(1);
    }

    @Test
    @DisplayName("PUT API -> /api/users/{id}")
    @WithMockUser(roles = "ADMIN")
    public void updatePassByIdTest() throws Exception {
        var response = new EmployeeReadDto();
        response.setId(1);
        var employee = Employee.builder().id(1).build();

        when(employeeConverter.toDto(any(Employee.class))).thenReturn(response);
        when(employeeConverter.toEmployee(any(EmployeeDto.class))).thenReturn(employee);
        when(service.updateById(eq(1), any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toEmployeeReadDto(any(Employee.class))).thenReturn(response);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/users/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(service).updateById(eq(1), any(Employee.class));
    }

    @Test
    @DisplayName("DELETE API -> /api/users/{id}")
    @WithMockUser(roles = "ADMIN")
    public void deletePassTest() throws Exception {

        doNothing().when(service).removeById(1);

        MockHttpServletRequestBuilder mockRequest = patch("/api/users/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        verify(service).removeById(1);
    }

    @Test
    @DisplayName("GET API -> /api/users/pages")
    @WithMockUser(roles = "USER")
    public void getUsersPageTest() throws Exception {

        var employee = Employee.builder().id(1).name("John").country("US").build();
        var employeeTwo = Employee.builder().id(2).name("Jane").country("UK").build();
        var employeeThree = Employee.builder().id(3).name("Bob").country("US").build();

        List<Employee> list = Arrays.asList(employee, employeeTwo, employeeThree);
        Page<Employee> employeesPage = new PageImpl<>(list);
        Pageable pageable = PageRequest.of(0, 5);

        EmployeeReadDto dto = new EmployeeReadDto();
        EmployeeReadDto dtoTwo = new EmployeeReadDto();
        EmployeeReadDto dtoThree = new EmployeeReadDto();

        when(service.getAllWithPagination(eq(pageable))).thenReturn(employeesPage);
        when(employeeMapper.toEmployeeReadDto(employee)).thenReturn(dto);
        when(employeeMapper.toEmployeeReadDto(employeeTwo)).thenReturn(dtoTwo);
        when(employeeMapper.toEmployeeReadDto(employeeThree)).thenReturn(dtoThree);

        MvcResult result = mockMvc.perform(get("/api/users/pages")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andReturn();

        verify(service).getAllWithPagination(eq(pageable));
        verify(employeeMapper, times(1)).toEmployeeReadDto(employee);
        verify(employeeMapper, times(1)).toEmployeeReadDto(employeeTwo);
        verify(employeeMapper, times(1)).toEmployeeReadDto(employeeThree);

        String contentType = result.getResponse().getContentType();
        assertNotNull(contentType);
        assertTrue(contentType.contains(MediaType.APPLICATION_JSON_VALUE));
        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
    }


    @Test
    @DisplayName("GET /api/users/e_null")
    @WithMockUser(roles = "USER")
    public void getEmailsByNullTest() throws Exception {
        var employee = EmployeeDto.builder()
                .id(1).name("John")
                .email(null)
                .build();

        List<EmployeeDto> employees = List.of(employee);

        when(employeeConverter.toDtoList(anyList())).thenReturn(employees);

        MvcResult result = mockMvc.perform(get("/api/users/e_null"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andReturn();

        verify(service).filterByEmailIsNull();
        String contentType = result.getResponse().getContentType();
        assertNotNull(contentType);
        assertTrue(contentType.contains(MediaType.APPLICATION_JSON_VALUE));
        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
    }


    @Test
    @DisplayName("PATCH /api/users/c_up_upper")
    @WithMockUser(roles = "ADMIN")
    public void updateCountryFirstLetterToUpperCaseTest() throws Exception {
        var employee = EmployeeDto.builder()
                .id(1)
                .name("John")
                .email("joth@gmail.com")
                .country("Us")
                .build();

        List<EmployeeDto> employees = List.of(employee);

        when(employeeConverter.toDtoList(anyList())).thenReturn(employees);

        MvcResult result = mockMvc.perform(patch("/api/users/c_up_upper"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].country", is("Us")))
                .andReturn();

        verify(service).updateCountryFirstLetterToUpperCase();

        String contentType = result.getResponse().getContentType();
        assertNotNull(contentType);
        assertTrue(contentType.contains(MediaType.APPLICATION_JSON_VALUE));
        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
    }

}