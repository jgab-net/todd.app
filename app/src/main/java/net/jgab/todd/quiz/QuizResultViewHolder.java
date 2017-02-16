package net.jgab.todd.quiz;

import android.content.Context;
import android.view.View;
;
import android.widget.TextView;

import net.jgab.todd.R;
import net.jgab.todd.core.utils.ScreenUtils;
import net.jgab.todd.to.Result;

/**
 * Created by sid on 15-02-17.
 */

class QuizResultViewHolder {

    private TextView quiz;
    private TextView percent;

    QuizResultViewHolder(View itemView) {
        quiz = ScreenUtils.findViewById(itemView, R.id.quiz);
        percent = ScreenUtils.findViewById(itemView, R.id.percent);
    }


    void bind(Context context, final Result result,
              final QuizListAdapter.QuizListListener listener, boolean isExpanded) {

        quiz.setText(result.getQuiz().getTitle());

        percent.setText(result.getFormattedPercent(context));
    }

}
