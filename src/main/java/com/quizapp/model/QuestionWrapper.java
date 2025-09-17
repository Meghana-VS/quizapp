package com.quizapp.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionWrapper {
    private Long id;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String question;
}
