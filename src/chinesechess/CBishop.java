package chinesechess;

import java.awt.Point;
import java.util.ArrayList;

class CBishop extends Piece {

    public CBishop(Board board, Point currMove) {
        super(board, currMove);
    }

    @Override
    public ArrayList<State> FindAllPossibleMoves() {
        allPossibleMove = new ArrayList<>();
        int[] dx = {1, 1, -1, -1};
        int[] dy = {1, -1, 1, -1};
        for (int i = 0; i < dx.length; i++) {
            int x = CurrMove.x + dx[i];
            int y = CurrMove.y + dy[i];
            if (x >= 0 && x <= 9 && y >= 0 && y <= 8) {
                byte val1 = board.cell[CurrMove.x][CurrMove.y];
                byte val2 = board.cell[x][y];
                byte val3 = board.mask[CurrMove.x][CurrMove.y];
                if ((val2 == 0 || ((RED && val2 >= 8 && val2 <= 14) || (!RED && val2 > 14))) && ((val3 != 0 && y == 4) || val3 == 0)) {
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
        int dx = Math.abs(CurrMove.x - King.x);
        int dy = Math.abs(CurrMove.y - King.y);
        if (dx == 1 && dy == 1) {
            return true;
        }
        return false;
    }
}
