package lv.rigadevday.android.ui.schedule.day.adapter

import lv.rigadevday.android.repository.model.schedule.Timeslot

sealed class ScheduleItem(val timeslot: Timeslot)

class NonSessionTimeslot(timeslot: Timeslot) : ScheduleItem(timeslot)

class MultiSessionTimeslot(timeslot: Timeslot) : ScheduleItem(timeslot)

class SingleSessionTimeslot(timeslot: Timeslot) : ScheduleItem(timeslot)
