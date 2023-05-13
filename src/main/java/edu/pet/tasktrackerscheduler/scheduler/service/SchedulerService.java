package edu.pet.tasktrackerscheduler.scheduler.service;

import edu.pet.tasktrackerscheduler.rabbitmq.producer.RabbitMessageSender;
import edu.pet.tasktrackerscheduler.scheduler.dto.SummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
//todo decomposition
@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final RabbitMessageSender rabbitMessageSender;
    private final SummaryService summaryService;
    @Scheduled(cron = "0 0 0 * * *")
    public void sendSummaryEmails(){
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Timestamp previous = Timestamp.valueOf(LocalDateTime.now().minusHours(24));

        List<SummaryDto> summaryList = summaryService.getSummaryList(now, previous);

        for (SummaryDto summary:
             summaryList) {

            rabbitMessageSender.sendSummaryEmail(summary);
        }
    }
}
