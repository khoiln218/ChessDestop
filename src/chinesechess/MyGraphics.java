package chinesechess;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import javax.swing.*;

public class MyGraphics {

    public final static int WIDTH = 588;
    public final static int HEIGHT = 686;
    public final static int WIDTH_CHAT = 300;
    public final static int PADDING = 28;
    public final static int CELL_SIZE = 540 / 9;
    public final static int BORDER = ((CELL_SIZE + 540 % 9) / 2) + PADDING;
    public final static int ROW = 9;
    public final static int COL = 8;
    public final static int LOGO = CELL_SIZE;
    public final static int LEFT = BORDER;
    public final static int UP = BORDER + PADDING;
    public final static int RIGHT = LEFT + COL * CELL_SIZE;
    public final static int DOWN = UP + ROW * CELL_SIZE;
    protected Image imageba;
    protected Image imagebb;
    protected Image imagebc;
    protected Image imagebk;
    protected Image imagebn;
    protected Image imagebp;
    protected Image imagebr;
    protected Image imagera;
    protected Image imagerb;
    protected Image imagerc;
    protected Image imagerk;
    protected Image imagern;
    protected Image imagerp;
    protected Image imagerr;
    protected Image imagesel;
    protected Image imageclr;
    protected Image imageban;
    protected Image imagetkr;
    protected Image imagetkb;
    protected Image imagewin;
    protected Image imagebottom;
    protected Image buttonImage;
    protected Image massegeImage;

    public MyGraphics() {
        imageba = new ImageIcon(getClass().getResource("/images/ba.png")).getImage();
        imagebb = new ImageIcon(getClass().getResource("/images/bb.png")).getImage();
        imagebc = new ImageIcon(getClass().getResource("/images/bc.png")).getImage();
        imagebk = new ImageIcon(getClass().getResource("/images/bk.png")).getImage();
        imagebn = new ImageIcon(getClass().getResource("/images/bn.png")).getImage();
        imagebp = new ImageIcon(getClass().getResource("/images/bp.png")).getImage();
        imagebr = new ImageIcon(getClass().getResource("/images/br.png")).getImage();
        imagera = new ImageIcon(getClass().getResource("/images/ra.png")).getImage();
        imagerb = new ImageIcon(getClass().getResource("/images/rb.png")).getImage();
        imagerc = new ImageIcon(getClass().getResource("/images/rc.png")).getImage();
        imagerk = new ImageIcon(getClass().getResource("/images/rk.png")).getImage();
        imagern = new ImageIcon(getClass().getResource("/images/rn.png")).getImage();
        imagerp = new ImageIcon(getClass().getResource("/images/rp.png")).getImage();
        imagerr = new ImageIcon(getClass().getResource("/images/rr.png")).getImage();
        imagesel = new ImageIcon(getClass().getResource("/images/Cselect.png")).getImage();
        imageclr = new ImageIcon(getClass().getResource("/images/Ccls.png")).getImage();
        imageban = new ImageIcon(getClass().getResource("/images/Cboard.jpg")).getImage();
        imagetkr = new ImageIcon(getClass().getResource("/images/Ctokenr.png")).getImage();
        imagetkb = new ImageIcon(getClass().getResource("/images/Ctokenb.png")).getImage();
        imagewin = new ImageIcon(getClass().getResource("/images/Cwin.jpg")).getImage();
        imagebottom = new ImageIcon(getClass().getResource("/images/Cbottom.jpg")).getImage();
        buttonImage = new ImageIcon(getClass().getResource("/images/Cbutton_on.png")).getImage();
        massegeImage = new ImageIcon(getClass().getResource("/images/CMessage.png")).getImage();
    }

    public void DrawBanCo(Graphics g, ImageObserver server) {
        DrawMain(g, server);
        DrawBorder(g);
        DrawX(g);
        DrawPlus(g, 1, 2, 0);
        DrawPlus(g, 1, 7, 0);
        DrawPlus(g, 7, 7, 0);
        DrawPlus(g, 7, 2, 0);
        for (int i = 1; i < 4; i++) {
            DrawPlus(g, i * 2, 3, 0);
            DrawPlus(g, i * 2, 6, 0);
        }
        DrawPlus(g, 0, 3, 1);
        DrawPlus(g, 0, 6, 1);
        DrawPlus(g, 8, 3, 2);
        DrawPlus(g, 8, 6, 2);
    }

