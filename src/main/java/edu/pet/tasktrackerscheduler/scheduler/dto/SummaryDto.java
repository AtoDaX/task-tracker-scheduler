package edu.pet.tasktrackerscheduler.scheduler.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryDto {
    @Email
    private String receiverEmail;

    private Integer completedTodayCount;
    private List<String> completedTodayTitles;

    private Integer notCompletedCount;
    private List<String> notCompletedTitles;

}
