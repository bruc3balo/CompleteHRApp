package com.example.hrapp.admin.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hrapp.R;
import com.example.hrapp.employees.updates.TicketComments;
import com.example.hrapp.models.Models;
import com.example.hrapp.utils.IdGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

import static com.example.hrapp.models.Models.Tickets.HIGH_S;
import static com.example.hrapp.models.Models.Tickets.LOW_S;
import static com.example.hrapp.models.Models.Tickets.MID_S;
import static com.example.hrapp.models.Models.Tickets.TICKET_COMMENT;
import static com.example.hrapp.models.Models.Tickets.TICKET_DB;

public class AdminTicketRvAdapter extends RecyclerView.Adapter<AdminTicketRvAdapter.ViewHolder> {

    private LinkedList<Models.Tickets> list;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private final FragmentManager fm;


    public AdminTicketRvAdapter(Context context, LinkedList<Models.Tickets> list, FragmentManager fm) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
        this.fm = fm;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.admin_ticket_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ticketUser.setText(list.get(position).getUserName());
        holder.ticketContent.setText(list.get(position).getTicketContent());
        holder.ticketPostedAt.setText(list.get(position).getDateCreatedAt());
        holder.ticketSolvedAt.setText(list.get(position).getDateSolvedAt());
        holder.ticketComments.setText("Status and Comments");
        holder.ticketComments.setOnClickListener(v -> {
            if (list.get(position).getTicketComments() == null || list.get(position).getTicketComments().size() == 0) {
                addComment(position);
            } else {
               // showBottomSheetDialogFragment(position);
            }
        });
        switch (list.get(position).getTicketSeverity()) {
            default:break;
            case LOW_S:
                holder.severityCard.setCardBackgroundColor(ColorStateList.valueOf(Color.CYAN));
                break;
            case MID_S:
                holder.severityCard.setCardBackgroundColor(ColorStateList.valueOf(Color.GREEN));
                break;
            case HIGH_S:
                holder.severityCard.setCardBackgroundColor(ColorStateList.valueOf(Color.RED));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ticketUser, ticketContent, ticketPostedAt, ticketSolvedAt, ticketComments;
        CardView severityCard;

        ViewHolder(View itemView) {
            super(itemView);

            ticketUser = itemView.findViewById(R.id.ticketUser);
            ticketContent = itemView.findViewById(R.id.ticketContent);
            ticketPostedAt = itemView.findViewById(R.id.ticketPostedAt);
            ticketSolvedAt = itemView.findViewById(R.id.ticketSolvedAt);
            ticketComments = itemView.findViewById(R.id.ticketComments);
            severityCard = itemView.findViewById(R.id.severityCard);

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

    public void showBottomSheetDialogFragment(int pos) {
        LinkedList<Models.Comments> comments = new LinkedList<>(list.get(pos).getTicketComments());
        System.out.println("commentList " + comments.get(0));
        TicketComments bottomSheetFragment = new TicketComments(comments);
        bottomSheetFragment.show(fm, bottomSheetFragment.getTag());
        bottomSheetFragment.getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    private void addComment(int pos) {
        Dialog d = new Dialog(mContext);
        d.setContentView(R.layout.new_comment);
        EditText field = d.findViewById(R.id.new_comment_field);
        Button update = d.findViewById(R.id.updateButton);
        d.show();
        update.setOnClickListener(v -> {
            Toast.makeText(mContext, "Updating comments", Toast.LENGTH_SHORT).show();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            if (!field.getText().toString().isEmpty()) {
                ArrayList<Models.Comments> commentsLinkedList = new ArrayList<>();
                if (list.get(pos).getTicketComments() != null) {
                    commentsLinkedList = (ArrayList<Models.Comments>) list.get(pos).getTicketComments();
                }
                commentsLinkedList.add(new Models.Comments(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), field.getText().toString(), IdGenerator.time));
                list.get(pos).setTicketComments(commentsLinkedList);
                db.collection(TICKET_DB).document(list.get(pos).getTicketId()).update(TICKET_COMMENT, commentsLinkedList).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(mContext, "Updated Comments", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        d.dismiss();
                    } else {
                        Toast.makeText(mContext, "Failed to update comment", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(mContext, "No Comment", Toast.LENGTH_SHORT).show();
                d.dismiss();
            }
        });
    }
}