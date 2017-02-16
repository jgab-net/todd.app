package net.jgab.todd.patient;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;

import net.jgab.todd.R;
import net.jgab.todd.core.utils.ScreenUtils;
import net.jgab.todd.to.Patient;

/**
 * Created by sid on 15-02-17.
 */

public class PatientDialogFragment extends DialogFragment {

    private PatientDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_patient, null))
                .setPositiveButton(R.string.patient_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        listener.onPatientAdded(addPatient(getDialog()));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();


    }

    private Patient addPatient(Dialog dialog) {
        Patient patient = new Patient();
        patient.setEmail(
                ScreenUtils.<TextView>findViewById(dialog, R.id.email).getText().toString()
        );
        patient.setName(
                ScreenUtils.<TextView>findViewById(dialog, R.id.name).getText().toString()
        );
        patient.save();
        return patient;
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
