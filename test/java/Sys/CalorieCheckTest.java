package Sys;

import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;

public class CalorieCheckTest {


    @Test
    public void changeCursor() {
        CalorieCheck calorieCheck = new CalorieCheck();
        boolean cursor = calorieCheck.isCursor();
        calorieCheck.changeCursor();

        //커서를 바꿔보고 실제로 바뀌었는지 확인
        assertEquals(!cursor, calorieCheck.isCursor());
    }

    @Test
    public void increaseData() {
        CalorieCheck calorieCheck = new CalorieCheck();

        //cursor의 기본값은 true
        //cursor = true, tempWeight 증가
        //정상적인 경우
        int tempWeight = calorieCheck.getWeight();
        calorieCheck.setTempWeight(tempWeight);
        calorieCheck.increaseData();
        assertTrue(calorieCheck.isCursor());
        assertEquals(tempWeight+1, calorieCheck.getTempWeight());

        //tempWeight = 999이면 0으로 바꿔야 한다
        tempWeight = 999;
        calorieCheck.setTempWeight(tempWeight);
        calorieCheck.increaseData();
        assertEquals(0, calorieCheck.getTempWeight());


        //cursor = false, Speed 증가
        calorieCheck.setCursor(false);
        int tempSpeed = calorieCheck.getSpeed();
        calorieCheck.setTempSpeed(tempSpeed);
        calorieCheck.increaseData();
        assertFalse(calorieCheck.isCursor());
        assertEquals(tempSpeed+1, calorieCheck.getTempSpeed());

        //tempSpeed = 99면 0으로 바꿔야 한다.
        tempSpeed = 99;
        calorieCheck.setTempSpeed(tempSpeed);
        calorieCheck.increaseData();
        assertEquals(0, calorieCheck.getTempSpeed());
    }

    @Test
    public void decreaseData() {
        CalorieCheck calorieCheck = new CalorieCheck();

        //cursor의 기본값은 true
        //cursor = true, tempWeight 감소
        //정상적인 경우
        int tempWeight = calorieCheck.getWeight();
        calorieCheck.setTempWeight(tempWeight);
        calorieCheck.decreaseData();
        assertTrue(calorieCheck.isCursor());
        assertEquals(tempWeight-1, calorieCheck.getTempWeight());

        //tempWeight = 0이면 999으로 바꿔야 한다
        tempWeight = 0;
        calorieCheck.setTempWeight(tempWeight);
        calorieCheck.decreaseData();
        assertEquals(999, calorieCheck.getTempWeight());


        //cursor = false, Speed 증가
        calorieCheck.setCursor(false);
        int tempSpeed = calorieCheck.getSpeed();
        calorieCheck.setTempSpeed(tempSpeed);
        calorieCheck.decreaseData();
        assertFalse(calorieCheck.isCursor());
        assertEquals(tempSpeed-1, calorieCheck.getTempSpeed());

        //tempSpeed = 0면 99으로 바꿔야 한다.
        tempSpeed = 0;
        calorieCheck.setTempSpeed(tempSpeed);
        calorieCheck.decreaseData();
        assertEquals(99, calorieCheck.getTempSpeed());
    }

    @Test
    public void saveCalorieSetting() {
        CalorieCheck calorieCheck = new CalorieCheck();

        int expectedSpeed = 10;
        int expectedWeight = 66;
        //temp speed, weight 설정
        calorieCheck.setTempSpeed(expectedSpeed);
        calorieCheck.setTempWeight(expectedWeight);

        calorieCheck.saveCalorieSetting();

        //temp speed, weight가 실제 speed, weight에 저장됐는지 확인
        assertEquals(expectedSpeed, calorieCheck.getSpeed());
        assertEquals(expectedWeight, calorieCheck.getWeight());

        //cursor=true로 변했는지 확인
        assertTrue(calorieCheck.isCursor());
    }

