package kz.tanat.web.app;

import kz.tanat.app.employee.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Web контроллер для Employee.
 *
 * @author Tanat
 * @since 14.07.2017.
 */
@Controller
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("employee/list", "employees", employeeService.getAll());
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable String id) {
        return new ModelAndView("employee/show", "employee", employeeService.get(id));
    }
}
