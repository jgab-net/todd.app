package net.jgab.todd.patient;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.jgab.todd.R;
import net.jgab.todd.core.utils.ScreenUtils;
import net.jgab.todd.to.Patient;

/**
 * Created by sid on 15-02-17.
 */

class PatientViewHolder {

    private TextView name;
    private TextView email;
    private ImageView picture;
    private ImageButton toQuiz;

    PatientViewHolder(View itemView) {
        name = ScreenUtils.findViewById(itemView, R.id.name);
        email = ScreenUtils.findViewById(itemView, R.id.email);
        picture = ScreenUtils.findViewById(itemView, R.id.picture);
        toQuiz = ScreenUtils.findViewById(itemView, R.id.to_quiz);
    }


    void bind(Context context, final Patient patient,
              final PatientListAdapter.PatientListListener listener, boolean isExpanded) {

        name.setText(patient.getName());
        email.setText(patient.getEmail());
        bindPicture(context, picture, patient.getPictureUrl());

        toQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPatientActionClicked(patient);
            }
        });
    }

    private void bindPicture(Context context, ImageView view, String url) {
        Picasso.with(context)
                .load(url)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_person)
                .into(view);
    }
}
