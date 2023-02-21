package com.example.demo.controller;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.model.Status;
import com.example.demo.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
public class EmployeeController {

  @Autowired
  private EmployeeServiceImpl employeeService;


    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/employees")
    public List<EmployeeDTO> getEmployee(){
        return employeeService.getEmployees();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> getBYId(@PathVariable int id){
      final EmployeeDTO employee = employeeService.getEmployee(id);
       return ResponseEntity.ok(employee);

    }

  @GetMapping("/employee/filter")
  public ResponseEntity<List<EmployeeDTO>> getBYId(@RequestParam(value = "status", required = false) Status status,
                                             @RequestParam(value = "salary", required = false) Double salary
  ) {
    final List<EmployeeDTO> employeeDTOS = employeeService.filterEmployee(status,salary);
    return ResponseEntity.ok(employeeDTOS);
  }


    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employee){
      final EmployeeDTO savedEmployee = employeeService.createEmployee(employee);
      return ResponseEntity.ok(savedEmployee);
    }
  @PostMapping("/employee/read/post")
  public ResponseEntity<List<EmployeeDTO>> addEmployeeData() throws IOException {
    final List<EmployeeDTO> employeeDTOS = employeeService.readAndWrite();
    return ResponseEntity.ok(employeeDTOS);
  }
  @GetMapping("/employee/read")
  public ResponseEntity<List<EmployeeDTO>> fetchEmployeeData() throws IOException {
    final List<EmployeeDTO> employeeDTOS = employeeService.read();
    return ResponseEntity.ok(employeeDTOS);
  }
}

