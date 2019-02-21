package egic.com.recordversion;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by mahmoud adel on 12/8/2015.
 */
public class PrefManager {
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("RMSecure", Context.MODE_PRIVATE);
    }
    public static void clear(Context context){
        getSharedPreferences(context).edit().clear().apply();
    }

    public static boolean saveAppVersion(Context context, String appVersion) {
        SharedPreferences.Editor prefsEditor = getSharedPreferences(context).edit();
        prefsEditor.putString("APPVERSION", appVersion);
        return prefsEditor.commit();
    }

    public static boolean clearAppVersion(Context context) {
        SharedPreferences.Editor prefsEditor = getSharedPreferences(context).edit();
        prefsEditor.putString("APPVERSION", "");
        return prefsEditor.commit();
    }

    public static AppVersion getAppVersionObject(Context context){
        String appversion =  getSharedPreferences(context).getString("APPVERSION", "");
        return new Gson().fromJson(appversion, AppVersion.class);
    }

}
