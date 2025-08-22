package com.viraj.sample.service;

import com.viraj.sample.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Map<Long, Employee> employeeStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Initialize with some sample data
    public EmployeeServiceImpl() {
        Employee emp1 = new Employee("John Doe", "Software Developer");
        emp1.setEmployeeId(idGenerator.getAndIncrement());
        employeeStore.put(emp1.getEmployeeId(), emp1);

        Employee emp2 = new Employee("Jane Smith", "Project Manager");
        emp2.setEmployeeId(idGenerator.getAndIncrement());
        employeeStore.put(emp2.getEmployeeId(), emp2);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        if (employee.getEmployeeId() == 0) {
            employee.setEmployeeId(idGenerator.getAndIncrement());
        }
        employeeStore.put(employee.getEmployeeId(), employee);
        return employee;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        if (employeeStore.containsKey(employee.getEmployeeId())) {
            employeeStore.put(employee.getEmployeeId(), employee);
            return employee;
        }
        throw new RuntimeException("Employee with ID " + employee.getEmployeeId() + " not found");
    }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeStore.values());
    }

    @Override
    public Employee getEmployee(Long employeeId) {
        Employee employee = employeeStore.get(employeeId);
        if (employee == null) {
            throw new RuntimeException("Employee with ID " + employeeId + " not found");
        }
        return employee;
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        if (!employeeStore.containsKey(employeeId)) {
            throw new RuntimeException("Employee with ID " + employeeId + " not found");
        }
        employeeStore.remove(employeeId);
    }
}
