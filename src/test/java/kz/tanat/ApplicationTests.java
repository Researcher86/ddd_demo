package kz.tanat;

import kz.tanat.app.employee.EmployeeService;
import kz.tanat.app.employee.dto.AddressDto;
import kz.tanat.app.employee.dto.EmployeeDto;
import kz.tanat.app.employee.dto.NameDto;
import kz.tanat.app.employee.dto.PhoneDto;
import kz.tanat.domain.employee.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Интеграционные тесты всей системы.
 *
 * @author Tanat
 * @since 07.07.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {
    @Autowired
    private TestRestTemplate template;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void getAll() throws Exception {
        ResponseEntity<EmployeeDto[]> response = template.getForEntity("/api/employees", EmployeeDto[].class);

        assertThat(response.getStatusCodeValue(), equalTo(200));
        assertThat(Arrays.asList(response.getBody()), is(not(empty())));
    }

    @Test
    public void getOne() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto(EmployeeBuilder.instance().withId(new UUID(0, 1)).build());

        ResponseEntity<EmployeeDto> response = template.getForEntity("/api/employees/{id}", EmployeeDto.class, employeeDto.getId());

        assertThat(response.getStatusCodeValue(), equalTo(200));
        assertThat(response.getBody().getName(), equalTo(employeeDto.getName()));
    }

    @Test
    public void create() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto(EmployeeBuilder.instance().withId(UUID.randomUUID()).build());

        ResponseEntity<EmployeeDto> response = template.postForEntity("/api/employees", employeeDto, EmployeeDto.class);

        assertThat(response.getStatusCodeValue(), equalTo(201));
        assertThat(response.getBody(), equalTo(employeeDto));
    }

    @Test(expected = IllegalArgumentException.class)
    public void remove() throws Exception {
        String employeeId = new UUID(0, 2).toString();
        template.postForEntity("/api/employees/{id}/archive", null, Void.class, employeeId);

        template.delete("/api/employees/{id}", employeeId);

        employeeService.get(employeeId);
    }

    @Test
    public void rename() throws Exception {
        Employee employee = EmployeeBuilder.instance().build();
        NameDto nameDto = new NameDto(employee.getName());
        nameDto.setFirst("Nik");

        ResponseEntity<Void> response = template.postForEntity("/api/employees/{id}/rename", nameDto, Void.class, employee.getId().toString());

        NameDto storedName = employeeService.get(employee.getId().toString()).getName();

        assertThat(response.getStatusCodeValue(), equalTo(200));
        assertThat(storedName, equalTo(nameDto));
    }

    @Test
    public void changeAddress() throws Exception {
        Employee employee = EmployeeBuilder.instance().build();
        AddressDto addressDto = new AddressDto(employee.getAddress());
        addressDto.setHouse("15");

        ResponseEntity<Void> response = template.postForEntity("/api/employees/{id}/change-address", addressDto, Void.class, employee.getId().toString());

        AddressDto storedAddress = employeeService.get(employee.getId().toString()).getAddress();

        assertThat(response.getStatusCodeValue(), equalTo(200));
        assertThat(storedAddress, equalTo(addressDto));
    }

    @Test
    public void addPhone() throws Exception {
        Employee employee = EmployeeBuilder.instance().build();
        PhoneDto phoneDto = new PhoneDto(employee.getPhones().get(0));
        phoneDto.setNumber("555555");

        ResponseEntity<Void> response = template.postForEntity("/api/employees/{id}/phones", phoneDto, Void.class, employee.getId().toString());

        List<PhoneDto> phones = employeeService.get(employee.getId().toString()).getPhones();
        PhoneDto storedPhone = phones.get(phones.size() - 1);

        assertThat(response.getStatusCodeValue(), equalTo(200));
        assertThat(storedPhone, equalTo(phoneDto));
    }

    @Test
    public void deletePhone() throws Exception {
        UUID employeeId = new UUID(0, 1);
        template.delete("/api/employees/{id}/phones/{index}", employeeId.toString(), 1);

        List<PhoneDto> phones = employeeService.get(employeeId.toString()).getPhones();
        assertThat(phones.size(), equalTo(2));
    }

    @Test
    @Transactional
    public void archive() throws Exception {
        UUID employeeId = new UUID(0, 1);
        ResponseEntity<Void> response = template.postForEntity("/api/employees/{id}/archive", null, Void.class, employeeId.toString());

        Employee employee = employeeRepository.getOne(new EmployeeId(employeeId));
        assertThat(response.getStatusCodeValue(), equalTo(200));
        assertThat(employee.getCurrentStatus(), equalTo(new Status(Status.State.ARCHIVED, LocalDate.now())));
    }

    @Test
    @Transactional
    public void reinstate() throws Exception {
        UUID employeeId = new UUID(0, 1);
        template.postForEntity("/api/employees/{id}/archive", null, Void.class, employeeId.toString());

        ResponseEntity<Void> response = template.postForEntity("/api/employees/{id}/reinstate", null, Void.class, employeeId.toString());

        Employee employee = employeeRepository.getOne(new EmployeeId(employeeId));
        assertThat(response.getStatusCodeValue(), equalTo(200));
        assertThat(employee.getCurrentStatus(), equalTo(new Status(Status.State.ACTIVE, LocalDate.now())));
    }

}
