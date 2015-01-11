package lv.rigadevday.android.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import javax.inject.Inject;

public class SocialNetworkNagivationService {

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

    public void goFacebook(String profile) {
        Intent intent;
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            String facebookScheme = "fb://profile/" + profile;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookScheme));
        } catch (PackageManager.NameNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + profile));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public void goGooglePlus(String profile) {
        //TODO: change to G+ intent
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + profile + "/posts"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void goWeb(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
