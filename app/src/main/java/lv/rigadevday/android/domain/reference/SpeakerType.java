package lv.rigadevday.android.domain.reference;

import java.util.HashMap;
import java.util.Map;

public enum SpeakerType {
    SPEAKER(0),
    WORKSHOPER(1);

    private static final Map<Integer, SpeakerType> byId = new HashMap<>();

    static {
        for (SpeakerType e : SpeakerType.values()) {
            byId.put(e.getId(), e);
        }
    }

    private final Integer id;

    private SpeakerType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SpeakerType getById(int id) {
        return byId.get(id);
    }


    public static SpeakerType byValue(String value) {
        for (SpeakerType speakerType : SpeakerType.values()) {
            if (value.toUpperCase().equals(speakerType.name().toUpperCase()))
                return speakerType;

        }
        throw new IllegalArgumentException("No such type: " + value);
    }
}
