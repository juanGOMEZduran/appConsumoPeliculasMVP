package com.example.apiconsumepeliculas.View.utilsView;



import android.view.View;
import android.app.Activity;
public class SystemUIHelper {

    public static void hideSystemUI(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
    }
}