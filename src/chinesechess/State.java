package chinesechess;

import java.awt.Point;

class State {

    Point prev, curr, pos;
    byte value1, value2, value3, value4;

    public State(Point p, Point c, byte val1, byte val2) {
        this.prev = new Point(p);
        this.curr = new Point(c);
        pos = null;
        this.value1 = val1;
        this.value2 = val2;
        this.value3 = -1;
        this.value4 = -1;
    }

    public State(Point p, Point c, Point pos, byte val1, byte val2, byte val3, byte val4) {
        this.prev = new Point(p);
        this.curr = new Point(c);
        this.pos = new Point(pos);
        this.value1 = val1;
        this.value2 = val2;
        this.value3 = val3;
        this.value4 = val4;
    }
}
