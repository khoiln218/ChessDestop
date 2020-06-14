package chinesechess;

import client.ConnectingGUI;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.util.ArrayList;

public class ChineseChess extends JFrame {

    MyGraphics graph;
    Board board;
    Piece piece;
    boolean GameOver;
    boolean RED;
    boolean XSide;
    boolean select;
    boolean start = false;
    boolean undo = false;
    int undoRed = 0;
    int undoBlack = 0;
    boolean updateBanCo = true;
    boolean updateMessage = false;
    boolean updateChat = true;
    public static String Message1 = "";
    String Message2 = "";
    final String INITIAL_TEXT = "Send to message";
    TextField txt;
    ArrayList<String> chat;
    ArrayList<Boolean> isMe;
    int name = 0;
    ///////////////////////
    private Socket chatDirectSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String User;

    public ChineseChess() {
        setTitle("Cờ úp");
        setSize(MyGraphics.WIDTH + MyGraphics.WIDTH_CHAT, MyGraphics.HEIGHT);
        setResizable(false);
        setFocusable(true);
        setLocationRelativeTo(null);
        setLayout(null);

        graph = new MyGraphics();

        txt = new TextField();
        txt.setSize(270, 20);
        txt.setLocation(600, 10);
        txt.setEditable(start);
        txt.setEnabled(start);
        chat = new ArrayList<>();
        isMe = new ArrayList<>();
        add(txt);

        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (start) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        Message1 = txt.getText();
                        chat.add(Message1);
                        isMe.add(false);
                        sendMessage("Chat:" + Message1);
                        if (XSide) {
                            Message1 = "YOU SAID: \"" + Message1 + "\"";
                        } else {
                            Message1 = "USER SAID: \"" + Message1 + "\"";
                        }
                        txt.setText("");
                        updateChat = true;
                        UpdateMessage();
                        repaint();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        txt.setText("");
                    }
                }
            }
        });
        txt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txt.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                txt.setText(INITIAL_TEXT);
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClickEvent(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //txt.setText("Pressed");
                int _x = e.getX();
                int _y = e.getY();
                int width = MyGraphics.WIDTH_CHAT;
                int left = MyGraphics.WIDTH;
                int right = left + width;
                int bottom = MyGraphics.HEIGHT;
                int cell_size = MyGraphics.CELL_SIZE;
                if (_x > left && _x < right && _y > 0 && _y < bottom) {
                    if (_x >= (left + cell_size / 4) && _x <= (left + cell_size / 4 + 120) && _y >= 400 && _y <= 440) {
                        name = 1;
                    } else if (_x >= (left + cell_size / 4 + width / 2) && _x <= (left + cell_size / 4 + width / 2 + 120) && _y >= 400 && _y <= 440) {
                        name = 2;
                    } else if (_x >= (left + cell_size / 4) && _x <= (left + cell_size / 4 + 120) && _y >= 460 && _y <= 500) {
                        name = 3;
                    } else if (_x >= (left + cell_size / 4 + width / 2) && _x <= (left + cell_size / 4 + width / 2 + 120) && _y >= 460 && _y <= 500) {
                        name = 4;
                    } else if (_x >= (left + cell_size / 4) && _x <= (left + cell_size / 4 + 120) && _y >= 520 && _y <= 560) {
                        name = 5;
                    } else if (_x >= (left + cell_size / 4 + width / 2) && _x <= (left + cell_size / 4 + width / 2 + 120) && _y >= 520 && _y <= 560) {
                        name = 6;
                    }
                    if (name != 0) {
                        repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //txt.setText("Released");
                if (name != 0) {
                    name = 0;
                    updateChat = true;
                    repaint();
                }
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                //txt.setText("Moved");
                int _x = e.getX();
                int _y = e.getY();
                int left = MyGraphics.WIDTH;
                int bottom = MyGraphics.HEIGHT;
                if (_x > 0 && _x < left && _y > 0 && _y < bottom) {
                    setCursor(Cursor.HAND_CURSOR);
                } else {
                    setCursor(Cursor.DEFAULT_CURSOR);
                }
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                updateBanCo = true;
                updateChat = true;
                UpdateMessage();
                repaint();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                exit();
            }
        });
    }

    void NewGame(boolean first) {
        GameOver = false;
        RED = !first;
        XSide = first;
        undo = false;
        if (!start) {
            txt.setText(INITIAL_TEXT);
        }
        start = true;
        select = false;
        piece = null;
        txt.setEditable(start);
        txt.setEnabled(start);
        updateBanCo = true;
    }

    @Override
    public void paint(Graphics g) {
        if (name != 0) {
            graph.button_Click(g, name, this);
        } else {
            if (updateBanCo) {
                graph.DrawBanCo(g, this);
                updateBanCo = false;
            }
            if (start) {
                graph.DrawQuanCo(g, board, this);
                graph.DrawSelect(g, board, select, this);
                if (XSide) {
                    graph.DrawAllPossibleMove(g, piece, select, this);
                }
            }

            if (updateChat) {
                graph.DrawFormChat(g, this);
                graph.clearMessage(g, this);
                graph.DrawChat(g, chat, isMe, this);
                updateChat = false;
            }
            if (updateMessage) {
                graph.clearMessage(g, this);
                if (!select) {
                    graph.DrawMessage(g, Message2, true, this);
                }
                graph.DrawMessage(g, Message1, false, this);
                updateMessage = false;
            }
        }
    }

    private void onClickEvent(MouseEvent event) {
        int _x = event.getX();
        int _y = event.getY();
        int width = MyGraphics.WIDTH_CHAT;
        int left = MyGraphics.WIDTH;
        int right = left + width;
        int bottom = MyGraphics.HEIGHT;
        int cell_size = MyGraphics.CELL_SIZE;
        if (_x > left && _x < right && _y > 0 && _y < bottom) {
            if (_x >= (left + cell_size / 4) && _x <= (left + cell_size / 4 + 120) && _y >= 400 && _y <= 440) {
                board = new Board();
                board.randomBoard();
                sendMessage("Start:" + board.getMask());
                NewGame(true);
                Message1 = "NEW GAME";
                Message2 = "YOU MOVE";
            } else if (_x >= (left + cell_size / 4 + width / 2) && _x <= (left + cell_size / 4 + width / 2 + 120) && _y >= 400 && _y <= 440) {
                ConnectingGUI client = new ConnectingGUI(this);
                client.setVisible(true);
                setVisible(false);
            } else if (_x >= (left + cell_size / 4) && _x <= (left + cell_size / 4 + 120) && _y >= 460 && _y <= 500) {
                if (undo && !GameOver && ((RED && undoRed < 3) || (!RED & undoBlack < 3))) {
                    undo();
                    Message1 = "UNDO";
                    Message2 = XSide ? "YOU MOVE" : "WAITTING";
                } else {
                    Message1 = "CAN'T UNDO";
                }
            } else if (_x >= (left + cell_size / 4 + width / 2) && _x <= (left + cell_size / 4 + width / 2 + 120) && _y >= 460 && _y <= 500) {
                if (loadFromFile("DATA.SAV")) {
                    loadGame();
                    Message1 = "LOAD SUCCESSFUL";
                    Message2 = XSide ? "YOU MOVE" : "WAITTING";
                } else {
                    Message1 = "CAN'T LOAD DATA";
                }
            } else if (_x >= (left + cell_size / 4) && _x <= (left + cell_size / 4 + 120) && _y >= 520 && _y <= 560) {
                if (start && saveToFile("DATA.SAV")) {
                    Message1 = "SAVE SUCCESSFUL";
                } else {
                    Message1 = "CAN'T SAVE TO FILE";
                }
            } else if (_x >= (left + cell_size / 4 + width / 2) && _x <= (left + cell_size / 4 + width / 2 + 120) && _y >= 520 && _y <= 560) {
                Object[] options = {"OK", "CANCEL"};
                Object obj = JOptionPane.showOptionDialog(null, "          Click OK to QUIT", "Warning",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                if (obj == 0) {
                    dispose();
                    exit();
                } else {
                    Message1 = "QUIT CANCEL";
                }
            }
            UpdateMessage();
            repaint();
        } else if (_x > 0 && _x < left && _y > 0 && _y < bottom) {
            if (XSide) {
                if (start && !GameOver) {
                    int x = (_y - MyGraphics.UP + cell_size / 2) / cell_size;
                    int y = (_x - MyGraphics.LEFT + cell_size / 2) / cell_size;
                    if (x >= 0 && x <= MyGraphics.ROW && y >= 0 && y <= MyGraphics.COL) {
                        if (select && piece != null && piece.checkMove(x, y)) {
                            moveTo(x, y);
                        } else {
                            byte value = board.cell[x][y];
                            if ((RED && value > 14) || (!RED && value >= 8 && value <= 14)) {
                                select(x, y);
                            }
                        }
                    }
                }
            }
        }
    }

    private void select(int x, int y) {
        byte value = board.cell[x][y];
        board.PrevMove = board.CurrMove;
        board.CurrMove = new Point(x, y);
        switch (value) {
            case 8:
            case 15:
                piece = new CKing(board, new Point(x, y));
                break;
            case 9:
            case 16:
                piece = new CBishop(board, new Point(x, y));
                break;
            case 10:
            case 17:
                piece = new CElephant(board, new Point(x, y));
                break;
            case 11:
            case 18:
                piece = new CKnight(board, new Point(x, y));
                break;
            case 12:
            case 19:
                piece = new CRook(board, new Point(x, y));
                break;
            case 13:
            case 20:
                piece = new CCannon(board, new Point(x, y));
                break;
            case 14:
            case 21:
                piece = new CPawn(board, new Point(x, y));
                break;
        }
        Message1 = "SELECT " + piece.name + " (" + x + "," + y + ") ";
        if (XSide) {
            sendMessage("Select:" + x + "," + y);
        }
        select = true;
        updateBanCo = true;
        UpdateMessage();
        repaint();
    }

    private void moveTo(int x, int y) {
        Point prev = new Point(board.PrevMove);
        board.PrevMove = board.CurrMove;
        byte val1 = board.cell[board.PrevMove.x][board.PrevMove.y];
        byte val2 = board.cell[x][y];
        byte val3 = board.mask[board.PrevMove.x][board.PrevMove.y];
        byte val4 = board.mask[x][y];
        if (val3 != 0) {
            board.cell[x][y] = val3;
            board.mask[board.PrevMove.x][board.PrevMove.y] = 0;
        } else {
            board.cell[x][y] = val1;
        }
        if (board.mask[x][y] != 0) {
            board.mask[x][y] = 0;
        }
        board.cell[board.PrevMove.x][board.PrevMove.y] = 0;
        board.CurrMove = new Point(x, y);
        board.listUndo.add(new State(prev, board.PrevMove, board.CurrMove, val1, val2, val3, val4));
        Message1 = piece.name + " (" + board.PrevMove.x + "," + board.PrevMove.y + ") ";
        Message1 += "TO (" + x + "," + y + ")";
        Message2 = XSide ? "WAITTING" : "YOU MOVE";
        if (XSide) {
            sendMessage("Move:" + x + "," + y);
        }
        piece = null;
        select = false;
        updateBanCo = true;
        UpdateMessage();
        repaint();
        SwithchPlayer();
    }

    private void SwithchPlayer() {
        if (XSide) {
            GameOver = board.IsGameOver(RED);
        } else {
            GameOver = board.IsGameOver(!RED);
        }
        if (GameOver) {
            Message1 = XSide ? "YOU WIN" : "YOU LOSE";
            UpdateMessage();
            repaint();
            JOptionPane.showMessageDialog(null, "              " + Message1, "Result " + getTitle().split("-")[0], JOptionPane.INFORMATION_MESSAGE);
        } else {
            undo = true;
            XSide = !XSide;
        }
    }

    private void UpdateMessage() {
        if (Message1 != null && !Message1.equals("")) {
            System.out.println(Message1);
        }
        updateMessage = true;
    }

    private void undo() {
        if (!board.listUndo.isEmpty()) {
            int n = board.listUndo.size() - 1;
            State remove = board.listUndo.get(n);
            board.PrevMove = remove.prev;
            board.CurrMove = remove.curr;
            board.cell[board.CurrMove.x][board.CurrMove.y] = remove.value1;
            board.cell[remove.pos.x][remove.pos.y] = remove.value2;
            board.mask[board.CurrMove.x][board.CurrMove.y] = remove.value3;
            board.mask[remove.pos.x][remove.pos.y] = remove.value4;
            board.listUndo.remove(n);
            if (RED) {
                undoRed++;
            } else {
                undoBlack++;
            }
            select(board.CurrMove.x, board.CurrMove.y);
            undo = false;
        }
    }

    public boolean saveToFile(String filename) {
        DataOutputStream dos;
        try {
            dos = new DataOutputStream(new FileOutputStream(filename));
            dos.writeBoolean(GameOver);
            dos.writeBoolean(RED);
            dos.writeInt(undoBlack);
            dos.writeInt(undoRed);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    dos.writeByte(board.cell[i][j]);
                    dos.writeByte(board.mask[i][j]);
                }
            }
            dos.close();
        } catch (IOException ex) {
            Message1 = "SAVE FAIL: " + ex.toString();
            return false;
        }
        return true;
    }

    public boolean loadFromFile(String filename) {
        DataInputStream dis;
        try {
            dis = new DataInputStream(new FileInputStream(filename));
            GameOver = dis.readBoolean();
            RED = dis.readBoolean();
            undoBlack = dis.readInt();
            undoRed = dis.readInt();
            board = new Board();
            board.mask = new byte[MyGraphics.ROW + 1][MyGraphics.COL + 1];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    board.cell[i][j] = dis.readByte();
                    board.mask[i][j] = dis.readByte();
                }
            }
            dis.close();
        } catch (IOException ex) {
            Message1 = "LOAD FAIL: " + ex.toString();
            return false;
        }
        return true;
    }

    void loadGame() {
        select = false;
        start = true;
        piece = null;
        undo = false;
        updateBanCo = true;
    }

    //////////////////////////////////////////
    public void setID(String user) {
        this.User = user;
    }

    public void setSocket(Socket s) {
        this.chatDirectSocket = s;
    }

    public void setDetailConnection(String addr, int port) {
        try {
            chatDirectSocket = new Socket(addr, port);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void sendMessage(String s) {
        try {
            out.println(s);
            out.flush();
        } catch (Exception e) {
            Message1 = "CAN'T SEND";
            UpdateMessage();
            repaint();
        }
    }

    public void run() {
        try {
            out = new PrintWriter(chatDirectSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(chatDirectSocket.getInputStream()));
            this.sendMessage(User);
            setTitle(User + " - " + "Cờ úp");
            Thread read = new Thread(new RemoteReader());
            read.start();
            setVisible(true);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private class RemoteReader implements Runnable {

        @Override
        public void run() {
            String receive;
            try {
                while ((receive = in.readLine()) != null) {
                    if (receive.startsWith("Disconnect")) {
                        exit();
                    } else if (receive.startsWith("Chat")) {
                        setVisible(true);
                        chat.add(receive.substring(5));
                        isMe.add(true);
                        updateChat = true;
                        UpdateMessage();
                        repaint();
                    } else if (receive.startsWith("Select")) {
                        Message1 = receive;
                        UpdateMessage();
                        repaint();
                        String[] p = receive.substring(7).split(",");
                        select(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
                    } else if (receive.startsWith("Move")) {
                        Message1 = receive;
                        UpdateMessage();
                        repaint();
                        String[] p = receive.substring(5).split(",");
                        moveTo(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
                    } else if (receive.startsWith("Start")) {
                        String[] s = receive.substring(6).split(",");
                        board = new Board();
                        board.setMask(s);
                        NewGame(false);
                        Message1 = "NEW GAME";
                        Message2 = "WAITTING";
                        updateBanCo = true;
                        UpdateMessage();
                        repaint();
                    }
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private void exit() {
        try {
            chatDirectSocket.close();
        } catch (Exception e) {
            System.err.println("Error while shutting down the GUI.");
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ChineseChess frame = new ChineseChess();
                    frame.setVisible(true);
                } catch (Exception e) {
                }
            }
        });
    }
}
