package ru.rubicon.salary.utils;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Utils {

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void animateSize(TextView view, int sizeFrom, int sizeTo) {
        ObjectAnimator animator = ObjectAnimator.ofObject(view,
                "height", new IntEvaluator(), sizeFrom, sizeTo);
        animator.setDuration(300);
        animator.start();
    }

    public static final String LOG_TAG = "salary";
    public static void log(String message){
        Log.d(LOG_TAG, message);
    }
    public static void toastShort(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void toastLong(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public static void snackBarShort(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
    public static void snackBarLong(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
    public static void nop(){
        /*dummy operation*/
    }
}
