package Sys;

import org.junit.Test;

import static org.junit.Assert.*;

public class ModeManagerTest {


    @Test
    public void enterEditMode() {
        ModeManager m = new ModeManager();
        m.enterEditMode();
        int result = m.getCurrentMode();
        assertEquals(8, result);
    }

    @Test
    public void changeCursor() {
        ModeManager m = new ModeManager();
        m.changeCursor();
        int result = m.getCurrentCursor();
        assertEquals(1, result);

        m.changeCursor();
        m.changeCursor();
        m.changeCursor();
        m.changeCursor();
        m.changeCursor();
        result = m.getCurrentCursor();
        //Cursor값의 범위를 벗아나지 않고 순회하며 정상적으로 값이 변경되는지
        assertEquals(0, result);

    }

    @Test
    public void changeStatus() {
        ModeManager m = new ModeManager();
        m.enterEditMode();
        m.changeStatus();
        boolean result = m.getEditStatus()[0];
        //changeStatus를 했을 때 정상적으로 상태를 변경하는지
        assertEquals(false, result);

        //그리고 activeModeCounter에 활성화된 mode의 개수가 정상적으로 반영되는지 
        assertEquals(3, m.getActiveModeCounter());
    }

    @Test
    public void saveModeData() {
        ModeManager m = new ModeManager();

        m.enterEditMode();
        //1 active deactive설정이 제대로 modes에 반영되는지 test, currentMode를 active한것들 중 첫번째 것으로 설정하는지 확인
        m.changeStatus();
        m.changeCursor();
        m.changeCursor();
        m.changeCursor();
        m.changeCursor();
        m.changeStatus();

        m.saveModeData();

        boolean result = m.getmodes()[0].getActive();
        int mode = m.getCurrentMode();
        //mode의 활성화 비활성화설정이 정상적으로 save를 했을 때 반영이 되는지
        assertEquals(false, result);
        
        //그리고 save하고 editMode를 나왔을 때 활성화된 모드중에 가장 첫번째 mode로 현재 mode가 설정되는지
        assertEquals(1, mode);

        m.enterEditMode();
        m.changeStatus();
        m.saveModeData();
        result = m.isEditMode();
        //activemodecounter가 4개가 아닐 때 editmode 탈출안하는지 확인
        assertEquals(true, result);

    }

    @Test
    public void changeMode() {
        ModeManager manager = new ModeManager();
        manager.changeMode();
        manager.changeMode();
        assertEquals(2, manager.getCurrentMode());
        manager.changeMode();
        manager.changeMode();
        manager.changeMode();
        assertEquals(1, manager.getCurrentMode());

    }
}