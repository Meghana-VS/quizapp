package com.quizapp.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Result {
    private Long id;
    private String questionAnswer;

    public Result(Long id, String questionAnswer) {
        this.id = id;
        this.questionAnswer = questionAnswer;
    }
}
