package application;
import Notepad.src.*;
import Account.*;

import Calculator.Calculator;
import Canlendar_Swing_final.src.MemoCalendar;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;


public class Main {
    private AccountBook accountBook;
    private MemoCalendar memoCalendar;
    public static boolean Chat_visible = false;
    public static boolean Calc_visible = false;
    public static boolean Note_visible = false;

    ImageIcon icon;
    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    application.Main window = new application.Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        new clientSwing();
        Calculator calc = new Calculator();
        calc.frame.setVisible(false);
        new NotepadClone();
        NotepadClone app = new NotepadClone();
        app.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent evt) {
                app.exit();
            }
        });
        app.setVisible(false);


    }




    /**
     * Create the application.
     */
    public Main() {
        icon = new ImageIcon("./images/travel.png");

        //배경 Panel 생성후 컨텐츠페인으로 지정
        JPanel background = new JPanel() {
            public void paintComponent(Graphics g) {
                // Approach 1: Dispaly image at at full size
                g.drawImage(icon.getImage(), 0, 0, null);
                // Approach 2: Scale image to size of component
                // Dimension d = getSize();
                // g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                // Approach 3: Fix the image position in the scroll pane
                // Point p = scrollPane.getViewport().getViewPosition();
                // g.drawImage(icon.getImage(), p.x, p.y, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };

        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBackground(new Color(255, 255, 255));
        frame.setBounds(100, 100, 1465, 900 );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JFXPanel panel = new JFXPanel();
        panel.setBounds(25, 25, 630, 830);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                web.initAndLoadWebView(panel);
            }
        }); // 웹페이지 출력 함수




        JLabel lblNewLabel = new JLabel("지도");
        lblNewLabel.setBounds(36, 6, 61, 16);
        frame.getContentPane().add(lblNewLabel);

        //AccountBook ab = new AccountBook();
       /* JPanel scrollPane = new JPanel();
        scrollPane.setBounds(656, 25, 522, 338);
        scrollPane.setLayout(new BorderLayout());
        //scrollPane.add(ab.newTransactionPanel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane);
        accountBook = new AccountBook();
        scrollPane.add(accountBook.getContentPane(), BorderLayout.CENTER);
        frame.setVisible(true);*/

        JLabel AccountLabel = new JLabel("가계부");
        //AccountLabel.setBounds(656, 376, 61, 16);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBounds(1000, 360, 455, 500);
        contentPane.setLayout(null);

        Border border = BorderFactory.createLineBorder(Color.RED);
        //rame.setContentPane(contentPane);
        frame.add(contentPane);

        frame.getContentPane().add(AccountLabel);
        accountBook = new AccountBook();
        contentPane.add(accountBook.getContentPane(), BorderLayout.CENTER);


        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.setBounds(656, 5, 800, 350);
        panel1.setLayout(null);
        frame.add(panel1);
        MemoCalendar memoCalendar = new MemoCalendar();
        panel1.add(memoCalendar.getWestP(), BorderLayout.WEST);
        panel1.add(memoCalendar.getEastP(), BorderLayout.CENTER);
        panel1.add(memoCalendar.getBtmP(), BorderLayout.SOUTH);
        memoCalendar.mainFrame.dispose();//캘린더 frame 창을 안보이게 함



        JPanel image = new JPanel() {
            Image background = new ImageIcon(Main.class.getResource("./images/travel.png")).getImage();
            public void paint(Graphics g) {
                g.drawImage(background,0,0,null);
            }
        };
        image.setLayout(null);
        image.setBounds(650, 350, 350,180);
        frame.add(image);

        //153 12 282 284 282+12
        // 아래 부터 각 버튼을 눌렀을때 각 기능의 GUI visible을 True로 만들어 화면에 표시함
        Calculator cc = new Calculator();
        JButton btnNewButton_2 = new JButton("계산기");
        btnNewButton_2.setBounds(690, 640, 250, 80);
        frame.getContentPane().add(btnNewButton_2);
        btnNewButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Calc_visible == false){
                    Calc_visible = true;
                    cc.check();
                }
                else if(Calc_visible == true){
                    Calc_visible = false;
                    cc.check();
                }
            }
        });
        NotepadClone Nc = new NotepadClone();
        Nc.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent evt) {
                Nc.exit();
            }
        });

        JButton btnNewButton_3 = new JButton("메모장");
        btnNewButton_3.setBounds(690, 740, 250, 80);
        frame.getContentPane().add(btnNewButton_3);
        btnNewButton_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Nc.setVisible(true);
            }
        });

    }
}
