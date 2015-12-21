package lv.rigadevday.android.v2.ui.schedule;

import android.content.Context;

import java.util.List;

import lv.rigadevday.android.v2.model.Days;

/**
 */
public interface ScheduleFragmentPresenter {
    void openTalk();

    Context getContext();

    void setupList(List<Days> data);
}
