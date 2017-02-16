package net.jgab.todd.patient;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import net.jgab.todd.R;
import net.jgab.todd.core.utils.ScreenUtils;
import net.jgab.todd.to.Result;

/**
 * Created by sid on 15-02-17.
 */

class ResultViewHolder {

    private TextView quiz;
    private TextView result;

    ResultViewHolder(View itemView) {

        quiz = ScreenUtils.findViewById(itemView, R.id.quiz);
        result = ScreenUtils.findViewById(itemView, R.id.result);
    }

    void bind(Context context, Result result) {

        quiz.setText(result.getQuiz().getTitle());
        this.result.setText(String.valueOf(result.getFormattedPercent(context)));
    }
}
