package com.theah64.retrokit.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.theah64.retrokit.R;

/**
 * Created by theapache64 on 15/9/17.
 */

public class DialogUtils {

    private final Context context;

    public DialogUtils(Context context) {
        this.context = context;
    }

    public MaterialDialog getClosedQuestionDialog(String question, final ClosedQuestionCallback callback) {
        return new MaterialDialog.Builder(context)
                .title(R.string.Confirm)
                .content(question)
                .negativeText(R.string.NO)
                .positiveText(R.string.YES)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        callback.onYes();
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        callback.onNo();
                    }
                }).build();
    }

    public MaterialDialog getProgressDialog(@StringRes int message) {
        return new MaterialDialog.Builder(context)
                .content(message)
                .progress(true, 0)
                .build();
    }

    public void showErrorDialog(@StringRes int message) {
        showErrorDialog(context.getString(message));
    }

    public void showErrorDialog(String message) {
        new MaterialDialog.Builder(context)
                .title(R.string.Error)
                .content(message)
                .positiveText(R.string.OK)
                .build().show();
    }


    public interface ClosedQuestionCallback {
        void onYes();

        void onNo();
    }
}
