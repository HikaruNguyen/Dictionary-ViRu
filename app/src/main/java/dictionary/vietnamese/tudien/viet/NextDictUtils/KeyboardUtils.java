package dictionary.vietnamese.tudien.viet.NextDictUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by manhi on 22/2/2016.
 */
public class KeyboardUtils {

    public static void hideKeyboard(Context context, EditText field) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(field.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showDelayedKeyboard(Context context, View view) {
        showDelayedKeyboard(context, view, 100);
    }

    public static void showDelayedKeyboard(final Context context, final View view, final int delay) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    try {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
