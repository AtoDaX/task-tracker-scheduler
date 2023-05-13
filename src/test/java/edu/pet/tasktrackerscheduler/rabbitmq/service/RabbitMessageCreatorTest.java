package edu.pet.tasktrackerscheduler.rabbitmq.service;

import edu.pet.tasktrackerscheduler.rabbitmq.dto.EmailDto;
import edu.pet.tasktrackerscheduler.scheduler.dto.SummaryDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class RabbitMessageCreatorTest {


    private RabbitMessageCreator rabbitMessageCreator;

    @BeforeEach
    protected void setUp(){
        rabbitMessageCreator = new RabbitMessageCreator();
    }

    @Test
    public void testCreateWelcomeMessage() {
        // Arrange
        String receiverEmail = "test@test.com";

        // Act
        EmailDto emailDto = rabbitMessageCreator.createWelcomeMessage(receiverEmail);

        // Assert
        assertEquals(receiverEmail, emailDto.getReceiverEmail());
        assertEquals("WELCOME", emailDto.getSubject());
        assertEquals("WELCOME", emailDto.getBody());
    }

    @Test
    public void testCreateSummaryMessageWithCompletedTasks() {
        // Arrange
        SummaryDto summaryDto = new SummaryDto();
        summaryDto.setReceiverEmail("test@test.com");
        summaryDto.setCompletedTodayCount(2);
        summaryDto.setCompletedTodayTitles(Arrays.asList("Task 1", "Task 2"));
        summaryDto.setNotCompletedCount(0);


        // Act
        EmailDto emailDto = rabbitMessageCreator.createSummaryMessage(summaryDto);

        // Assert
        assertEquals("test@test.com", emailDto.getReceiverEmail());
        assertEquals("Summary", emailDto.getSubject());
        assertEquals("Good job! Today you completed 2 tasks! Some of them: Task 1, Task 2.", emailDto.getBody());
    }

    @Test
    public void testCreateSummaryMessageWithNotCompletedTasks() {
        // Arrange
        SummaryDto summaryDto = new SummaryDto();
        summaryDto.setReceiverEmail("test@test.com");
        summaryDto.setCompletedTodayTitles(new ArrayList<>());
        summaryDto.setCompletedTodayCount(0);
        summaryDto.setNotCompletedCount(2);
        summaryDto.setNotCompletedTitles(Arrays.asList("Task 1", "Task 2"));

        // Act
        EmailDto emailDto = rabbitMessageCreator.createSummaryMessage(summaryDto);

        // Assert
        assertEquals("test@test.com", emailDto.getReceiverEmail());
        assertEquals("Summary", emailDto.getSubject());
        assertEquals("You have 2 tasks to do! Some of them: Task 1, Task 2.", emailDto.getBody());
    }

    @Test
    public void testCreateSummaryMessageWithBothCompletedAndNotCompletedTasks() {
        // Arrange
        SummaryDto summaryDto = new SummaryDto();
        summaryDto.setReceiverEmail("test@test.com");
        summaryDto.setCompletedTodayCount(2);
        summaryDto.setCompletedTodayTitles(Arrays.asList("Task 1", "Task 2"));
        summaryDto.setNotCompletedCount(2);
        summaryDto.setNotCompletedTitles(Arrays.asList("Task 3", "Task 4"));

        // Act
        EmailDto emailDto = rabbitMessageCreator.createSummaryMessage(summaryDto);

        // Assert
        assertEquals("test@test.com", emailDto.getReceiverEmail());
        assertEquals("Summary", emailDto.getSubject());
        assertEquals("Good job! Today you completed 2 tasks! Some of them: Task 1, Task 2. But dont relax! You also have 2 tasks to do! Some of them: Task 3, Task 4.", emailDto.getBody());
    }

    @Test
    public void testCreateSummaryMessageWithNoTasks() {
        // Arrange
        SummaryDto summaryDto = new SummaryDto();
        summaryDto.setReceiverEmail("test@test.com");
        summaryDto.setNotCompletedCount(0);
        summaryDto.setCompletedTodayCount(0);

        // Act
        EmailDto emailDto = rabbitMessageCreator.createSummaryMessage(summaryDto);

        // Assert
        assertEquals("test@test.com", emailDto.getReceiverEmail());
        assertEquals("Summary", emailDto.getSubject());
        assertEquals("notasksmessage", emailDto.getBody());
    }
}
