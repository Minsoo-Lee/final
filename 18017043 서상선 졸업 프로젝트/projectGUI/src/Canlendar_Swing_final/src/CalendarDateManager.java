package Canlendar_Swing_final.src;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Calendar;
import java.util.GregorianCalendar;

class CalendarDataManager {
    static final int CAL_WIDTH = 7;
    static final int CAL_HEIGHT = 6;
    int[][] calDates = new int[6][7];
    int calYear;
    int calMonth;
    int calDayOfMon;
    final int[] calLastDateOfMonth = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int calLastDate;
    Calendar today = Calendar.getInstance();
    Calendar cal;

    public CalendarDataManager() {
        this.setToday();
    }

    public void setToday() {
        this.calYear = this.today.get(1);
        this.calMonth = this.today.get(2);
        this.calDayOfMon = this.today.get(5);
        this.makeCalData(this.today);
    }

    private void makeCalData(Calendar cal) {
        int calStartingPos = (cal.get(7) + 7 - cal.get(5) % 7) % 7;
        if (this.calMonth == 1) {
            this.calLastDate = this.calLastDateOfMonth[this.calMonth] + this.leapCheck(this.calYear);
        } else {
            this.calLastDate = this.calLastDateOfMonth[this.calMonth];
        }

        int i;
        int num;
        for(i = 0; i < 6; ++i) {
            for(num = 0; num < 7; ++num) {
                this.calDates[i][num] = 0;
            }
        }

        i = 0;
        num = 1;

        for(boolean k = false; i < 6; ++i) {
            int q;
            if (i == 0) {
                q = calStartingPos;
            } else {
                q = 0;
            }

            for(int j = q; j < 7; ++j) {
                if (num <= this.calLastDate) {
                    this.calDates[i][j] = num++;
                }
            }
        }

    }

    private int leapCheck(int year) {
        return (year % 4 != 0 || year % 100 == 0) && year % 400 != 0 ? 0 : 1;
    }

    public void moveMonth(int mon) {
        this.calMonth += mon;
        if (this.calMonth > 11) {
            while(this.calMonth > 11) {
                ++this.calYear;
                this.calMonth -= 12;
            }
        } else if (this.calMonth < 0) {
            while(this.calMonth < 0) {
                --this.calYear;
                this.calMonth += 12;
            }
        }

        this.cal = new GregorianCalendar(this.calYear, this.calMonth, this.calDayOfMon);
        this.makeCalData(this.cal);
    }
}
