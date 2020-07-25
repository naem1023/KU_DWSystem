package Sys;

import junit.framework.TestCase;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class AlarmTests extends TestCase {

    public void tearDown() throws Exception {
    }

    public void testIsAlarmTimeCheck() {
        ModeManager man = new ModeManager();
        Buzzer buzzer = man.getBuzzer();

        Alarm alarm = ((Alarm)(man.getmodes()[1]));
        alarm.enterEditAlarm();
        alarm.increaseAlarmTime();
        alarm.changeCursor();
        alarm.increaseAlarmTime();
        alarm.saveAlarm();
        Time time = ((Time)(man.getmodes()[0]));
        time.setCurrentTime(LocalDateTime.of(2020,1,1,1,1));

        alarm.isAlarmTimeCheck();
        assertEquals(false, buzzer.getBuzzerOn());
        assertFalse(buzzer.getIsAlarmRinging());
    }

    public void testChangeAlarm() {
        Buzzer buzzer = new Buzzer();
        Time time = new Time();

        Alarm alarm = new Alarm(buzzer, time);

        alarm.changeAlarm();
        alarm.changeAlarm();
        alarm.changeAlarm();
        alarm.changeAlarm();

        assertEquals(0, alarm.getCurrentAlarmIndex());
    }

    public void testTurnOnOffAlarm() {
        Buzzer buzzer = new Buzzer();
        Time time = new Time();

        Alarm alarm = new Alarm(buzzer, time);

        alarm.changeAlarm();
        alarm.turnOnOffAlarm();
        boolean isActivated = alarm.getCurrentAlarmTimerObject().isActivatedTimer();

        assertEquals(true, isActivated);

    }


    public void testIncreaseAlarmTime() {
        Buzzer buzzer = new Buzzer();
        Time time = new Time();

        Alarm alarm = new Alarm(buzzer, time);

        alarm.enterEditAlarm();
        alarm.increaseAlarmTime(); alarm.increaseAlarmTime(); alarm.increaseAlarmTime();
        alarm.changeCursor();
        alarm.increaseAlarmTime();
        alarm.increaseAlarmTime();
        LocalDateTime s = alarm.getCopyOfAlarmTimer();
        LocalTime t = s.toLocalTime();

        assertEquals(0,  t.compareTo(LocalTime.of(3,2,0)));
    }
    public void testIncreaseAlarmTime_overflow() {
        Buzzer buzzer = new Buzzer();
        Time time = new Time();
        int i;
        Alarm alarm = new Alarm(buzzer, time);

        alarm.enterEditAlarm();
        for(i=0; i < 25; i++)
            alarm.increaseAlarmTime();
        alarm.changeCursor();
        for(i=0; i < 70; i++)
            alarm.increaseAlarmTime();
        LocalDateTime s = alarm.getCopyOfAlarmTimer();
        LocalTime t = s.toLocalTime();

        assertEquals(0,  t.compareTo(LocalTime.of(2,10,0)));
    }

    public void testDecreaseAlarmTime() {
        Buzzer buzzer = new Buzzer();
        Time time = new Time();

        Alarm alarm = new Alarm(buzzer, time);

        alarm.enterEditAlarm();
        alarm.increaseAlarmTime(); alarm.increaseAlarmTime(); alarm.increaseAlarmTime();
        alarm.changeCursor();
        alarm.increaseAlarmTime();alarm.increaseAlarmTime();

        alarm.decreaseAlarmTime();
        alarm.changeCursor();
        alarm.decreaseAlarmTime();

        LocalDateTime s = alarm.getCopyOfAlarmTimer();
        LocalTime t = s.toLocalTime();

        assertEquals(0,  t.compareTo(LocalTime.of(2,1,0)));
    }
    public void testDecreaseAlarmTime_overflow() {
        Buzzer buzzer = new Buzzer();
        Time time = new Time();

        Alarm alarm = new Alarm(buzzer, time);

        alarm.enterEditAlarm();
        alarm.decreaseAlarmTime();
        alarm.changeCursor();
        alarm.decreaseAlarmTime();

        LocalDateTime s = alarm.getCopyOfAlarmTimer();
        LocalTime t = s.toLocalTime();

        assertEquals(0,  t.compareTo(LocalTime.of(22,59,0)));
    }

    public void testChangeCursor() {
        Buzzer buzzer = new Buzzer();
        Time time = new Time();

        Alarm alarm = new Alarm(buzzer, time);
        alarm.changeCursor(); alarm.changeCursor(); alarm.changeCursor(); alarm.changeCursor();
        assertEquals(true, alarm.isCursorHour());

    }

    public void testSaveAlarm() {
        Buzzer buzzer = new Buzzer();
        Time time = new Time();

        Alarm alarm = new Alarm(buzzer, time);
        alarm.enterEditAlarm();
        alarm.increaseAlarmTime(); alarm.increaseAlarmTime(); alarm.increaseAlarmTime();
        alarm.changeCursor();
        alarm.increaseAlarmTime();
        alarm.increaseAlarmTime();
        LocalDateTime s = alarm.getCopyOfAlarmTimer();
        LocalTime t = s.toLocalTime();

        alarm.saveAlarm();
        assertEquals(0, alarm.getCopyOfAlarmTimer().toLocalTime().compareTo(alarm.getCurrentAlarmTimerObject().requestExpirationTime()));

    }

    public void testTurnOffAlarm(){
        ModeManager man = new ModeManager();
        Buzzer buzzer = man.getBuzzer();

        Alarm alarm = ((Alarm)(man.getmodes()[1]));
        alarm.enterEditAlarm();
        alarm.increaseAlarmTime();
        alarm.changeCursor();
        alarm.increaseAlarmTime();
        alarm.saveAlarm();
        Time time = ((Time)(man.getmodes()[0]));
        time.setCurrentTime(LocalDateTime.of(2020,1,1,1,1));

        alarm.isAlarmTimeCheck();
        assertEquals(false, buzzer.getBuzzerOn());
        assertFalse(buzzer.getIsAlarmRinging());

        alarm.turnOnOffAlarm();
        alarm.isAlarmTimeCheck();
        assertEquals(true, buzzer.getBuzzerOn());

    }


}