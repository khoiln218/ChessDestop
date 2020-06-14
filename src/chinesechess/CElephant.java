package chinesechess;

import java.awt.Point;
import java.util.ArrayList;

class CElephant extends Piece {

    public CElephant(Board board, Point currMove) {
        super(board, currMove);
    }

    @Override
    public ArrayList<State> FindAllPossibleMoves() {
        allPossibleMove = new ArrayList<>();
        byte[] dx = {2, 2, -2, -2};
        byte[] dy = {2, -2, 2, -2};
        for (int i = 0; i < dx.length; i++) {
            int x = CurrMove.x + dx[i];
            int y = CurrMove.y + dy[i];
            if (x >= 0 && x <= 9 && y >= 0 && y <= 8) {
                byte val1 = board.cell[CurrMove.x][CurrMove.y];
                byte val2 = board.cell[x][y];
                if ((val2 == 0 || ((RED && val2 >= 8 && val2 <= 14) || (!RED && val2 > 14))) && checkProject(new Point(x, y))) {
                    doMove(x, y);
                    ArrayList<Point> arr = board.FindPieces(RED);
                    if (!checkProject(board, arr)) {
                        allPossibleMove.add(new State(CurrMove, new Point(x, y), val1, val2));
                    }
                    reMove(x, y, val2);
                }
            }
        }
        return allPossibleMove;
    }

    @Override
    boolean checkProject(Point King) {
        int dong = King.x - CurrMove.x;
        int cot = King.y - CurrMove.y;
        if (Math.abs(dong) == 2 && Math.abs(cot) == 2) {
            if (dong > 0) {
                if (cot > 0) {
                    if (board.cell[CurrMove.x + 1][CurrMove.y + 1] == 0) {
                        return true;
                    }
                } else {
                    if (board.cell[CurrMove.x + 1][CurrMove.y - 1] == 0) {
                        return true;
                    }
                }
            } else {
                if (cot > 0) {
                    if (board.cell[CurrMove.x - 1][CurrMove.y + 1] == 0) {
                        return true;
                    }
                } else {
                    if (board.cell[CurrMove.x - 1][CurrMove.y - 1] == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
