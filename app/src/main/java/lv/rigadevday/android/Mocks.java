package lv.rigadevday.android;

import lv.rigadevday.android.domain.Contact;
import lv.rigadevday.android.domain.ContactType;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.Speaker;

/**
 * Valid for prototyping, remove once import over http is implemented
 */
public class Mocks {
    public static void createPresentations() {
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

    public static void createSpeakers() {
        for (int i = 0; i < 10; i++) {
            Speaker sp = new Speaker();
            sp.setBio("Bio " + i);
            sp.setCompany("Company " + i);
            sp.setCountry("LV");
            sp.setName("Speaker " + i);
            sp.setLineup(i);
            sp.save();

            createContact(sp);
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
