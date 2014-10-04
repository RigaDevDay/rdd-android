package lv.rigadevday.android.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

public class SharedPrefsService {
	ClassLogger logger = new ClassLogger(NetworkService.class);

	@Inject
	public Context context;

	private SharedPreferences getPrefs() {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

    public boolean getBool(String key) {
        return getPrefs().getBoolean(key, false);
    }

    public void setBool(String key, boolean value) {
        getPrefs().edit().putBoolean(key, value).commit();
    }
}
