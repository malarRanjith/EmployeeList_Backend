package com.example.demo.Services;

import com.example.demo.Enitites.Department;
import com.example.demo.Enitites.Employee;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class DepartmentService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<Employee> getEmployeesInDepartment(Long deptId) {
        Department department = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return department.getEmployees();
    }

    @Transactional
    public Employee addEmployeeToDepartment(Long deptId, Employee employee) {
        Department department = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        employee.setDepartment(department);

        department.getEmployees().add(employee);

        employeeRepository.save(employee);
        return employee;
    }

    @Transactional
    public ResponseEntity<String> deleteEmployee(Long deptId, String empId) {
        Optional<Department> department = departmentRepository.findById(deptId);

        if (!department.isPresent())
            return ResponseEntity.ok().body("Department not found");

        Optional<Employee> employee = department.get().getEmployees().stream()
                .filter(e -> e.getId().equals(empId))
                .findFirst();
        if (!employee.isPresent())
            return ResponseEntity.ok().body("Employee not found");

        department.get().getEmployees().remove(employee);
        employeeRepository.delete(employee.get());
        return ResponseEntity.ok().body("Deleted");
    }
}

