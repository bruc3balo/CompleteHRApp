package com.example.hrapp.utils;

import java.util.Calendar;

import static com.example.hrapp.login.LoginActivity.truncate;
import static com.example.hrapp.models.Models.Applications.APPLICATION_SUR;
import static com.example.hrapp.models.Models.Tickets.TICKET_SUR;
import static com.example.hrapp.models.Models.TimeTableModel.TIMELINE_SUR;
import static com.example.hrapp.models.Models.Updates.UPDATE_SUR;

public interface IdGenerator {

    String time = truncate(Calendar.getInstance().getTime().toString(), 16);

    static String getUpdateId(String title) {
        return title.concat(time).concat(UPDATE_SUR);
    }

    static String getTicketId(String userId) {
        return userId.concat(time).concat(TICKET_SUR);
    }

    static String getAppId(String title) {
        return title.concat(time).concat(APPLICATION_SUR);
    }

    static String getTimelineId (String title) {
        return title.concat(time).concat(TIMELINE_SUR);
    }

}
