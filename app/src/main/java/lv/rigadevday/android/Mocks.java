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
import lv.rigadevday.android.domain.ScheduleSlot;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.integration.json.PresentationDto;
import lv.rigadevday.android.integration.json.ScheduleDto;
import lv.rigadevday.android.integration.json.ScheduleSlotDto;

/**
 * Valid for prototyping, remove once import over http is implemented
 */
public class Mocks {
    public static void createScheduleSlots(Context ctx) {
        InputStreamReader reader = null;
        try {
            InputStream stream = ctx.getAssets().open("schedule.json");
            reader = new InputStreamReader(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().create();
        ScheduleDto schedule = gson.fromJson(reader, ScheduleDto.class);

        for (ScheduleSlotDto slot : schedule.getSchedule()) {
            List<PresentationDto> presentations = slot.getPresentations();
            for (int i = 0; i < presentations.size(); i++) {
                PresentationDto p = presentations.get(i);
                Presentation presentation = p.toPresentation(presentations.size());
                presentation.save();
            }
            ScheduleSlot scheduleSlot = slot.toScheduleSlot();
            scheduleSlot.save();
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
        Gson gson = new GsonBuilder().create();
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
