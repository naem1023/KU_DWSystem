package Sys;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TimeTest {

    @Test
    public void timeflow() {
        Time t=new Time();
        for(int i=0;i<100;i++){
            t.timeflow();
        }
        int result=t.getCurrentTime().getSecond();
        assertEquals(1,result);
        LocalDateTime test=LocalDateTime.of(9999, 12, 31, 23, 59, 59, 990000000);
        t.setCurrentTime(test);
        for(int i=0;i<100;i++){
            t.timeflow();
        }
        //10000년 예외체크 0으로 돌아가는지.
        result=t.getCurrentTime().getYear();
        assertEquals(0,result);
    }

    @Test
    public void changeCursor() {
        Time t= new Time();
        t.changeCursor();
        //한 번 바꿨을 때 cursor증가하는지
        int result=t.getCurrentCursor();
        assertEquals(1,result);
        t.changeCursor();
        t.changeCursor();
        t.changeCursor();
        t.changeCursor();
        t.changeCursor();
        t.changeCursor();
        t.changeCursor();

        //계속 change해서 끝에 다다랐을 때 다시 처음으로 돌아오는지
        result=t.getCurrentCursor();
        assertEquals(0,result);
    }


    @Test
    public void increaseData() {
        Time t= new Time();
        t.enterEditData();

        t.increaseData();
        int result=t.getEditTime().getSecond();
        assertEquals(1,result);
        LocalDateTime test=LocalDateTime.of(9999, 12, 31, 23, 59, 59, 0);
        t.setEditTime(test);
        t.increaseData();
        //년도 10000이상일 경우 0이되는지
        result=t.getEditTime().getYear();
        assertEquals(0,result);

        //gmt 증가확인
        t.setCurrentCursor(6);
        t.increaseData();
        result=t.getGMT();
        assertEquals(10,result);

        //gmt가 14보다 커지면 -12에서부터 다시 계산되는지 check
        t.increaseData();
        t.increaseData();
        t.increaseData();
        t.increaseData();
        t.increaseData();
        result=t.getGMT();
        assertEquals(-12,result);

        //format
        t.setCurrentCursor(7);
        t.increaseData();
        boolean re=t.getFormat();
        assertEquals(false,re);
    }

    @Test
    public void decreaseData() {
        Time t= new Time();
        t.enterEditData();

        t.decreaseData();
        int result=t.getEditTime().getSecond();
        assertEquals(59,result);
        LocalDateTime test=LocalDateTime.of(0, 1, 1, 00, 00, 00, 0);
        t.setEditTime(test);
        t.decreaseData();
        //decrease로 년도가 음수가 될 경우 9999로 설정되는지
        result=t.getEditTime().getYear();
        assertEquals(9999,result);

        //gmt 감소 확인
        t.setCurrentCursor(6);
        t.decreaseData();
        result=t.getGMT();
        assertEquals(8,result);
        
        //gmt 범위 확인 -12보다 작아질시 14부터 다시 시작되는지
        t.setGMT(-10);
        t.decreaseData();
        t.decreaseData();
        t.decreaseData();
        result=t.getGMT();
        assertEquals(14,result);

        //format
        t.setCurrentCursor(7);
        t.decreaseData();
        boolean re=t.getFormat();
        assertEquals(false,re);

    }

    @Test
    public void saveData() {
        Time t= new Time();
        LocalDateTime test=LocalDateTime.of(7878, 1, 1, 00, 00, 00, 0);
        t.setEditTime(test);
        t.saveData();
        int result=t.getCurrentTime().getYear();
        assertEquals(7878,result);
        assertEquals(null,t.getEditTime());
        //editTime이 currentTime으로 정상적으로 반영되는지
    }
}