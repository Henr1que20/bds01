package com.devsuperior.bds01.services;

import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.entities.Employee;
import com.devsuperior.bds01.repositories.DepartmentRepository;
import com.devsuperior.bds01.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAllPaged(Pageable pageable) {
        Page<Employee> list = employeeRepository.findAll(pageable);
        return list.map(employee -> new EmployeeDTO(employee));

    }

    @Transactional
    public EmployeeDTO insert(EmployeeDTO employeeDTO) {
        Employee entity = new Employee();

        entity.setName(employeeDTO.getName());
        entity.setEmail(employeeDTO.getEmail());
        entity.setDepartment(departmentRepository.getOne(employeeDTO.getDepartmentId()));
        employeeRepository.save(entity);
        return new EmployeeDTO(entity);

    }
}
