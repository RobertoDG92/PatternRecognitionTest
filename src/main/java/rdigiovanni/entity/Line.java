package rdigiovanni.entity;

import java.util.Objects;

public class Line {
    private final double slope;
    private final double yIntercept;

    public Line(rdigiovanni.entity.Point p1, Point p2) {
        if (p1.x == p2.x) {
            this.slope = Double.MAX_VALUE;
            this.yIntercept = p1.x;
        } else {
            this.slope = (double) (p2.y - p1.y) / (p2.x - p1.x);
            this.yIntercept = p1.y - this.slope * p1.x;
        }
    }

    boolean isOnLine(rdigiovanni.entity.Point p) {
        if (this.slope == Double.MAX_VALUE) {
            return this.yIntercept == p.x;
        } else {
            return Math.abs(p.y - (this.slope * p.x + this.yIntercept)) < 0.0001;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Double.compare(line.slope, slope) == 0 &&
                Double.compare(line.yIntercept, yIntercept) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(slope, yIntercept);
    }
}
