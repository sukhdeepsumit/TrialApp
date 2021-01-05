package com.example.trialapp.StudentDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trialapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class TeacherRecordAdapter extends FirebaseRecyclerAdapter<ModelTeacherRecord, TeacherRecordAdapter.MyViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TeacherRecordAdapter(@NonNull FirebaseRecyclerOptions<ModelTeacherRecord> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull ModelTeacherRecord model) {
        String name = model.getFirstName() + " " + model.getLastName();
        holder.name.setText(name);
        holder.email.setText(model.getMyEmail());
        holder.phone.setText(model.getMyContact());
        holder.subject.setText(model.getMySubject());
        holder.standard.setText(model.getMyStandard());

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_teacher_show, parent, false);
        return new MyViewHolder(view);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, email, phone, subject, standard;
        Button add, call;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameShow);
            email = itemView.findViewById(R.id.emailShow);
            subject = itemView.findViewById(R.id.subjectShow);
            phone = itemView.findViewById(R.id.phoneShow);
            standard = itemView.findViewById(R.id.standardShow);

            add = itemView.findViewById(R.id.add);
            call = itemView.findViewById(R.id.call);
        }
    }
}
