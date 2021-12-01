package com.example.poc.masterspreparation.service;

import com.example.poc.masterspreparation.model.AttendanceSchedule;
import com.example.poc.masterspreparation.model.Customer;
import com.example.poc.masterspreparation.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final AttendanceRepository attendanceRepository;

    @Scheduled(cron = "* 0 8 * * *")
//    @Scheduled(cron = "30 * * * * *")
    public void sendMail() {
        List<AttendanceSchedule> customers = attendanceRepository.findAll();
        List<String> emails = getEmailAddress(customers);
        emails.forEach(this::send);

        System.out.println("Mail sent successfully");
    }

    private void send(String receiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        String text = " Не забудьте, що ми чекаємо на Вас сьогодні!";
        String subject = "Нагадування";
        message.setFrom("myuserforemail@gmail.com");
        message.setTo(receiver);
        message.setText(text);
        message.setSubject(subject);

        javaMailSender.send(message);
    }

    private List<String> getEmailAddress(List<AttendanceSchedule> customers) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String time = dtf.format(LocalDateTime.now());
        return customers.stream().filter(attendanceSchedule -> attendanceSchedule.getDate().toString().contains(time))
                .map(AttendanceSchedule::getClient).map(Customer::getEmail).collect(Collectors.toList());
    }
}
