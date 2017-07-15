package kz.tanat.web.api;

import kz.tanat.app.employee.EmployeeService;
import kz.tanat.app.employee.dto.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * REST контроллер для Employee.
 *
 * @author Tanat
 * @since 15.07.2017.
 */
@RestController
@RequestMapping("/api/employees")
public class ApiEmployeeController {

    private final EmployeeService employeeService;

    public ApiEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity list() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> show(@PathVariable String id) {
//        try {
            return ResponseEntity.ok(employeeService.get(id));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.notFound().build();
//        }
    }
}
