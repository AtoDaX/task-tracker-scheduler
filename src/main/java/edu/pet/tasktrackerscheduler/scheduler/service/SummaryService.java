package edu.pet.tasktrackerscheduler.scheduler.service;

import edu.pet.tasktrackerscheduler.repository.TaskRepository;
import edu.pet.tasktrackerscheduler.repository.UserRepository;
import edu.pet.tasktrackerscheduler.scheduler.dto.SummaryDto;
import edu.pet.tasktrackerscheduler.scheduler.model.Task;
import edu.pet.tasktrackerscheduler.scheduler.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SummaryService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    //todo wrapper for timestamps??
    //todo decompose
    //todo tests
    public List<SummaryDto> getSummaryList(Timestamp now, Timestamp previous){
        List<SummaryDto> summaryList = new LinkedList<>();
        List<User> users = userRepository.findAll();

        for (User usr:
             users) {
            SummaryDto summaryDto = new SummaryDto();

            List<Task> completedToday = taskRepository.getTasksByUserAndCompletedAtBetween(usr, previous, now);
            List<String> completedTodayTitles = completedToday.stream()
                    .map(Task::getTitle)
                    .collect(Collectors.toList());
            Integer completedTodayCount = completedToday.size();

            List<Task> notCompleted = taskRepository.getTasksByUserAndCompleted(usr, false);
            List<String> notCompletedTitles = notCompleted.stream()
                    .map(Task::getTitle)
                    .collect(Collectors.toList());
            Integer notCompletedCount = notCompleted.size();

            summaryDto.setReceiverEmail(usr.getUsername());
            summaryDto.setCompletedTodayCount(completedTodayCount);
            summaryDto.setCompletedTodayTitles(completedTodayTitles);
            summaryDto.setNotCompletedCount(notCompletedCount);
            summaryDto.setNotCompletedTitles(notCompletedTitles);

            summaryList.add(summaryDto);
        }

        return summaryList;
    }
}
