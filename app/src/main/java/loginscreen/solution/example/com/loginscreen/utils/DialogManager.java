package loginscreen.solution.example.com.loginscreen.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.EditText;


public class DialogManager {


    private Context context;
    private AlertDialog mErrorDialog;

    public DialogManager(Context context){
        this.context = context;
    }

    public void showInvalidLoginDialog(String reason) {

        final Context ctx = context;
        if(ctx!=null) {

            if (mErrorDialog == null) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
                dialog.setTitle("Atenção");
                dialog.setMessage(reason);

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mErrorDialog = null;;
                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mErrorDialog = null;
                    }
                });

                mErrorDialog = dialog.show();
            }
        }
    }

    public void dismissErrorDialog() {
        if (mErrorDialog != null && mErrorDialog.isShowing()) {
            try {
                mErrorDialog.dismiss();
                mErrorDialog = null;
            } catch (WindowManager.BadTokenException e) {
                e.printStackTrace();
            }
        }
    }
}
