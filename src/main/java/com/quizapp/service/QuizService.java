package com.quizapp.service;

import com.quizapp.model.Question;
import com.quizapp.model.QuestionWrapper;
import com.quizapp.model.Quiz;
import com.quizapp.model.Result;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;
    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title){
        List<Question> randomQuestions = questionRepository.findRandomQuestionByCategory(category, numQ);
        Quiz quiz = Quiz.builder()
                .title(title)
                .questions(randomQuestions)
                .build();

        quizRepository.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestionsById(Long id){
        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Question> questionsList = quiz.get().getQuestions();
        List<QuestionWrapper> questionWrappersList = new ArrayList<>();

        for(Question q : questionsList){
            QuestionWrapper questionWrapper = QuestionWrapper.builder()
                    .id(q.getId())
                    .optionA(q.getOptionA())
                    .optionB(q.getOptionB())
                    .optionC(q.getOptionC())
                    .optionD(q.getOptionD())
                    .question(q.getQuestion())
                    .build();
            questionWrappersList.add(questionWrapper);
        }
        return new ResponseEntity<>(questionWrappersList, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Long id, List<Result> results){
        Quiz quiz = quizRepository.findById(id).get();
        List<Question> questionList = quiz.getQuestions();
        int correctAnswer = 0;

        // a map for quick lookup of questions by their ID
        Map<Long, Question> questionMap = questionList.stream()
                .collect(Collectors.toMap(Question::getId, question -> question));

        for(Result result : results){
            // Get the question from the map using the result's question ID
            Question question = questionMap.get(result.getId());

            // Check if the question exists and if the answer is correct
            if(question != null && result.getQuestionAnswer().equals(question.getCorrectAnswer())){
                correctAnswer++;
            }
        }
        return new ResponseEntity<>(correctAnswer, HttpStatus.OK);
    }
}
