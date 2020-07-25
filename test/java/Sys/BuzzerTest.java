package Sys;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class BuzzerTest {

    @Test
    public void beepBuzzer(){
        ModeManager man = new ModeManager();
        Buzzer buzzer = man.getBuzzer();

        Alarm alarm = ((Alarm)(man.getmodes()[1]));
        alarm.enterEditAlarm();

        alarm.increaseAlarmTime(); alarm.changeCursor(); alarm.increaseAlarmTime();
        alarm.saveAlarm(); alarm.turnOnOffAlarm();  //01:01로 1번 alarm 설정, 활성화

        Time time = ((Time)(man.getmodes()[0]));

        alarm.changeAlarm(); alarm.turnOnOffAlarm();    //00:00으로 2번 alarm 설정, 활성화

        time.setCurrentTime(LocalDateTime.of(2020,1,1,0,0));
        alarm.isAlarmTimeCheck();
        //alarm이 울리고 있는 상태.
        time.setCurrentTime(LocalDateTime.of(2020,1,1,1,1));

        assertEquals(true, buzzer.getBuzzerOn());
        assertTrue(buzzer.getIsAlarmRinging());
        alarm.isAlarmTimeCheck();
        //버저가 울리고 있는 상태에서 알람을 꺼주는 operation을 test내에서 부르지 않았기때문에 flag는 계속 켜져있고
        // 계속 울리고 있는 상태.
        assertEquals(true, buzzer.getBuzzerOn());

    }

    @Test
    public void stopBuzzer() {
        ModeManager man = new ModeManager();
        Buzzer buzzer = man.getBuzzer();

        Alarm alarm = ((Alarm)(man.getmodes()[1]));
        alarm.enterEditAlarm();
        alarm.increaseAlarmTime();
        alarm.changeCursor();
        alarm.increaseAlarmTime();
        alarm.saveAlarm();
        alarm.turnOnOffAlarm();
        Time time = ((Time)(man.getmodes()[0]));
        time.setCurrentTime(LocalDateTime.of(2020,1,1,1,1));
        alarm.isAlarmTimeCheck();
        time.setCurrentTime(LocalDateTime.of(2020,1,1,1,1));
        alarm.isAlarmTimeCheck();

        //stopBuzzer를 하면 알람 아이콘이 켜져있었다면 꺼지고  buzzer가 꺼진다.
        assertTrue(buzzer.getBuzzerOn());
        buzzer.stopBuzzer();
        assertFalse(buzzer.getBuzzerOn());
        assertFalse(buzzer.getIsAlarmRinging());
    }
}