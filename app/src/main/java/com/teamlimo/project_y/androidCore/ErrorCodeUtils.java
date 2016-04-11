package com.teamlimo.project_y.androidCore;

import android.content.Context;

import com.teamlimo.project_y.R;
import com.teamlimo.project_y.core.ErrorCodes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Project0rion on 11.04.2016.
 */
public final class ErrorCodeUtils {

    private static Map<Integer, ErrorCodeDialogInfo> errorCodeDialogInfoCache = new HashMap<>();

    public static ErrorCodeDialogInfo getErrorCodeDialogInfo(int errorCode, Context context) {

        if (!errorCodeDialogInfoCache.containsKey(errorCode))
            errorCodeDialogInfoCache.put(errorCode, createErrorCodeDialogInfo(errorCode, context));

        return errorCodeDialogInfoCache.get(errorCode);
    }

    private static ErrorCodeDialogInfo createErrorCodeDialogInfo(int errorCode, Context context) {
        switch (errorCode) {

            case ErrorCodes.CONNECTION_FAILED:
                return new ErrorCodeDialogInfo(
                        errorCode,
                        context.getString(R.string.connection_failed_dialog_title),
                        context.getString(R.string.connection_failed_dialog_message),
                        context.getString(R.string.retry_dialog_button),
                        context.getString(R.string.return_dialog_button));

            case ErrorCodes.NO_DATA_FOUND:
                return new ErrorCodeDialogInfo(
                        errorCode,
                        context.getString(R.string.no_data_found_dialog_title),
                        context.getString(R.string.no_data_found_dialog_message),
                        context.getString(R.string.retry_dialog_button),
                        context.getString(R.string.return_dialog_button));

            case ErrorCodes.REMAINING_QUIZ:
                return new ErrorCodeDialogInfo(
                        errorCode,
                        context.getString(R.string.remaining_quiz_dialog_title),
                        context.getString(R.string.remaining_quiz_dialog_message),
                        context.getString(R.string.proceed_dialog_button),
                        context.getString(R.string.new_quiz_dialog_button));
        }

        return new ErrorCodeDialogInfo(
                errorCode,
                context.getString(R.string.error_dialog_title),
                context.getString(R.string.error_dialog_message),
                context.getString(R.string.retry_dialog_button),
                context.getString(R.string.return_dialog_button));
    }
}
