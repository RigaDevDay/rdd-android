package lv.rigadevday.android.infrastructure.db;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import lv.rigadevday.android.domain.Contact;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.PresentationTag;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.domain.SpeakerPresentation;
import lv.rigadevday.android.domain.Tag;
import lv.rigadevday.android.domain.dto.ScheduleDto;
import lv.rigadevday.android.domain.dto.SpeakerInfo;
import lv.rigadevday.android.domain.mapper.PresentationMapper;
import lv.rigadevday.android.domain.mapper.SpeakerMapper;


public class DataImportHelper {

    private static SpeakerMapper speakerMapper = new SpeakerMapper();
    private static PresentationMapper presentationMapper = new PresentationMapper();

    public static void importFromJson(Context ctx) {
        importSpeakers(ctx);
        importPresentations(ctx);
    }

    public static void importPresentations(Context ctx) {
        InputStreamReader reader = null;
        try {
            InputStream stream = ctx.getAssets().open("schedule.json");
            reader = new InputStreamReader(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().create();
        ScheduleDto schedule = gson.fromJson(reader, ScheduleDto.class);

        Set<String> tags = presentationMapper.getTags(schedule);
        saveTags(tags);

        List<Presentation> presentations = presentationMapper.getPresentations(schedule);
        savePresentations(presentations);
    }

    private static void savePresentations(List<Presentation> presentations) {
        for (Presentation presentation : presentations) {
            presentation.setBookmarked(true); //TODO: REMOVE THIS
            presentation.save();

            List<Speaker> speakers = presentation.getSpeakers();
            for (Speaker speaker : speakers) {
                SpeakerPresentation sp = new SpeakerPresentation();
                sp.setPresentation(presentation);
                sp.setSpeaker(speaker);
                sp.save();
            }

            List<Tag> tags = presentation.getTags();
            for (Tag tag : tags) {
                PresentationTag pt = new PresentationTag();
                pt.setPresentation(presentation);
                pt.setTag(tag);
                pt.save();
            }
        }

    }

    private static void saveTags(Set<String> tags) {
        for (String tagValue : tags) {
            Tag tag = new Tag();
            tag.setName(tagValue);
            tag.save();
        }
    }

    public static void importSpeakers(Context ctx) {
        InputStreamReader reader = null;
        try {
            InputStream stream = ctx.getAssets().open("speakers.json");
            reader = new InputStreamReader(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type listType = new TypeToken<List<SpeakerInfo>>() {
        }.getType();
        Gson gson = new GsonBuilder().create();
        List<SpeakerInfo> speakers = gson.fromJson(reader, listType);

        for (SpeakerInfo speakerInfo : speakers) {
            Speaker speaker = speakerMapper.getSpeaker(speakerInfo);
            speaker.save();
            createContact(speaker);
        }
    }

    public static void createContact(Speaker speaker) {
        for (Contact contact : speaker.getContacts()) {
            contact.setSpeaker(speaker);
            contact.save();
        }
    }
}
