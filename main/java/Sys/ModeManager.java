package Sys;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



/**
 *
 */
public class ModeManager {

    /**
     * Default constructor
     */
    public ModeManager() {

        buzzer = new Buzzer();

        modes = new Mode[6];
        modes[0] = new Time();
        modes[1] = new Alarm(buzzer, modes[0]);
        modes[2] = new Timer(buzzer);
        modes[3] = new StopWatch();
        modes[4] = new CalorieCheck();
        modes[5] = new WorldTime();

        editStatus= new Boolean[6];
        currentMode=0;
        isEditMode = false;
        buzzerFlag=false;
    }


    //Time Alarm Timer Stopwatch CalorieCheck WorldTime
    //0     1       2       3       4            5
    //private Boolean[] isModeActive; //사용이제 안하는..
    private Boolean[] editStatus;//set mode 중 return to default screen시 저장안하기 위해 이 변수로 편집.

    private Mode[] modes;
    private int currentMode;//0~5 Time Alarm Timer Stopwatch Calorie Check 순으로    *8일경우 setMode

    private Boolean buzzerFlag;
    private float elapsedTime;
    private int ActiveModeCounter;//setMode때 4개의 mode가 활성화되어야만 탈출가능 그때 참조할 변수 active된 mode의 개수
    private int currentCursor;//setMode시에 status값을 바꿀 때 참조할 index

    //Mode  Adjust  Forward Reverse
    //0     1       2       3
    private int Button;

    private boolean isEditMode;

    //객체 생성 ms
    private Buzzer buzzer;



    public void makeThread(){
        Tick task = new Tick(this);
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Void> future = service.submit(task);
    }


    public void plus_ElapsedTime(float tt){
        if(isEditMode){
            elapsedTime += tt;
            returnToDefault();
        }

    }

    /**
     *
     *///
    public void changeMode() {
        while(true){
            currentMode = (currentMode+1)%6;
            if(modes[currentMode].getActive() == true){
                break;
            }
        }
    }
    //설정모드에서 5초가 지나면 default화면으로 복귀
    private void returnToDefault(){

        if(isEditMode && elapsedTime >= 5.0f){
            switch (currentMode){
                case 0:
                    ((Time) modes[0]).saveData();
                    isEditMode = !isEditMode;
                    break;
                case 1:
                    ((Alarm) modes[1]).saveAlarm();
                    isEditMode = !isEditMode;
                    break;
                case 2:
                    ((Timer) modes[2]).saveTimer();
                    isEditMode = !isEditMode;
                    break;
                case 4:
                    ((CalorieCheck) modes[4]).saveCalorieSetting();
                    isEditMode = !isEditMode;
                    break;
                case 8:
                    if(getActiveModeCounter() == 4) {
                        for(int j = 0; j < 6; j++) modes[j].setActive(editStatus[j].booleanValue());
                        for(int i = 0; i < 6; i++){
                            if(modes[i].getActive()){
                                currentMode = i;
                                break;
                            }
                        }
                        isEditMode = !isEditMode;
                        break;
                    }
                    break; //setMode중 return to default 발생시 저장안하고 종료
                default: // 3-stopwatch, 5 worldtime은 set이 없다.
                    break;
            }
        }
    }

