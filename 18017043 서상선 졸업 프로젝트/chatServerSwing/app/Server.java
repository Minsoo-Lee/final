package app;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends JFrame {


    private static final long serialVersionUID = 1L;

    public static ExecutorService threadPool;
    public static Vector<Client> clients = new Vector<>();

    private ServerSocket serverSocket;

    public void startServer(String IP, int port) {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(IP, port));
        } catch (Exception e) {
            e.printStackTrace();
            if (!serverSocket.isClosed())
                stopServer();
            return;
        }

        Runnable thread = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                        clients.add(new Client(socket));
                        System.out.println("[클라이언트 접속] " + socket.getRemoteSocketAddress() + ": "
                                + Thread.currentThread().getName());
                    } catch (Exception e) {
                        if (!serverSocket.isClosed())
                            stopServer();
                        break;
                    }
                }
            }
        };
        threadPool = Executors.newCachedThreadPool();
        threadPool.submit(thread);
    }

    public void stopServer() {
        try {
            Iterator<Client> iterator = clients.iterator();
            while (iterator.hasNext()) {
                Client client = iterator.next();
                client.socket.close();
                iterator.remove();
            }
            if (serverSocket != null && !serverSocket.isClosed())
                serverSocket.close();
            if (threadPool != null && !threadPool.isShutdown())
                threadPool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Server() {
        super("[ 채팅 서버 ]");
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("나눔고딕", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(textArea);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 343, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, scrollPane, 400, SpringLayout.WEST, getContentPane());
        getContentPane().add(scrollPane);

        JButton toggleButton = new JButton("시작하기");
        springLayout.putConstraint(SpringLayout.NORTH, toggleButton, 343, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, toggleButton, 0, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, toggleButton, 400, SpringLayout.WEST, getContentPane());
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toggleButton.getText().equals("시작하기")) {
                    startServer("127.0.0.1", 9876);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            String message = String.format("[서버 시작]\n");
                            textArea.append(message);
                            toggleButton.setText("종료하기");
                        }
                    });
                } else {
                    stopServer();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            String message = String.format("[서버 종료]\n");
                            textArea.append(message);
                            toggleButton.setText("시작하기");
                        }
                    });
                }
            }
        });
        getContentPane().add(toggleButton);

        JScrollPane scrollPane_1 = new JScrollPane();
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane_1, 0, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, scrollPane_1, 6, SpringLayout.EAST, scrollPane);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_1, 0, SpringLayout.SOUTH, toggleButton);
        springLayout.putConstraint(SpringLayout.EAST, scrollPane_1, -10, SpringLayout.EAST, getContentPane());
        getContentPane().add(scrollPane_1);

        JTextArea textArea_1 = new JTextArea();
        scrollPane_1.setViewportView(textArea_1);
        textArea_1.setEditable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(583, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Server();
            }
        });
    }
}
