package kz.tanat.web.controller;

import kz.tanat.app.employee.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Web контроллер для Employee.
 *
 * @author Tanat
 * @version 1.0
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
    public ModelAndView get(@PathVariable String id) {
        return new ModelAndView("show", "employee", employeeService.get(id));
    }
}
