package com.example.hrapp.admin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrapp.R;
import com.example.hrapp.models.Models;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

import static com.example.hrapp.models.Models.EmployeeApplication.APPROVED;
import static com.example.hrapp.models.Models.EmployeeApplication.APP_STATUS;
import static com.example.hrapp.models.Models.EmployeeApplication.DENIED;
import static com.example.hrapp.models.Models.EmployeeApplication.JOB_APPLICATION_DB;

public class AppRv extends RecyclerView.Adapter<AppRv.ViewHolder> {

    private final LinkedList<Models.EmployeeApplication> applicationList;
    private ItemClickListener mClickListener;
    private final Context mContext;
    private final String appId;


    public AppRv(Context context, LinkedList<Models.EmployeeApplication> applicationList, String appId) {
        this.applicationList = applicationList;
        this.mContext = context;
        this.appId = appId;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_request_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.why.setText("Why get job : " + applicationList.get(position).getWhyText());
        holder.cover.setText("Cover Letter :  " + applicationList.get(position).getCoverLetter());
        holder.time.setText("Applied at " + applicationList.get(position).getCreatedAt());
        holder.user.setText("User email is : "+applicationList.get(position).getEmail_address());
        holder.deny.setOnClickListener(v -> denyApplication(position));
        holder.aprove.setOnClickListener(v -> acceptApplication(position));

        if (applicationList.get(position).getStatus().equals(APPROVED)) {
            holder.statusBg.setCardBackgroundColor(ColorStateList.valueOf(Color.GREEN));
        } else if (applicationList.get(position).getStatus().equals(DENIED)) {
            holder.statusBg.setCardBackgroundColor(ColorStateList.valueOf(Color.RED));
        } else {
            holder.statusBg.setCardBackgroundColor(ColorStateList.valueOf(Color.BLACK));
        }


    }

    private void acceptApplication(int pos) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(JOB_APPLICATION_DB).document(applicationList.get(pos).getUid()).update(APP_STATUS, APPROVED).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(mContext, "Accepted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Failed to accept", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void denyApplication(int pos) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(JOB_APPLICATION_DB).document(applicationList.get(pos).getUid()).update(APP_STATUS, DENIED).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(mContext, "Denied", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Approved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button aprove, deny;
        CardView statusBg;
        TextView user, cover, why, time;

        ViewHolder(View itemView) {
            super(itemView);
            aprove = itemView.findViewById(R.id.approveButton);
            deny = itemView.findViewById(R.id.denyButton);
            user = itemView.findViewById(R.id.user__tv);
            cover = itemView.findViewById(R.id.cover_letter_tv);
            why = itemView.findViewById(R.id.why_tv);
            time = itemView.findViewById(R.id.jobPostedAt);
            statusBg = itemView.findViewById(R.id.statusBg);

            // usersApplied = itemView.findViewById(R.id.usersApplied);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}