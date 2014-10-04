package lv.rigadevday.android.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

import static android.content.SharedPreferences.Editor;

public class SharedPrefsService {
	private static final String SESSION_PREFIX = "Session_";
	ClassLogger logger = new ClassLogger(NetworkService.class);

	@Inject
	public Context context;

	public boolean hasVotedForSession(int sessionId) {
		return getPrefs().getBoolean(getKeyForSession(sessionId), false);
	}

	public void saveVoteForSession(int sessionId) {
		Editor editor = getPrefs().edit();
		editor.putBoolean(getKeyForSession(sessionId), true);
		editor.commit();
		logger.d(String.format("Vote for session %d is saved", sessionId));
	}

	private SharedPreferences getPrefs() {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	private String getKeyForSession(int sessionId) {
		return SESSION_PREFIX + sessionId;
	}
}
