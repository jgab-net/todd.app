package net.jgab.todd.quiz;

import com.orm.SugarTransactionHelper;

import net.jgab.todd.to.Answer;
import net.jgab.todd.to.Patient;
import net.jgab.todd.to.Question;
import net.jgab.todd.to.Quiz;
import net.jgab.todd.to.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sid on 15-02-17.
 */

class ResultHelper {

    private Patient patient;
    private List<Result> results = new ArrayList<>();

    ResultHelper(Patient patient) {
        this.patient = patient;
    }

    List<Result> getAllPendingQuizzes() {

        SugarTransactionHelper.doInTransaction(new SugarTransactionHelper.Callback() {
            @Override
            public void manipulateInTransaction() {

                generateNewResults(patient.getNewQuizzes());

                results.addAll(populateAnswers(patient.getDoingQuizzes()));

            }
        });

        return results;
    }


    /*
        TODO: fork sugar, and add support for one-to-many relation for list,
        TODO: getting a list of parent objects and loading nested objects with parent_id.
        TODO: after, change that ugly code.
        TODO: maybe lazy loading on expanded list
     */
    private List<Result> populateAnswers(List<Result> results) {
        for (Result result: results) {
            result.setAnswers(result.getStoredAnswers());
        }
        return results;
    }

    private List<Result> generateNewResults(List<Quiz> quizzes) {
        List<Result> newResults = new ArrayList<>();

        for (Quiz quiz: quizzes) {
            Result result = new Result();
            result.setQuiz(quiz);
            result.setPatient(patient);
            result.save();

            result.setAnswers(new ArrayList<Answer>());

            for (Question question: quiz.getStoredQuestions()) {
                Answer answer = new Answer();
                answer.setResult(result);
                answer.setQuestion(question);
                answer.save();

                result.getAnswers().add(answer);
            }

            newResults.add(result);
        }
        return newResults;
    }
}
