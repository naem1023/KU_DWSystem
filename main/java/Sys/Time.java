package Sys;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;



public class Time implements Mode{
    private boolean isActivated;
    private LocalDateTime currentTime;// setTime 도중에 currentTime으로 바로 값을 바꿀경우 alarm 이 울릴 것을 대비해
    private LocalDateTime editTime; //editTime에 값을 설정하고 후에 saveTime때 currentTime에 반영한다.

    private int currentCursor; // 초 분 시 일 월 년 GMT 12/24시간 형식 0~7

    private int GMT;

    private boolean format; // false-> 12시간형식  True-> 24시간 형식
    //index와 format은 바로바로 저장되도 문제가 없기에 edit변수를 따로 만들지 않아도 된다.

    public Time() {
        this.currentTime=LocalDateTime.of(2020, 01, 01, 00, 00, 00, 00);
        this.editTime= null;
        this.format=true;
        this.isActivated=true;
        this.GMT=9;
    }



    /*
     enterEditData에 들어가면 cursor가 second를 가리키고 (Gui가 getter로 currentCursor값받아서 하기)
     mode manager쪽에 set하는것에 대한 flag가 존재해서 그것을 바꿔준다.
     그러면 gui가 그걸 반영해서 화면 바꿔주는 작업, 중간에 return to default screen이 발생하더라도 saveTime은 이루어진다.


     return to default해도 저장함


     */

    public void timeflow() {//thread로 돌릴 함수 1초마다 currentTime 값 1씩 증가.

        if(this.currentTime.plusNanos(10000000).getYear()<10000) {
            this.currentTime=this.currentTime.plusNanos(10000000);
        }
        else {
            this.currentTime = this.currentTime.plusNanos(10000000).plusYears(-10000);
        }
    }
    public void editTimeFlow(){
        if(this.editTime.plusNanos(10000000).getYear()<10000) {
            this.editTime=this.editTime.plusNanos(10000000);
        }
        else {
            this.editTime = this.editTime.plusNanos(10000000).plusYears(-10000);
        }
    }

    public void enterEditData() {
        this.editTime=this.currentTime;
        this.currentCursor=0;
    }

    /**
     커서 바꾸기 6일 경우 다시 0으로 설정.
     */
    public void changeCursor() {
        if(this.currentCursor==7) {
            this.currentCursor=0;
            return;
        }
        this.currentCursor+=1;
    }

    /**
     increase data
     현재 cursor에 따라서 그에 맞는 값을 증가시킨다.
     초분시일월년 GMT 12/24
     */
    public void increaseData() {

        switch(this.currentCursor) {
            case 0:
                if(this.editTime.plusSeconds(1).getYear()<9999) {//예외처리 년도는 0~9999
                    this.editTime=this.editTime.plusSeconds(1);
                }
                else {
                    this.editTime=this.editTime.plusSeconds(1).plusYears(-10000);
                }
                break;
            case 1:
                if(this.editTime.plusMinutes(1).getYear()<9999) {
                    this.editTime=this.editTime.plusMinutes(1);
                }
                else {
                    this.editTime=this.editTime.plusMinutes(1).plusYears(-10000);
                }
                break;
            case 2:
                if(this.editTime.plusHours(1).getYear()<9999) {
                    this.editTime=this.editTime.plusHours(1);
                }
                else {
                    this.editTime=this.editTime.plusHours(1).plusYears(-10000);
                }
                break;
            case 3:
                if(this.editTime.plusDays(1).getYear()<9999) {
                    this.editTime=this.editTime.plusDays(1);
                }
                else {
                    this.editTime=this.editTime.plusDays(1).plusYears(-10000);
                }
                break;
            case 4:
                if(this.editTime.plusMonths(1).getYear()<9999) {
                    this.editTime=this.editTime.plusMonths(1);
                }
                else {
                    this.editTime=this.editTime.plusMonths(1).plusYears(-10000);
                }
                break;
            case 5:
                if(this.editTime.plusYears(1).getYear()<9999) {
                    this.editTime=this.editTime.plusYears(1);
                }
                else {
                    this.editTime=this.editTime.plusYears(1).plusYears(-10000);
                }
                break;
            case 6:
                if(this.GMT==14) {
                    this.GMT=-12;
                }
                else {
                    this.GMT+=1;
                }
                break;
            case 7:
                this.format=!this.format;
                break;
        }

    }

    /*
     * increase data와 동일한 logic과 예외 처리진행
     */
    public void decreaseData() {
        // TODO implement here
        switch(this.currentCursor) {
            case 0:
                if(this.editTime.plusSeconds(-1).getYear()>0) {
                    this.editTime=this.editTime.plusSeconds(-1);
                }
                else {
                    this.editTime=this.editTime.plusSeconds(-1).plusYears(10000);
                }
                break;
            case 1:
                if(this.editTime.plusMinutes(1).getYear()>0) {
                    this.editTime=this.editTime.plusMinutes(-1);
                }
                else {
                    this.editTime=this.editTime.plusMinutes(-1).plusYears(10000);
                }
                break;
            case 2:
                if(this.editTime.plusHours(1).getYear()>0) {
                    this.editTime=this.editTime.plusHours(-1);
                }
                else {
                    this.editTime=this.editTime.plusHours(-1).plusYears(10000);
                }
                break;
            case 3:
                if(this.editTime.plusDays(1).getYear()>0) {
                    this.editTime=this.editTime.plusDays(-1);
                }
                else {
                    this.editTime=this.editTime.plusDays(-1).plusYears(10000);
                }
                break;
            case 4:
                if(this.editTime.plusMonths(1).getYear()>0) {
                    this.editTime=this.editTime.plusMonths(-1);
                }
                else {
                    this.editTime=this.editTime.plusMonths(-1).plusYears(10000);
                }
                break;
            case 5:
                if(this.editTime.plusYears(1).getYear()>0) {
                    this.editTime=this.editTime.plusYears(-1);
                }
                else {
                    this.editTime=this.editTime.plusYears(-1).plusYears(10000);
                }
                break;
            case 6:
                if(this.GMT==-12) {
                    this.GMT=14;
                }
                else {
                    this.GMT-=1;
                }
                break;
            case 7:
                this.format=!this.format;
                break;
        }
    }

    /**
     *
     */

    public void saveData() {
        // TODO implement here
        this.currentTime=this.editTime;
        this.editTime=null;
    }



    //getter and setter
    public LocalDateTime getCurrentTime() {
        return this.currentTime;
    }
    public int getCurrentCursor() {
        return this.currentCursor;
    }

    public LocalDateTime getEditTime() {
        return this.editTime;
    }

    public void setGMT(int gmt){this.GMT=gmt;}
    public int getGMT() {
        return this.GMT;
    }

    public boolean getFormat() {return this.format; }

    public void setCurrentTime(LocalDateTime currentTime){
        this.currentTime=currentTime;
    }

    public void setEditTime(LocalDateTime editTime) {
        this.editTime = editTime;
    }
    public void setCurrentCursor(int currentCursor){
        this.currentCursor=currentCursor;
    }
    @Override
    public void setActive(boolean act) {
        this.isActivated = act;
    }

    @Override
    public boolean getActive() {
        return this.isActivated;
    }
}