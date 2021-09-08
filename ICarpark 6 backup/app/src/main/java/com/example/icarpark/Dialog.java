package com.example.icarpark;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {


    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Success")
        .setMessage("Registration Complete. Please Login.")
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);

            }
        });

        return  builder.create();
    }
}
