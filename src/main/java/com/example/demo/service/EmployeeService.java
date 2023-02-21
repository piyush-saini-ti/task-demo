package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.model.Status;
import com.example.demo.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    List<EmployeeDTO> getEmployees();

    EmployeeDTO getEmployee(Integer id);

    List<EmployeeDTO> readAndWrite() throws IOException;

    List<EmployeeDTO> read() throws IOException;

    List<EmployeeDTO> filterEmployee(Status status, Double salary);
}
