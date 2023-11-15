package Canlendar_Swing_final.src;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;


public class MemoCalendar {
    public JFrame mainFrame = new JFrame("캘린더");
    ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("icon.png")));
    JPanel calOpPanel;
    JButton todayBut;
    JLabel todayLab;
    JButton lYearBut;
    JButton lMonBut;
    JLabel curMMYYYYLab;
    JButton nMonBut;
    JButton nYearBut;
    ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();
    public JPanel calPanel;
    JButton[] weekDaysName;
    JButton[][] dateButs = new JButton[6][7];
    listenForDateButs lForDateButs = new listenForDateButs();
    JPanel infoPanel;
    JLabel infoClock;
    public JPanel memoPanel;
    JLabel selectedDate;
    JTextArea memoArea;
    JScrollPane memoAreaSP;
    JPanel memoSubPanel;
    JButton saveBut;
    JButton delBut;
    JButton clearBut;
    public JPanel frameBottomPanel;
    public JPanel frameSubPanelWest;
    public JPanel frameSubPanelEast;
    JLabel bottomInfo = new JLabel("Memo Calendar");
    final String[] WEEK_DAY_NAME = new String[]{"일", "월", "화", "수", "목", "금", "토"};

    Calendar cal = Calendar.getInstance();
    Calendar today = new GregorianCalendar();

    int calMonth = today.get(Calendar.MONTH);
    int calYear = today.get(Calendar.YEAR);
    int calDayOfMon = today.get(Calendar.DAY_OF_MONTH);

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MemoCalendar();
            }
        });
    }*/

    public MemoCalendar() {

        this.selectedDate = new JLabel("<html><font size=3>" + (this.calMonth + 1) + "/" + this.calDayOfMon + "/" + this.calYear + "&nbsp;(Today)</html>", SwingConstants.CENTER);

        this.mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.mainFrame.setSize(800, 400);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setResizable(false);
        this.mainFrame.setIconImage(this.icon.getImage());
        JTextArea memoArea = new JTextArea();
        this.memoArea = memoArea;
        this.cal = Calendar.getInstance();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this.mainFrame);
        } catch (Exception e) {
            this.bottomInfo.setText("ERROR: LookAndFeel setting failed");
        }

        this.calOpPanel = new JPanel();
        this.todayBut = new JButton("오늘");
        this.todayBut.setToolTipText("오늘");
        this.todayBut.addActionListener(this.lForCalOpButtons);
        this.todayLab = new JLabel(this.today.get(Calendar.MONTH) + 1 + "/" + this.today.get(Calendar.DAY_OF_MONTH) + "/" + this.today.get(Calendar.YEAR));
        this.lYearBut = new JButton("<<");
        this.lYearBut.setToolTipText("이전 년도");
        this.lYearBut.addActionListener(this.lForCalOpButtons);
        this.lMonBut = new JButton("<");
        this.lMonBut.setToolTipText("이전 달");
        this.lMonBut.addActionListener(this.lForCalOpButtons);
        this.curMMYYYYLab = new JLabel("<html><table width=100><tr><th><font size=5>" + (this.calMonth + 1 < 10 ? "&nbsp;" : "") + (this.calMonth + 1) + " / " + this.calYear + "</th></tr></table></html>");
        this.nMonBut = new JButton(">");
        this.nMonBut.setToolTipText("다음 달");
        this.nMonBut.addActionListener(this.lForCalOpButtons);
        this.nYearBut = new JButton(">>");
        this.nYearBut.setToolTipText("다음 년도");
        this.nYearBut.addActionListener(this.lForCalOpButtons);
        this.calOpPanel.setLayout(new GridBagLayout());
        GridBagConstraints calOpGC = new GridBagConstraints();
        calOpGC.gridx = 1;
        calOpGC.gridy = 1;
        calOpGC.gridwidth = 2;
        calOpGC.gridheight = 1;
        calOpGC.weightx = 1.0;
        calOpGC.weighty = 1.0;
        calOpGC.insets = new Insets(5, 5, 0, 0);
        calOpGC.anchor = GridBagConstraints.NORTHWEST;
        calOpGC.fill = GridBagConstraints.NONE;
        this.calOpPanel.add(this.todayBut, calOpGC);
        calOpGC.gridwidth = 3;
        calOpGC.gridx = 2;
        calOpGC.gridy = 1;
        this.calOpPanel.add(this.todayLab, calOpGC);
        calOpGC.anchor = GridBagConstraints.WEST;
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 1;
        calOpGC.gridy = 2;
        this.calOpPanel.add(this.lYearBut, calOpGC);
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 2;
        calOpGC.gridy = 2;
        this.calOpPanel.add(this.lMonBut, calOpGC);
        calOpGC.gridwidth = 2;
        calOpGC.gridx = 3;
        calOpGC.gridy = 2;
        this.calOpPanel.add(this.curMMYYYYLab, calOpGC);
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 5;
        calOpGC.gridy = 2;
        this.calOpPanel.add(this.nMonBut, calOpGC);
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 6;
        calOpGC.gridy = 2;
        this.calOpPanel.add(this.nYearBut, calOpGC);

        this.calPanel = new JPanel();
        this.weekDaysName = new JButton[7];

        for (int i = 0; i < 7; ++i) {
            this.weekDaysName[i] = new JButton(this.WEEK_DAY_NAME[i]);
            this.weekDaysName[i].setBorderPainted(false);
            this.weekDaysName[i].setContentAreaFilled(false);
            this.weekDaysName[i].setForeground(Color.WHITE);
            if (i == 0) {
                this.weekDaysName[i].setBackground(new Color(0, 0, 0));
            } else if (i == 6) {
                this.weekDaysName[i].setBackground(new Color(61, 61, 61));
            } else {
                this.weekDaysName[i].setBackground(new Color(150, 150, 150));
            }

            this.weekDaysName[i].setOpaque(true);
            this.weekDaysName[i].setFocusPainted(false);
            this.calPanel.add(this.weekDaysName[i]);
        }

        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 7; ++j) {
                this.dateButs[i][j] = new JButton();
                this.dateButs[i][j].setBorderPainted(false);
                this.dateButs[i][j].setContentAreaFilled(false);
                this.dateButs[i][j].setBackground(Color.WHITE);
                this.dateButs[i][j].setOpaque(true);
                this.dateButs[i][j].addActionListener(this.lForDateButs);
                this.calPanel.add(this.dateButs[i][j]);
            }
        }

        this.calPanel.setLayout(new GridLayout(0, 7, 2, 2));
        this.calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        this.showCal();


        this.infoPanel = new JPanel();
        this.infoPanel.setLayout(new BorderLayout());
        this.infoClock = new JLabel("", SwingConstants.CENTER);
        this.infoClock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.infoPanel.add(this.infoClock, BorderLayout.NORTH);
        this.selectedDate = new JLabel("<html><font size=3>" + (this.today.get(Calendar.MONTH) + 1) + "/" + this.today.get(Calendar.DAY_OF_MONTH) + "/" + this.today.get(Calendar.YEAR) + "&nbsp;(오늘)</html>", SwingConstants.CENTER);
        this.selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        this.memoPanel = new JPanel();
        this.memoPanel.setBorder(BorderFactory.createTitledBorder("일정"));
        this.memoArea = new JTextArea();
        this.memoArea.setLineWrap(true);
        this.memoArea.setWrapStyleWord(true);
        this.memoAreaSP = new JScrollPane(this.memoArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.readMemo();
        this.memoSubPanel = new JPanel();
        this.saveBut = new JButton("저장");
        this.saveBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    File f = new File("MemoData");
                    if (!f.isDirectory()) {
                        f.mkdir();
                    }

                    String memo = MemoCalendar.this.memoArea.getText();
                    if (memo.length() > 0) {
                        BufferedWriter out = new BufferedWriter(new FileWriter("MemoData/" + MemoCalendar.this.calYear + (MemoCalendar.this.calMonth + 1 < 10 ? "0" : "") + (MemoCalendar.this.calMonth + 1) + (MemoCalendar.this.calDayOfMon < 10 ? "0" : "") + MemoCalendar.this.calDayOfMon + ".txt"));
                        String str = MemoCalendar.this.memoArea.getText();
                        out.write(str);
                        out.close();
                        MemoCalendar.this.bottomInfo.setText(MemoCalendar.this.calYear + (MemoCalendar.this.calMonth + 1 < 10 ? "0" : "") + (MemoCalendar.this.calMonth + 1) + (MemoCalendar.this.calDayOfMon < 10 ? "0" : "") + MemoCalendar.this.calDayOfMon + ".txt" + " saved in MemoData folder.");
                    } else {
                        MemoCalendar.this.bottomInfo.setText("Write something first.");
                    }
                } catch (IOException var6) {
                    MemoCalendar.this.bottomInfo.setText("<html><font color=red>에러: 파일 작성에 실패했습니다.</html>");
                }

                MemoCalendar.this.showCal();
            }
        });
        this.delBut = new JButton("삭제");
        this.delBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MemoCalendar.this.memoArea.setText("");
                File f = new File("MemoData/" + MemoCalendar.this.calYear + (MemoCalendar.this.calMonth + 1 < 10 ? "0" : "") + (MemoCalendar.this.calMonth + 1) + (MemoCalendar.this.calDayOfMon < 10 ? "0" : "") + MemoCalendar.this.calDayOfMon + ".txt");
                if (f.exists()) {
                    f.delete();
                    MemoCalendar.this.showCal();
                    MemoCalendar.this.bottomInfo.setText("삭제되었습니다.");
                } else {
                    MemoCalendar.this.bottomInfo.setText("삭제되었습니다.");
                }
            }
        });
        this.clearBut = new JButton("다시쓰기");
        this.clearBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                MemoCalendar.this.memoArea.setText(null);
                MemoCalendar.this.bottomInfo.setText("글을 지웠습니다.");
            }
        });
        this.memoSubPanel.add(this.saveBut);
        this.memoSubPanel.add(this.delBut);
        this.memoSubPanel.add(this.clearBut);
        this.memoPanel.setLayout(new BorderLayout());
        this.memoPanel.add(this.selectedDate, BorderLayout.NORTH);
        this.memoPanel.add(this.memoAreaSP, BorderLayout.CENTER);
        this.memoPanel.add(this.memoSubPanel, BorderLayout.SOUTH);
        frameSubPanelWest = new JPanel();
        Dimension calOpPanelSize = this.calOpPanel.getPreferredSize();
        calOpPanelSize.height = 90;
        this.calOpPanel.setPreferredSize(calOpPanelSize);
        frameSubPanelWest.setLayout(new BorderLayout());
        frameSubPanelWest.add(this.calOpPanel, BorderLayout.NORTH);
        frameSubPanelWest.add(this.calPanel, BorderLayout.CENTER);
        frameSubPanelEast = new JPanel();
        Dimension infoPanelSize = this.infoPanel.getPreferredSize();
        infoPanelSize.height = 65;
        this.infoPanel.setPreferredSize(infoPanelSize);
        frameSubPanelEast.setLayout(new BorderLayout());
        frameSubPanelEast.add(this.infoPanel, BorderLayout.NORTH);
        frameSubPanelEast.add(this.memoPanel, BorderLayout.CENTER);
        Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
        frameSubPanelWestSize.width = 410;
        frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);
        this.frameBottomPanel = new JPanel();
        this.frameBottomPanel.add(this.bottomInfo);
        this.mainFrame.setLayout(new BorderLayout());
        this.mainFrame.add(frameSubPanelWest, BorderLayout.WEST);
        this.mainFrame.add(frameSubPanelEast, BorderLayout.CENTER);
        this.mainFrame.add(this.frameBottomPanel, BorderLayout.SOUTH);
        this.mainFrame.setVisible(true);

        this.focusToday();
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateClock();
            }
        });
        timer.start();

    }
    public JPanel getWestP(){
        return frameSubPanelWest;
    }
    public JPanel getEastP(){
        return frameSubPanelEast;
    }
    public JPanel getBtmP(){
        return frameBottomPanel;
    }

    private void focusToday() {
        if (this.today.get(Calendar.DAY_OF_WEEK) == 1) {
            this.dateButs[this.today.get(Calendar.WEEK_OF_MONTH) - 1][this.today.get(Calendar.DAY_OF_WEEK) - 1].requestFocusInWindow();
        } else {
            this.dateButs[this.today.get(Calendar.WEEK_OF_MONTH) - 1][this.today.get(Calendar.DAY_OF_WEEK) - 2].requestFocusInWindow();
        }
    }

    private void showCal() {
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 7; ++j) {
                this.dateButs[i][j].setText("");
            }
        }

        this.cal.set(this.calYear, this.calMonth, 1);
        int numOfDaysInMonth = this.cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int startOfMonth = this.cal.get(Calendar.DAY_OF_WEEK) - 1;

        for (int i = 0, day = 1; i < 6; ++i) {
            for (int j = startOfMonth; j < 7; ++j) {
                if (day <= numOfDaysInMonth) {
                    this.dateButs[i][j].setText("" + day);
                    ++day;
                }
            }

            startOfMonth = 0;
        }

        this.curMMYYYYLab.setText("<html><table width=100><tr><th><font size=5>" + (this.calMonth + 1 < 10 ? "&nbsp;" : "") + (this.calMonth + 1) + " / " + this.calYear + "</th></tr></table></html>");
        this.selectedDate.setText("<html><font size=3>" + (this.calMonth + 1) + "/" + this.calDayOfMon + "/" + this.calYear + "</html>");
        this.selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        //this.bottomInfo.setText("캘린더");
    }

    private void updateClock() {
        this.cal = Calendar.getInstance();
        this.infoClock.setText(new StringBuilder().append(this.cal.get(Calendar.YEAR)).append("-").append(this.cal.get(Calendar.MONTH) + 1 < 10 ? "0" : "").append(this.cal.get(Calendar.MONTH) + 1).append("-").append(this.cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "").append(this.cal.get(Calendar.DAY_OF_MONTH)).append(" ").append(this.cal.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "").append(this.cal.get(Calendar.HOUR_OF_DAY)).append(":").append(this.cal.get(Calendar.MINUTE) < 10 ? "0" : "").append(this.cal.get(Calendar.MINUTE)).append(":").append(this.cal.get(Calendar.SECOND) < 10 ? "0" : "").append(this.cal.get(Calendar.SECOND)).toString());
    }

    private void readMemo() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("MemoData/" + this.calYear + (this.calMonth + 1 < 10 ? "0" : "") + (this.calMonth + 1) + (this.calDayOfMon < 10 ? "0" : "") + this.calDayOfMon + ".txt"));
            String str = "";
            String line;
            while ((line = in.readLine()) != null) {
                str += line + "\n";
            }
            in.close();
            this.memoArea.setText(str);
        } catch (IOException e) {
            this.memoArea.setText("");
        }
    }

    private class ListenForCalOpButtons implements ActionListener {
        private ListenForCalOpButtons() {
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == MemoCalendar.this.todayBut) {
                MemoCalendar.this.calMonth = MemoCalendar.this.today.get(Calendar.MONTH);
                MemoCalendar.this.calYear = MemoCalendar.this.today.get(Calendar.YEAR);
                MemoCalendar.this.calDayOfMon = MemoCalendar.this.today.get(Calendar.DAY_OF_MONTH);
                MemoCalendar.this.showCal();
                MemoCalendar.this.readMemo();
            } else if (e.getSource() == MemoCalendar.this.lMonBut) {
                if (MemoCalendar.this.calMonth == 0) {
                    MemoCalendar.this.calMonth = 11;
                    --MemoCalendar.this.calYear;
                } else {
                    --MemoCalendar.this.calMonth;
                }
            } else if (e.getSource() == MemoCalendar.this.nMonBut) {
                if (MemoCalendar.this.calMonth == 11) {
                    MemoCalendar.this.calMonth = 0;
                    ++MemoCalendar.this.calYear;
                } else {
                    ++MemoCalendar.this.calMonth;
                }
            } else if (e.getSource() == MemoCalendar.this.lYearBut) {
                --MemoCalendar.this.calYear;
            } else if (e.getSource() == MemoCalendar.this.nYearBut) {
                ++MemoCalendar.this.calYear;
            }

            MemoCalendar.this.showCal();
        }
    }




    private class listenForDateButs implements ActionListener {
        private listenForDateButs() {
        }

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 6; ++i) {
                for (int j = 0; j < 7; ++j) {
                    if (e.getSource() == MemoCalendar.this.dateButs[i][j]) {
                        if (!MemoCalendar.this.dateButs[i][j].getText().equals("")) {
                            MemoCalendar.this.calDayOfMon = Integer.parseInt(MemoCalendar.this.dateButs[i][j].getText());
                            MemoCalendar.this.showCal();
                            MemoCalendar.this.readMemo();
                        }
                    }
                }
            }
        }
    }
}
