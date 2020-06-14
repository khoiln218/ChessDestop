package chinesechess;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public final class Board {

    public final static byte[][] cellStartup = {
        {19, 18, 17, 16, 15, 16, 17, 18, 19},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 20, 0, 0, 0, 0, 0, 20, 0},
        {21, 0, 21, 0, 21, 0, 21, 0, 21},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {14, 0, 14, 0, 14, 0, 14, 0, 14},
        {0, 13, 0, 0, 0, 0, 0, 13, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {12, 11, 10, 9, 8, 9, 10, 11, 12}};
    public byte[][] mask;
    public byte[][] cell;
    public Point CurrMove;
    public Point PrevMove;
    ArrayList<State> listUndo;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        CurrMove = new Point(-1, -1);
        PrevMove = new Point(-1, -1);
        listUndo = new ArrayList<>();
        cell = new byte[MyGraphics.ROW + 1][MyGraphics.COL + 1];
        for (int i = 0; i <= MyGraphics.ROW; i++) {
            for (int j = 0; j <= MyGraphics.COL; j++) {
                cell[i][j] = cellStartup[i][j];
            }
        }
    }

    public void randomBoard() {
        byte[] quanDo = new byte[16];
        byte[] quanDen = new byte[16];
        int iDo = 0, iDen = 0;
        ArrayList<Point> pointR = new ArrayList<>();
        ArrayList<Point> pointB = new ArrayList<>();
        mask = new byte[MyGraphics.ROW + 1][MyGraphics.COL + 1];
        for (int i = 0; i <= MyGraphics.ROW; i++) {
            for (int j = 0; j <= MyGraphics.COL; j++) {
                mask[i][j] = 0;
                byte val = cellStartup[i][j];
                if (val > 8 && val != 15) {
                    if (val > 14) {
                        quanDo[iDo++] = val;
                        pointR.add(new Point(i, j));
                    } else {
                        quanDen[iDen++] = val;
                        pointB.add(new Point(i, j));
                    }
                }
            }
        }
        Random rand = new Random();
        for (int i = 15; i > 0; i--) {
            int x = rand.nextInt(i);
            mask[pointR.get(15 - i).x][pointR.get(15 - i).y] = quanDo[x];
            quanDo[x] = quanDo[i - 1];

            x = rand.nextInt(i);
            mask[pointB.get(15 - i).x][pointB.get(15 - i).y] = quanDen[x];
            quanDen[x] = quanDen[i - 1];
        }
    }

    public void setMask(String[] p) {
        mask = new byte[MyGraphics.ROW + 1][MyGraphics.COL + 1];
        int k = 0;
        for (int i = 0; i <= MyGraphics.ROW; i++) {
            for (int j = 0; j <= MyGraphics.COL; j++) {
                mask[i][j] = Byte.parseByte(p[k++]);
            }
        }
    }

    public String getMask() {
        String s = "";
        for (int i = 0; i <= MyGraphics.ROW; i++) {
            for (int j = 0; j <= MyGraphics.COL; j++) {
                s += mask[i][j] + ",";
            }
        }
        return s;
    }

    ArrayList<Point> FindPieces(boolean RED) {
        ArrayList<Point> allpiece = new ArrayList<>();
        allpiece.add(0, new Point(-1, -1));
        for (int i = 0; i <= MyGraphics.ROW; i++) {
            for (int j = 0; j <= MyGraphics.COL; j++) {
                byte val = cell[i][j];
                if (val >= 8 && val <= 21) {
                    if ((RED && val == 15) || (!RED && val == 8)) {
                        allpiece.remove(0);
                        allpiece.add(0, new Point(i, j));
                    }
                    if ((RED && val <= 14) || (!RED && val > 14)) {
                        allpiece.add(new Point(i, j));
                    }
                }
            }
        }
        return allpiece;
    }

    boolean IsGameOver(boolean RED) {
        ArrayList<Point> allpiece = FindPieces(RED);
        allpiece.get(0);
        for (int i = 1; i < allpiece.size(); i++) {
            Point pos = allpiece.get(i);
            ArrayList<State> arrMoves = null;
            byte val = cell[pos.x][pos.y];
            switch (val) {
                case 8:
                case 15:
                    CKing king = new CKing(this, pos);
                    arrMoves = king.FindAllPossibleMoves();
                    break;
                case 9:
                case 16:
                    CBishop bishop = new CBishop(this, pos);
                    arrMoves = bishop.FindAllPossibleMoves();
                    break;
                case 10:
                case 17:
                    CElephant elephant = new CElephant(this, pos);
                    arrMoves = elephant.FindAllPossibleMoves();
                    break;
                case 11:
                case 18:
                    CKnight knight = new CKnight(this, pos);
                    arrMoves = knight.FindAllPossibleMoves();
                    break;
                case 12:
                case 19:
                    CRook rook = new CRook(this, pos);
                    arrMoves = rook.FindAllPossibleMoves();
                    break;
                case 13:
                case 20:
                    CCannon cannon = new CCannon(this, pos);
                    arrMoves = cannon.FindAllPossibleMoves();
                    break;
                case 14:
                case 21:
                    CPawn pawn = new CPawn(this, pos);
                    arrMoves = pawn.FindAllPossibleMoves();
                    break;
            }
            if (arrMoves != null && !arrMoves.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
