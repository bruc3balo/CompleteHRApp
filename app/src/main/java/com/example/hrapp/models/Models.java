package com.example.hrapp.models;

import java.util.List;

public class Models {

    public static class Users {
        private String uid;
        public static final String UID = "uid";
        private String first_name;
        public static final String FIRST_NAME = "first_name";
        private String last_name;
        public static final String LAST_NAME = "last_name";
        private String secret_key;
        public static final String SECRET_KEY = "secret_key";
        private String date_of_birth;
        public static final String DOB = "date_of_birth";
        private String phone_number;
        public static final String PHONE_NO = "phone_number";
        private String email_address;
        public static final String EMAIL_ADDRESS = "email_address";
        private String wage;
        public static final String WAGE = "wage";
        private String leave_dates_available;
        public static final String LEAVE_DATES_AVAILABLE = "leave_dates_available";
        private String performance_ratings;
        public static final String PERFORMANCE_RATING = "performance_ratings";
        private String nationalId;
        public static final String NATIONAL_ID = "nationalId";
        private String kraPin;
        public static final String KRA_PIN = "kraPin";
        private String nssf;
        public static final String NSSF = "nssf";
        private String nhif;
        public static final String NHIF = "nhif";
        private String createdAt;
        public static final String CREATED_AT = "createdAt";
        private String role;
        public static final String ROLE = "role";
        private String position;
        public static final String POSITION = "position";


        public static final String ADMIN = "Admin";
        public static final String EMPLOYEE = "Employee";

        public static final String EMPLOYEE_DB = "Employees";

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public Users() {
        }

        public String getSecret_key() {
            return secret_key;
        }

