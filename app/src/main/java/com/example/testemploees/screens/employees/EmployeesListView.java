package com.example.testemploees.screens.employees;

import com.example.testemploees.pojo.Employee;

import java.util.List;

public interface EmployeesListView {
    void showData(List<Employee> employees);
    void showError();
}
