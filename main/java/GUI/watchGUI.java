package GUI;

import Sys.*;
import Sys.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class watchGUI extends JFrame implements Runnable{

	public ModeManager modeManager;
	
	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 320;
	private static final int BUTTON_WIDTH = 10;
	private static final int BUTTON_HEIGHT = 45;

	private static final int MODE_TIMEKEEPING = 0;
	private static final int MODE_ALARM = 1;
	private static final int MODE_TIMER = 2;
	private static final int MODE_STOPWATCH = 3;
	private static final int MODE_CCHECK = 4;
	private static final int MODE_WTIME = 5;
	private static final int MODE_SETMODE = 8;

	private static final int MODE_BUTTON = 0;
	private static final int ADJUST_BUTTON = 1;
	private static final int FORWARD_BUTTON = 2;
	private static final int REVERSE_BUTTON = 3;

	private JPanel framePane;

	private JPanel watchBodyPane;

	private TimeKeeping_Pane timeKeepingPane;
	private Alarm_Pane alarmPane;
	private Timer_Pane timerPane;
	private Stopwatch_Pane swPane;
	private WorldTime_Pane wtPane;
	private CalorieCheck_Pane ccPane;

	private SetMode_Pane smPane;

	private JLabel nameLabel;

	private JButton adjustB;
	private JButton modeB;
	private JButton forwardB;
	private JButton reverseB;

	//ImageIcon (will put into JLabel)
	private ImageIcon seg14DeadBigImg; //grey big seg image
	private ImageIcon seg14DeadImg; //grey seg image
	private ImageIcon numBigImgs[]; //big num image 0 ~ 9
	private ImageIcon numImgs[]; //small num image 0 ~ 9
	private ImageIcon colonBigImg; //big colon image
	private ImageIcon colonImg; //small colon image
	private ImageIcon clockImg; //black clock image
	private ImageIcon clockDeadImg; //grey clock image
	private ImageIcon alphaAImg; //alphabet A
	private ImageIcon alphaCImg; //alphabet C
	private ImageIcon alphaDImg; //alphabet D
	private ImageIcon alphaEImg; //alphabet E
	private ImageIcon alphaIImg; //alphabet I
	private ImageIcon alphaLImg; //alphabet L
	//alphabet O = Number 0
	private ImageIcon alphaPImg; //alphabet P
	private ImageIcon alphaRImg; //alphabet R
	private ImageIcon alphaNImg; //alphabet N
	private ImageIcon alphaFImg; //alphabet F
	private ImageIcon alphaGImg; //alphabet G
	private ImageIcon alphaMImg; // M
	private ImageIcon alphaTImg; // T
	//alphabet S = Number 5
	private ImageIcon alphaWImg; //alphabet W
	private ImageIcon lineImg; // -
	private ImageIcon plusImg; // +
	private ImageIcon cursorImg; //cursor for mode setting
	private ImageIcon cursorSmallImg; //cursor for 2nd seg

	//Variables to save Mode Manager's value
	private static boolean isEditMode;
	private static boolean isAlarmRinging;

	//Variables to save TimeKeeping's value
	private static boolean tkFormat;
	private static boolean isGMTPlus;
	private static int tkGMT;
	private static int tkYear;
	private static int tkMonth;
	private static int tkDay;
	private static int tkHour;
	private static int tkMinute;
	private static int tkSecond;
	private static int tkGMTNum[] = new int[2];
	private static int tkYearNum[] = new int[4];
	private static int tkMonthNum[] = new int[2];
	private static int tkDayNum[] = new int[2];
	private static int tkHourNum[] = new int[2];
	private static int tkMinNum[] = new int[2];
	private static int tkSecNum[] = new int[2];

	//Variables to save Alarm's value
	private static int alarmHour;
	private static int alarmMin;
	private static int timerIndex;
	private static int ahNum[] = new int[2];
	private static int amNum[] = new int[2];

	//Variables to save Timer's value
	private static int stDay;
	private static int stHour;
	private static int stMin;
	private static int stSec;
	private static int timerDay;
	private static int timerHour;
	private static int timerMin;
	private static int timerSec;
	private static int sethNum[] = new int[2];
	private static int setmNum[] = new int[2];
	private static int setsNum[] = new int[2];
	private static int thNum[] = new int[2];
	private static int tmNum[] = new int[2];
	private static int tsNum[] = new int[2];

	//Variables to save Stopwatch's value
	private static int swHour;
	private static int swMin;
	private static int swSec;
	private static int swmSec;
	private static int lapHour;
	private static int lapMin;
	private static int lapSec;
	private static int lapmSec;
	private static int smNum[] = new int[2];
	private static int ssNum[] = new int[2];
	private static int smsNum[] = new int[2];
	private static int lmNum[] = new int[2];
	private static int lsNum[] = new int[2];
	private static int lmsNum[] = new int[2];

	//Variables to save Calorie's value
	private static int ccWeight;
	private static int ccSpeed;
	private static int ccCalorie;
	private static int cwNum[] = new int[3];
	private static int csNum[] = new int[2];
	private static int ccNum[] = new int[6];

	//Variables to save WorldTime's value
	private static int wtYear;
	private static int wtMonth;
	private static int wtDay;
	private static int wtHour;
	private static int wtMinute;
	private static int wtSecond;
	private static int wtYearNum[] = new int[4];
	private static int wtMonthNum[] = new int[2];
	private static int wtDayNum[] = new int[2];
	private static int wtHourNum[] = new int[2];
	private static int wtMinNum[] = new int[2];
	private static int wtSecNum[] = new int[2];

	//Variables to save SET MODE value
	private static int setModeCursor;
	private static Boolean[] isModeActive;
	private static int activeModeCounter;


	public watchGUI() {

		modeManager = new ModeManager();
		modeManager.makeThread();

		//load Images from resource folder
		seg14DeadBigImg = new ImageIcon(this.getClass().getResource(ImageDir.SegDead14Big_dir));
		seg14DeadImg = new ImageIcon(this.getClass().getResource(ImageDir.SegDead14_dir));
		colonImg = new ImageIcon(this.getClass().getResource(ImageDir.colon_dir));
		colonBigImg = new ImageIcon(this.getClass().getResource(ImageDir.colonBig_dir));
		clockImg = new ImageIcon(this.getClass().getResource(ImageDir.clock_dir));
		clockDeadImg = new ImageIcon(this.getClass().getResource(ImageDir.clockDead_dir));
		alphaFImg = new ImageIcon(this.getClass().getResource(ImageDir.fSeg_dir));
		alphaNImg = new ImageIcon(this.getClass().getResource(ImageDir.nSeg_dir));
		alphaGImg = new ImageIcon(this.getClass().getResource(ImageDir.gSeg_dir));
		alphaMImg = new ImageIcon(this.getClass().getResource(ImageDir.mSeg_dir));
		alphaTImg = new ImageIcon(this.getClass().getResource(ImageDir.tSeg_dir));
		alphaAImg = new ImageIcon(this.getClass().getResource(ImageDir.aSeg_dir));
		alphaCImg = new ImageIcon(this.getClass().getResource(ImageDir.cSeg_dir));
		alphaDImg = new ImageIcon(this.getClass().getResource(ImageDir.dSeg_dir));
		alphaEImg = new ImageIcon(this.getClass().getResource(ImageDir.eSeg_dir));
		alphaIImg = new ImageIcon(this.getClass().getResource(ImageDir.iSeg_dir));
		alphaLImg = new ImageIcon(this.getClass().getResource(ImageDir.lSeg_dir));
		alphaPImg = new ImageIcon(this.getClass().getResource(ImageDir.pSeg_dir));
		alphaRImg = new ImageIcon(this.getClass().getResource(ImageDir.rSeg_dir));
		alphaWImg = new ImageIcon(this.getClass().getResource(ImageDir.wSeg_dir));
		lineImg = new ImageIcon(this.getClass().getResource(ImageDir.centerLine_dir));
		plusImg = new ImageIcon(this.getClass().getResource(ImageDir.plus_dir));
		cursorImg = new ImageIcon(this.getClass().getResource(ImageDir.cursor_dir));
		cursorSmallImg = new ImageIcon(this.getClass().getResource(ImageDir.cursur_10_dir));
		numBigImgs = new ImageIcon[10]; // first segment's numbers
		for(int i=0; i<10; i++){
			numBigImgs[i] = new ImageIcon(this.getClass().getResource(ImageDir.numBigdirs[i]));
		}
		numImgs = new ImageIcon[10]; // second segment's numbers
		for(int i=0; i<10; i++){
			numImgs[i] = new ImageIcon(this.getClass().getResource(ImageDir.numdirs[i]));
		}

		//set window's name
		this.setTitle("Digital Watch");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

		timeKeepingPane = new TimeKeeping_Pane();
		alarmPane = new Alarm_Pane();
		timerPane = new Timer_Pane();
		swPane = new Stopwatch_Pane();
		wtPane = new WorldTime_Pane();
		ccPane = new CalorieCheck_Pane();
		smPane = new SetMode_Pane();

		framePane = new JPanel();

		nameLabel = new JLabel("Digital Watch #2");

		adjustB = new JButton();
		adjustB.addMouseListener(new MouseCustomAdapter() {
			@Override
			public void shortClicked(MouseEvent e) {
				modeManager.clickButton(ADJUST_BUTTON, false);
			}
			@Override
			public void longClicked(MouseEvent e) {
				modeManager.clickButton(ADJUST_BUTTON, true);
			}
		});
		modeB = new JButton();
		modeB.addMouseListener(new MouseCustomAdapter() {
			@Override
			public void shortClicked(MouseEvent e) {
				modeManager.clickButton(MODE_BUTTON, false);
			}
			@Override
			public void longClicked(MouseEvent e) {
				modeManager.clickButton(MODE_BUTTON, true);
			}
		});

		forwardB = new JButton();
		forwardB.addMouseListener(new MouseCustomAdapter() {
			@Override
			public void shortClicked(MouseEvent e) {
				modeManager.clickButton(FORWARD_BUTTON, false);
			}
			@Override
			public void longClicked(MouseEvent e) {
				modeManager.clickButton(FORWARD_BUTTON, true);
			}
		});

		reverseB = new JButton();
		reverseB.addMouseListener(new MouseCustomAdapter() {
			@Override
			public void shortClicked(MouseEvent e) {
				modeManager.clickButton(REVERSE_BUTTON, false);
			}
			@Override
			public void longClicked(MouseEvent e) {
				modeManager.clickButton(REVERSE_BUTTON, true);
			}
		});


		this.watchBodyPane = new JPanel();

		setWatchBodyPane(timeKeepingPane);

		framePane.setBackground(Color.WHITE);
		framePane.setLayout(null);

		nameLabel.setBounds(12, 10, 94, 15);

		adjustB.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		adjustB.setLocation(watchBodyPane.getLocation().x - BUTTON_WIDTH, watchBodyPane.getLocation().y + 55);
		adjustB.setBackground(Color.BLACK);

		modeB.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		modeB.setLocation(watchBodyPane.getLocation().x - BUTTON_WIDTH, watchBodyPane.getLocation().y + 135);
		modeB.setBackground(Color.BLACK);

		forwardB.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		forwardB.setLocation((watchBodyPane.getLocation().x + watchBodyPane.getWidth()), watchBodyPane.getLocation().y + 55);
		forwardB.setBackground(Color.BLACK);

		reverseB.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		reverseB.setLocation((watchBodyPane.getLocation().x + watchBodyPane.getWidth()), watchBodyPane.getLocation().y + 135);
		reverseB.setBackground(Color.BLACK);


		this.add(framePane);

		framePane.add(nameLabel);
		framePane.add(adjustB);
		framePane.add(modeB);
		framePane.add(forwardB);
		framePane.add(reverseB);
		framePane.add(watchBodyPane);

		revalidate();
		repaint();
	}

	//getters
	public TimeKeeping_Pane getTimeKeepingPane() { return timeKeepingPane; }

	public Alarm_Pane getAlarmPane() { return alarmPane; }

	public Timer_Pane getTimerPane() { return timerPane; }

	public Stopwatch_Pane getSwPane() { return swPane; }

	public WorldTime_Pane getWtPane() { return wtPane; }

	public CalorieCheck_Pane getCcPane() { return ccPane; }

	public SetMode_Pane getSmPane() { return smPane; }

	public JPanel getFramePane() {
		return framePane;
	}

	public JPanel getWatchBodyPane() {
		return watchBodyPane;
	}

	public void setWatchBodyPane(JPanel watchBodyPane) {
		this.watchBodyPane = watchBodyPane;
	}

	public JButton getAdjustB() {
		return adjustB;
	}

	public JButton getModeB() {
		return modeB;
	}

	public JButton getForwardB() {
		return forwardB;
	}

	public JButton getReverseB() {
		return reverseB;
	}

	public ImageIcon getSeg14DeadBigImg() { return seg14DeadBigImg; }

	public ImageIcon getSeg14DeadImg() { return seg14DeadImg; }

	public ImageIcon[] getNumBigImgs() { return numBigImgs; }

	public ImageIcon[] getNumImgs() { return numImgs; }

	public ImageIcon getColonBigImg() { return colonBigImg; }

	public ImageIcon getColonImg() { return colonImg; }

	public ImageIcon getClockImg() { return clockImg; }

	public ImageIcon getClockDeadImg() { return clockDeadImg; }

	public ImageIcon getAlphaNImg() { return alphaNImg; }

	public ImageIcon getAlphaFImg() { return alphaFImg; }

	public ImageIcon getAlphaGImg() { return alphaGImg; }

	public ImageIcon getAlphaMImg() { return alphaMImg; }

	public ImageIcon getAlphaTImg() { return alphaTImg; }

	public ImageIcon getLineImg() { return lineImg; }

	public ImageIcon getPlusImg() { return plusImg; }

	public ImageIcon getAlphaAImg() { return alphaAImg; }

	public ImageIcon getAlphaCImg() { return alphaCImg; }

	public ImageIcon getAlphaDImg() { return alphaDImg; }

	public ImageIcon getAlphaEImg() { return alphaEImg; }

	public ImageIcon getAlphaIImg() { return alphaIImg; }

	public ImageIcon getAlphaLImg() { return alphaLImg; }

	public ImageIcon getAlphaPImg() { return alphaPImg; }

	public ImageIcon getAlphaRImg() { return alphaRImg; }

	public ImageIcon getAlphaWImg() { return alphaWImg; }

	//GUI Update Methods
	private static void switchPanel(watchGUI mainGUI, JPanel newPanel){
		mainGUI.getFramePane().remove(mainGUI.getWatchBodyPane());
		mainGUI.setWatchBodyPane(newPanel);
		mainGUI.getFramePane().add(mainGUI.getWatchBodyPane());
		mainGUI.revalidate();
		mainGUI.repaint();
	}

	private void changeSecondImg(int currentMode, int index, int digitIndex){
		switch(currentMode) {
			case MODE_TIMEKEEPING:
				switch (digitIndex) {
					case 0:
						getTimeKeepingPane().getSecondSegs()[index].setIcon(getNumImgs()[0]);
						break;
					case 1:
						getTimeKeepingPane().getSecondSegs()[index].setIcon(getNumImgs()[1]);
						break;
					case 2:
						getTimeKeepingPane().getSecondSegs()[index].setIcon(getNumImgs()[2]);
						break;
					case 3:
						getTimeKeepingPane().getSecondSegs()[index].setIcon(getNumImgs()[3]);
						break;
					case 4:
						getTimeKeepingPane().getSecondSegs()[index].setIcon(getNumImgs()[4]);
						break;
					case 5:
						getTimeKeepingPane().getSecondSegs()[index].setIcon(getNumImgs()[5]);
						break;
					case 6:
						getTimeKeepingPane().getSecondSegs()[index].setIcon(getNumImgs()[6]);
						break;
					case 7:
						getTimeKeepingPane().getSecondSegs()[index].setIcon(getNumImgs()[7]);
						break;
					case 8:
						getTimeKeepingPane().getSecondSegs()[index].setIcon(getNumImgs()[8]);
						break;
					case 9:
						getTimeKeepingPane().getSecondSegs()[index].setIcon(getNumImgs()[9]);
						break;
				}
				break;
			case MODE_ALARM:
				switch (digitIndex) {
					case 0:
						getAlarmPane().getSecondSegs()[index].setIcon(getNumImgs()[0]);
						break;
					case 1:
						getAlarmPane().getSecondSegs()[index].setIcon(getNumImgs()[1]);
						break;
					case 2:
						getAlarmPane().getSecondSegs()[index].setIcon(getNumImgs()[2]);
						break;
					case 3:
						getAlarmPane().getSecondSegs()[index].setIcon(getNumImgs()[3]);
						break;
					case 4:
						getAlarmPane().getSecondSegs()[index].setIcon(getNumImgs()[4]);
						break;
					case 5:
						getAlarmPane().getSecondSegs()[index].setIcon(getNumImgs()[5]);
						break;
					case 6:
						getAlarmPane().getSecondSegs()[index].setIcon(getNumImgs()[6]);
						break;
					case 7:
						getAlarmPane().getSecondSegs()[index].setIcon(getNumImgs()[7]);
						break;
					case 8:
						getAlarmPane().getSecondSegs()[index].setIcon(getNumImgs()[8]);
						break;
					case 9:
						getAlarmPane().getSecondSegs()[index].setIcon(getNumImgs()[9]);
						break;
				}
				break;
			case MODE_TIMER:
				switch (digitIndex) {
					case 0:
						getTimerPane().getSecondSegs()[index].setIcon(getNumImgs()[0]);
						break;
					case 1:
						getTimerPane().getSecondSegs()[index].setIcon(getNumImgs()[1]);
						break;
					case 2:
						getTimerPane().getSecondSegs()[index].setIcon(getNumImgs()[2]);
						break;
					case 3:
						getTimerPane().getSecondSegs()[index].setIcon(getNumImgs()[3]);
						break;
					case 4:
						getTimerPane().getSecondSegs()[index].setIcon(getNumImgs()[4]);
						break;
					case 5:
						getTimerPane().getSecondSegs()[index].setIcon(getNumImgs()[5]);
						break;
					case 6:
						getTimerPane().getSecondSegs()[index].setIcon(getNumImgs()[6]);
						break;
					case 7:
						getTimerPane().getSecondSegs()[index].setIcon(getNumImgs()[7]);
						break;
					case 8:
						getTimerPane().getSecondSegs()[index].setIcon(getNumImgs()[8]);
						break;
					case 9:
						getTimerPane().getSecondSegs()[index].setIcon(getNumImgs()[9]);
						break;
				}
				break;
			case MODE_STOPWATCH:
				switch (digitIndex) {
					case 0:
						getSwPane().getSecondSegs()[index].setIcon(getNumImgs()[0]);
						break;
					case 1:
						getSwPane().getSecondSegs()[index].setIcon(getNumImgs()[1]);
						break;
					case 2:
						getSwPane().getSecondSegs()[index].setIcon(getNumImgs()[2]);
						break;
					case 3:
						getSwPane().getSecondSegs()[index].setIcon(getNumImgs()[3]);
						break;
					case 4:
						getSwPane().getSecondSegs()[index].setIcon(getNumImgs()[4]);
						break;
					case 5:
						getSwPane().getSecondSegs()[index].setIcon(getNumImgs()[5]);
						break;
					case 6:
						getSwPane().getSecondSegs()[index].setIcon(getNumImgs()[6]);
						break;
					case 7:
						getSwPane().getSecondSegs()[index].setIcon(getNumImgs()[7]);
						break;
					case 8:
						getSwPane().getSecondSegs()[index].setIcon(getNumImgs()[8]);
						break;
					case 9:
						getSwPane().getSecondSegs()[index].setIcon(getNumImgs()[9]);
						break;
				}
				break;
			case MODE_CCHECK:
				switch (digitIndex) {
					case 0:
						getCcPane().getSecondSegs()[index].setIcon(getNumImgs()[0]);
						break;
					case 1:
						getCcPane().getSecondSegs()[index].setIcon(getNumImgs()[1]);
						break;
					case 2:
						getCcPane().getSecondSegs()[index].setIcon(getNumImgs()[2]);
						break;
					case 3:
						getCcPane().getSecondSegs()[index].setIcon(getNumImgs()[3]);
						break;
					case 4:
						getCcPane().getSecondSegs()[index].setIcon(getNumImgs()[4]);
						break;
					case 5:
						getCcPane().getSecondSegs()[index].setIcon(getNumImgs()[5]);
						break;
					case 6:
						getCcPane().getSecondSegs()[index].setIcon(getNumImgs()[6]);
						break;
					case 7:
						getCcPane().getSecondSegs()[index].setIcon(getNumImgs()[7]);
						break;
					case 8:
						getCcPane().getSecondSegs()[index].setIcon(getNumImgs()[8]);
						break;
					case 9:
						getCcPane().getSecondSegs()[index].setIcon(getNumImgs()[9]);
						break;
				}
				break;
			case MODE_WTIME:
				switch (digitIndex) {
					case 0:
						getWtPane().getSecondSegs()[index].setIcon(getNumImgs()[0]);
						break;
					case 1:
						getWtPane().getSecondSegs()[index].setIcon(getNumImgs()[1]);
						break;
					case 2:
						getWtPane().getSecondSegs()[index].setIcon(getNumImgs()[2]);
						break;
					case 3:
						getWtPane().getSecondSegs()[index].setIcon(getNumImgs()[3]);
						break;
					case 4:
						getWtPane().getSecondSegs()[index].setIcon(getNumImgs()[4]);
						break;
					case 5:
						getWtPane().getSecondSegs()[index].setIcon(getNumImgs()[5]);
						break;
					case 6:
						getWtPane().getSecondSegs()[index].setIcon(getNumImgs()[6]);
						break;
					case 7:
						getWtPane().getSecondSegs()[index].setIcon(getNumImgs()[7]);
						break;
					case 8:
						getWtPane().getSecondSegs()[index].setIcon(getNumImgs()[8]);
						break;
					case 9:
						getWtPane().getSecondSegs()[index].setIcon(getNumImgs()[9]);
						break;
				}
				break;
		}
	}

	private void changefirstImg(int currentMode, int index, int digitIndex){
		switch(currentMode) {
			case MODE_TIMEKEEPING:
				switch (digitIndex) {
					case 0:
						getTimeKeepingPane().getFirstSegs()[index].setIcon(getNumBigImgs()[0]);
						break;
					case 1:
						getTimeKeepingPane().getFirstSegs()[index].setIcon(getNumBigImgs()[1]);
						break;
					case 2:
						getTimeKeepingPane().getFirstSegs()[index].setIcon(getNumBigImgs()[2]);
						break;
					case 3:
						getTimeKeepingPane().getFirstSegs()[index].setIcon(getNumBigImgs()[3]);
						break;
					case 4:
						getTimeKeepingPane().getFirstSegs()[index].setIcon(getNumBigImgs()[4]);
						break;
					case 5:
						getTimeKeepingPane().getFirstSegs()[index].setIcon(getNumBigImgs()[5]);
						break;
					case 6:
						getTimeKeepingPane().getFirstSegs()[index].setIcon(getNumBigImgs()[6]);
						break;
					case 7:
						getTimeKeepingPane().getFirstSegs()[index].setIcon(getNumBigImgs()[7]);
						break;
					case 8:
						getTimeKeepingPane().getFirstSegs()[index].setIcon(getNumBigImgs()[8]);
						break;
					case 9:
						getTimeKeepingPane().getFirstSegs()[index].setIcon(getNumBigImgs()[9]);
						break;
				}
				break;
			case MODE_ALARM:
				switch (digitIndex) {
					case 0:
						getAlarmPane().getFirstSegs()[index].setIcon(getNumBigImgs()[0]);
						break;
					case 1:
						getAlarmPane().getFirstSegs()[index].setIcon(getNumBigImgs()[1]);
						break;
					case 2:
						getAlarmPane().getFirstSegs()[index].setIcon(getNumBigImgs()[2]);
						break;
					case 3:
						getAlarmPane().getFirstSegs()[index].setIcon(getNumBigImgs()[3]);
						break;
					case 4:
						getAlarmPane().getFirstSegs()[index].setIcon(getNumBigImgs()[4]);
						break;
					case 5:
						getAlarmPane().getFirstSegs()[index].setIcon(getNumBigImgs()[5]);
						break;
					case 6:
						getAlarmPane().getFirstSegs()[index].setIcon(getNumBigImgs()[6]);
						break;
					case 7:
						getAlarmPane().getFirstSegs()[index].setIcon(getNumBigImgs()[7]);
						break;
					case 8:
						getAlarmPane().getFirstSegs()[index].setIcon(getNumBigImgs()[8]);
						break;
					case 9:
						getAlarmPane().getFirstSegs()[index].setIcon(getNumBigImgs()[9]);
						break;
				}
				break;
			case MODE_TIMER:
				switch (digitIndex) {
					case 0:
						getTimerPane().getFirstSegs()[index].setIcon(getNumBigImgs()[0]);
						break;
					case 1:
						getTimerPane().getFirstSegs()[index].setIcon(getNumBigImgs()[1]);
						break;
					case 2:
						getTimerPane().getFirstSegs()[index].setIcon(getNumBigImgs()[2]);
						break;
					case 3:
						getTimerPane().getFirstSegs()[index].setIcon(getNumBigImgs()[3]);
						break;
					case 4:
						getTimerPane().getFirstSegs()[index].setIcon(getNumBigImgs()[4]);
						break;
					case 5:
						getTimerPane().getFirstSegs()[index].setIcon(getNumBigImgs()[5]);
						break;
					case 6:
						getTimerPane().getFirstSegs()[index].setIcon(getNumBigImgs()[6]);
						break;
					case 7:
						getTimerPane().getFirstSegs()[index].setIcon(getNumBigImgs()[7]);
						break;
					case 8:
						getTimerPane().getFirstSegs()[index].setIcon(getNumBigImgs()[8]);
						break;
					case 9:
						getTimerPane().getFirstSegs()[index].setIcon(getNumBigImgs()[9]);
						break;
				}
				break;
			case MODE_STOPWATCH:
				switch (digitIndex) {
					case 0:
						getSwPane().getFirstSegs()[index].setIcon(getNumBigImgs()[0]);
						break;
					case 1:
						getSwPane().getFirstSegs()[index].setIcon(getNumBigImgs()[1]);
						break;
					case 2:
						getSwPane().getFirstSegs()[index].setIcon(getNumBigImgs()[2]);
						break;
					case 3:
						getSwPane().getFirstSegs()[index].setIcon(getNumBigImgs()[3]);
						break;
					case 4:
						getSwPane().getFirstSegs()[index].setIcon(getNumBigImgs()[4]);
						break;
					case 5:
						getSwPane().getFirstSegs()[index].setIcon(getNumBigImgs()[5]);
						break;
					case 6:
						getSwPane().getFirstSegs()[index].setIcon(getNumBigImgs()[6]);
						break;
					case 7:
						getSwPane().getFirstSegs()[index].setIcon(getNumBigImgs()[7]);
						break;
					case 8:
						getSwPane().getFirstSegs()[index].setIcon(getNumBigImgs()[8]);
						break;
					case 9:
						getSwPane().getFirstSegs()[index].setIcon(getNumBigImgs()[9]);
						break;
				}
				break;
			case MODE_CCHECK:
				switch (digitIndex) {
					case 0:
						getCcPane().getFirstSegs()[index].setIcon(getNumBigImgs()[0]);
						break;
					case 1:
						getCcPane().getFirstSegs()[index].setIcon(getNumBigImgs()[1]);
						break;
					case 2:
						getCcPane().getFirstSegs()[index].setIcon(getNumBigImgs()[2]);
						break;
					case 3:
						getCcPane().getFirstSegs()[index].setIcon(getNumBigImgs()[3]);
						break;
					case 4:
						getCcPane().getFirstSegs()[index].setIcon(getNumBigImgs()[4]);
						break;
					case 5:
						getCcPane().getFirstSegs()[index].setIcon(getNumBigImgs()[5]);
						break;
					case 6:
						getCcPane().getFirstSegs()[index].setIcon(getNumBigImgs()[6]);
						break;
					case 7:
						getCcPane().getFirstSegs()[index].setIcon(getNumBigImgs()[7]);
						break;
					case 8:
						getCcPane().getFirstSegs()[index].setIcon(getNumBigImgs()[8]);
						break;
					case 9:
						getCcPane().getFirstSegs()[index].setIcon(getNumBigImgs()[9]);
						break;
				}
				break;
			case MODE_WTIME:
				switch (digitIndex) {
					case 0:
						getWtPane().getFirstSegs()[index].setIcon(getNumBigImgs()[0]);
						break;
					case 1:
						getWtPane().getFirstSegs()[index].setIcon(getNumBigImgs()[1]);
						break;
					case 2:
						getWtPane().getFirstSegs()[index].setIcon(getNumBigImgs()[2]);
						break;
					case 3:
						getWtPane().getFirstSegs()[index].setIcon(getNumBigImgs()[3]);
						break;
					case 4:
						getWtPane().getFirstSegs()[index].setIcon(getNumBigImgs()[4]);
						break;
					case 5:
						getWtPane().getFirstSegs()[index].setIcon(getNumBigImgs()[5]);
						break;
					case 6:
						getWtPane().getFirstSegs()[index].setIcon(getNumBigImgs()[6]);
						break;
					case 7:
						getWtPane().getFirstSegs()[index].setIcon(getNumBigImgs()[7]);
						break;
					case 8:
						getWtPane().getFirstSegs()[index].setIcon(getNumBigImgs()[8]);
						break;
					case 9:
						getWtPane().getFirstSegs()[index].setIcon(getNumBigImgs()[9]);
						break;
				}
				break;
		}
	}

	private void keepValueToArray(int timeValue, int valueArray[]){
		if(timeValue < 10){
			valueArray[0] = 0;
			valueArray[1] = timeValue;
		}else{
			for(int i=valueArray.length-1; i>=0; i--){
				valueArray[i] = timeValue%10;
				timeValue /= 10;
			}
		}
	}

	private void keepHourToArray(int hour, int hourArray[]){
		if(hour == 0){
			hourArray[0] = 1;
			hourArray[1] = 2;
		}else if(hour >=1 && hour <=12){ //AM
			if(hour < 10){
				hourArray[0] = 0;
				hourArray[1] = hour;
			}else {
				for (int i = hourArray.length - 1; i >= 0; i--) {
					hourArray[i] = hour % 10;
					hour /= 10;
				}
			}
		}
		else if(hour >= 13 && hour <=23){ //PM
			hour -= 12;
			if(hour < 10){
				hourArray[0] = 0;
				hourArray[1] = hour;
			}else{
				for(int i=hourArray.length-1; i>=0; i--){
					hourArray[i] = hour%10;
					hour /= 10;
				}
			}
		}
	}

	private void keepYearToArray(int year, int yearArray[]){
		if(year < 10){
			for(int i=0; i<3; i++) yearArray[i] = 0;
			yearArray[3] = year;
		}else if(year>=10 && year<100){
			for(int i=0; i<2; i++) yearArray[i] = 0;
			for(int i=yearArray.length-1; i>=2; i--){
				yearArray[i] = year%10;
				year /= 10;
			}
		}else if(year>=100 && year<1000){
			yearArray[0] = 0;
			for(int i=yearArray.length-1; i>=1; i--){
				yearArray[i] = year%10;
				year /= 10;
			}
		}else{
			for(int i=yearArray.length-1; i>=0; i--){
				yearArray[i] = year%10;
				year /= 10;
			}
		}
	}

	private void keepWeightToArray(int weight, int weightArray[]){
		if(weight < 10){
			for(int i=0; i<2; i++) weightArray[i] = 0;
			weightArray[2] = weight;
		}else if(weight >= 10 && weight < 100){
			weightArray[0] = 0;
			for(int i=weightArray.length-1; i>=1; i--){
				weightArray[i] = weight%10;
				weight /= 10;
			}
		}else{
			for(int i=weightArray.length-1; i>=0; i--){
				weightArray[i] = weight%10;
				weight /= 10;
			}
		}
	}

	private void keepCalorieToArray(int calorie, int calorieArray[]){
		if(calorie < 10){
			for(int i=0; i<5; i++) calorieArray[i] = 0;
			calorieArray[5] = calorie;
		}else if(calorie>=10 && calorie<100){
			for(int i=0; i<4; i++) calorieArray[i] = 0;
			for(int i=calorieArray.length-1; i>=4; i--){
				calorieArray[i] = calorie%10;
				calorie /= 10;
			}
		}else if(calorie>=100 && calorie<1000){
			for(int i=0; i<3; i++) calorieArray[i] = 0;
			for(int i=calorieArray.length-1; i>=3; i--){
				calorieArray[i] = calorie%10;
				calorie /= 10;
			}
		}else if(calorie>=1000 && calorie<10000){
			for(int i=0; i<2; i++) calorieArray[i] = 0;
			for(int i=calorieArray.length-1; i>=2; i--){
				calorieArray[i] = calorie%10;
				calorie /= 10;
			}
		}else if(calorie>=10000 && calorie<100000){
			for(int i=0; i<1; i++) calorieArray[i] = 0;
			for(int i=calorieArray.length-1; i>=1; i--){
				calorieArray[i] = calorie%10;
				calorie /= 10;
			}
		}else{
			for(int i=calorieArray.length-1; i>=0; i--){
				calorieArray[i] = calorie%10;
				calorie /= 10;
			}
		}
	}

	@Override
	public void run() { // update GUI
		while(true) {

			isEditMode = modeManager.isEditMode();
			isAlarmRinging = modeManager.getBuzzer().getIsAlarmRinging();

			switch (modeManager.getCurrentMode()) {
				case MODE_TIMEKEEPING://if current Mode is Time Keeping
					// Set Time Mode
					if(isEditMode){
						Time time = (Time) modeManager.getmodes()[0];

						LocalDateTime editTime = time.getEditTime();
						int timeCursor = time.getCurrentCursor();

						tkFormat = time.getFormat();
						tkGMT = time.getGMT();
						tkYear = editTime.getYear();
						tkMonth = editTime.getMonthValue();
						tkDay = editTime.getDayOfMonth();
						tkHour = editTime.getHour();
						tkMinute = editTime.getMinute();
						tkSecond = editTime.getSecond();

						if(tkGMT >= 0) isGMTPlus = true;
						else{
							isGMTPlus = false;
							tkGMT = -tkGMT; //get absolute value
						}

						if(!tkFormat) {
							if (tkHour < 12) {
								getTimeKeepingPane().getMeridiemLabel().setText("AM");
							} else {
								getTimeKeepingPane().getMeridiemLabel().setText("PM");
							}
						}else{
							getTimeKeepingPane().getMeridiemLabel().setText("");
						}
						getTimeKeepingPane().getDowLabel().setText(editTime.getDayOfWeek().toString().substring(0, 3)); //display tkDay of week only 3 words

						//split time value
						keepValueToArray(tkGMT, tkGMTNum);
						keepYearToArray(tkYear, tkYearNum);
						keepValueToArray(tkMonth, tkMonthNum);
						keepValueToArray(tkDay, tkDayNum);
						keepValueToArray(tkMinute, tkMinNum);
						keepValueToArray(tkSecond, tkSecNum);

						if(tkFormat) keepValueToArray(tkHour, tkHourNum);
						else keepHourToArray(tkHour, tkHourNum);

						//set cursor image && choose visible cursor
						for(int i=0; i<6; i++){
							if(i<3)getTimeKeepingPane().getCursorLabel()[i].setIcon(cursorSmallImg);
							else if(i>=3) getTimeKeepingPane().getCursorLabel()[i].setIcon(cursorImg);
						}
						switch(timeCursor){
							case 0:
							case 6:
								getTimeKeepingPane().getCursorLabel()[5].setVisible(true);
								for(int i=0; i<4; i++) getTimeKeepingPane().getCursorLabel()[i].setVisible(false);
								break;
							case 1:
								getTimeKeepingPane().getCursorLabel()[4].setVisible(true);
								for(int i=0; i<6; i++){
									if(i != 4) getTimeKeepingPane().getCursorLabel()[i].setVisible(false);
								}
								break;
							case 2:
							case 7:
								getTimeKeepingPane().getCursorLabel()[3].setVisible(true);
								for(int i=0; i<6; i++){
									if(i != 3) getTimeKeepingPane().getCursorLabel()[i].setVisible(false);
								}
								break;
							case 3:
								getTimeKeepingPane().getCursorLabel()[2].setVisible(true);
								for(int i=0; i<6; i++){
									if(i != 2) getTimeKeepingPane().getCursorLabel()[i].setVisible(false);
								}
								break;
							case 4:
								getTimeKeepingPane().getCursorLabel()[1].setVisible(true);
								for(int i=0; i<6; i++){
									if(i != 1) getTimeKeepingPane().getCursorLabel()[i].setVisible(false);
								}
								break;
							case 5:
								getTimeKeepingPane().getCursorLabel()[0].setVisible(true);
								for(int i=1; i<6; i++)getTimeKeepingPane().getCursorLabel()[i].setVisible(false);
								break;
						}

						//Normal Edit
						if(timeCursor<6) {
							//set 2nd segment Img (tkYear, tkMonth, tkDay)
							for (int i = 0; i < 10; i++) {
								if (i >= 0 && i < 4) changeSecondImg(MODE_TIMEKEEPING, i, tkYearNum[i]);
								else if (i >= 5 && i < 7) changeSecondImg(MODE_TIMEKEEPING, i, tkMonthNum[i - 5]);
								else if (i >= 8 && i < 10) changeSecondImg(MODE_TIMEKEEPING, i, tkDayNum[i - 8]);
								else if (i == 4 || i == 7) getTimeKeepingPane().getSecondSegs()[i].setIcon(getColonImg());
							}
							//set first segment Img
							for (int i = 0; i < 8; i++) {
								if (i >= 0 && i < 2) changefirstImg(MODE_TIMEKEEPING, i, tkHourNum[i]);
								else if (i >= 3 && i < 5) changefirstImg(MODE_TIMEKEEPING, i, tkMinNum[i - 3]);
								else if (i >= 6 && i < 8) changefirstImg(MODE_TIMEKEEPING, i, tkSecNum[i - 6]);
								else if (i == 2 || i == 5) getTimeKeepingPane().getFirstSegs()[i].setIcon(getColonBigImg());
							}
						}
						//GMT
						else if(timeCursor == 6){
							//set 2nd segment Img
							for (int i = 0; i < 10; i++) {
								if (!(i==4 || i==7)) getTimeKeepingPane().getSecondSegs()[i].setIcon(getSeg14DeadImg());
								else getTimeKeepingPane().getSecondSegs()[i].setIcon(getColonImg());
							}
							//set first segment Img (tkHour, tkMinute, sec)
							getTimeKeepingPane().getFirstSegs()[0].setIcon(getAlphaGImg());
							getTimeKeepingPane().getFirstSegs()[1].setIcon(getAlphaMImg());
							getTimeKeepingPane().getFirstSegs()[2].setIcon(getColonBigImg());
							getTimeKeepingPane().getFirstSegs()[3].setIcon(getAlphaTImg());
							getTimeKeepingPane().getFirstSegs()[5].setIcon(getColonBigImg());
							for (int i = 6; i < 8; i++) changefirstImg(MODE_TIMEKEEPING, i, tkGMTNum[i-6]);

							if(isGMTPlus) getTimeKeepingPane().getFirstSegs()[4].setIcon(getPlusImg());
							else getTimeKeepingPane().getFirstSegs()[4].setIcon(getLineImg());

						}
						//12.24 Format
						else if(timeCursor == 7){
							//set 2nd segment Img
							for (int i = 0; i < 10; i++) {
								if (!(i==4 || i==7)) getTimeKeepingPane().getSecondSegs()[i].setIcon(getSeg14DeadImg());
								else getTimeKeepingPane().getSecondSegs()[i].setIcon(getColonImg());
							}
							//set 1st seg (12/24)
							if(tkFormat){ // 24 Format
								getTimeKeepingPane().getFirstSegs()[0].setIcon(getNumBigImgs()[2]);
								getTimeKeepingPane().getFirstSegs()[1].setIcon(getNumBigImgs()[4]);
								for(int i=2; i<8; i++){
									if(!(i==2 || i==5)) getTimeKeepingPane().getFirstSegs()[i].setIcon(getSeg14DeadBigImg());
									else getTimeKeepingPane().getFirstSegs()[i].setIcon(getColonBigImg());
								}
							}else{ // 12 Format
								getTimeKeepingPane().getFirstSegs()[0].setIcon(getNumBigImgs()[1]);
								getTimeKeepingPane().getFirstSegs()[1].setIcon(getNumBigImgs()[2]);
								for(int i=2; i<8; i++){
									if(!(i==2 || i==5)) getTimeKeepingPane().getFirstSegs()[i].setIcon(getSeg14DeadBigImg());
									else getTimeKeepingPane().getFirstSegs()[i].setIcon(getColonBigImg());
								}
							}
						}
						getTimeKeepingPane().getTitle().setText("SET TIME");
					}
					// Normal Time Keeping Mode
					else
						{
						Time time = (Time) modeManager.getmodes()[0];

						LocalDateTime now = time.getCurrentTime();

						tkFormat = time.getFormat();
						tkYear = now.getYear();
						tkMonth = now.getMonthValue();
						tkDay = now.getDayOfMonth();
						tkHour = now.getHour();
						tkMinute = now.getMinute();
						tkSecond = now.getSecond();

						if(!tkFormat) {
							if (tkHour < 12) {
								getTimeKeepingPane().getMeridiemLabel().setText("AM");
							} else {
								getTimeKeepingPane().getMeridiemLabel().setText("PM");
							}
						}else{
								getTimeKeepingPane().getMeridiemLabel().setText("");
						}
						getTimeKeepingPane().getDowLabel().setText(now.getDayOfWeek().toString().substring(0, 3)); //display tkDay of week only 3 words

						//split time value
						keepYearToArray(tkYear, tkYearNum);
						keepValueToArray(tkMonth, tkMonthNum);
						keepValueToArray(tkDay, tkDayNum);
						keepValueToArray(tkMinute, tkMinNum);
						keepValueToArray(tkSecond, tkSecNum);

						if(tkFormat) keepValueToArray(tkHour, tkHourNum);
						else keepHourToArray(tkHour, tkHourNum);

						//make cursors unvisible
							for(int i=0; i<6; i++)getTimeKeepingPane().getCursorLabel()[i].setVisible(false);

						//set 2nd segment Img (tkYear, tkMonth, tkDay)
						for (int i=0; i<10; i++) {
							if (i>=0 && i<4) changeSecondImg(MODE_TIMEKEEPING, i, tkYearNum[i]);
							else if (i>=5 && i<7) changeSecondImg(MODE_TIMEKEEPING, i, tkMonthNum[i-5]);
							else if (i>=8 && i<10) changeSecondImg(MODE_TIMEKEEPING, i, tkDayNum[i-8]);
							else if(i==4 || i==7) getTimeKeepingPane().getSecondSegs()[i].setIcon(getColonImg());
						}

						//set first segment Img (tkHour, tkMinute, sec)
						for (int i=0; i<8; i++) {
							if (i>=0 && i<2) changefirstImg(MODE_TIMEKEEPING, i, tkHourNum[i]);
							else if (i>=3 && i<5) changefirstImg(MODE_TIMEKEEPING, i, tkMinNum[i-3]);
							else if (i>=6 && i<8) changefirstImg(MODE_TIMEKEEPING, i, tkSecNum[i-6]);
							else if(i==2 || i==5) getTimeKeepingPane().getFirstSegs()[i].setIcon(getColonBigImg());
						}
							getTimeKeepingPane().getTitle().setText("TIME KEEPING");
					}


					//set clock icon
					if(isAlarmRinging) getTimeKeepingPane().getClockLabel().setIcon(getClockImg());
					else getTimeKeepingPane().getClockLabel().setIcon(getClockDeadImg());

					if(!(getWatchBodyPane().equals(timeKeepingPane))) switchPanel(this, timeKeepingPane);
					break;

				case MODE_ALARM: //if current Mode is Alarm

					//Set Alarm
					if(isEditMode){
						Alarm alarm = (Alarm) modeManager.getmodes()[1];

						LocalDateTime alarmTime = alarm.getCopyOfAlarmTimer();
						boolean isActivatedTimer = alarm.getCurrentAlarmisActivated();
						boolean isCursorHour = alarm.isCursorHour();
						boolean currentFormat = ((Time)modeManager.getmodes()[0]).getFormat();

						alarmHour = alarmTime.getHour();
						alarmMin = alarmTime.getMinute();
						timerIndex = alarm.getCurrentAlarmIndex() + 1;

						if(!currentFormat) {
							if (alarmHour < 12) {
								getAlarmPane().getMeridiemLabel().setText("AM");
							} else {
								getAlarmPane().getMeridiemLabel().setText("PM");
							}
						}else{
							getAlarmPane().getMeridiemLabel().setText("");
						}

						//split time value
						keepValueToArray(alarmMin, amNum);

						if(currentFormat) keepValueToArray(alarmHour, ahNum);
						else keepHourToArray(alarmHour, ahNum);

						//set cursor image && choose visible cursor
						for(int i=0; i<2; i++)getAlarmPane().getCursorLabel()[i].setIcon(cursorImg);
						if(isCursorHour){
							getAlarmPane().getCursorLabel()[0].setVisible(true);
							getAlarmPane().getCursorLabel()[1].setVisible(false);
						}else{
							getAlarmPane().getCursorLabel()[0].setVisible(false);
							getAlarmPane().getCursorLabel()[1].setVisible(true);
						}

						//2nd Segment img set
						if(isActivatedTimer){ //DISPLAY ON
							for(int i=0; i<2; i++) getAlarmPane().getSecondSegs()[i].setIcon(getSeg14DeadImg());
							getAlarmPane().getSecondSegs()[2].setIcon(getNumImgs()[0]);
							getAlarmPane().getSecondSegs()[3].setIcon(getAlphaNImg());
						}else{ // DISPLAY OFF
							getAlarmPane().getSecondSegs()[0].setIcon(getSeg14DeadImg());
							getAlarmPane().getSecondSegs()[1].setIcon(getNumImgs()[0]);
							getAlarmPane().getSecondSegs()[2].setIcon(getAlphaFImg());
							getAlarmPane().getSecondSegs()[3].setIcon(getAlphaFImg());
						}
						getAlarmPane().getSecondSegs()[4].setIcon(getColonImg());
						getAlarmPane().getSecondSegs()[5].setIcon(getAlphaNImg());
						getAlarmPane().getSecondSegs()[6].setIcon(getNumImgs()[0]);
						getAlarmPane().getSecondSegs()[7].setIcon(getColonImg());
						getAlarmPane().getSecondSegs()[8].setIcon(getNumImgs()[timerIndex]);
						getAlarmPane().getSecondSegs()[9].setIcon(getSeg14DeadImg());

						//first Segment img set
						for (int i=0; i<8; i++) {
							if (i>=0 && i<2) changefirstImg(MODE_ALARM, i, ahNum[i]);
							else if (i>=3 && i<5) changefirstImg(MODE_ALARM, i, amNum[i-3]);
							else if (i>=6 && i<8) getAlarmPane().getFirstSegs()[i].setIcon(getSeg14DeadBigImg());
							else if(i==2 || i==5) getAlarmPane().getFirstSegs()[i].setIcon(getColonBigImg());
						}

						getAlarmPane().getTitle().setText("SET ALARM");
					}
					//Normal Alarm display
					else{
						Alarm alarm = (Alarm) modeManager.getmodes()[1];

						LocalDateTime alarmTime = alarm.getCurrentAlarmTimer();
						boolean isActivatedTimer = alarm.getCurrentAlarmisActivated();
						boolean currentFormat = ((Time) modeManager.getmodes()[0]).getFormat();

						alarmHour = alarmTime.getHour();
						alarmMin = alarmTime.getMinute();
						timerIndex = alarm.getCurrentAlarmIndex() + 1;




						//split time value
						keepValueToArray(alarmMin, amNum);

						if(currentFormat) keepValueToArray(alarmHour, ahNum);
						else keepHourToArray(alarmHour, ahNum);

						//unvisible cursors
						for(int i=0; i<2; i++) getAlarmPane().getCursorLabel()[i].setVisible(false);

						//2nd Segment img set
						if(isActivatedTimer){ //DISPLAY ON
							for(int i=0; i<2; i++) getAlarmPane().getSecondSegs()[i].setIcon(getSeg14DeadImg());
							getAlarmPane().getSecondSegs()[2].setIcon(getNumImgs()[0]);
							getAlarmPane().getSecondSegs()[3].setIcon(getAlphaNImg());
						}else{ // DISPLAY OFF
							getAlarmPane().getSecondSegs()[0].setIcon(getSeg14DeadImg());
							getAlarmPane().getSecondSegs()[1].setIcon(getNumImgs()[0]);
							getAlarmPane().getSecondSegs()[2].setIcon(getAlphaFImg());
							getAlarmPane().getSecondSegs()[3].setIcon(getAlphaFImg());
						}
						getAlarmPane().getSecondSegs()[4].setIcon(getColonImg());
						getAlarmPane().getSecondSegs()[5].setIcon(getAlphaNImg());
						getAlarmPane().getSecondSegs()[6].setIcon(getNumImgs()[0]);
						getAlarmPane().getSecondSegs()[7].setIcon(getColonImg());
						getAlarmPane().getSecondSegs()[8].setIcon(getNumImgs()[timerIndex]);
						getAlarmPane().getSecondSegs()[9].setIcon(getSeg14DeadImg());

						//first Segment img set
						for (int i = 0; i < 8; i++) {
							if (i >= 0 && i < 2) changefirstImg(MODE_ALARM, i, ahNum[i]);
							else if (i >= 3 && i < 5) changefirstImg(MODE_ALARM, i, amNum[i - 3]);
							else if (i >= 6 && i < 8) getAlarmPane().getFirstSegs()[i].setIcon(getSeg14DeadBigImg());
							else if (i == 2 || i == 5) getAlarmPane().getFirstSegs()[i].setIcon(getColonBigImg());
						}

						getAlarmPane().getTitle().setText("ALARM");
					}

					//set clock icon
					if(isAlarmRinging) getAlarmPane().getClockLabel().setIcon(getClockImg());
					else getAlarmPane().getClockLabel().setIcon(getClockDeadImg());
					if(!(getWatchBodyPane().equals(alarmPane))) switchPanel(this, alarmPane);
					break;

				case MODE_TIMER: //if current Mode is Timer
					//Set timer mode
					if(isEditMode) {
						Sys.Timer timer = (Sys.Timer) modeManager.getmodes()[2];

						LocalDateTime timerTime = timer.getTimerTime();
						int timerCursor = timer.getCurrentCursor();

						//save time value to variables
						timerDay = timerTime.getDayOfMonth();
						timerHour = (timerDay - 1) * 24 + timerTime.getHour();
						timerMin = timerTime.getMinute();
						timerSec = timerTime.getSecond();

						//split Time Value to digit
						keepValueToArray(timerHour, thNum);
						keepValueToArray(timerMin, tmNum);
						keepValueToArray(timerSec, tsNum);

						//set cursor image && choose visible cursor
						for(int i=0; i<3; i++){
							getTimerPane().getCursorLabel()[i].setIcon(cursorImg);
							if(!(i == timerCursor)){
								getTimerPane().getCursorLabel()[i].setVisible(false);
							}else{
								getTimerPane().getCursorLabel()[i].setVisible(true);
							}
						}

						//set 2nd segment Img (hour, min, sec)
						for (int i = 0; i < 10; i++) {
							if (!(i == 4 || i == 7)) getTimerPane().getSecondSegs()[i].setIcon(getSeg14DeadImg());
							else getTimerPane().getSecondSegs()[i].setIcon(colonImg);
						}

						//set first segment Img (hour, min, sec)
						for (int i = 0; i < 8; i++) {
							if (i >= 0 && i < 2) changefirstImg(MODE_TIMER, i, thNum[i]);
							else if (i >= 3 && i < 5) changefirstImg(MODE_TIMER, i, tmNum[i - 3]);
							else if (i >= 6 && i < 8) changefirstImg(MODE_TIMER, i, tsNum[i - 6]);
							else if (i == 2 || i == 5) getTimerPane().getFirstSegs()[i].setIcon(getColonBigImg());
						}
						getTimerPane().getTitle().setText("SET TIMER");
					}
					//normal timer mode
					else{
						Sys.Timer timer = (Sys.Timer) modeManager.getmodes()[2];

						LocalDateTime timerTime = timer.getTimerTime();
						LocalDateTime setTime = timer.getSettingTimer();

						stDay = setTime.getDayOfMonth();
						stHour = (stDay - 1) * 24 + setTime.getHour();
						stMin = setTime.getMinute();
						stSec = setTime.getSecond();
						timerDay = timerTime.getDayOfMonth();
						timerHour = (timerDay - 1) * 24 + timerTime.getHour();
						timerMin = timerTime.getMinute();
						timerSec = timerTime.getSecond();


						//split Time Value to digit
						keepValueToArray(stHour, sethNum);
						keepValueToArray(stMin, setmNum);
						keepValueToArray(stSec, setsNum);
						keepValueToArray(timerHour, thNum);
						keepValueToArray(timerMin, tmNum);
						keepValueToArray(timerSec, tsNum);

						//unvisible cursors
						for(int i=0; i<3; i++) getTimerPane().getCursorLabel()[i].setVisible(false);

						//set 2nd segment Img (hour, min, sec)
						for (int i = 0; i < 10; i++) {
							if (i >= 0 && i < 2) getTimerPane().getSecondSegs()[i].setIcon(getSeg14DeadImg());
							else if (i >= 2 && i < 4) changeSecondImg(MODE_TIMER, i, sethNum[i - 2]);
							else if (i >= 5 && i < 7) changeSecondImg(MODE_TIMER, i, setmNum[i - 5]);
							else if (i >= 8 && i < 10) changeSecondImg(MODE_TIMER, i, setsNum[i - 8]);
							else if (i == 4 || i == 7) getTimerPane().getSecondSegs()[i].setIcon(colonImg);
						}

						//set first segment Img (hour, min, sec)
						for (int i = 0; i < 8; i++) {
							if (i >= 0 && i < 2) changefirstImg(MODE_TIMER, i, thNum[i]);
							else if (i >= 3 && i < 5) changefirstImg(MODE_TIMER, i, tmNum[i - 3]);
							else if (i >= 6 && i < 8) changefirstImg(MODE_TIMER, i, tsNum[i - 6]);
							else if (i == 2 || i == 5) getTimerPane().getFirstSegs()[i].setIcon(getColonBigImg());
						}
						getTimerPane().getTitle().setText("TIMER");
					}

					//set clock icon
					if(isAlarmRinging) getTimerPane().getClockLabel().setIcon(getClockImg());
					else getTimerPane().getClockLabel().setIcon(getClockDeadImg());
					if(!(getWatchBodyPane().equals(timerPane))) switchPanel(this, timerPane);

					break;

				case MODE_STOPWATCH: //if current Mode is Stopwatch

					StopWatch stopwatch = (StopWatch) modeManager.getmodes()[3];

					LocalTime swTime = stopwatch.getCurrentStopWatchTime();
					LocalTime lapTime = stopwatch.getLapStopWatchTime();

					swHour = swTime.getHour(); // we will not print 'HOUR'. But to convert it into minutes.
					swMin = swTime.getMinute();
					swSec = swTime.getSecond();
					swmSec = swTime.getNano()/10000000;

					lapHour = lapTime.getHour();
					lapMin = lapTime.getMinute();
					lapSec = lapTime.getSecond();
					lapmSec = lapTime.getNano()/10000000;

					if(swHour == 1) swMin += 60;
					if(lapHour == 1) lapMin += 60;

					//split Time Value
					keepValueToArray(swMin, smNum);
					keepValueToArray(swSec, ssNum);
					keepValueToArray(swmSec, smsNum);
					keepValueToArray(lapMin, lmNum);
					keepValueToArray(lapSec, lsNum);
					keepValueToArray(lapmSec, lmsNum);

					//set second segment Img (min, sec, ms)
					for (int i = 0; i < 10; i++) {
						if (i>=0 && i<2) getSwPane().getSecondSegs()[i].setIcon(getSeg14DeadImg());
						else if(i>=2 && i<4) changeSecondImg(MODE_STOPWATCH, i, lmNum[i-2]);
						else if(i>=5 && i<7) changeSecondImg(MODE_STOPWATCH, i, lsNum[i-5]);
						else if(i>=8 && i<10) changeSecondImg(MODE_STOPWATCH, i, lmsNum[i-8]);
						else if(i==4 || i==7) getSwPane().getSecondSegs()[i].setIcon(colonImg);
					}

					//set first segment Img (min, sec, ms)
					for (int i = 0; i < 8; i++) {
						if (i>=0 && i<2) changefirstImg(MODE_STOPWATCH, i, smNum[i]);
						else if (i>=3 && i<5) changefirstImg(MODE_STOPWATCH, i, ssNum[i-3]);
						else if (i>=6 && i<8) changefirstImg(MODE_STOPWATCH, i, smsNum[i-6]);
						else if(i==2 || i==5) getSwPane().getFirstSegs()[i].setIcon(getColonBigImg());
					}

					//set clock icon
					if(isAlarmRinging) getSwPane().getClockLabel().setIcon(getClockImg());
					else getSwPane().getClockLabel().setIcon(getClockDeadImg());
					if(!(getWatchBodyPane().equals(swPane))) switchPanel(this, swPane);
					break;

				case MODE_CCHECK: //if current Mode is CalorieCheck

					//Set Speed and Weight Display
					if(isEditMode){
						CalorieCheck cCheck = (CalorieCheck) modeManager.getmodes()[4];
						boolean calorieCursor = cCheck.isCursor(); // false = speed, true = weight

						ccWeight = cCheck.getTempWeight();
						ccSpeed = cCheck.getTempSpeed();

						//split value to array
						keepWeightToArray(ccWeight, cwNum);
						keepValueToArray(ccSpeed, csNum);

						//set cursor image && choose visible cursor
						for(int i=0; i<2; i++){
							getCcPane().getCursorLabel()[i].setIcon(cursorSmallImg);
						}

							if(calorieCursor){
								getCcPane().getCursorLabel()[0].setVisible(true);
								getCcPane().getCursorLabel()[1].setVisible(false);
							}else{
								getCcPane().getCursorLabel()[0].setVisible(false);
								getCcPane().getCursorLabel()[1].setVisible(true);
							}


						//set Second seg Img (weight, speed)
						for (int i = 0; i < 10; i++) {
							if (i==0 || i==5 || i==6) getCcPane().getSecondSegs()[i].setIcon(getSeg14DeadImg());
							else if(i>=1 && i<4) changeSecondImg(MODE_CCHECK, i, cwNum[i-1]);
							else if(i>=8 && i<10) changeSecondImg(MODE_CCHECK, i, csNum[i-8]);
							else if(i==4 || i==7) getCcPane().getSecondSegs()[i].setIcon(colonImg);
						}

						//set first seg Img
						for (int i = 0; i < 8; i++) {
							if(!(i==2 || i==5)) getCcPane().getFirstSegs()[i].setIcon(getSeg14DeadBigImg());
							else getCcPane().getFirstSegs()[i].setIcon(getColonBigImg());
						}

						getCcPane().getTitle().setText("SET SPEED & WEIGHT");
					}
					//Normal Calorie Check Display
					else{
						CalorieCheck cCheck = (CalorieCheck) modeManager.getmodes()[4];

						ccWeight = cCheck.getWeight();
						ccSpeed = cCheck.getSpeed();
						ccCalorie = cCheck.getCalorie();

						//split value to array
						keepWeightToArray(ccWeight, cwNum);
						keepValueToArray(ccSpeed, csNum);
						keepCalorieToArray(ccCalorie, ccNum);

						//unvisible cursors
						for(int i=0; i<2; i++) getCcPane().getCursorLabel()[i].setVisible(false);

						//set Second seg Img (weight, speed)
						for (int i = 0; i < 10; i++) {
							if (i==0 || i==5 || i==6) getCcPane().getSecondSegs()[i].setIcon(getSeg14DeadImg());
							else if(i>=1 && i<4) changeSecondImg(MODE_CCHECK, i, cwNum[i-1]);
							else if(i>=8 && i<10) changeSecondImg(MODE_CCHECK, i, csNum[i-8]);
							else if(i==4 || i==7) getCcPane().getSecondSegs()[i].setIcon(colonImg);
						}

						//set first seg Img
						for (int i = 0; i < 8; i++) {
							if (i==2 || i==5) getCcPane().getFirstSegs()[i].setIcon(getColonBigImg());
							else if(i>=0 && i<2) changefirstImg(MODE_CCHECK, i, ccNum[i]);
							else if(i>=3 && i<5) changefirstImg(MODE_CCHECK, i, ccNum[i-1]);
							else if(i>=6 && i<8) changefirstImg(MODE_CCHECK, i, ccNum[i-2]);
						}

						getCcPane().getTitle().setText("CALORIE CHECK");
					}

					//set clock icon
					if(isAlarmRinging) getCcPane().getClockLabel().setIcon(getClockImg());
					else getCcPane().getClockLabel().setIcon(getClockDeadImg());
					if(!(getWatchBodyPane().equals(ccPane))) switchPanel(this, ccPane);
					break;

				case MODE_WTIME: //if current Mode is WorldTime

					Time time = (Time) modeManager.getmodes()[0];
					WorldTime wt = (WorldTime) modeManager.getmodes()[5];
					boolean currentFormat = ((Time)modeManager.getmodes()[0]).getFormat();

					LocalDateTime worldTime = wt.getWorldTime(time.getCurrentTime(), time.getGMT());

					wtYear = worldTime.getYear();
					wtMonth = worldTime.getMonthValue();
					wtDay = worldTime.getDayOfMonth();
					wtHour = worldTime.getHour();
					wtMinute = worldTime.getMinute();
					wtSecond = worldTime.getSecond();

					if(!currentFormat) {
						if (wtHour < 12) {
							getWtPane().getMeridiemLabel().setText("AM");
						} else {
							getWtPane().getMeridiemLabel().setText("PM");
						}
					}else{
						getWtPane().getMeridiemLabel().setText("");
					}
					getWtPane().getWtLabel().setText(wt.getCurrentCityName()); //display city name

					//split time value
					keepYearToArray(wtYear, wtYearNum);
					keepValueToArray(wtMonth, wtMonthNum);
					keepValueToArray(wtDay, wtDayNum);
					keepHourToArray(wtHour, wtHourNum);
					keepValueToArray(wtMinute, wtMinNum);
					keepValueToArray(wtSecond, wtSecNum);

					if(currentFormat) keepValueToArray(wtHour, wtHourNum);
					else keepHourToArray(wtHour, wtHourNum);

					//set 2nd segment Img (Year, Month, Day)
					for (int i=0; i<10; i++) {
						if (i>=0 && i<4) changeSecondImg(MODE_WTIME, i, wtYearNum[i]);
						else if (i>=5 && i<7) changeSecondImg(MODE_WTIME, i, wtMonthNum[i-5]);
						else if (i>=8 && i<10) changeSecondImg(MODE_WTIME, i, wtDayNum[i-8]);
						else if(i==4 || i==7) getWtPane().getSecondSegs()[i].setIcon(getColonImg());
					}

					//set first segment Img (Hour, Minute, sec)
					for (int i=0; i<8; i++) {
						if (i>=0 && i<2) changefirstImg(MODE_WTIME, i, wtHourNum[i]);
						else if (i>=3 && i<5) changefirstImg(MODE_WTIME, i, wtMinNum[i-3]);
						else if (i>=6 && i<8) changefirstImg(MODE_WTIME, i, wtSecNum[i-6]);
						else if(i==2 || i==5) getWtPane().getFirstSegs()[i].setIcon(getColonBigImg());
					}

					//set clock icon
					if(isAlarmRinging) getWtPane().getClockLabel().setIcon(getClockImg());
					else getWtPane().getClockLabel().setIcon(getClockDeadImg());
					if(!(getWatchBodyPane().equals(wtPane))) switchPanel(this, wtPane);
					break;

				case MODE_SETMODE:

					setModeCursor = modeManager.getCurrentCursor();
					isModeActive = modeManager.getEditStatus();
					activeModeCounter = modeManager.getActiveModeCounter();

					//set 2nd segment Img
					for (int i = 0; i < 10; i++) {
						if (!(i == 4 || i == 7)) getSmPane().getSecondSegs()[i].setIcon(getSeg14DeadImg());
						else getSmPane().getSecondSegs()[i].setIcon(colonImg);
					}

					getSmPane().getCursorLabel().setIcon(cursorImg);
					getSmPane().getCursorLabel().setVisible(true);

					switch(setModeCursor){
						case MODE_TIMEKEEPING:
							//set first segment Img
							getSmPane().getFirstSegs()[0].setIcon(getAlphaTImg());
							getSmPane().getFirstSegs()[1].setIcon(getAlphaIImg());
							getSmPane().getFirstSegs()[2].setIcon(getColonBigImg());
							getSmPane().getFirstSegs()[3].setIcon(getAlphaMImg());
							getSmPane().getFirstSegs()[4].setIcon(getAlphaEImg());
							getSmPane().getFirstSegs()[5].setIcon(getColonBigImg());
							getSmPane().getFirstSegs()[7].setIcon(getSeg14DeadBigImg());

							if(isModeActive[setModeCursor]) getSmPane().getFirstSegs()[6].setIcon(getAlphaAImg());
							else getSmPane().getFirstSegs()[6].setIcon(getAlphaDImg());
							break;

						case MODE_ALARM:
							//set first segment Img
							getSmPane().getFirstSegs()[0].setIcon(getAlphaAImg());
							getSmPane().getFirstSegs()[1].setIcon(getAlphaLImg());
							getSmPane().getFirstSegs()[2].setIcon(getColonBigImg());
							getSmPane().getFirstSegs()[3].setIcon(getAlphaRImg());
							getSmPane().getFirstSegs()[4].setIcon(getAlphaMImg());
							getSmPane().getFirstSegs()[5].setIcon(getColonBigImg());
							getSmPane().getFirstSegs()[7].setIcon(getSeg14DeadBigImg());

							if(isModeActive[setModeCursor]) getSmPane().getFirstSegs()[6].setIcon(getAlphaAImg());
							else getSmPane().getFirstSegs()[6].setIcon(getAlphaDImg());
							break;

						case MODE_TIMER:
							//set first segment Img
							getSmPane().getFirstSegs()[0].setIcon(getAlphaTImg());
							getSmPane().getFirstSegs()[1].setIcon(getAlphaMImg());
							getSmPane().getFirstSegs()[2].setIcon(getColonBigImg());
							getSmPane().getFirstSegs()[3].setIcon(getAlphaEImg());
							getSmPane().getFirstSegs()[4].setIcon(getAlphaRImg());
							getSmPane().getFirstSegs()[5].setIcon(getColonBigImg());
							getSmPane().getFirstSegs()[7].setIcon(getSeg14DeadBigImg());

							if(isModeActive[setModeCursor]) getSmPane().getFirstSegs()[6].setIcon(getAlphaAImg());
							else getSmPane().getFirstSegs()[6].setIcon(getAlphaDImg());
							break;

						case MODE_STOPWATCH:
							//set first segment Img
							getSmPane().getFirstSegs()[0].setIcon(getNumBigImgs()[5]); // S = 5
							getSmPane().getFirstSegs()[1].setIcon(getAlphaTImg());
							getSmPane().getFirstSegs()[2].setIcon(getColonBigImg());
							getSmPane().getFirstSegs()[3].setIcon(getAlphaPImg());
							getSmPane().getFirstSegs()[4].setIcon(getAlphaWImg());
							getSmPane().getFirstSegs()[5].setIcon(getColonBigImg());
							getSmPane().getFirstSegs()[7].setIcon(getSeg14DeadBigImg());

							if(isModeActive[setModeCursor]) getSmPane().getFirstSegs()[6].setIcon(getAlphaAImg());
							else getSmPane().getFirstSegs()[6].setIcon(getAlphaDImg());
							break;

						case MODE_CCHECK:
							//set first segment Img
							getSmPane().getFirstSegs()[0].setIcon(getAlphaCImg());
							getSmPane().getFirstSegs()[1].setIcon(getAlphaAImg());
							getSmPane().getFirstSegs()[2].setIcon(getColonBigImg());
							getSmPane().getFirstSegs()[3].setIcon(getAlphaLImg());
							getSmPane().getFirstSegs()[4].setIcon(getNumBigImgs()[0]); // O = 0
							getSmPane().getFirstSegs()[5].setIcon(getColonBigImg());
							getSmPane().getFirstSegs()[7].setIcon(getSeg14DeadBigImg());

							if(isModeActive[setModeCursor]) getSmPane().getFirstSegs()[6].setIcon(getAlphaAImg());
							else getSmPane().getFirstSegs()[6].setIcon(getAlphaDImg());
							break;

						case MODE_WTIME:
							//set first segment Img
							getSmPane().getFirstSegs()[0].setIcon(getAlphaWImg());
							getSmPane().getFirstSegs()[1].setIcon(getAlphaRImg());
							getSmPane().getFirstSegs()[2].setIcon(getColonBigImg());
							getSmPane().getFirstSegs()[3].setIcon(getAlphaLImg());
							getSmPane().getFirstSegs()[4].setIcon(getAlphaDImg());
							getSmPane().getFirstSegs()[5].setIcon(getColonBigImg());
							getSmPane().getFirstSegs()[7].setIcon(getSeg14DeadBigImg());

							if(isModeActive[setModeCursor]) getSmPane().getFirstSegs()[6].setIcon(getAlphaAImg());
							else getSmPane().getFirstSegs()[6].setIcon(getAlphaDImg());
							break;
					}

					if(!(activeModeCounter == 4)) getSmPane().getTitle().setText("EXIT CONDITION: 4 ACTIVES, YOURS : " + activeModeCounter);
					else getSmPane().getTitle().setText("SET MODE");

					//set clock icon
					if(isAlarmRinging) getSmPane().getClockLabel().setIcon(getClockImg());
					else getSmPane().getClockLabel().setIcon(getClockDeadImg());
					if(!(getWatchBodyPane().equals(smPane))) switchPanel(this, smPane);
					break;
			}

			revalidate();
			repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
	}
}
