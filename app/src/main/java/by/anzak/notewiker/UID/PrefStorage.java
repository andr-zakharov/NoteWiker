package by.anzak.notewiker.UID;

import android.content.Context;
import android.content.SharedPreferences;

import by.anzak.notewiker.Constants;

/**
 * Класс хранит пары UID - Link в SharedPreferences.
 */


public class PrefStorage implements Storage{

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public PrefStorage(Context context) {
        preferences = context.getSharedPreferences(Constants.UID_MAP_FILE, Context.MODE_PRIVATE);
    }

    @Override
    public void set(String uid, String link) {
        if (editor == null) editor = preferences.edit();
        editor.putString(uid, link);
        editor.commit();
    }

    @Override
    public String getLink(String uid) {
        return preferences.getString(uid, null);
    }

    public void clear(){
        if (editor == null) editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

}
