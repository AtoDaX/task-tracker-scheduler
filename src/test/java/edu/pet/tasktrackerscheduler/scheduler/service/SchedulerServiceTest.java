package edu.pet.tasktrackerscheduler.scheduler.service;

import edu.pet.tasktrackerscheduler.rabbitmq.producer.RabbitMessageSender;
import edu.pet.tasktrackerscheduler.scheduler.dto.SummaryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchedulerServiceTest {

    @Mock
    private RabbitMessageSender rabbitMessageSender;

    @Mock
    private SummaryService summaryService;

    @InjectMocks
    private SchedulerService schedulerService;

    private Timestamp now;
    private Timestamp previous;



    @Test
    public void sendSummaryEmail_ValidSummaryDto_CallsRabbitMessageSender() {
        // Arrange
        List<SummaryDto> summaryList = new ArrayList<>();
        summaryList.add(new SummaryDto());

        when(summaryService.getSummaryList(any(Timestamp.class), any(Timestamp.class))).thenReturn(summaryList);

        // Act
        schedulerService.sendSummaryEmails();

        // Assert
        verify(rabbitMessageSender, times(1)).sendSummaryEmail(any(SummaryDto.class));
    }
}