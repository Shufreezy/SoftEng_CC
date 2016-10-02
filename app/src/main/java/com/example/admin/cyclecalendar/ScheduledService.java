package com.example.admin.cyclecalendar;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.IntentService;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

/**
 * schedule values
 */
@TargetApi(3)
public class ScheduledService extends IntentService {

    public ScheduledService() {
        super("My service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
            new AlertDialog.Builder(this)
                    .setTitle("Cycle Calendar")
                    .setMessage("Your period begins in about 3 days.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
}