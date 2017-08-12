package kz.tanat.app.employee;

import kz.tanat.app.employee.dto.AddressDto;
import kz.tanat.app.employee.dto.EmployeeDto;
import kz.tanat.app.employee.dto.NameDto;
import kz.tanat.app.employee.dto.PhoneDto;
import kz.tanat.domain.DomainEventPublisher;
import kz.tanat.domain.EventTracking;
import kz.tanat.domain.employee.EmployeeId;
import kz.tanat.domain.employee.EmployeeRepository;
import kz.tanat.domain.employee.event.EmployeeArchived;
import kz.tanat.domain.employee.event.EmployeeReinstated;
import kz.tanat.domain.event.EventRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Тестирование сервиса по работе с агрегатом/сущностью сотрудник.
 *
 * @author Tanat
 * @since 08.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
	private EventTracking eventTracking;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private EventRepository eventRepository;

//    @Mock
//    private StoredEvent storedEvent;

	private EmployeeService service;

	private UUID uuidId = new UUID(0L, 1L);
	private String stringId = uuidId.toString();
	private NameDto name = new NameDto("Пупкин", "Василий", "Петрович");
	private AddressDto address = new AddressDto("Россия", "Липецкая обл.", "г. Пушкин", "ул. Ленина", "25");
	private List<PhoneDto> phones = new ArrayList<>(Arrays.asList(
			new PhoneDto(7, "920", "00000001"),
			new PhoneDto(7, "910", "00000002")
	));

	private EmployeeDto createDto = new EmployeeDto(stringId, name, address, phones);

	@Before
	public void setUp() throws Exception {
		DomainEventPublisher.instance().reset();
		given(employeeRepository.findOne(new EmployeeId(uuidId))).willReturn(createDto.createEmployee(new EmployeeId(uuidId)));
//        when(eventStore.save(any(StoredEvent.class))).thenReturn(storedEvent);
//        given(eventStore.save(any(StoredEvent.class))).willReturn(storedEvent);

		service = new EmployeeService(employeeRepository, eventRepository);
		eventTracking = new EventTracking();
	}

	@Test
	public void create() throws Exception {
		service.create(createDto);

		EmployeeDto found = service.get(stringId);
		assertEquals(createDto.getId(), found.getId());
		assertEquals(createDto.getName(), found.getName());
		assertEquals(createDto.getAddress(), found.getAddress());
		assertThat(createDto.getPhones(), is(found.getPhones()));
	}

	@Test
	public void rename() throws Exception {
		NameDto nameDto = new NameDto("Test", "Test", "Test");

		service.create(createDto);
		service.rename(stringId, nameDto);

		EmployeeDto found = service.get(stringId);
		assertEquals(nameDto, found.getName());
	}

	@Test
	public void changeAddress() throws Exception {
		AddressDto addressDto = new AddressDto("Test", "Test", "Test", "Test", "Test");

		service.create(createDto);
		service.changeAddress(stringId, addressDto);

		EmployeeDto found = service.get(stringId);
		assertEquals(addressDto, found.getAddress());
	}

	@Test
	public void addPhone() throws Exception {
		PhoneDto phoneDto = new PhoneDto(7, "777", "000005");

		service.create(createDto);
		service.addPhone(stringId, phoneDto);
		phones.add(phoneDto);

		EmployeeDto found = service.get(stringId);
		assertThat(phones, is(found.getPhones()));
	}

	@Test
	public void removePhone() throws Exception {
		service.create(createDto);

		service.removePhone(stringId, 1);

		EmployeeDto found = service.get(stringId);
		assertEquals(1, found.getPhones().size());
	}

	@Test
	public void archive() throws Exception {
		LocalDate date = LocalDate.of(2017, 7, 9);
		service.create(createDto);

		service.archive(stringId, date);

		eventTracking.expectedLastEvent(EmployeeArchived.class);
		EmployeeArchived lastEvent = (EmployeeArchived) eventTracking.getLastEvent();
		assertEquals(stringId, lastEvent.getId().toString());
		assertEquals(date, lastEvent.getDate());
	}

	@Test
	public void reinstate() throws Exception {
		LocalDate date = LocalDate.of(2017, 7, 9);
		service.create(createDto);

		service.archive(stringId, date);
		service.reinstate(stringId, date);

		eventTracking.expectedLastEvent(EmployeeReinstated.class);
		EmployeeReinstated lastEvent = (EmployeeReinstated) eventTracking.getLastEvent();
		assertEquals(stringId, lastEvent.getId().toString());
		assertEquals(date, lastEvent.getDate());
	}

	@Test
	public void remove() throws Exception {
		service.create(createDto);

		service.archive(stringId, LocalDate.of(2017, 7, 9));
		service.remove(stringId);

		given(employeeRepository.findOne(new EmployeeId(uuidId))).willReturn(null);

		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("Employee not found.");
		service.get(stringId);
	}

}