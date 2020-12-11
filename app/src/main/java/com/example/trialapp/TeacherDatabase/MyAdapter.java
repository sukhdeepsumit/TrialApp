package com.example.trialapp.TeacherDatabase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trialapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    protected void onBindViewHolder(@NonNull final MyViewHolder holder, final int position, @NonNull final Model model) {
        holder.firstName.setText(model.getFirstName());
        holder.lastName.setText(model.getLastName());
        holder.subject.setText(model.getSubject());
        holder.contact.setText(model.getContact());
        holder.email.setText(model.getEmail());
        holder.feeStatus.setText(model.getFeeStatus());

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus =DialogPlus.newDialog(holder.firstName.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_content))
                        .setExpanded(true, 1200)
                        .create();

                View myView = dialogPlus.getHolderView();
                final EditText firstName = myView.findViewById(R.id.updateFirstName);
                final EditText lastName = myView.findViewById(R.id.updateLastName);
                final EditText email = myView.findViewById(R.id.updateEmail);
                final EditText contact = myView.findViewById(R.id.updateContact);
                final EditText subject = myView.findViewById(R.id.updateSubject);
                final EditText feeStatus = myView.findViewById(R.id.updateFeeStatus);
                Button submit = myView.findViewById(R.id.submit);

                firstName.setText(model.getFirstName());
                lastName.setText(model.getLastName());
                email.setText(model.getEmail());
                contact.setText(model.getContact());
                subject.setText(model.getSubject());
                feeStatus.setText(model.getFeeStatus());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("firstName", firstName.getText().toString());
                        map.put("lastName", lastName.getText().toString());
                        map.put("email", email.getText().toString());
                        map.put("contact", contact.getText().toString());
                        map.put("subject", subject.getText().toString());
                        map.put("feeStatus", feeStatus.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child(new StudentDetails().user)
                                .child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(holder.firstName.getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.firstName.getContext(), "Updating Unsuccessful", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context;
                AlertDialog.Builder builder =new AlertDialog.Builder(holder.firstName.getContext());
                builder.setTitle("Delete Window");
                builder.setMessage("Do you want to delete ??");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child(new StudentDetails().user)
                                .child(Objects.requireNonNull(getRef(position).getKey())).removeValue();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {  }
                });

                builder.show();
            }
        });
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
