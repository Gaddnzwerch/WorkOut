package de.gaddenzwerch.workout;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class LeaveStayFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.leave_timer)
                .setPositiveButton(R.string.Leave, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                        }
                )
                .setNegativeButton(R.string.Stay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){

                    }
                });
        return builder.create();
    }
}
