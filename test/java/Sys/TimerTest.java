package Sys;
import junit.framework.TestCase;
import org.junit.Test;

import java.time.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


public class TimerTest {

    @Test
    public void enterEditTimer(){
        Buzzer buzzer = new Buzzer();
        Timer timer=new Timer(buzzer);

        timer.enterEditTimer();
        //enter Edit시 Timer 변수 체크
        assertEquals(timer.getpauseTimerFlag(),true);
        assertEquals(timer.getsaveTimerFlag(),false);
        assertEquals(timer.getCurrentCursor(),0);

    }

    @Test
    public void changeCursor(){
        Buzzer buzzer = new Buzzer();
        Timer timer=new Timer(buzzer);

        int tempCursor=timer.getCurrentCursor();
        tempCursor=(tempCursor+1)%3;
        timer.changeCursor();
        //changeCursor 후 현재 커서가 제대로 변경되었는지 check
        assertEquals(timer.getCurrentCursor(),tempCursor);

    }

    @Test
    public void increaseData(){
        Buzzer buzzer = new Buzzer();
        Timer timer=new Timer(buzzer);

        timer.enterEditTimer();
        //시간test
        //현재 커서가 0(시간인지 확인)
        assertEquals(timer.getCurrentCursor(),0);

        //시간이 99시 일때 increase data 했을 떄 0(day=1, hour=0) 되는지 확인
        timer.setTimerTime(LocalDateTime.of(2000,1,5,3,0,0));
        timer.increaseData();
        assertEquals(timer.getTimerTime().getDayOfMonth(),1);
        assertEquals(timer.getTimerTime().getHour(),0);

        //시간이 0일때 increasedata후 1더해지는지 확인
        int temp=timer.getTimerTime().getHour()+1;
        timer.increaseData();
        assertEquals(timer.getTimerTime().getHour(), temp);

        //분 테스트
        timer.changeCursor();
        //현재 커서가 1(분)인지 확인
        assertEquals(timer.getCurrentCursor(),1);

        //분이 59일떄 increasedat 후 0이 되는 지 확인
        timer.setTimerTime(LocalDateTime.of(2000,1,5,3,59,0));
        timer.increaseData();
        assertEquals(timer.getTimerTime().getHour(),3);
        assertEquals(timer.getTimerTime().getMinute(),0);
        assertEquals(timer.getTimerTime().getSecond(),0);

        //분이 0일떄 increasedata 후 1되는지 확인
        temp=timer.getTimerTime().getMinute()+1;
        timer.increaseData();
        assertEquals(timer.getTimerTime().getMinute(),temp);

        //초 테스트
        timer.changeCursor();
        //현재 커서가 2(초)인지 확인
        assertEquals(timer.getCurrentCursor(),2);

        //초가 59일떄 increasedat 후 0이 되는 지 확인
        timer.setTimerTime(LocalDateTime.of(2000,1,5,3,0,59));
        timer.increaseData();
        assertEquals(timer.getTimerTime().getMinute(),0);
        assertEquals(timer.getTimerTime().getSecond(),0);

        //초가 0일떄 increasedata 후 1되는지 확인
        temp=timer.getTimerTime().getSecond()+1;
        timer.increaseData();
        assertEquals(timer.getTimerTime().getSecond(),temp);
    }

    @Test
    public void decreaseData(){
        Buzzer buzzer = new Buzzer();
        Timer timer=new Timer(buzzer);

        timer.enterEditTimer();

        //시간test
        //현재 커서가 0(시간인지 확인)
        assertEquals(timer.getCurrentCursor(),0);

        //시간이 0시 일때 decreaseData 했을 떄 99(day=5, hour=3) 되는지 확인
        timer.setTimerTime(LocalDateTime.of(2000,1,1,0,0,0));
        timer.decreaseData();
        assertEquals(timer.getTimerTime().getDayOfMonth(),5);
        assertEquals(timer.getTimerTime().getHour(),3);

        //시간이 99일때 decreaseData 후 1 감소되는지 확인
        int temp=timer.getTimerTime().getHour()-1;
        timer.decreaseData();
        assertEquals(timer.getTimerTime().getHour(), temp);

        //분 테스트
        timer.changeCursor();
        //현재 커서가 1(분)인지 확인
        assertEquals(timer.getCurrentCursor(),1);

        //분이 0일떄 decreaseData 후 59이 되는 지 확인
        timer.setTimerTime(LocalDateTime.of(2000,1,5,3,0,0));
        timer.decreaseData();
        assertEquals(timer.getTimerTime().getHour(),3);
        assertEquals(timer.getTimerTime().getMinute(),59);
        assertEquals(timer.getTimerTime().getSecond(),0);

        //분이 59일떄 decreaseData 후 -1되는지 확인
        temp=timer.getTimerTime().getMinute()-1;
        timer.decreaseData();
        assertEquals(timer.getTimerTime().getMinute(),temp);

        //초 테스트
        timer.changeCursor();
        //현재 커서가 2(초)인지 확인
        assertEquals(timer.getCurrentCursor(),2);

        //초가 0일떄 decreaseData 후 59이 되는 지 확인
        timer.setTimerTime(LocalDateTime.of(2000,1,5,3,0,0));
        timer.decreaseData();
        assertEquals(timer.getTimerTime().getMinute(),0);
        assertEquals(timer.getTimerTime().getSecond(),59);

        //초가 59일떄 decreaseData 후 -1되는지 확인
        temp=timer.getTimerTime().getSecond()-1;
        timer.decreaseData();
        assertEquals(timer.getTimerTime().getSecond(),temp);
    }

