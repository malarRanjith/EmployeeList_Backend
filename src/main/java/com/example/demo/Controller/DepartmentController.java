package com.example.demo.Controller;

import com.example.demo.Enitites.Department;
import com.example.demo.Enitites.Employee;
import com.example.demo.Services.DepartmentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = departmentService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/departments/{deptId}/employees")
    public List<Employee> getEmployeesInDepartment(@PathVariable Long deptId) {
        return departmentService.getEmployeesInDepartment(deptId);
    }

    @PostMapping("/departments/{deptId}/employees")
    public ResponseEntity<String> addEmployeeToDepartment(@PathVariable Long deptId, @RequestBody Employee employee) {
        Employee addedEmployee = departmentService.addEmployeeToDepartment(deptId, employee);
        return ResponseEntity.ok().body(addedEmployee.getId());
    }

    @DeleteMapping("/departments/{deptId}/employees/{empId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long deptId, @PathVariable String empId) throws Exception {
        return departmentService.deleteEmployee(deptId, empId);
    }
}



