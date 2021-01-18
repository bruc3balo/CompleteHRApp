package com.example.hrapp.employees.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrapp.R;
import com.example.hrapp.employees.appAvailabilities.JobApplicationActivity;
import com.example.hrapp.models.Models;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

import static com.example.hrapp.models.Models.Applications.APP_ID;

public class AppAvailabilitiesRvAdapter extends RecyclerView.Adapter<AppAvailabilitiesRvAdapter.ViewHolder> {

    private final LinkedList<Models.Applications> applicationList;
    private ItemClickListener mClickListener;
    private final Context mContext;


    public AppAvailabilitiesRvAdapter(Context context, LinkedList<Models.Applications> applicationList) {
        this.applicationList = applicationList;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.jPostedAt.setText("Posted At "+applicationList.get(position).getCreatedAt());
        holder.jPos.setText("Job Position : \n \n"+applicationList.get(position).getAppPositionField());
        holder.jTitle.setText("Job Title : "+applicationList.get(position).getAppTitle());

        holder.jQual.setText("Job Qualification : \n"+applicationList.get(position).getAppQualifications());
        holder.jF.setText("Job Function : \n \n"+applicationList.get(position).getAppJobFunction());
        holder.jET.setText("Employment type : \n \n" + applicationList.get(position).getAppEmploymentType());

        holder.jR.setText("Job responsibilities : \n \n"+applicationList.get(position).getAppResponsibilities());
        holder.jD.setText("Job description : \n \n"+applicationList.get(position).getAppDescription());
      /*  try {
            holder.usersApplied.setText(String.valueOf(applicationList.get(position).getUsersApplied().size()));
        } catch (Exception e) {
            e.printStackTrace();
            holder.usersApplied.setText("-");
        }*/
        holder.apply.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, JobApplicationActivity.class).putExtra(APP_ID, applicationList.get(position).getAppId())));
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView jD, jR, jET, jF, jPos, jQual, jTitle, jPostedAt,usersApplied;
        Button apply;

        ViewHolder(View itemView) {
            super(itemView);
            jD = itemView.findViewById(R.id.jobDescription);
            jR = itemView.findViewById(R.id.jobResponsibilities);
            jET = itemView.findViewById(R.id.jobEmploymentType);

            jF = itemView.findViewById(R.id.jobFunction);
            jPos = itemView.findViewById(R.id.jobProfession);
            jQual = itemView.findViewById(R.id.jobQualification);

            jTitle = itemView.findViewById(R.id.jobTitle);
            jPostedAt = itemView.findViewById(R.id.jobPostedAt);
            apply = itemView.findViewById(R.id.applyJob);
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