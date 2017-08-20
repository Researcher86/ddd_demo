package kz.tanat.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kz.tanat.app.employee.EmployeeService;
import kz.tanat.app.employee.dto.AddressDto;
import kz.tanat.app.employee.dto.EmployeeDto;
import kz.tanat.app.employee.dto.NameDto;
import kz.tanat.app.employee.dto.PhoneDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
            @ApiResponse(code = 200, message = "Успешно")
    })
    public Iterable list() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Поиск сотрудника по идентификатору", response = EmployeeDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Ресурс не найден")
    })
    public EmployeeDto show(@PathVariable String id) {
        return employeeService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Добавить информацию о новом сотруднику", response = EmployeeDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Ресурс создан")
    })
    public EmployeeDto create(@RequestBody EmployeeDto employeeDto) {
        return employeeService.create(employeeDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удалить информацию о сотруднике")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Нет содержимого"),
            @ApiResponse(code = 404, message = "Ресурс не найден")
    })
    public void remove(@PathVariable String id) {
        employeeService.remove(id);
    }

    @PostMapping("/{id}/rename")
    @ApiOperation(value = "Переименование сотрудника")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Ресурс не найден")
    })
    public void rename(@PathVariable String id, @RequestBody NameDto nameDto) {
        employeeService.rename(id, nameDto);
    }

    @PostMapping("/{id}/change-address")
    @ApiOperation(value = "Смена адреса сотрудника")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Ресурс не найден")
    })
    public void changeAddress(@PathVariable String id, @RequestBody AddressDto addressDto) {
        employeeService.changeAddress(id, addressDto);
    }

    @PostMapping("/{id}/phones")
    @ApiOperation(value = "Добавить новый телефонный номер сотрудника")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Ресурс не найден")
    })
    public void addPhone(@PathVariable String id, @RequestBody PhoneDto phoneDto) {
        employeeService.addPhone(id, phoneDto);
    }

    @DeleteMapping("/{id}/phones/{index}")
    @ApiOperation(value = "Удалить телефонный номер сотрудника")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Ресурс не найден")
    })
    public void deletePhone(@PathVariable String id, @PathVariable Integer index) {
        employeeService.removePhone(id, index);
    }

    @PostMapping("/{id}/archive")
    @ApiOperation(value = "Перенести информацию о сотруднике в архив")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Ресурс не найден")
    })
    public void archive(@PathVariable String id) {
        employeeService.archive(id, LocalDate.now());
    }

    @PostMapping("/{id}/reinstate")
    @ApiOperation(value = "Восстановить информацию о сотруднике из архива")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Ресурс не найден")
    })
    public void reinstate(@PathVariable String id) {
        employeeService.reinstate(id, LocalDate.now());
    }
}
