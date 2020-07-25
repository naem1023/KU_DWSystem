package Sys;


import com.intellij.diagnostic.hprof.action.SystemTempFilenameSupplier;

import java.util.*;
import java.awt.*;
/**
 *
 */
public class Buzzer {

    /**
     * Default constructor
     */
    public Buzzer() {
        buzzerOn = false;
        buzzerThread = new BuzzerThread("buzzerThread");
        Thread t = new Thread(buzzerThread);
        t.start();

        isAlarmRinging = false;
        isBuzzerRinging = false;
    }


    private Boolean buzzerOn;
    private Boolean isAlarmRinging;
    private Boolean isBuzzerRinging;
    //추가된 부분
    private final int BEEPCOUNT = 5;
    //추가된 부분
    private BuzzerThread buzzerThread;

    private class BuzzerThread implements Runnable{
        int i = 0;
        BuzzerThread(String name){

        }
        public void run(){
            while(true) {
                i++;
                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    System.out.println("Sleep interrupted");
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }

                if (buzzerOn == true && isBuzzerRinging == false) {
                    isBuzzerRinging = true;
                    beepBuzzer();
                    buzzerOn = false;
                    isBuzzerRinging = false;
                    isAlarmRinging = false;
                }
            }
        }
    }

    /**
     *
     */
    public void beepBuzzer() {
        int i = 0;
        for(i=0; i < BEEPCOUNT && buzzerOn; i++){
            if(buzzerOn == false)
                return;
            java.awt.Toolkit.getDefaultToolkit().beep();
            try {
                Thread.sleep(1000); // introduce delay
            } catch (InterruptedException e) {
                System.out.println("Sleep interrupted");
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     *
     */
    public void stopBuzzer() {
        buzzerOn = false;
        isAlarmRinging = false;
    }
    public boolean getBuzzerOn(){return buzzerOn;}
    public boolean getIsAlarmRinging(){return isAlarmRinging;}
    public void setIsAlarmRinging(boolean isAlarmRinging){this.isAlarmRinging = isAlarmRinging;}
    public void setBuzzerOn(boolean onOff){this.buzzerOn = onOff;}

}
