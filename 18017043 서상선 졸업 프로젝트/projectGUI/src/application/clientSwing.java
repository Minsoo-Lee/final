package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class clientSwing extends JFrame {

    private Socket socket;
    private JTextArea textArea;

    public void startClient(String IP, int port) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(IP, port);
                    receive();
                } catch (Exception e) {
                    if (!socket.isClosed()) {
                        stopClient();
                        System.out.println("[서버 접속 실패]");
                        System.exit(0);
                    }
                }
            }
        });
        thread.start();
    }

    public void stopClient() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receive() {
        while (true) {
            try {
                InputStream in = socket.getInputStream();
                byte[] buffer = new byte[512];
                int length = in.read(buffer);
                if (length == -1) throw new IOException();
                String message = new String(buffer, 0, length, "UTF-8");
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        textArea.append(message);
                    }
                });
            } catch (Exception e) {
                stopClient();
                break;
            }
        }
    }

    public void send(String message) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OutputStream out = socket.getOutputStream();
                    byte[] buffer = message.getBytes("UTF-8");
                    out.write(buffer);
                    out.flush();
                } catch (Exception e) {
                    stopClient();
                }
            }
        });
        thread.start();
    }

    public clientSwing() {
        super("[ 채팅 클라이언트 ]");

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JTextField userName = new JTextField();
        userName.setPreferredSize(new Dimension(150, 30));
        userName.setToolTipText("닉네임을 입력하시오.");
        JLabel User = new JLabel("이름");
        JTextField IPText = new JTextField("127.0.0.1");
        JTextField portText = new JTextField("9876");
        portText.setPreferredSize(new Dimension(80, 30));
        topPanel.add(User);
        topPanel.add(userName);
        topPanel.add(IPText);
        topPanel.add(portText);
        add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        input.setEnabled(false);
        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send(userName.getText() + ": " + input.getText() + "\n");
                input.setText("");
                input.requestFocus();
            }
        });

        JButton sendButton = new JButton("보내기");
        sendButton.setEnabled(false);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send(userName.getText() + ": " + input.getText() + "\n");
                input.setText("");
                input.requestFocus();
            }
        });

        JButton connectionButton = new JButton("접속하기");
        connectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connectionButton.getText().equals("접속하기")) {
                    int port = 9876;
                    try {
                        port = Integer.parseInt(portText.getText());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    startClient(IPText.getText(), port);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            textArea.append("[ 채팅방 접속 ]\n");
                        }
                    });
                    connectionButton.setText("종료하기");
                    input.setEnabled(true);
                    sendButton.setEnabled(true);
                    input.requestFocus();
                } else {
                    stopClient();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            textArea.append("[ 채팅방 퇴장 ]\n");
                        }
                    });
                    connectionButton.setText("접속하기");
                    input.setEnabled(false);
                    sendButton.setEnabled(false);
                }
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(connectionButton, BorderLayout.WEST);
        bottomPanel.add(input, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(400, 400);


        connectionButton.requestFocus();
    }
    public void check() {
        if (Main.Chat_visible == false) setVisible(false);
        else if (Main.Chat_visible == true) setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new clientSwing();
            }
        });

    }
}

