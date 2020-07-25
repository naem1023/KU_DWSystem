package Sys;

import java.util.*;
import java.time.*;

/**
 *
 */
public class Timer implements  Mode{

    /**
     * Default constructor
     */
    private boolean isActivated;
    @Override
    public void setActive(boolean act) {
        this.isActivated=act;
    }

    @Override
    public boolean getActive() {
        return this.isActivated;
    }

    public Timer(Buzzer buzzer) {
        isActivated=true;
        timerTime= LocalDateTime.of(2000,1,1,0,0,0);
        settingTimer=LocalDateTime.of(2000,1,1,0,0,0);
        pauseTimerFlag=true;
        this.buzzer=buzzer;
    }

    //cancel 용 Timer 변수 저장
    private LocalDateTime settingTimer;

    //시간 을 99 까지 표현해야 하므로 Display할때 day까지 묶어서 계산해야함
    // 추가적으로 Day는 0값을 가질수 없어 1으로 초기값을 설정 그래서 Hour계산할때
    // (Day-1)*24 + Hour 이 Display될 시간
    private LocalDateTime timerTime;
    private Buzzer buzzer;
    private Boolean saveTimerFlag;

    private Boolean pauseTimerFlag;

    //추가한 변수
    private int timerCursor;
    //추가한 함수
    public void enterEditTimer(){
        pauseTimerFlag=true;
        saveTimerFlag=false;
        timerCursor = 0;
    }

    public void changeCursor() {
        // TODO implement here
        timerCursor= (timerCursor+1)%3;
    }

    // 0~59 처리 포함 되어야 함 분을 59->0 해도 시간 증가 안하게
    public void increaseData() {
        // TODO implement here
        switch (timerCursor){
            case 0:
                if(timerTime.getDayOfMonth() >= 5 && timerTime.getHour() >=3 ){
                    timerTime= LocalDateTime.of(2000,1,1,0,timerTime.getMinute(),timerTime.getSecond());
                }
                else{
                    timerTime = timerTime.plusHours(1);
                }
                break;
            case 1:
                if(timerTime.getMinute() >= 59){
                    timerTime = timerTime.minusMinutes(timerTime.getMinute());
                }
                else{
                    timerTime = timerTime.plusMinutes(1);
                }
                break;
            case 2:
                if(timerTime.getSecond() >= 59){
                    timerTime = timerTime.minusSeconds(timerTime.getSecond());
                }
                else{
                    timerTime = timerTime.plusSeconds(1);
                }
                break;
        }
    }


    public void decreaseData() {
        // TODO implement here
        switch (timerCursor){
            case 0:
                if(timerTime.getDayOfMonth() == 1 && timerTime.getHour() ==0 ){
                    timerTime= LocalDateTime.of(2000,1,5,3,timerTime.getMinute(),timerTime.getSecond());
                }
                else{
                    timerTime= timerTime.plusHours(-1);
                }
                break;
            case 1:
                if(timerTime.getMinute() == 0){
                    timerTime= timerTime.plusMinutes(59);
                }
                else{
                    timerTime= timerTime.plusMinutes(-1);
                }
                break;
            case 2:
                if(timerTime.getSecond() == 0){
                    timerTime= timerTime.plusSeconds(59);
                }
                else{
                    timerTime= timerTime.plusSeconds(-1);
                }
                break;
        }
    }


    public void saveTimer() {
        // TODO implement here
        this.settingTimer=this.timerTime;
        saveTimerFlag=true;
    }

    //public으로 바꿈
    public boolean decreaseTimer() {
        // TODO implement here
        if(pauseTimerFlag){
            return false;
        }

        //
        timerTime = timerTime.minusNanos(10000000);

        LocalDateTime defaulTime=LocalDateTime.of(2000,1,1,0,0,0);
        if(defaulTime.isAfter(timerTime) || defaulTime.isEqual(timerTime)){
            //ModeManager.beepbuzzer()

            buzzer.setBuzzerOn(true);
            pauseTimerFlag=true;
            timerTime=LocalDateTime.of(2000,1,1,0,0,0);
            return true;
        }
        return false;
    }



    public void startTimer() {
        // TODO implement here
        if(timerTime.getDayOfMonth()==1 && timerTime.getHour()==0 && timerTime.getSecond()==0 && timerTime.getMinute()==0){
            //0 리셋 상태일 때 아무작동 x
            return;
        }
        else {
            pauseTimerFlag = false;
        }
    }


    public void pauseTimer() {
        // TODO implement here
        pauseTimerFlag=true;
    }


    public void resumeTimer() {
        // TODO implement here
        pauseTimerFlag=false;
    }


    public void cancelTimer() {
        // TODO implement here
        if(pauseTimerFlag) timerTime= this.settingTimer;
    }

    //get cursor
    public int getCurrentCursor() {
        return this.timerCursor;
    }
    //get Timer time
    public LocalDateTime getTimerTime() {return this.timerTime;}
    public boolean getpauseTimerFlag() {return this.pauseTimerFlag; }
    public boolean getsaveTimerFlag() {return this.saveTimerFlag; }
    public LocalDateTime getSettingTimer() { return settingTimer; }

    public void setSettingTimer(LocalDateTime setTime){this.settingTimer=setTime;}
    public void setTimerTime(LocalDateTime timerTime){this.timerTime=timerTime;}

}


