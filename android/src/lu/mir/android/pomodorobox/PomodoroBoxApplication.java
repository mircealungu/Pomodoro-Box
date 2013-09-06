package lu.mir.android.pomodorobox;

import android.app.Application;
import android.content.Context;

public class PomodoroBoxApplication extends Application {

	 private static Context context;
     public void onCreate(){
       context=getApplicationContext();
     }
     
     public static Context context(){
         return context;
       } 
}
