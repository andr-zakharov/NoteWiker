package by.anzak.notewiker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;

/**
 * Created by Andrey on 30.01.2015.
 * Декоратор для класса настроек приложения
 */
public class PrefManager {

    private final SharedPreferences preferences;

    public PrefManager(Context context){
        preferences = context.getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
    }

    public String getString(String key, String defValue){
        Environment.getRootDirectory();
        return preferences.getString(key, defValue);
    }

    public String getRootFolder(){
        File defaultPath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            defaultPath = Environment.getExternalStorageDirectory();
        } else {
            defaultPath = Environment.getRootDirectory();
        }
        return preferences.getString(Constants.ROOT_PATH_KEY, defaultPath.getAbsolutePath());
    }

    public void setRootFolder(){

    }




}
