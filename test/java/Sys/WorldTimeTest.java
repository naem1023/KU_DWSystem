package Sys;

import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class WorldTimeTest {

    @Test
    public void changeTimezone() {
        WorldTime w= new WorldTime();
        w.changeTimezone();
        int result=w.getCurrentCity();
        //city변경이 정상적으로 이루어지는지
        assertEquals(1,result);


        w.changeTimezone();
        w.changeTimezone();
        w.changeTimezone();
        w.changeTimezone();
        w.changeTimezone();

        result=w.getCurrentCity();
        //범위를 벗어나지 않고, 순회하며 정상적으로 변경이 이루어지는지
        assertEquals(0,result);
    }


    @Test
    public void getWorldTime() {
        Time t=new Time();
        WorldTime w= new WorldTime();
        w.changeTimezone();

        LocalDateTime tm=t.getCurrentTime();
        int gmt=t.getGMT();
        int result=w.getWorldTime(tm,gmt).getHour();
        //paris로 gmt설정하고 정상적으로 시간을 계산하는지
        assertEquals(16,result);
    }
}