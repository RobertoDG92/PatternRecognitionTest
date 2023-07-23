package rdigiovanni.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rdigiovanni.entity.Line;
import rdigiovanni.entity.Point;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test")
public class PointController {

    private final ReentrantLock lock = new ReentrantLock();
    private final Map<Line, Set<Point>> linesMap = new HashMap<>();

    /**
     * Add a new point to the plane.
     *
     * @param point The point to add.
     * @return A ResponseEntity with HTTP status CREATED if the point was successfully added, or BAD_REQUEST otherwise.
     */
    @PostMapping("/point")
    public ResponseEntity<Void> addPoint(@RequestBody Point point) {
        lock.lock();
        try {
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
            return new ResponseEntity<>(HttpStatus.CREATED);
        } finally {
            lock.unlock();
        }
    }
    /**
     * Get all points currently on the plane.
     *
     * @return A ResponseEntity containing a Set of all points.
     */
    @GetMapping("/space")
    public ResponseEntity<Set<Point>> getAllPoints() {
        return ResponseEntity.ok(linesMap.values().stream().flatMap(Collection::stream).collect(Collectors.toSet()));
    }
    /**
     * Get all line segments passing through at least N points.
     *
     * @param n The minimum number of points a line segment must pass through.
     * @return A ResponseEntity containing a List of Sets of Points, where each Set represents a line segment.
     */
    @GetMapping("/lines/{n}")
    public ResponseEntity<List<Set<Point>>> getLines(@PathVariable int n) {
        if (n <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        lock.lock();
        try {
            List<Set<Point>> result = new ArrayList<>();
            for (Set<Point> points : linesMap.values()) {
                if (points.size() >= n) {
                    result.add(new HashSet<>(points));
                }
            }
            return ResponseEntity.ok(result);
        } finally {
            lock.unlock();
        }
    }
    /**
     * Remove all points from the plane.
     *
     * @return A ResponseEntity with HTTP status NO_CONTENT.
     */
    @DeleteMapping("/space")
    public ResponseEntity<Void> removeAllPoints() {
        lock.lock();
        try {
            linesMap.clear();
            return ResponseEntity.noContent().build();
        } finally {
            lock.unlock();
        }
    }


}