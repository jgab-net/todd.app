package net.jgab.todd.sync;

import com.orm.SugarRecord;
import com.orm.SugarTransactionHelper;

import net.jgab.todd.to.Question;
import net.jgab.todd.to.Quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sid on 15-02-17.
 */

class SyncHelper {

    private List<SugarRecord> toSync = new ArrayList<>();

    SyncHelper(List<Quiz> quizzes) {

        for (Quiz quiz : quizzes) {
            toSync.add(quiz);
            for (Question question: quiz.getQuestions()) {
                question.setQuiz(quiz);
                toSync.add(question);
            }

        }
    }

    void store(final Callback callback) {

        SugarTransactionHelper.doInTransaction(new SugarTransactionHelper.Callback() {
            @Override
            public void manipulateInTransaction() {
                for (int i = 0, l = toSync.size(); i < l; i++) {
                    toSync.get(i).save();
                    callback.onItemStored(i, l);
                }

                callback.onReady();
            }
        });

    }

    interface Callback {
        void onItemStored(int current, int total);
        void onReady();
    }
}
