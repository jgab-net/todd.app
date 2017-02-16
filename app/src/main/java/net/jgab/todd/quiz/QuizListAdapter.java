package net.jgab.todd.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import net.jgab.todd.R;
import net.jgab.todd.to.Answer;
import net.jgab.todd.to.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sid on 15-02-17.
 */

class QuizListAdapter extends BaseExpandableListAdapter {

    private QuizListListener listener;
    private Context context;
    private List<Result> results = new ArrayList<>();

    QuizListAdapter(QuizListListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        QuizResultViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.element_quizzes_result, parent, false);
            holder = new QuizResultViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (QuizResultViewHolder) convertView.getTag();

        holder.bind(context, (Result) getGroup(groupPosition), listener, isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        AnswerViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.element_quizzes_answer, parent, false);
            holder = new AnswerViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (AnswerViewHolder) convertView.getTag();
        holder.bind(
                (Result) getGroup(groupPosition),
                childPosition,
                (Answer) getChild(groupPosition, childPosition),
                listener
        );
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    @Override
    public int getGroupCount() {
        return results.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return results.get(groupPosition).getAnswers().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return results.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return results.get(groupPosition).getAnswers().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return results.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return results.get(groupPosition).getAnswers().get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
            return true;
    }

    void addResults(List<Result> results) {
        this.results.addAll(results);
        notifyDataSetChanged();
    }

    interface QuizListListener {
        void onAnswerChangedClicked(Result result, int answerPosition, boolean isChecked);

    }
}
