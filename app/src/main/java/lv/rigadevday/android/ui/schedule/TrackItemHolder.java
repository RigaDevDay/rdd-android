package lv.rigadevday.android.ui.schedule;

import lv.rigadevday.android.domain.Event;
import lv.rigadevday.android.domain.Presentation;

public class TrackItemHolder implements Comparable<TrackItemHolder> {
    private final Event event;
    private final Presentation presentation;

    public TrackItemHolder(Event event) {
        this.event = event;
        this.presentation = null;
    }

    public TrackItemHolder(Presentation presentation) {
        this.presentation = presentation;
        this.event = null;
    }

    public Event getEvent() {
        return event;
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public boolean isEventItem() {
        return event != null;
    }

    @Override
    public String toString() {
        return "TrackItemHolder{" +
                "event=" + event +
                ", presentation=" + presentation +
                '}';
    }

    @Override
    public int compareTo(TrackItemHolder trackItemHolder) {
        if (isEventItem()) {
            return event.getStartTime().compareTo(trackItemHolder.isEventItem() ? trackItemHolder.event.getStartTime() : trackItemHolder.presentation.getStartTime());
        } else {
            return presentation.getStartTime().compareTo(trackItemHolder.isEventItem() ? trackItemHolder.event.getStartTime() : trackItemHolder.presentation.getStartTime());
        }
    }
}
