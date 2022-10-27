package baktiyar.com.testfragment.utils

import android.app.Activity
import android.widget.Toast

/**
 * Created by admin on 23.04.2018.
 */
object Utils {
    fun makeToast(message: String?, activity: Activity?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    fun stringContainsNothing(text: String?): Boolean {
        return text == ""
    }

    @JvmStatic
    fun stringIsNull(text: String?): Boolean {
        return text == null
    }
}