    public void clickButton(int Button, boolean longClickedFlag) {
        elapsedTime=0.0f;
        if(buzzer.getBuzzerOn()){// 버저 울릴때
            //아무 버튼이나 들어오면
            if(Button != -1) {
                buzzer.stopBuzzer();
                if(buzzer.getIsAlarmRinging() == true)
                    buzzer.setIsAlarmRinging(false);
            }
        }
        else{
            switch (currentMode){
                case 0://Time 모드  일때
                    if (isEditMode) {
                        //setTime일때
                        switch (Button) {
                            case 0:
                                ((Time) this.modes[0]).changeCursor();
                                break;
                            case 1:
                                ((Time) this.modes[0]).saveData();
                                isEditMode = !isEditMode;
                                break;
                            case 2:
                                ((Time) this.modes[0]).increaseData();
                                break;
                            case 3:
                                ((Time) this.modes[0]).decreaseData();
                                break;
                        }

                    }
                    else {
                        //time keeping 일 때
                        switch (Button) {
                            case 0:
                                if (longClickedFlag == true) {
                                    this.enterEditMode();
                                } else
                                    this.changeMode();
                                break;
                            case 1:
                                if (longClickedFlag == true){
                                    ((Time) this.modes[0]).enterEditData();
                                    isEditMode = !isEditMode;}
                                break;
                            case 2:
                                //do nothing
                                break;
                            case 3:
                                //do nothing
                                break;
                        }
                    }
                    break;
                
                case 1://알람 모드  일때
                    if(isEditMode){
                        //button 1234
                        if(Button == 0)    //Mode : Cursor 옮김
                            ((Alarm)modes[1]).changeCursor();
                        else if(Button == 1) {  //Adjust를 눌렀을 때 저장하고 defualtScreen으로.
                            ((Alarm) modes[1]).saveAlarm();
                            isEditMode = !isEditMode;
                        }
                        else if(Button == 2)    //Forward : Cursor의 데이터를 증가시킴.
                            ((Alarm)modes[1]).increaseAlarmTime();
                        else if(Button == 3)    //Reverse : Cursor의 데이터를 감소시킴.
                            ((Alarm)modes[1]).decreaseAlarmTime();
                    }
                    else{
                        if(Button == 0 && longClickedFlag == false) {    //Mode : changeMode
                            this.changeMode();
                        }
                        else if(Button == 0 && longClickedFlag == true) {
                            this.enterEditMode();
                        }
                        else if(Button == 1 && longClickedFlag == true) {    //Adjust 눌렀을 때 set Alarm으로 진잊
                            ((Alarm) modes[1]).enterEditAlarm(); //
                            isEditMode = !isEditMode;
                        }
                        else if(Button == 1 && longClickedFlag == false) {    //Adjust : 현재 보고 있는 Alarm을 바꾼다.
                            ((Alarm) modes[1]).changeAlarm();
                        }
                        else if(Button == 2 && longClickedFlag == false) {    //Forward : 현재 보고 있는 알람을 on/off시킨다.
                            ((Alarm) modes[1]).turnOnOffAlarm();
                        }
                        else if(Button == 3);   //지정된 버튼이 없다.
                    }
                    break;
                case 2: //Timer Mode

                    if(isEditMode){
                        if(Button==0){
                            ((Timer)modes[2]).changeCursor();
                        }else if(Button ==1){
                            ((Timer)modes[2]).saveTimer();
                            isEditMode = !isEditMode;
                        }else if(Button==2){
                            ((Timer)modes[2]).increaseData();
                        }else if(Button ==3){
                            ((Timer)modes[2]).decreaseData();
                        }else{}

                    }
                    else{
                        if(Button == 0 && longClickedFlag == true) {    //set Mode로 진입.
                            this.enterEditMode();
                        }
                        else if(Button == 0 && longClickedFlag == false) {    //Mode : changeMode
                            this.changeMode();
                        }else if(Button ==1 && longClickedFlag ==false){
                            ((Timer)modes[2]).cancelTimer();
                        }else if(Button ==1 && longClickedFlag ==true && ((Timer)modes[2]).getpauseTimerFlag()){ //setTimer 진입
                            ((Timer)modes[2]).enterEditTimer();
                            isEditMode= !isEditMode;
                        }else if(Button==2 ){ //start Timer , pause Timer
                            if(((Timer)modes[2]).getpauseTimerFlag()){
                                ((Timer)modes[2]).startTimer();
                            }
                            else{
                                ((Timer)modes[2]).pauseTimer();
                            }
                        }else if(Button ==3){

                        }
                    }
                    break;
                case 3: //StopWatch
                        if(Button == 0 && longClickedFlag == false)
                            this.changeMode();  //Mode : changeMode
                        else if(Button == 0 && longClickedFlag == true)
                            this.enterEditMode();
                        else if(Button == 1 && !( ((StopWatch)modes[3]).getIsPaused() ))  //Adjust : resume 되어있었다면 laptime save.
                            ((StopWatch)modes[3]).lapStopwatch();
                        else if(Button == 1 && ((StopWatch)modes[3]).getIsPaused()) //Adjust : paused라면 reset.
                            ((StopWatch)modes[3]).resetStopwatch();
                        else if(Button == 2 && ((StopWatch)modes[3]).getIsPaused())  //Forward : paused라면 start. , 사실 Resume이나 Start나 operation 내부 동작은 같다...
                            ((StopWatch)modes[3]).startStopwatch();
                        else if(Button == 2 && !( ((StopWatch)modes[3]).getIsPaused() )) //Forward : paused가 아니라면 pause.
                            ((StopWatch)modes[3]).pauseStopwatch();
                        else if(Button == 3);    //지정된 버튼이 없다.
                    break;
                case 4: //CalorieCheck
                    //Set speed and weight 일 때
                    if(isEditMode){
                        switch (Button){
                            //Mode Button
                            //change cursor로 speed와 weight중 선택
                            case 0:
                                ((CalorieCheck) modes[4]).changeCursor();
                                break;

                            //Adjust Button
                            //save data and exit set speed and weight
                            case 1:
                                ((CalorieCheck) modes[4]).saveCalorieSetting();
                                isEditMode = !isEditMode;
                                break;

                            //Forward Button
                            case 2:
                                ((CalorieCheck) modes[4]).increaseData();
                                break;

                            //Reverse Button
                            case 3:
                                ((CalorieCheck) modes[4]).decreaseData();
                                break;
                        }
                    }

                    //Set speed and weight가 아닐 때
                    else {
                        switch(Button) {
                            //Mode Button
                            case 0:
                                //Mode Button이 꾹 눌렸을 때 = Set Mode를 한다
                                if (longClickedFlag) {
                                    this.enterEditMode();
                                }
                                //Mode Button이 짧게 눌렸을 때 = 다음 Mode를 화면에 디스플레이한다.
                                else {
                                    this.changeMode();
                                }
                                break;

                            //Adjust Button
                            case 1:
                                //Adjust Button이 꾹 눌렸을 때 = Set Speed and Weight를 한다, Non-start 상태일때만
                                if (longClickedFlag && !((CalorieCheck) modes[4]).getIsStart()) {
                                    ((CalorieCheck) modes[4]).enterSetSpeedandWeight();
                                    isEditMode = !isEditMode;
                                }
                                //Adjust Button이 한번 짧게 누렸을 때 = Reset CalorieCheck
                                else{
                                    //pause 상태일 때만 reset
                                    if(((CalorieCheck) modes[4]).getIsPause()){
                                        ((CalorieCheck) modes[4]).resetCalorieCheck();
                                    }
                                }
                                break;

                            //Forward Button
                            //start CalorieCheck, pause CalorieCheck, resume CalorieCheck
                            case 2:
                                //CalorieCheck가 시작된 상태다.
                                if (((CalorieCheck) modes[4]).getIsStart()) {
                                    //CalorieCheck가 시작된 상태고 puase 상태다.
                                    if (((CalorieCheck) modes[4]).getIsPause()) {
                                        ((CalorieCheck) modes[4]).resumeCaloreCheck();
                                    }
                                    //CalorieCheck가 시작된 상태고 pause 상태가 아니다.
                                    else {
                                        ((CalorieCheck) modes[4]).pauseCalorieCheck();
                                    }
                                }
                                //CalorieCheck가 시작되지 않은 상태다.
                                else {
                                    ((CalorieCheck) modes[4]).startCalorieCheck();
                                }
                                break;

                            //Reverse Button
                            case 3:
                                //아무일도 안함
                                break;
                        }
                    }
                    break;
                case 5: //World Time
                    switch (Button) {
                        case 0:
                            if (longClickedFlag == true)
                                this.enterEditMode();
                            else
                                this.changeMode();
                            break;
                        case 1:
                            //do nothing
                            break;
                        case 2:
                            ((WorldTime) this.modes[5]).changeTimezone();
                            break;
                        case 3:
                            //do nothing
                            break;
                    }
                    break;
                case 8: //Set mode
                    switch (Button) {
                        case 0:
                            this.changeCursor();
                            break;
                        case 1:

                            this.saveModeData();
                            break;
                        case 2:
                            this.changeStatus();
                            break;
                        case 3:
                            //do nothing
                            break;
                    }
                    break;
                default:
                    break;
            }
        }


    }


