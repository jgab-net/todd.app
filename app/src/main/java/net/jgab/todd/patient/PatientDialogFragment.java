package net.jgab.todd.patient;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.jgab.todd.R;
import net.jgab.todd.core.utils.ScreenUtils;
import net.jgab.todd.to.Patient;

/**
 * Created by sid on 15-02-17.
 */

public class PatientDialogFragment extends DialogFragment {

    private PatientDialogListener listener;

    private TextView emailTextView;
    private TextView nameTextView;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_patient, null))
                .setPositiveButton(R.string.patient_add, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();


        Button okButton = ((AlertDialog)getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);

        emailTextView = ScreenUtils.<TextView>findViewById(getDialog(), R.id.email);
        nameTextView = ScreenUtils.<TextView>findViewById(getDialog(), R.id.name);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid(emailTextView.getText(), nameTextView.getText())) {
                    listener.onPatientAdded(addPatient());
                    PatientDialogFragment.this.dismiss();
                }
            }
        });
    }

    private Patient addPatient() {
        Patient patient = new Patient();

        patient.setEmail(emailTextView.getText().toString());
        patient.setName(nameTextView.getText().toString());
        patient.save();

        return patient;
    }

    private boolean isValid(CharSequence email, CharSequence name) {

        emailTextView.setError(null);
        nameTextView.setError(null);

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailTextView.setError(getString(R.string.email_error));
            return false;
        }

        if (TextUtils.isEmpty(name)) {
            nameTextView.setError(getString(R.string.name_error));
            return false;
        }

        return true;
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (PatientDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement PatientDialogListener");
        }
    }

    interface PatientDialogListener {
        void onPatientAdded(Patient patient);
    }
}
