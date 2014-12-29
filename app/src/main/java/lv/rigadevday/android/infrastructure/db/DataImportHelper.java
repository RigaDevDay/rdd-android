package lv.rigadevday.android.infrastructure.db;

import android.content.Context;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import lv.rigadevday.android.domain.Contact;
import lv.rigadevday.android.domain.Event;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.PresentationTag;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.domain.SpeakerPresentation;
import lv.rigadevday.android.domain.Tag;
import lv.rigadevday.android.domain.Track;
import lv.rigadevday.android.domain.TrackPresentations;
import lv.rigadevday.android.domain.dto.ScheduleDto;
import lv.rigadevday.android.domain.dto.SpeakerInfo;
import lv.rigadevday.android.domain.mapper.EventMapper;
import lv.rigadevday.android.domain.mapper.PresentationMapper;
import lv.rigadevday.android.domain.mapper.SpeakerMapper;


public class DataImportHelper {

    private static SpeakerMapper speakerMapper = new SpeakerMapper();
    private static PresentationMapper presentationMapper = new PresentationMapper();
    private static EventMapper eventMapper = new EventMapper();

    public static void importFromJson(Context ctx) {
        importSpeakers(ctx);
        importSchedule(ctx);
    }

    public static void importSchedule(Context ctx) {
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
        List<Event> events = eventMapper.getEvents(schedule);

        saveEvents(events);
        saveTracks(presentations);
        savePresentations(presentations);
    }

    private static void saveEvents(List<Event> events) {
        for (Event event : events) {
            event.save();
        }
    }

    private static void savePresentations(List<Presentation> presentations) {
        for (Presentation presentation : presentations) {
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

            if (presentation.getRoom().equals("Keynote")) {
                List<Track> all = Track.getAll();
                for (Track track : all) {
                    saveTrackPresentation(presentation, track);
                }
            } else {
                Track track = Track.getByName(presentation.getRoom());
                saveTrackPresentation(presentation, track);
            }
        }
    }

    private static void saveTrackPresentation(Presentation presentation, Track track) {
        TrackPresentations tp = new TrackPresentations();
        tp.setTrack(track);
        tp.setPresentation(presentation);
        tp.save();
    }

    private static void saveTracks(List<Presentation> presentations) {
        Set<Track> tracks = Sets.newTreeSet(new Comparator<Track>() {
            @Override
            public int compare(Track o, Track o2) {
                return o.getTrackName().compareTo(o2.getTrackName());
            }
        });

        for (Presentation presentation : presentations) {
            tracks.add(new Track(presentation.getRoom()));
        }

        for (Track track : tracks) {
            if (track.getTrackName().equals("Keynote")) continue;
            track.save();
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