    @Test
    public void saveTimer() {
        Buzzer buzzer = new Buzzer();
        Timer timer = new Timer(buzzer);

        timer.setTimerTime(LocalDateTime.of(2000,1,1,1,1,1));
        timer.setSettingTimer(LocalDateTime.of(2000,3,1,5,2,1));

        LocalDateTime temp=timer.getTimerTime();

        timer.saveTimer();

        assertEquals(timer.getTimerTime(),temp);


    }

    @Test
    public void decreaseTimer() {
        Buzzer buzzer = new Buzzer();
        Timer timer = new Timer(buzzer);

        timer.setTimerTime(LocalDateTime.of(2000,1,1,0,0,4));
        timer.startTimer();
        //timer가 시작되었는지 확인
        assertEquals(timer.getpauseTimerFlag(),false);
        //10ms씩 100번 내려서 1초 감소 시키고 4->3 확인
        for(int i=0;i<100;i++)
            timer.decreaseTimer();
        assertEquals(timer.getTimerTime().getSecond(),3);
        //3초가 더흐르게 하고 buzzer가 on되었는지 확인
        for(int i=0;i<300;i++)
            timer.decreaseTimer();
        //buzzer on 확인
        assertEquals(buzzer.getBuzzerOn(),true);
        //Timer멈췄는지 확인
        assertEquals(timer.getpauseTimerFlag(),true);
        //Timer
        assertEquals(timer.getTimerTime(),LocalDateTime.of(2000,1,1,0,0,0));

    }
        @Test
    public void startTimer(){
        Buzzer buzzer = new Buzzer();
        Timer timer = new Timer(buzzer);

        //처음에 정지상태 확인
        assertEquals(timer.getpauseTimerFlag(),true);

        //리셋 상태일 때 startTimer후 시작이 되지않는지 확인
        timer.setTimerTime(LocalDateTime.of(2000,1,1,0,0,0));
        timer.startTimer();
        assertEquals(timer.getpauseTimerFlag(),true);

        //리셋 상태가 아닐때 startTimer후 시작이 되는 지 확인
        timer.setTimerTime(LocalDateTime.of(2000,1,1,4,0,0));
        timer.startTimer();
        assertEquals(timer.getpauseTimerFlag(),false);

    }

    @Test
    public void pauseTimer(){
        Buzzer buzzer = new Buzzer();
        Timer timer = new Timer(buzzer);

        //리셋 상태가 아닐 때 startTimer 후 시작상태 확인
        timer.setTimerTime(LocalDateTime.of(2000,1,1,4,0,0));
        timer.startTimer();
        assertEquals(timer.getpauseTimerFlag(),false);
        //pauseTimer 후 종료되었느지 확인
        timer.pauseTimer();
        assertEquals(timer.getpauseTimerFlag(),true);

    }

    @Test
    public void cancelTimer() {
        Buzzer buzzer = new Buzzer();
        Timer timer = new Timer(buzzer);

        timer.setTimerTime(LocalDateTime.of(2000,1,1,1,1,1));
        timer.setSettingTimer(LocalDateTime.of(2000,3,1,5,2,1));
        LocalDateTime temp=timer.getSettingTimer();

        //cancel Timer 후 settingTimer의 값이 timerTime에 저장되었는지 확인
        timer.cancelTimer();

        assertEquals(timer.getTimerTime(),temp);

    }

}
