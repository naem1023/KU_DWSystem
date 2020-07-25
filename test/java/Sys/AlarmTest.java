package Sys;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class AlarmTest {

    public void tearDown() throws Exception {
    }

    @Test
    public void IsAlarmTimeCheck() {
        ModeManager man = new ModeManager();
        Buzzer buzzer = man.getBuzzer();

        Alarm alarm = ((Alarm)(man.getmodes()[1]));
        alarm.enterEditAlarm();
        alarm.increaseAlarmTime();
        alarm.changeCursor();
        alarm.increaseAlarmTime();
        alarm.saveAlarm();
        //알람을 01:01로 설정.
        Time time = ((Time)(man.getmodes()[0]));
        //시간을 00:01:01로 설정.
        time.setCurrentTime(LocalDateTime.of(2020,1,1,1,1, 0));

        alarm.isAlarmTimeCheck();
        //alarm을 활성화시키지 않아 buzzer가 울리지 않는 상태
        assertEquals(false, buzzer.getBuzzerOn());
        assertFalse(buzzer.getIsAlarmRinging());

        //alarm을 활성화시킴
        alarm.turnOnOffAlarm();
        alarm.isAlarmTimeCheck();
        assertEquals(true, buzzer.getBuzzerOn());
        assertTrue(buzzer.getIsAlarmRinging());

    }

    @Test
    public void ChangeAlarm() {
        Buzzer buzzer = new Buzzer();
        Time time = new Time();

        Alarm alarm = new Alarm(buzzer, time);

        alarm.changeAlarm();
        alarm.changeAlarm();
        alarm.changeAlarm();
        alarm.changeAlarm();
        //alarm을 4번 바꾸면 0번째로 되돌아감.
        assertEquals(0, alarm.getCurrentAlarmIndex());
    }

    @Test
    public void TurnOnOffAlarm() {
        Buzzer buzzer = new Buzzer();
        Time time = new Time();

        Alarm alarm = new Alarm(buzzer, time);

        //alarm을 deactivate->activate.
        alarm.changeAlarm();
        alarm.turnOnOffAlarm();
        boolean isActivated = alarm.getCurrentAlarmTimerObject().isActivatedTimer();

        assertEquals(true, isActivated);

    }

    @Test
    public void IncreaseAlarmTime() {
        Buzzer buzzer = new Buzzer();
        Time time = new Time();
        int i;
        Alarm alarm = new Alarm(buzzer, time);

        alarm.enterEditAlarm();
        //hour를 25번 증가
        for(i=0; i < 25; i++)
            alarm.increaseAlarmTime();
        alarm.changeCursor();
        //minute를 70번 증가.
        for(i=0; i < 70; i++)
            alarm.increaseAlarmTime();
        LocalDateTime s = alarm.getCopyOfAlarmTimer();
        LocalTime t = s.toLocalTime();

        assertEquals(0,  t.compareTo(LocalTime.of(2,10,0)));
    }


    @Test
    public void DecreaseAlarmTime() {
        Buzzer buzzer = new Buzzer();
        Time time = new Time();

        Alarm alarm = new Alarm(buzzer, time);

        alarm.enterEditAlarm();
        alarm.decreaseAlarmTime();
        alarm.changeCursor();
        alarm.decreaseAlarmTime();

        LocalDateTime s = alarm.getCopyOfAlarmTimer();
        LocalTime t = s.toLocalTime();
        //00:00에서 hour를 1번 감소, minute를 1번 감소. -> 22:59분 이 출력되어야 정상.
        assertEquals(0,  t.compareTo(LocalTime.of(22,59,0)));
    }


    @Test
    public void ChangeCursor() {
        Buzzer buzzer = new Buzzer();
        Time time = new Time();

        Alarm alarm = new Alarm(buzzer, time);
        alarm.changeCursor(); alarm.changeCursor(); alarm.changeCursor(); alarm.changeCursor();
        assertEquals(true, alarm.isCursorHour());

    }

    @Test
    public void SaveAlarm() {
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
        //alarm의 시간이 저장되어 보고있던 alarm의 index에 저장이 되는지 test.
        assertEquals(0, alarm.getCopyOfAlarmTimer().toLocalTime().
                compareTo(alarm.getCurrentAlarmTimerObject().requestExpirationTime()));


    }

    @Test
    public void TurnOffAlarm(){
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
        //알람이 두번 울릴 경우 버저가 그대로 울리는지 test

    }

}