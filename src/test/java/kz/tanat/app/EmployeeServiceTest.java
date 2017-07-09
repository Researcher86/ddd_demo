package kz.tanat.app;

import kz.tanat.app.dto.AddressDto;
import kz.tanat.app.dto.EmployeeDto;
import kz.tanat.app.dto.NameDto;
import kz.tanat.app.dto.PhoneDto;
import kz.tanat.domain.EventTracking;
import kz.tanat.domain.employee.event.EmployeeArchived;
import kz.tanat.domain.employee.event.EmployeeReinstated;
import kz.tanat.infrastructure.persistence.MemoryEmployeeRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Тестирование сервиса по работе с агрегатом/сущностью сотрудник.
 *
 * @author Tanat
 * @version 1.1
 * @since 08.07.2017.
 */
public class EmployeeServiceTest {
    private EventTracking eventTracking;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private EmployeeService service;

    private String id = "1";
    private NameDto name = new NameDto("Пупкин", "Василий", "Петрович");
    private AddressDto address = new AddressDto("Россия", "Липецкая обл.", "г. Пушкин", "ул. Ленина", "25");
    private List<PhoneDto> phones = new ArrayList<>(Arrays.asList(
            new PhoneDto(7, "920", "00000001"),
            new PhoneDto(7, "910", "00000002")
    ));

    private EmployeeDto createDto = new EmployeeDto() {{
        setName(name);
        setAddress(address);
        setPhones(phones);
    }};

    @Before
    public void setUp() throws Exception {
//        DomainEventPublisher.instance().reset();
        service = new DefaultEmployeeService(new MemoryEmployeeRepository());
        eventTracking = new EventTracking();
    }

    @Test
    public void create() throws Exception {
        service.create(createDto);

        EmployeeDto found = service.get(id);
        assertEquals(createDto.getName(), found.getName());
        assertEquals(createDto.getAddress(), found.getAddress());
        assertThat(createDto.getPhones(), is(found.getPhones()));
    }

    @Test
    public void rename() throws Exception {
        NameDto nameDto = new NameDto("Test", "Test", "Test");

        service.create(createDto);
        service.rename(id, nameDto);

        EmployeeDto found = service.get(id);
        assertEquals(nameDto, found.getName());
    }

    @Test
    public void changeAddress() throws Exception {
        AddressDto addressDto = new AddressDto("Test", "Test", "Test", "Test", "Test");

        service.create(createDto);
        service.changeAddress(id, addressDto);

        EmployeeDto found = service.get(id);
        assertEquals(addressDto, found.getAddress());
    }

    @Test
    public void addPhone() throws Exception {
        PhoneDto phoneDto = new PhoneDto(7, "777", "000005");

        service.create(createDto);
        service.addPhone(id, phoneDto);
        phones.add(phoneDto);

        EmployeeDto found = service.get(id);
        assertThat(phones, is(found.getPhones()));
    }

    @Test
    public void removePhone() throws Exception {
        service.create(createDto);

        service.removePhone(id, 1);

        EmployeeDto found = service.get(id);
        assertEquals(1, found.getPhones().size());
    }

    @Test
    public void archive() throws Exception {
        LocalDate date = LocalDate.of(2017, 7, 9);
        service.create(createDto);

        service.archive(id, date);

        eventTracking.expectedLastEvent(EmployeeArchived.class);
        EmployeeArchived lastEvent = (EmployeeArchived) eventTracking.getLastEvent();
        assertEquals(id, lastEvent.getId().toString());
        assertEquals(date, lastEvent.getDate());
    }

    @Test
    public void reinstate() throws Exception {
        LocalDate date = LocalDate.of(2017, 7, 9);
        service.create(createDto);

        service.archive(id, date);
        service.reinstate(id, date);

        eventTracking.expectedLastEvent(EmployeeReinstated.class);
        EmployeeReinstated lastEvent = (EmployeeReinstated) eventTracking.getLastEvent();
        assertEquals(id, lastEvent.getId().toString());
        assertEquals(date, lastEvent.getDate());
    }

    @Test
    public void remove() throws Exception {
        service.create(createDto);

        service.archive(id, LocalDate.of(2017, 7, 9));
        service.remove(id);

        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Employee not found.");
        service.get(id);
    }

}