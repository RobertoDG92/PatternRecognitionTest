package rdigiovanni.Service;

import rdigiovanni.entity.Line;
import rdigiovanni.entity.Point;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PointService {
    void addPoint(Map<Line, Set<Point>> linesMap, Point point);


    Set<Point> getAllPoint(Map<Line, Set<Point>> linesMap);

    List<Set<Point>> getLines(int n, Map<Line, Set<Point>> linesMap);

    void removeAllPoints(Map<Line, Set<Point>> linesMap);
}
