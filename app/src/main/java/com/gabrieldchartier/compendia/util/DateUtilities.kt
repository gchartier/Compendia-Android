package com.gabrieldchartier.compendia.util

import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

class DateUtilities {

    companion object {
        // dates from server look like this: "2019-07-23T03:28:01.406944Z"
        fun convertServerStringDateToLong(sd: String): Long{
            val stringDate = sd.removeRange(sd.indexOf("T") until sd.length)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            try {
                val time = sdf.parse(stringDate).time
                return time
            } catch (e: Exception) {
                throw Exception(e)
            }
        }

        // dates from server look like this: "2019-07-23T03:28:01.406944Z"
        fun convertServerStringDateToDate(sd: String): Date{
            val stringDate = sd.removeRange(sd.indexOf("T") until sd.length)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            try {
                val time = sdf.parse(stringDate).time
                return Date(time)
            } catch (e: Exception) {
                throw Exception(e)
            }
        }

        fun convertLongToStringDate(longDate: Long): String{
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            try {
                val date = sdf.format(Date(longDate))
                return date
            } catch (e: Exception) {
                throw Exception(e)
            }
        }

        fun getCurrentReleaseWeek(): Long {
            val today = LocalDateTime.now()
            var releaseWeek: LocalDateTime = today

            if(today.dayOfWeek != DayOfWeek.WEDNESDAY)
                while(releaseWeek.dayOfWeek != DayOfWeek.WEDNESDAY)
                    releaseWeek = releaseWeek.minusDays(1)

            return releaseWeek.toInstant(ZoneOffset.UTC).toEpochMilli()
        }

//        fun isDateInCurrentWeek(date: Date?): Boolean {
//            val currentCalendar = Calendar.getInstance()
//            val week = currentCalendar[Calendar.WEEK_OF_YEAR]
//            val year = currentCalendar[Calendar.YEAR]
//            val targetCalendar = Calendar.getInstance()
//            targetCalendar.time = date
//            val targetWeek = targetCalendar[Calendar.WEEK_OF_YEAR]
//            val targetYear = targetCalendar[Calendar.YEAR]
//            return week == targetWeek && year == targetYear
//        }
//
//        fun getClosestWeek(comics: List<Comic>, weeks: List<String?>): List<Comic> {
//            val format: DateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
//            val dates: MutableList<Date> = ArrayList()
//            val closestWeeksComics: MutableList<Comic> = ArrayList()
//            val closestWeek: Date
//            try {
//                for (d in weeks) dates.add(format.parse(d))
//            } catch (error: ParseException) {
//                Log.e("DateUtilities", "Error occurred while trying to get the most recent week: $error")
//            }
//            closestWeek = Collections.min(dates)
//            Log.d("DateUtilities", format.format(closestWeek))
//            if (isDateInCurrentWeek(closestWeek)) for (c in comics) if (c.releaseDate == format.format(closestWeek)) closestWeeksComics.add(c)
//            Log.d("DateUtilities", closestWeeksComics.toString())
//            return closestWeeksComics
//        }

        //val currentDateTime: Date
            //get() = Calendar.getInstance().time
    }
}