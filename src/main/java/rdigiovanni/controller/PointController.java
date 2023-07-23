package rdigiovanni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rdigiovanni.service.PointService;
import rdigiovanni.entity.Line;
import rdigiovanni.entity.Point;

import java.util.*;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/test")
public class PointController {

    private final ReentrantLock lock = new ReentrantLock();
    private final Map<Line, Set<Point>> linesMap = new HashMap<>();

    @Autowired
    private PointService pointService;

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
            this.pointService.addPoint(linesMap, point);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }finally {
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
        Set<Point> points = this.pointService.getAllPoint(linesMap);
        return ResponseEntity.ok(points);
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
            List<Set<Point>> result = this.pointService.getLines(n, linesMap);
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
            this.pointService.removeAllPoints(linesMap);
            return ResponseEntity.noContent().build();
        } finally {
            lock.unlock();
        }
    }


}