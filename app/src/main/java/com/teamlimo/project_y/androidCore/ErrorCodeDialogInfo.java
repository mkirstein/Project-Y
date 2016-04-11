package com.teamlimo.project_y.androidCore;

/**
 * Created by Project0rion on 11.04.2016.
 */
public class ErrorCodeDialogInfo {

    private int errorCode;
    private String title;
    private String message;
    private String positiveChoice;
    private String negativeChoice;

    public ErrorCodeDialogInfo(int errorCode, String title, String message, String positiveChoice, String negativeChoice) {
        this.errorCode = errorCode;
        this.title = title;
        this.message = message;
        this.positiveChoice = positiveChoice;
        this.negativeChoice = negativeChoice;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getPositiveChoice() {
        return positiveChoice;
    }

    public String getNegativeChoice() {
        return negativeChoice;
    }
}
