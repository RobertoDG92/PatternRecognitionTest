package rdigiovanni.Service.Implementation;

import org.springframework.stereotype.Service;
import rdigiovanni.Service.PointService;
import rdigiovanni.entity.Line;
import rdigiovanni.entity.Point;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PointServiceImpl implements PointService {
    @Override
    public void addPoint(Map<Line, Set<Point>> linesMap, Point point) {
        if(linesMap.isEmpty()){
            // If the map is empty, add a new line with the point.
            Set<Point> newPointsSet = new HashSet<>();
            newPointsSet.add(point);
            linesMap.put(new Line(point, point), newPointsSet); // We make a line using the same point twice, this is arbitrary and can be any line.
        }
        else {
            Map<Line, Set<Point>> newLinesMap = new HashMap<>(linesMap);
            for (Set<Point> points : linesMap.values()) {
                for (Point existingPoint : points) {
                    Line newLine = new Line(existingPoint, point);
                    if (newLinesMap.containsKey(newLine)) {
                        newLinesMap.get(newLine).add(point);
                    } else {
                        Set<Point> newPoints = new HashSet<>();
                        newPoints.add(existingPoint);
                        newPoints.add(point);
                        newLinesMap.put(newLine, newPoints);
                    }
                }
            }
            linesMap.putAll(newLinesMap);
        }
    }

    @Override
    public Set<Point> getAllPoint(Map<Line, Set<Point>> linesMap) {
        return linesMap.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    @Override
    public List<Set<Point>> getLines(int n, Map<Line, Set<Point>> linesMap) {
        List<Set<Point>> result = new ArrayList<>();
        for (Set<Point> points : linesMap.values()) {
            if (points.size() >= n) {
                result.add(new HashSet<>(points));
            }
        }
        return result;
    }

    @Override
    public void removeAllPoints(Map<Line, Set<Point>> linesMap) {
        linesMap.clear();
    }


}
