package Sys;

import org.junit.Test;

import static org.junit.Assert.*;

public class ModeManagerTest_ {

    @Test
    public void testchangeMode(){
        ModeManager manager = new ModeManager();
        manager.changeMode();
        manager.changeMode();
        assertEquals(2, manager.getCurrentMode());
    }

    @Test
    public void testchangeMode_overflow(){
        ModeManager manager = new ModeManager();
        manager.changeMode();
        manager.changeMode();
        manager.changeMode();
        manager.changeMode();
        manager.changeMode();
        assertEquals(1, manager.getCurrentMode());
    }
}