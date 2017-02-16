package net.jgab.todd.patient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import net.jgab.todd.R;
import net.jgab.todd.to.Patient;
import net.jgab.todd.to.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sid on 15-02-17.
 */

class PatientListAdapter extends BaseExpandableListAdapter {

    private PatientListListener listener;
    private Context context;
    private List<Patient> patients = new ArrayList<>();

    PatientListAdapter(PatientListListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        PatientViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.element_patients_patient, parent, false);
            holder = new PatientViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (PatientViewHolder) convertView.getTag();

        holder.bind(context, (Patient) getGroup(groupPosition), listener, isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ResultViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.element_patients_result, parent, false);
            holder = new ResultViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ResultViewHolder) convertView.getTag();
        Result item = (Result) getChild(groupPosition, childPosition);
        holder.bind(context, item);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    @Override
    public int getGroupCount() {
        return patients.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return patients.get(groupPosition).getResults().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return patients.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return patients.get(groupPosition).getResults().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return patients.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return patients.get(groupPosition).getResults().get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
            return false;
    }

    void addPatients(List<Patient> patients) {
        this.patients.clear();
        this.patients.addAll(patients);
        notifyDataSetChanged();
    }

    void addPatient(Patient patient) {
        this.patients.add(patient);
        notifyDataSetChanged();
    }


    interface PatientListListener {
        void onPatientActionClicked(Patient patient);
    }
}
