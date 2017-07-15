package kz.tanat.web.controller;

import kz.tanat.app.employee.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * Web контроллер для Employee.
 *
 * @author Tanat
 * @since 14.07.2017.
 */
@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("list", "employees", employeeService.getAll());
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable String id) {
        try {
            return new ModelAndView("show", "employee", employeeService.get(id));
        } catch (IllegalArgumentException e) {
            return new ModelAndView("error/404", new HashMap<String, String>(){{put("error", e.getMessage());}}, HttpStatus.NOT_FOUND);
        }
    }
}