    /*
    setMode
    currentMode를 8으로 설정(for display)
     */

    public void enterEditMode() {
        this.currentMode=8;
        this.currentCursor=0;
        this.ActiveModeCounter=4;
        isEditMode=true;
        for(int i=0; i<6;i++){
            editStatus[i]= modes[i].getActive();
        }
    }


    public void changeCursor() {
        if(this.currentCursor==5) {
            this.currentCursor=0;
            return;
        }
        this.currentCursor+=1;
    }


    public void changeStatus() {// active->disable  disable->active
        if(this.editStatus[this.currentCursor]) {
            this.editStatus[this.currentCursor]=false;
            this.ActiveModeCounter--;
        }
        else {
            this.editStatus[this.currentCursor]=true;
            this.ActiveModeCounter++;
        }
    }


    public void saveModeData() {
        if(this.ActiveModeCounter!=4) return;

        for(int i=0; i<6; i++) this.modes[i].setActive(this.editStatus[i]);
        for(int i=0; i<6; i++){
           if(modes[i].getActive()==true){
               this.currentMode=i;
               break;
           }
        }
        this.isEditMode=false;
    }
    public int getCurrentMode(){ return this.currentMode; }
    //시퀀스 다이어그램 수정 사항. 없애도 되는 함수.
    public void changeToFirstMode() {
        // TODO implement here
    }


    public int getCurrentCursor(){ return this.currentCursor; }


    public Mode[] getmodes() { return modes; }

    public boolean isEditMode() { return isEditMode; }

    public Boolean[] getEditStatus(){ return this.editStatus;}

    public Buzzer getBuzzer() { return buzzer; }

    public int getActiveModeCounter() {return this.ActiveModeCounter;}

}