        public void setSecret_key(String secret_key) {
            this.secret_key = secret_key;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public Users(String first_name) {
            this.first_name = first_name;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getDate_of_birth() {
            return date_of_birth;
        }

        public void setDate_of_birth(String date_of_birth) {
            this.date_of_birth = date_of_birth;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getEmail_address() {
            return email_address;
        }

        public void setEmail_address(String email_address) {
            this.email_address = email_address;
        }

        public String getWage() {
            return wage;
        }

        public void setWage(String wage) {
            this.wage = wage;
        }

        public String getLeave_dates_available() {
            return leave_dates_available;
        }

        public void setLeave_dates_available(String leave_dates_available) {
            this.leave_dates_available = leave_dates_available;
        }

        public String getPerformance_ratings() {
            return performance_ratings;
        }

        public void setPerformance_ratings(String performance_ratings) {
            this.performance_ratings = performance_ratings;
        }

        public String getNationalId() {
            return nationalId;
        }

        public void setNationalId(String nationalId) {
            this.nationalId = nationalId;
        }

        public String getKraPin() {
            return kraPin;
        }

        public void setKraPin(String kraPin) {
            this.kraPin = kraPin;
        }

        public String getNssf() {
            return nssf;
        }

        public void setNssf(String nssf) {
            this.nssf = nssf;
        }

        public String getNhif() {
            return nhif;
        }

        public void setNhif(String nhif) {
            this.nhif = nhif;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }

    public static class Updates {
        private String updateId;
        public static final String UPDATE_ID = "updateId";
        private String updateTitle;
        public static final String UPDATE_TITLE = "updateTitle";
        private String updatePostedAt;
        public static final String UPDATE_POSTED_AT = "updatePostedAt";
        private String updateContent;
        public static final String UPDATE_CONTENT = "updateContent";

        public static final String UPDATE_DB = "Updates";

        public static final String UPDATE_SUR = "UPD";

        public String getUpdateId() {
            return updateId;
        }

        public void setUpdateId(String updateId) {
            this.updateId = updateId;
        }

        public String getUpdateTitle() {
            return updateTitle;
        }

        public void setUpdateTitle(String updateTitle) {
            this.updateTitle = updateTitle;
        }

        public String getUpdatePostedAt() {
            return updatePostedAt;
        }

        public void setUpdatePostedAt(String updatePostedAt) {
            this.updatePostedAt = updatePostedAt;
        }

        public String getUpdateContent() {
            return updateContent;
        }

        public void setUpdateContent(String updateContent) {
            this.updateContent = updateContent;
        }
    }

    public static class Comments {
        private String uid;
        private String email_address;
        private String commentContent;
        public static final String COMMENT_CONTENT = "commentContent";
        private String commentedAt;
        public static final String COMMENTED_AT = "commentedAt";

        public Comments(String commentContent) {
            this.commentContent = commentContent;
        }

        public Comments(String uid, String email_address, String commentContent, String commentedAt) {
            this.uid = uid;
            this.email_address = email_address;
            this.commentContent = commentContent;
            this.commentedAt = commentedAt;
        }

        public Comments() {
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getEmail_address() {
            return email_address;
        }

        public void setEmail_address(String email_address) {
            this.email_address = email_address;
        }

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public String getCommentedAt() {
            return commentedAt;
        }

        public void setCommentedAt(String commentedAt) {
            this.commentedAt = commentedAt;
        }
    }

    public static class Tickets {
        private String ticketId;
        public static final String TICKET_ID = "ticketId";
        private boolean solved;
        public static final String SOLVED = "solved";
        private String uid;
        private String userName;
        public static final String USER_NAME = "userName";
        private String dateCreatedAt;
        public static final String DATE_CREATED = "dateCreatedAt";
        private String dateSolvedAt;
        public static final String DATE_SOLVED = "dateSolvedAt";
        private String ticketContent;
        public static final String TICKET_CONTENT = "ticketContent";
        private String ticketSeverity;
        public static final String TICKET_SEVERITY = "ticketSeverity";
        private List<Comments> ticketComments;
        public static final String TICKET_COMMENT = "ticketComments";

        public static final String TICKET_SUR = "TKT";

        public static final String LOW_S = "Low";
        public static final String MID_S = "Mid";
        public static final String HIGH_S = "High";

        public static final String TICKET_DB = "Tickets";


        public Tickets() {
        }

        public Tickets(String ticketContent) {
            this.ticketContent = ticketContent;
        }

        public List<Comments> getTicketComments() {
            return ticketComments;
        }

        public void setTicketComments(List<Comments> ticketComments) {
            this.ticketComments = ticketComments;
        }

        public String getTicketId() {
            return ticketId;
        }

        public void setTicketId(String ticketId) {
            this.ticketId = ticketId;
        }

        public boolean isSolved() {
            return solved;
        }

        public void setSolved(boolean solved) {
            this.solved = solved;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDateCreatedAt() {
            return dateCreatedAt;
        }

        public void setDateCreatedAt(String dateCreatedAt) {
            this.dateCreatedAt = dateCreatedAt;
        }

        public String getDateSolvedAt() {
            return dateSolvedAt;
        }

        public void setDateSolvedAt(String dateSolvedAt) {
            this.dateSolvedAt = dateSolvedAt;
        }

        public String getTicketContent() {
            return ticketContent;
        }

        public void setTicketContent(String ticketContent) {
            this.ticketContent = ticketContent;
        }

        public String getTicketSeverity() {
            return ticketSeverity;
        }

        public void setTicketSeverity(String ticketSeverity) {
            this.ticketSeverity = ticketSeverity;
        }
    }

    public static class Applications {
        private String appTitle;
        public static final String APP_TITLE = "appTitle";
        private String appDescription;
        public static final String APP_DESCRIPTION = "appDescription";
        private String appResponsibilities;
        public static final String APP_RESPONSIBILITIES = "appResponsibilities";

        private String appQualifications;
        public static final String APP_QUALIFICATIONS = "appQualifications";
        private String appJobFunction;
        public static final String APP_JOB_FUNCTION = "appJobFunction";
        private String appEmploymentType;
        public static final String APP_EMPLOYMENT_TYPE = "appEmploymentType";

        private String appPositionField;
        public static final String APP_POSITION_FIELD = "appPositionField";
        private String createdAt;
        public static final String APP_ID = "appId";
        private String appId;

        //private ArrayList<EmployeeApplication> usersApplied;
        public static final String USERS_APPLIED = "usersApplied";

        public static final String APPLICATION_SUR = "APP";
        public static final String APPLICATION_DB = "Applications";

        public Applications() {
        }

        public Applications(String appTitle) {
            this.appTitle = appTitle;
        }

        public String getAppTitle() {
            return appTitle;
        }

        public void setAppTitle(String appTitle) {
            this.appTitle = appTitle;
        }

        public String getAppDescription() {
            return appDescription;
        }

        public void setAppDescription(String appDescription) {
            this.appDescription = appDescription;
        }

        public String getAppResponsibilities() {
            return appResponsibilities;
        }

        public void setAppResponsibilities(String appResponsibilities) {
            this.appResponsibilities = appResponsibilities;
        }

        public String getAppQualifications() {
            return appQualifications;
        }

        public void setAppQualifications(String appQualifications) {
            this.appQualifications = appQualifications;
        }

        public String getAppJobFunction() {
            return appJobFunction;
        }

        public void setAppJobFunction(String appJobFunction) {
            this.appJobFunction = appJobFunction;
        }

        public String getAppEmploymentType() {
            return appEmploymentType;
        }

        public void setAppEmploymentType(String appEmploymentType) {
            this.appEmploymentType = appEmploymentType;
        }

        public String getAppPositionField() {
            return appPositionField;
        }

        public void setAppPositionField(String appPositionField) {
            this.appPositionField = appPositionField;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }


    }

    public static class EmployeeApplication {
        private String coverLetter;
        public static final String COVER_LETTER = "coverLetter";
        private String whyText;
        public static final String WHY_TEXT = "whyText";
        private String createdAt;
        private String uid;
        private String email_address;
        private String status;
        private String appId;
        public static final String APP_STATUS = "status";

        public static final String PENDING = "Pending";
        public static final String APPROVED = "Approved";
        public static final String DENIED = "Denied";
        public static final String JOB_APPLICATION_DB = "Job Applications";

        public EmployeeApplication() {
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getEmail_address() {
            return email_address;
        }

        public void setEmail_address(String email_address) {
            this.email_address = email_address;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public EmployeeApplication(String coverLetter) {
            this.coverLetter = coverLetter;
        }

        public String getCoverLetter() {
            return coverLetter;
        }

        public void setCoverLetter(String coverLetter) {
            this.coverLetter = coverLetter;
        }

        public String getWhyText() {
            return whyText;
        }

        public void setWhyText(String whyText) {
            this.whyText = whyText;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

    public static class TimeTableModel {
        private String activityId;
        public static final String ACTIVITY_ID = "activityId";
        private String activityTitle;
        public static final String ACTIVITY_TITLE = "activityTitle";
        private String activityDate;
        public static final String ACTIVITY_DATE = "activityDate";
        private String activityDescription;
        public static final String ACTIVITY_DESCRIPTION = "activityDescription";
        private String createdAt;

        public static final String TIMELINE_SUR = "TLN";
        public static final String TIMELINE_DB = "Timelines";

        public TimeTableModel(String activityTitle) {
            this.activityTitle = activityTitle;
        }

        public TimeTableModel() {
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getActivityTitle() {
            return activityTitle;
        }

        public void setActivityTitle(String activityTitle) {
            this.activityTitle = activityTitle;
        }

        public String getActivityDate() {
            return activityDate;
        }

        public void setActivityDate(String activityDate) {
            this.activityDate = activityDate;
        }

        public String getActivityDescription() {
            return activityDescription;
        }

        public void setActivityDescription(String activityDescription) {
            this.activityDescription = activityDescription;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }

    public static class Leave {
        private String uid;
        private String createdAt;
        private String date1;
        public static final String DATE_1 = "date1";
        private String date2;
        public static final String DATE_2 = "date2";
        private String date3;
        public static final String DATE_3 = "date3";
        private String status;

        public static final String LEAVE_DB = "Leave";

        public Leave() {
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDate1() {
            return date1;
        }

        public void setDate1(String date1) {
            this.date1 = date1;
        }

        public String getDate2() {
            return date2;
        }

        public void setDate2(String date2) {
            this.date2 = date2;
        }

        public String getDate3() {
            return date3;
        }

        public void setDate3(String date3) {
            this.date3 = date3;
        }
    }

    public static class Advances {
        private String uid;
        private String min;
        public static final String MIN = "min";
        private String max;
        public static final String MAX = "max";
        private String reason;
        public static final String REASON  = "reason";
        public static final String ADVANCE_DB = "Advances";

        public Advances() {
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
