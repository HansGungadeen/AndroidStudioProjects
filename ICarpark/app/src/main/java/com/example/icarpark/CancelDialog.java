package com.example.icarpark;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CancelDialog extends AppCompatDialogFragment {
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Cancelled")
                .setMessage("Booking Cancelled.")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        Intent intent = new Intent(getContext(), Dashboard.class);
                        startActivity(intent);

                    }
                });


        return  builder.create();
    }



}