    @Test
    public void enterSetSpeedandWeight() {
        CalorieCheck calorieCheck = new CalorieCheck();

        calorieCheck.enterSetSpeedandWeight();
        //실제 speed, weight가 tempSpeed, tempWeight에 들어갔는지 확인
        assertEquals(calorieCheck.getSpeed(), calorieCheck.getTempSpeed());
        assertEquals(calorieCheck.getWeight(), calorieCheck.getTempWeight());
    }

    @Test
    public void startCalorieCheck() {
        CalorieCheck calorieCheck = new CalorieCheck();

        calorieCheck.startCalorieCheck();
        //flag 설정 제대로 됐는지 확인
        assertFalse(calorieCheck.getIsPause());
        assertTrue(calorieCheck.getIsStart());
    }

    @Test
    public void resumeCaloreCheck() {
        CalorieCheck calorieCheck = new CalorieCheck();

        calorieCheck.resumeCaloreCheck();
        //flag 설정 제대로 됐는지 확인
        assertEquals(false, calorieCheck.getIsPause());
    }

    @Test
    public void pauseCalorieCheck() {
        CalorieCheck calorieCheck = new CalorieCheck();

        //flag 설정 제대로 됐는지 확인
        assertEquals(true, calorieCheck.getIsPause());
    }

    @Test
    public void endCalorieCheck() {
        CalorieCheck calorieCheck = new CalorieCheck();

        //flag 설정 제대로 됐는지 확인
        assertEquals(true, calorieCheck.getIsPause());
        assertEquals(false, calorieCheck.getIsStart());
    }

    @Test
    public void increaseCalorieCheckTimer() {
        CalorieCheck calorieCheck = new CalorieCheck();

        LocalTime time = calorieCheck.getCalorieTime();
        //calorieCheck.startCalorieCheck();
        calorieCheck.setStartFlag(true);
        calorieCheck.setPauseFlag(false);

        /*pause = false, start = true
        최초로 시작하고 0시 0분 0초에서 한번 증가시켜줬을 때를 가정*/
        calorieCheck.increaseCalorieCheckTimer();
        time = time.plusNanos(10000000);
        assertEquals(time, calorieCheck.getCalorieTime());
//        assertTrue(time.equals(calorieCheck.getCalorieTime()));

        //23시 59분 59초가 됐을 때
        /*
        1s = 10^3ms

        increaseCalorieCheckTimer 1번 당 10ms
        = 100번당 1초

        23시 59분 59초를 초로 환산하면
        23 * 3600 + 59 * 60 + 59 = n

        즉, CalorieTime이 초기화된 상태일 때
        increaseCalorieCheckTimer를 n*100번만큼 호출하면
        23시 59분 59초까지 간다.

        처음에 한번 호출했으니 n*100 - 1만큼 호출해야 23시 59분 59초가 된다.
        하지만 1번더 호출해야지 endCalorieCheck를 호출하므로
        n*100 -1 + 1 = n*100 번 호출하는게 맞다.
        */
        double n = 23 * 3600 + 59 * 60 + 59;
        n = n*100;
        for(int i=0; i<n; i++){
            calorieCheck.increaseCalorieCheckTimer();
        }
        time = LocalTime.of(23, 59, 59);

        //시간이 23시 59분 59초까지 늘어났는지 체크
        assertEquals(time, calorieCheck.getCalorieTime());
//        assertTrue(time.equals(calorieCheck.getCalorieTime()));

        assertTrue(calorieCheck.getIsPause());
        assertFalse(calorieCheck.getIsStart());
    }

    @Test
    public void resetCalorieCheck() {
        CalorieCheck calorieCheck = new CalorieCheck();

        calorieCheck.resetCalorieCheck();

        assertFalse(calorieCheck.isCursor());
        assertEquals(0, calorieCheck.getCalorie());
        assertEquals(LocalTime.of(0,0,0,0)
            , calorieCheck.getCalorieTime());
    }
}