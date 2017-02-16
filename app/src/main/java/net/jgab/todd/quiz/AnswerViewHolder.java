package net.jgab.todd.quiz;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import net.jgab.todd.R;
import net.jgab.todd.core.utils.ScreenUtils;
import net.jgab.todd.to.Answer;
import net.jgab.todd.to.Result;

/**
 * Created by sid on 15-02-17.
 */

class AnswerViewHolder {

    private TextView text;
    private SwitchCompat answer;

    AnswerViewHolder(View itemView) {
        text = ScreenUtils.findViewById(itemView, R.id.text);
        answer = ScreenUtils.findViewById(itemView, R.id.answer);
    }

    void bind(final Result result, final int answerPosition,
              final Answer answer, final QuizListAdapter.QuizListListener listener) {

        text.setText(answer.getQuestion().getText());

        this.answer.setChecked(answer.isAnswer());
        this.answer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onAnswerChangedClicked(result, answerPosition, isChecked);
            }
        });

    }

}