    private void DrawMain(Graphics g, ImageObserver server) {
        g.drawImage(imageban, 0, 0, WIDTH, HEIGHT, server);
        for (int i = 0; i < 10; i++) {
            g.drawLine(LEFT, (i) * CELL_SIZE + UP, RIGHT, (i) * CELL_SIZE + UP);
            if (i <= 8) {
                g.drawString("" + i, (i) * CELL_SIZE + BORDER, UP - 35);
            }
            g.drawString("" + i, LEFT - 35, (i) * CELL_SIZE + UP);
        }
        for (int i = 1; i < 8; i++) {
            g.drawLine((i) * CELL_SIZE + BORDER, UP, (i) * CELL_SIZE + BORDER, UP + 4 * CELL_SIZE);
            g.drawLine((i) * CELL_SIZE + BORDER, UP + 5 * CELL_SIZE, (i) * CELL_SIZE + BORDER, DOWN);
        }
        Font deFont = g.getFont();
        Color decColor = g.getColor();
        Font ft = new Font("Monospaced", Font.BOLD, 15);
        g.setFont(ft);
        g.setColor(Color.WHITE);
        g.drawString("CHINESE CHESS", 3 * CELL_SIZE + BORDER + 3, UP + 4 * CELL_SIZE + 35);
        g.setFont(ft);
        g.setColor(decColor);
        g.setFont(deFont);
    }

    private void DrawX(Graphics g) {
        g.drawLine(LEFT + 3 * CELL_SIZE, UP, LEFT + 5 * CELL_SIZE, UP + 2 * CELL_SIZE);
        g.drawLine(LEFT + 5 * CELL_SIZE, UP, LEFT + 3 * CELL_SIZE, UP + 2 * CELL_SIZE);
        g.drawLine(LEFT + 3 * CELL_SIZE, DOWN, LEFT + 5 * CELL_SIZE, DOWN - 2 * CELL_SIZE);
        g.drawLine(LEFT + 5 * CELL_SIZE, DOWN, LEFT + 3 * CELL_SIZE, DOWN - 2 * CELL_SIZE);
    }

    private void DrawBorder(Graphics g) {
        int border = 4;
        g.drawLine(LEFT, UP, LEFT, DOWN);
        g.drawLine(RIGHT, UP, RIGHT, DOWN);
        g.drawLine(LEFT - border, UP - border, RIGHT + border, UP - border);
        g.drawLine(LEFT - border, DOWN + border, RIGHT + border, DOWN + border);

        g.drawLine(LEFT - border, UP - border, LEFT - border, DOWN + border);
        g.drawLine(RIGHT + border, UP - border, RIGHT + border, DOWN + border);
    }

    private void DrawPlus(Graphics g, int x, int y, int pos) {
        int border = 3;
        int kc = CELL_SIZE / 4;
        x = x * CELL_SIZE + LEFT;
        y = y * CELL_SIZE + UP;
        if (pos != 1) {
            g.drawLine(x - border, y - border, x - kc, y - border);
            g.drawLine(x - border, y - border, x - border, y - kc);

            g.drawLine(x - border, y + border, x - kc, y + border);
            g.drawLine(x - border, y + border, x - border, y + kc);
        }
        if (pos != 2) {
            g.drawLine(x + border, y - border, x + kc, y - border);
            g.drawLine(x + border, y - border, x + border, y - kc);

            g.drawLine(x + border, y + border, x + kc, y + border);
            g.drawLine(x + border, y + border, x + border, y + kc);
        }
    }

