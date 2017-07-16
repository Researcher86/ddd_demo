package kz.tanat.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kz.tanat.app.employee.EmployeeService;
import kz.tanat.app.employee.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST контроллер для Employee.
 *
 * @author Tanat
 * @since 15.07.2017.
 */
@RestController
@RequestMapping("/api/employees")
@Api(value = "employees", description = "Операции, относящиеся к сотрудникам")
public class ApiEmployeeController {

    private final EmployeeService employeeService;

    public ApiEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @ApiOperation(value = "Просмотреть список сотрудников", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получен список"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра этого ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу запрещен"),
            @ApiResponse(code = 404, message = "Ресурс не найден")
    })
    public Iterable list() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Поиск сотрудника по идентификатору", response = EmployeeDto.class)
    public EmployeeDto show(@PathVariable String id) {
        return employeeService.get(id);
    }
}
