package lv.rigadevday.android;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import lv.rigadevday.android.domain.Contact;
import lv.rigadevday.android.domain.ContactType;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.Speaker;

/**
 * Valid for prototyping, remove once import over http is implemented
 */
public class Mocks {
    public static void createPresentations(BaseApplication baseApplication) {


        for (int i = 0; i < 20; i++) {
            Presentation p = new Presentation();
            p.setStartTime("10:00");
            p.setEndTime("11:00");
            p.setTitle("Presentation: " + i);
            p.setHeader(i % 5 == 0);
            p.setBookmarked(i % 4 == 0);
            p.setRoom("Hall 2");
            p.save();
        }
    }

    public static void createSpeakers(Context ctx) {
        InputStreamReader reader = null;
        try {
            InputStream stream = ctx.getAssets().open("speakers.json");
             reader = new InputStreamReader(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type listType = new TypeToken<List<Speaker>>() {}.getType();

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        List<Speaker> speakers = gson.fromJson(reader, listType);

        for (Speaker speaker : speakers) {
            speaker.save();
            createContact(speaker);
        }
    }

    public static Contact createContact(Speaker speaker) {
        Contact c = new Contact();
        c.setType(ContactType.TWITTER);
        c.setValue("@randomName");
        c.setSpeaker(speaker);
        c.save();
        return c;
    }
}
