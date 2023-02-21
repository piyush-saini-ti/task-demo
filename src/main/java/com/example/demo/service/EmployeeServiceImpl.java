package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.exception.EmployeeNotNull;
import com.example.demo.exception.ResourceNotFound;
import com.example.demo.model.Employee;
import com.example.demo.model.Status;
import com.example.demo.repo.EmployeeRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO.getName() == null || employeeDTO.getName().isEmpty()) {
            throw new EmployeeNotNull("Employee cannot be null or empty");
        } else {
            final Employee employee = modelMapper.map(employeeDTO, Employee.class);
            employeeRepo.save(employee);
            logger.info("saved employee : {}", employee);
            final EmployeeDTO savedEmployee = modelMapper.map(employee, EmployeeDTO.class);
            return savedEmployee;
        }
    }

    @Override
    public List<EmployeeDTO> getEmployees() {
        final List<Employee> employees = employeeRepo.findAll();
        logger.info("retrieving employee data");
        final List<EmployeeDTO> employeeDTOS;
        employeeDTOS = employees.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
        Collections.sort(employeeDTOS);
        return employeeDTOS;
    }


    @Override
    public EmployeeDTO getEmployee(Integer id) {
        final Optional<Employee> byId = employeeRepo.findById(id);
        if(byId.isEmpty()) throw new ResourceNotFound("Employee not found : "+id);
        return modelMapper.map(byId, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> readAndWrite() throws IOException {
        List<EmployeeDTO> employeeDTOs;
        try {
            employeeDTOs = objectMapper.readValue(new File("src/main/resources/employeeData.json"), new TypeReference<List<EmployeeDTO>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Employee> employees;
        employees = employeeDTOs.stream().map(employee -> modelMapper.map(employee, Employee.class))
                .collect(Collectors.toList());
        logger.info("saving employee : {}",employees);
        employeeRepo.saveAll(employees);
        return employeeDTOs;
    }

    @Override
    public List<EmployeeDTO> read() throws IOException {
        final List<EmployeeDTO> employeeDTOS;
        logger.info("retrieving employee data");
        try {
            employeeDTOS = objectMapper.readValue(new File("src/main/resources/employeeData.json"), new TypeReference<List<EmployeeDTO>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(employeeDTOS);
        return employeeDTOS;
    }

    @Override
    public List<EmployeeDTO> filterEmployee(Status status, Double salary) {
        List<Employee> employees = employeeRepo.findAll();
        List<EmployeeDTO> employeeDTOS = employees.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
        if (status == null) {
            return employeeDTOS.stream().filter(x -> x.getStatus().equals(Status.MARRIED) && x.getSalary() < 50000).collect(Collectors.toList());
        } else if (status.equals(Status.SINGLE)) {
            return employeeDTOS.stream().filter(x -> x.getStatus().equals(Status.SINGLE)).collect(Collectors.toList());
        } else if (status.equals(Status.MARRIED)) {
            return employeeDTOS.stream().filter(x -> x.getStatus().equals(Status.MARRIED)).collect(Collectors.toList());
        }
        return employeeDTOS;
    }
}
