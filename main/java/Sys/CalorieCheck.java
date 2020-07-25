package Sys;

import java.time.*;
import java.util.*;

/*
import GUI class;
import Time class;
 */
public class CalorieCheck implements Mode{

    /**
     * Default constructor
     */
    public CalorieCheck() {
        pauseCalorieCheckFlag = true;
        startCalorieCheckFlag = false;
        isActivated = false;
        cursor = true;
        Speed = 5;
        Weight = 60;
        Calorie = 0;

        //stopwatch 개념이므로 0시0분0초에서 시작
        CalorieTime= LocalTime.of(0,0,0,0);
    }
    LocalTime overflowStopWatchTime;

    /**
     *
     */
    private int Speed;
    private int tempSpeed;
    public int getSpeed() {return Speed;};
    public void setSpeed(int Speed) {this.Speed = Speed;}
    public void setTempSpeed(int tempSpeed){this.tempSpeed = tempSpeed;}
    /**
     *
     */
    private int Weight;
    private int tempWeight;
    public int getWeight() {return Weight;}
    public void setWeight(int Weight){this.Weight = Weight;}
    public void setTempWeight(int tempWeight){this.tempWeight = tempWeight;}
    /**
     *
     */
    private int Calorie;
    public int getCalorie() {
        calculateCalorie();
        return Calorie;
    }
    public void setCalorie(int Calorie){this.Calorie = Calorie;}

    /**
     stopwatch 개념의 시계
     0시0분0초 ~ 23시 59분 59초까지 측정 가능하게 할 것
     */
    private LocalTime CalorieTime;

    //calorieTime getter
    public LocalTime getCalorieTime() {return CalorieTime;}
    /**
    public int getHour(){return CalorieTime.getHour();}
    public int getMinute(){return CalorieTime.getMinute();}
    public int getSecond(){return CalorieTime.getSecond();}
    */

    //For set speend & weight
    public int getTempSpeed() { return tempSpeed;
    }

    public int getTempWeight() { return tempWeight;
    }




    //    flag
    private boolean pauseCalorieCheckFlag;
    public boolean getIsPause(){return pauseCalorieCheckFlag;}
    public void setPauseFlag(boolean flag){pauseCalorieCheckFlag = flag;}

    private boolean startCalorieCheckFlag;
    public boolean getIsStart(){return startCalorieCheckFlag;}
    public void setStartFlag(boolean flag){startCalorieCheckFlag = flag;}
    private boolean isActivated;

    //    false = speed, true = weight
    private boolean cursor;

    public void setCursor(boolean cursor){this.cursor=cursor;}



    /**
     *
     */
    public void changeCursor() {
        cursor = !cursor;
    }

    public boolean isCursor() { return cursor;}

    public void setActive(boolean act) {isActivated = act;};
    public boolean getActive() {return isActivated;};
    /**
     *
     */
    public void increaseData() {
        if(cursor){
            if(tempWeight == 999){
                tempWeight = 0;
            } else{
                tempWeight += 1;
            }
        }else{
            if(tempSpeed == 99){
                tempSpeed = 0;
            } else {
                tempSpeed += 1;
            }
        }
    }

    /**
     *
     */
    public void decreaseData() {
        if(cursor){
            if(tempWeight == 0){
                tempWeight = 999;
            } else{
                tempWeight -= 1;
            }
        }else{
            if(tempSpeed == 0){
                tempSpeed = 99;
            } else {
                tempSpeed -= 1;
            }
        }
    }

    /**
     임시변수에 저장해놓은 값을 실제 speed, weigth 변수에 저장
     */
    public void saveCalorieSetting() {
        //System.out.println("CalorieCheck saveData");
        Speed = tempSpeed;
        Weight = tempWeight;
        cursor = true;
    }

    /**
     *
     */
    public void enterSetSpeedandWeight() {
//        do some display logic
        tempWeight = Weight;
        tempSpeed = Speed;
    }

    /**
     *
     */
    public void startCalorieCheck() {
        //System.out.println("CalorieCheck start");
        pauseCalorieCheckFlag = false;
        startCalorieCheckFlag = true;
        cursor = false;
    }

    /**
     *
     */
    public void resumeCaloreCheck() {
        pauseCalorieCheckFlag = false;
    }

    /**
     *
     */
    public void pauseCalorieCheck() {
        pauseCalorieCheckFlag = true;
    }

    public void endCalorieCheck(){
        pauseCalorieCheckFlag = true;
        startCalorieCheckFlag = false;
    }
    /**
     *
     */
    public void increaseCalorieCheckTimer() {
        //10ms마다 호출된다
        //하지만 기본단위는 초이다.

        //calorie check가 pause 상태가 아닐 때
//        if(!pauseCalorieCheckFlag && startCalorieCheckFlag){
            //23시 59분 59초가 되면 시간측정 및 계산 종료
            if(CalorieTime.getHour() == 23 && CalorieTime.getMinute() == 59
                    && CalorieTime.getSecond() == 59){
                endCalorieCheck();
            }
            else{
                CalorieTime = CalorieTime.plusNanos(10000000);
                /*System.out.println(CalorieTime);
                System.out.println("Calorie=" + getCalorie());*/
            }
//        }
    }

    public void resetCalorieCheck(){
        cursor = false;
        Calorie = 0;
        startCalorieCheckFlag = false;
        CalorieTime = LocalTime.of(0,0,0,0);
    }

    private void calculateCalorie(){
        double allSeconds = CalorieTime.getHour()*3600 + CalorieTime.getMinute()*60
                + CalorieTime.getSecond();
        Calorie = (int) (0.0157 * ( ( 0.1 * Speed + 3.5 ) /3.5 ) * Weight * allSeconds);
    }
}