    public void DrawQuanCo(Graphics g, Board board, ImageObserver server) {
        Image[] pictureBox = {imagebk, imageba, imagebb, imagebn, imagebr,
            imagebc, imagebp, imagerk, imagera, imagerb, imagern, imagerr,
            imagerc, imagerp};
        for (int i = 0; i <= ROW; i++) {
            for (int j = 0; j <= COL; j++) {
                Rectangle r = makeRectangle(new Point(i, j));
                byte val1 = board.mask[i][j];
                if (val1 != 0) {
                    if (val1 > 14) {
                        g.drawImage(imagetkr, r.x, r.y, r.width, r.height, server);
                    } else {
                        g.drawImage(imagetkb, r.x, r.y, r.width, r.height, server);
                    }
                } else {
                    byte val2 = board.cell[i][j];
                    if (val2 != 0) {
                        g.drawImage(pictureBox[val2 - 8], r.x, r.y, r.width, r.height, null);
                    }
                }
            }
        }
    }

    public void DrawSelect(Graphics g, Board board, boolean select, ImageObserver server) {
        Point curr = board.CurrMove;
        Point prev = board.PrevMove;
        if (curr.x >= 0 && curr.y >= 0 && select) {
            Rectangle r = makeRectangle(curr);
            g.drawImage(imagesel, r.x, r.y, r.width, r.height, server);
        }
        if (prev.x >= 0 && prev.y >= 0 && curr.x >= 0 && curr.y >= 0 && !select) {
            Rectangle r = makeRectangle(curr);
            g.drawImage(imagesel, r.x, r.y, r.width, r.height, server);
            r = makeRectangle(prev);
            g.drawImage(imagesel, r.x, r.y, r.width, r.height, server);
        }
    }

    void DrawAllPossibleMove(Graphics g, Piece piece, boolean select, ImageObserver server) {
        if (select) {
            Color decColor = g.getColor();
            ArrayList<State> posibleMove = piece.FindAllPossibleMoves();
            if (piece != null) {
                for (int i = 0; i < posibleMove.size(); i++) {
                    Point pos = posibleMove.get(i).curr;
                    Rectangle r = makeRectangle(pos);
                    if (piece.board.cell[pos.x][pos.y] != 0) {
                        g.drawImage(imagesel, r.x, r.y, r.width, r.height, server);
                    } else {
                        g.setColor(Color.BLUE);
                        g.fillOval(r.x + 15, r.y + 15, r.width - 30, r.height - 30);
                    }
                }
            }
            g.setColor(decColor);
        }
    }

    public void DrawMessage(Graphics g, String message, boolean two, ImageObserver server) {
        Font deFont = g.getFont();
        Color decColor = g.getColor();
        Font ft = new Font("Monospaced", Font.BOLD, 16);
        g.setFont(ft);
        g.setColor(Color.BLUE);
        try {
            int leght = message.length();
            String print = message;
            if (leght >= 27) {
                int k = 26;
                while (print.charAt(k) != ' ') {
                    k--;
                }
                print = print.substring(0, k);
                print = print.concat("...");
                leght = k + "...".length();
            }
            leght *= 5;
            int mid = WIDTH_CHAT / 2;
            if (two) {
                g.drawString(print, WIDTH + mid - leght, 625);
            } else {
                g.drawString(print, WIDTH + mid - leght, 600);
            }
        } catch (Exception e) {
            ChineseChess.Message1 = "ERROR: " + e.toString();
        }
        g.setFont(deFont);
        g.setColor(decColor);
    }

    public void DrawChat(Graphics g, ArrayList<String> message, ArrayList<Boolean> isRed, ImageObserver server) {
        Font deFont = g.getFont();
        Color decColor = g.getColor();
        Font ft = new Font("Monospaced", Font.BOLD, 11);
        g.setFont(ft);
        g.setColor(Color.WHITE);
        int n = message.size() - 1;
        for (int i = n; i >= n - 4 && i >= 0; i--) {
            int leght = message.get(i).length();
            String print = message.get(i);
            if (leght >= 37) {
                int k = 36;
                while (k > 0 && print.charAt(k) != ' ') {
                    k--;
                }
                print = print.substring(0, k);
                print = print.concat("...");
                leght = k + "...".length();
            }
            leght *= 7;
            if (isRed.get(i)) {
                g.drawImage(massegeImage, WIDTH + WIDTH_CHAT - (leght + 30), 60 + (n - i) * 40, leght + 25, 40, server);
                g.drawString(print, WIDTH + WIDTH_CHAT - (leght + 17), 80 + (n - i) * 40);
            } else {
                g.drawImage(massegeImage, WIDTH + 5, 60 + (n - i) * 40, leght + 25, 40, server);
                g.drawString(print, WIDTH + 19, 80 + (n - i) * 40);
            }
        }
        g.setFont(deFont);
        g.setColor(decColor);
    }

