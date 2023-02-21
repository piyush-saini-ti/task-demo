package com.example.demo.dto;

import com.example.demo.model.Employee;
import com.example.demo.model.Status;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class EmployeeDTO implements Comparable<EmployeeDTO>
{


    private Integer id;

    @NotBlank(message = "name cannot blank")
    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    private String name;
    private Double salary;
    private Status status;

    public EmployeeDTO(){

    }

    public EmployeeDTO(Integer id, String name, Double salary, Status status) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.status = status;
    }

    public EmployeeDTO(String name, Double salary, Status status) {
        this.name = name;
        this.salary = salary;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int compareTo(EmployeeDTO o) {
        return   this.getId()- o.getId();
    }
}
