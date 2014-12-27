package lv.rigadevday.android.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import javax.inject.Inject;

public class TwitterService {

    @Inject
    Context context;

    public void goTwitter(String profile) {
        Intent intent;
        try {
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + profile));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + profile));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
