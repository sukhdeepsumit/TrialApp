package com.example.trialapp.TeacherDatabase;

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

public class MyAdapter extends FirebaseRecyclerAdapter<Model, MyAdapter.MyViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Model model) {
        holder.firstName.setText(model.getFirstName());
        holder.lastName.setText(model.getLastName());
        holder.subject.setText(model.getSubject());
        holder.contact.setText(model.getContact());
        holder.email.setText(model.getEmail());
        holder.feeStatus.setText(model.getFeeStatus());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_card, parent, false);
        return new MyViewHolder(view);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, lastName, subject, contact, feeStatus, email;
        Button update, delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.firstNameShow);
            lastName = itemView.findViewById(R.id.lastNameShow);
            subject = itemView.findViewById(R.id.subjectShow);
            contact = itemView.findViewById(R.id.PhoneTextShow);
            feeStatus = itemView.findViewById(R.id.feeStatusShow);
            email = itemView.findViewById(R.id.EmailTextShow);

            update = itemView.findViewById(R.id.update);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
