package lv.rigadevday.android.domain.mapper;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.domain.Tag;
import lv.rigadevday.android.domain.dto.PresentationDto;
import lv.rigadevday.android.domain.dto.ScheduleDto;
import lv.rigadevday.android.domain.dto.ScheduleSlotDto;

public class PresentationMapper {

    private final SimpleDateFormat format;

    public PresentationMapper() {
        format = new SimpleDateFormat("HH:mm");
    }

    public Set<String> getTags(ScheduleDto dto) {
        Set<String> tags = Sets.newHashSet();
        for (ScheduleSlotDto scheduleSlotDto : dto.getSchedule()) {
            for (PresentationDto presentationDto : scheduleSlotDto.getPresentations()) {
                List<String> rawTags = presentationDto.getTags();
                if (rawTags != null) {
                    for (String tagValue : rawTags) {
                        tags.add(tagValue);
                    }
                }

            }
        }
        return tags;
    }

    public List<Presentation> getPresentations(ScheduleDto dto) {
        List<Presentation> entities = Lists.newArrayList();

        List<String> roomNames = dto.getRoomNames();

        for (ScheduleSlotDto scheduleSlot : dto.getSchedule()) {
            String rawStartTime = scheduleSlot.getStartTime();
            String rawEndTime = scheduleSlot.getEndTime();

            Date start = convert(rawStartTime);
            Date end = convert(rawEndTime);

            List<PresentationDto> presentations = scheduleSlot.getPresentations();

            for (int i = 0; i < presentations.size(); i++) {
                PresentationDto presentationDto = presentations.get(i);

                if (Strings.isNullOrEmpty(presentationDto.getSubtitle()))
                    continue;

                Presentation presentation = new Presentation();

                presentation.setTitle(presentationDto.getSubtitle());
                presentation.setDescription(presentationDto.getDescription());
                presentation.setStartTime(start);
                presentation.setEndTime(end);

                if (presentations.size() == 1) {
                    presentation.setRoom("Keynote");
                } else {
                    String room = roomNames.get(i);
                    presentation.setRoom(room);
                }

                List<Speaker> speakers = getSpeakers(presentationDto.getSpeakers());
                presentation.setSpeakers(speakers);

                List<Tag> tags = getTags(presentationDto.getTags());
                presentation.setTags(tags);

                entities.add(presentation);
            }
        }

        return entities;
    }

    private List<Speaker> getSpeakers(List<Integer> uids) {
        List<Speaker> speakers = Lists.newArrayList();
        for (Integer uid : uids) {
            Speaker s = Speaker.getByUid(uid);
            speakers.add(s);
        }
        return speakers;
    }

    private List<Tag> getTags(List<String> tagStrings) {
        List<Tag> tags = Lists.newArrayList();
        for (String name : tagStrings) {
            Tag tag = Tag.getByName(name);
            tags.add(tag);
        }
        return tags;
    }


    private Date convert(String time) {
        try {
            Date date = format.parse(time);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            cal.set(Calendar.YEAR, 2015);
            cal.set(Calendar.MONTH, 1);
            cal.set(Calendar.DAY_OF_MONTH, 22);

            return cal.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
