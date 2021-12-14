package com.example.poc.masterspreparation;

import com.example.poc.masterspreparation.dto.AttendanceScheduleDto;
import com.example.poc.masterspreparation.model.AttendanceSchedule;
import com.example.poc.masterspreparation.model.Customer;
import com.example.poc.masterspreparation.repository.AttendanceRepository;
import com.example.poc.masterspreparation.repository.CustomerRepository;
import com.example.poc.masterspreparation.repository.RoleRepository;
import com.example.poc.masterspreparation.service.AttendanceService;
import com.example.poc.masterspreparation.service.AttendanceServiceImpl;
import com.example.poc.masterspreparation.service.CustomerService;
import com.example.poc.masterspreparation.service.CustomerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class AttendanceServiceTest {

    private AttendanceRepository attendanceRepository = mock(AttendanceRepository.class);
    private CustomerRepository customerRepository = mock(CustomerRepository.class);
    private RoleRepository roleRepository = mock(RoleRepository.class);
    private CustomerService customerService = new CustomerServiceImpl(customerRepository, roleRepository);
    AttendanceService attendanceService = new AttendanceServiceImpl(attendanceRepository, customerService);

    @Test
    public void saveAttendance_okTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Customer customer = new Customer();
        customer.setEmail("1@gmail.com");
        Customer worker = new Customer();
        worker.setEmail("2@gmail.com");
        when(customerRepository.findByEmail("1@gmail.com")).thenReturn(customer);
        when(customerRepository.findByEmail("2@gmail.com")).thenReturn(worker);
        when(attendanceRepository.save(getAttendanceSchedule(localDateTime))).thenReturn(getAttendanceSchedule(localDateTime));

        attendanceService.saveAttendance(getAttendanceScheduleDto(localDateTime));

        verify(customerRepository, times(2)).findByEmail(anyString());
        verify(attendanceRepository, times(1)).save(any());
    }

    @Test
    public void getAttendanceScheduleForDate_okTest() {
        when(attendanceRepository.findAll()).thenReturn(getListOfAttendance());

        List<AttendanceScheduleDto> actual = attendanceService.getAttendanceScheduleForDate(LocalDateTime.parse("2021-12-08T23:55:59.904833800"), LocalDateTime.parse("2021-12-29T23:55:59.904833800"), "worker@gmail.com");

        assertEquals(3, actual.size());
    }

    @Test
    public void getAttendanceScheduleForToday_okTest() {
        when(attendanceRepository.findAll()).thenReturn(getListOfAttendance());

        List<AttendanceScheduleDto> actual = attendanceService.getAttendanceScheduleForToday("worker@gmail.com");

        assertEquals(1, actual.size());
    }

    @Test
    public void getAttendanceScheduleById_okTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        when(attendanceRepository.findById(1L)).thenReturn(Optional.of(getAttendanceSchedule(localDateTime)));

        AttendanceScheduleDto actual = attendanceService.getAttendanceScheduleById(1L);

        assertEquals(getAttendanceScheduleDto(localDateTime), actual);
    }

    @Test
    public void deleteAttendanceScheduleById_okTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        when(attendanceRepository.getOne(1L)).thenReturn(getAttendanceSchedule(localDateTime));

        attendanceService.deleteAttendanceById(1L);

        verify(attendanceRepository, times(1)).getOne(any());
        verify(attendanceRepository, times(1)).delete(any());
    }


    private AttendanceScheduleDto getAttendanceScheduleDto(LocalDateTime localDateTime) {
        AttendanceScheduleDto attendanceScheduleDto = new AttendanceScheduleDto();
        attendanceScheduleDto.setClientEmail("1@gmail.com");
        attendanceScheduleDto.setWorkerEmail("2@gmail.com");
        attendanceScheduleDto.setDate(localDateTime.toString());
        attendanceScheduleDto.setComment("Comment");
        attendanceScheduleDto.setSum(400);

        return attendanceScheduleDto;

    }

    private AttendanceSchedule getAttendanceSchedule(LocalDateTime localDateTime) {
        AttendanceSchedule attendanceSchedule = new AttendanceSchedule();
        Customer customer = new Customer();
        customer.setEmail("1@gmail.com");
        Customer worker = new Customer();
        worker.setEmail("2@gmail.com");

        attendanceSchedule.setClient(customer);
        attendanceSchedule.setWorker(worker);
        attendanceSchedule.setDate(localDateTime);
        attendanceSchedule.setComment("Comment");
        attendanceSchedule.setSum(400);

        return attendanceSchedule;
    }

    private List<AttendanceSchedule> getListOfAttendance() {
        Customer worker = new Customer();
        worker.setEmail("worker@gmail.com");
        AttendanceSchedule attendanceSchedule1 = new AttendanceSchedule();
        attendanceSchedule1.setClient(new Customer());
        attendanceSchedule1.setWorker(worker);
        attendanceSchedule1.setDate(LocalDateTime.parse("2021-12-08T23:55:59.904833800"));
        attendanceSchedule1.setComment("Comment");
        attendanceSchedule1.setSum(400);

        AttendanceSchedule attendanceSchedule2 = new AttendanceSchedule();
        attendanceSchedule2.setClient(new Customer());
        attendanceSchedule2.setWorker(worker);
        attendanceSchedule2.setDate(LocalDateTime.parse("2021-12-29T20:55:59.904833800"));
        attendanceSchedule2.setComment("Comment");
        attendanceSchedule2.setSum(400);

        AttendanceSchedule attendanceSchedule3 = new AttendanceSchedule();
        attendanceSchedule3.setClient(new Customer());
        attendanceSchedule3.setWorker(worker);
        attendanceSchedule3.setDate(LocalDateTime.parse("2021-12-01T23:55:59.904833800"));
        attendanceSchedule3.setComment("Comment");
        attendanceSchedule3.setSum(400);

        AttendanceSchedule attendanceSchedule4 = new AttendanceSchedule();
        attendanceSchedule4.setClient(new Customer());
        attendanceSchedule4.setWorker(worker);
        attendanceSchedule4.setDate(LocalDateTime.now());
        attendanceSchedule4.setComment("Comment");
        attendanceSchedule4.setSum(400);


        return List.of(attendanceSchedule1, attendanceSchedule2, attendanceSchedule3, attendanceSchedule4);

    }
}
