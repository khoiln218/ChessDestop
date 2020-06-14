package client;

import chinesechess.ChineseChess;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

public class ConnectingGUI extends JFrame {

    private JLabel lblIP;
    private JTextField IP;
    private JLabel lblUserid;
    private JTextField UserID;
    private JLabel lblPort;
    private JTextField Port;
    private JButton send;
    private JLabel Error;
    ChineseChess cc;

    public ConnectingGUI(ChineseChess c) {
        cc = c;
        initGUI();
    }

    private void initGUI() {
        setTitle("Connect To Server");
        setBounds(100, 100, 366, 236);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(null);

        lblIP = new JLabel("IP");
        lblIP.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblIP.setBounds(10, 27, 60, 17);
        getContentPane().add(lblIP);

        IP = new JTextField();
        IP.setColumns(10);
        IP.setBounds(74, 24, 270, 20);
        getContentPane().add(IP);

        lblPort = new JLabel("Port");
        lblPort.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblPort.setBounds(10, 58, 60, 17);
        getContentPane().add(lblPort);

        Port = new JTextField();
        Port.setColumns(10);
        Port.setBounds(74, 55, 270, 20);
        getContentPane().add(Port);

        lblUserid = new JLabel("UserID");
        lblUserid.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblUserid.setBounds(10, 86, 60, 17);
        getContentPane().add(lblUserid);

        UserID = new JTextField();
        UserID.setColumns(10);
        UserID.setBounds(74, 86, 270, 20);
        getContentPane().add(UserID);

        send = new JButton("Connect");
        send.setFont(new Font("Tahoma", Font.PLAIN, 13));
        send.setBounds(126, 117, 116, 32);
        getContentPane().add(send);
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Socket clientSocket;

                //get Use input
                int listenPort = 2700;
                String ip = IP.getText();
                String clientPort = Port.getText();
                String user = UserID.getText();

                try {
                    //Create a new client GUI to connect to server
                    clientSocket = new Socket(ip, listenPort);
                    ClientGUI clientGUI = new ClientGUI(cc);
                    clientGUI.setSocket(clientSocket);
                    clientGUI.setUserID(user);
                    clientGUI.setPort(clientPort);
                    clientGUI.doconnect();
                    clientGUI.setVisible(true);
                    dispose();
                } catch (NumberFormatException | IOException ex) {
                    Error.setText("Status : Error connecting to server");
                }
            }
        });

        Error = new JLabel("");
        Error.setFont(new Font("Tahoma", Font.PLAIN, 13));
        Error.setBounds(10, 160, 266, 14);
        getContentPane().add(Error);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cc.setVisible(true);
            }
        });
    }

    public void setError() {
        Error.setText("Status: Error connecting to Server");
    }
}
