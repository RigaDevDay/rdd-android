package lv.rigadevday.android.ui.schedule.day.adapter

import lv.rigadevday.android.repository.model.schedule.Timeslot

sealed class ScheduleItem(val timeslot: Timeslot) {

    class SingleSessionItem(timeslot: Timeslot) : ScheduleItem(timeslot)

    class MultipleSessionItem(timeslot: Timeslot) : ScheduleItem(timeslot)

}