    public void clearMessage(Graphics g, ImageObserver server) {
        g.drawImage(imagebottom, WIDTH, 580, WIDTH_CHAT, 106, server);
    }

    public void DrawFormChat(Graphics g, ImageObserver server) {
        int left = WIDTH + CELL_SIZE / 4;
        int mid = WIDTH_CHAT / 2;
        int top = 400;
        int width = 120;
        int height = 40;
        int padding = 20;
        Font deFont = g.getFont();
        Color decColor = g.getColor();
        Font ft = new Font("Monospaced", Font.BOLD, 15);
        g.setFont(ft);
        g.setColor(Color.WHITE);
        g.drawImage(imagewin, WIDTH, 25, WIDTH_CHAT, 565, server);

        g.drawImage(buttonImage, left, top, width, height, server);
        g.drawString("NEW GAME", left + (width - 8 * 8) / 2, top + 23);

        g.drawImage(buttonImage, left + mid, top, width, height, server);
        g.drawString("MULTI", left + mid + (width - 5 * 8) / 2, top + 23);

        g.drawImage(buttonImage, left, top + height + padding, width, height, server);
        g.drawString("UNDO", left + (width - 4 * 8) / 2, top + height + padding + 23);

        g.drawImage(buttonImage, left + mid, top + height + padding, width, height, server);
        g.drawString("LOAD", left + mid + (width - 4 * 8) / 2, top + height + padding + 23);

        g.drawImage(buttonImage, left, top + 2 * (height + padding), width, height, server);
        g.drawString("SAVE", left + (width - 4 * 8) / 2, top + 2 * (height + padding) + 23);

        g.drawImage(buttonImage, left + mid, top + 2 * (height + padding), width, height, server);
        g.drawString("QUIT", left + mid + (width - 4 * 8) / 2, top + 2 * (height + padding) + 23);

        g.setFont(deFont);
        g.setColor(decColor);
    }

    void button_Click(Graphics g, int name, ImageObserver server) {
        int left = WIDTH + CELL_SIZE / 4;
        int mid = WIDTH_CHAT / 2;
        int top = 400;
        int width = 120;
        int height = 40;
        int padding = 20;
        Font deFont = g.getFont();
        Color decColor = g.getColor();
        Font ft = new Font("Monospaced", Font.BOLD, 15);
        g.setFont(ft);
        g.setColor(Color.BLUE);
        switch (name) {
            case 1:
                g.drawString("NEW GAME", left + (width - 8 * 8) / 2, top + 23);
                break;
            case 2:
                g.drawString("MULTI", left + mid + (width - 5 * 8) / 2, top + 23);
                break;
            case 3:
                g.drawString("UNDO", left + (width - 4 * 8) / 2, top + height + padding + 23);
                break;
            case 4:
                g.drawString("LOAD", left + mid + (width - 4 * 8) / 2, top + height + padding + 23);
                break;
            case 5:
                g.drawString("SAVE", left + (width - 4 * 8) / 2, top + 2 * (height + padding) + 23);
                break;
            case 6:
                g.drawString("QUIT", left + mid + (width - 4 * 8) / 2, top + 2 * (height + padding) + 23);
                break;
        }
        g.setFont(deFont);
        g.setColor(decColor);
    }

    private Rectangle makeRectangle(Point p) {
        Rectangle selRectangle = new Rectangle();
        selRectangle.x = p.y * CELL_SIZE + LEFT - PADDING;
        selRectangle.y = p.x * CELL_SIZE + UP - PADDING;
        selRectangle.width = 2 * PADDING;
        selRectangle.height = 2 * PADDING;
        return selRectangle;
    }
}
