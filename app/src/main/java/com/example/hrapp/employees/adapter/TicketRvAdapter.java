package com.example.hrapp.employees.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrapp.R;
import com.example.hrapp.employees.updates.TicketComments;
import com.example.hrapp.models.Models;
import com.example.hrapp.utils.IdGenerator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Objects;

import static com.example.hrapp.models.Models.Tickets.TICKET_COMMENT;
import static com.example.hrapp.models.Models.Tickets.TICKET_DB;

public class TicketRvAdapter extends RecyclerView.Adapter<TicketRvAdapter.ViewHolder> {

    private LinkedList<Models.Tickets> list;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private final FragmentManager fm;


    public TicketRvAdapter(Context context, LinkedList<Models.Tickets> list, FragmentManager fm) {
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
            if (list.get(position).getTicketComments() == null) {
                addComment(position);
            } else {
               // showBottomSheetDialogFragment(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ticketUser, ticketContent, ticketPostedAt, ticketSolvedAt, ticketComments;

        ViewHolder(View itemView) {
            super(itemView);

            ticketUser = itemView.findViewById(R.id.ticketUser);
            ticketContent = itemView.findViewById(R.id.ticketContent);
            ticketPostedAt = itemView.findViewById(R.id.ticketPostedAt);
            ticketSolvedAt = itemView.findViewById(R.id.ticketSolvedAt);
            ticketComments = itemView.findViewById(R.id.ticketComments);

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
        TicketComments bottomSheetFragment = new TicketComments(comments);
        bottomSheetFragment.show(fm, bottomSheetFragment.getTag());
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
                LinkedList<Models.Comments> commentsLinkedList = new LinkedList<>();
                if (list.get(pos).getTicketComments() != null) {
                    commentsLinkedList = (LinkedList<Models.Comments>) list.get(pos).getTicketComments();

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