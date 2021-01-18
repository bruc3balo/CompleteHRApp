package com.example.hrapp.employees.updates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrapp.R;
import com.example.hrapp.employees.adapter.CommentsRvAdapter;
import com.example.hrapp.employees.adapter.TicketRvAdapter;
import com.example.hrapp.models.Models;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.LinkedList;

public class TicketComments extends BottomSheetDialogFragment {

    private final LinkedList<Models.Comments> ticketList;

    public TicketComments(LinkedList<Models.Comments> ticketList) {
        this.ticketList = ticketList;
       // System.out.println(ticketList + "commentList");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.comments_layout, container, false);

        RecyclerView commentsRv = root.findViewById(R.id.commentsRv);
        commentsRv.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        CommentsRvAdapter ticketRvAdapter = new CommentsRvAdapter(requireContext(),ticketList);
        commentsRv.setAdapter(ticketRvAdapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
