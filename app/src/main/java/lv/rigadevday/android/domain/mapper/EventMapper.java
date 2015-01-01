package lv.rigadevday.android.domain.mapper;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lv.rigadevday.android.domain.Event;
import lv.rigadevday.android.domain.dto.PresentationDto;
import lv.rigadevday.android.domain.dto.ScheduleDto;
import lv.rigadevday.android.domain.dto.ScheduleSlotDto;

public class EventMapper {

    private final SimpleDateFormat format;

    public EventMapper() {
        format = new SimpleDateFormat("HH:mm");
    }

    public List<Event> getEvents(ScheduleDto dto) {
        List<Event> entities = Lists.newArrayList();

        for (ScheduleSlotDto scheduleSlot : dto.getSchedule()) {
            String rawStartTime = scheduleSlot.getStartTime();
            String rawEndTime = scheduleSlot.getEndTime();

            Date start = convert(rawStartTime);
            Date end = convert(rawEndTime);

            List<PresentationDto> presentations = scheduleSlot.getPresentations();

            for (int i = 0; i < presentations.size(); i++) {
                PresentationDto presentationDto = presentations.get(i);

                if (Strings.isNullOrEmpty(presentationDto.getTitle()))
                    continue;

                Event event = new Event();

                event.setTitle(presentationDto.getTitle());
                event.setStartTime(start);
                event.setEndTime(end);

                entities.add(event);
            }
        }

        return entities;
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
