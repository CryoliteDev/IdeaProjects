import java.util.Calendar;
public class DateInfo {


    //variable declarations
    private int year, month, dayOfMonth;

    //Calender Object
    Calendar calendar = Calendar.getInstance();
    Calendar c1 = Calendar.getInstance();
    Calendar checkDate = Calendar.getInstance();
    Calendar maturityCal = Calendar.getInstance();




    public DateInfo(int year, int month, int dayOfMonth) {
        setYear(year);
        setMonth(month);
        setDayOfMonth(dayOfMonth);
    }

    public DateInfo(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

}