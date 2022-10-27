package baktiyar.com.testfragment.utils;

import android.app.Activity;
import android.widget.Toast;

import java.util.Objects;


/**
 * Created by admin on 23.04.2018.
 */

public class Utils {

    public static void makeToast(String message, Activity activity) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static Boolean stringContainsNothing(String text) {
        return Objects.equals(text, "");
    }

    public static Boolean stringIsNull(String text) {
        return text == null;
    }
}
