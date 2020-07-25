package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import Sys.*;

public class Main {

  public static void main(String[] args) {

    watchGUI mainGUI = new watchGUI(); //initialized with TimeKeeping mode
    Thread updateGUI = new Thread(mainGUI);
    updateGUI.start();

  }

}
