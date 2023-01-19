package com.cutesmouse.maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

public class Screen extends JPanel {
    private Maze maze;
    private int x;
    private int y;
    private int boarder_x;
    private int boarder_y;
    private int line_width;
    private int room_size_x;
    private int room_size_y;
    private boolean answer;

    public Screen(Maze m, int x, int y, int boarder_x, int boarder_y, int line_width) {
        super();
        this.maze = m;
        this.x = x;
        this.y = y;
        this.boarder_x = boarder_x;
        this.boarder_y = boarder_y;
        this.line_width = line_width;
        this.room_size_x = ((x - line_width) / m.getSize_x());
        this.room_size_y = ((y - line_width) / m.getSize_y());
        answer = false;
    }

    public boolean isAnswerDisplay() {
        return answer;
    }

    public void setAnswerDisplay(boolean b) {
        answer = b;
    }

    @Override
    public void paint(Graphics g) {
        //walls
        for (Wall wall : maze.walls) {
            if (wall == null) continue;
            Room a = wall.getConnected_A();
            Room b = wall.getConnected_B();
            // horizontal line
            if (a.getX() == b.getX()) {
                int from = (room_size_x * b.getX()) + boarder_x;
                int y = (room_size_y * b.getY()) + boarder_y;
                g.fillRect(from - line_width / 2, y - line_width / 2, room_size_x + line_width, line_width);
            }
            // vertical line
            if (a.getY() == b.getY()) {
                int from = (room_size_y * b.getY()) + boarder_y;
                int x = (room_size_x * b.getX()) + boarder_x;
                g.fillRect(x - line_width / 2, from - line_width / 2, line_width, room_size_y + line_width);
            }
        }
        //boarder
        g.fillRect(boarder_x - line_width / 2, boarder_y - line_width / 2, line_width, room_size_y * maze.getSize_y() + line_width);
        g.fillRect(boarder_x + room_size_x * maze.getSize_x() - line_width / 2, boarder_y - line_width / 2, line_width, room_size_y * maze.getSize_y() + line_width);
        g.fillRect(boarder_x - line_width / 2, boarder_y - line_width / 2, +room_size_y * maze.getSize_y(), line_width);
        g.fillRect(boarder_x - line_width / 2, boarder_y + room_size_y * maze.getSize_y() - line_width / 2, +room_size_y * maze.getSize_y(), line_width);

        //BFS Trace
        if (answer) {
            g.setColor(Color.RED);

            ArrayList<Room> answer = maze.getAnswer();
            for (int i = 0; i < answer.size() - 1; i++) {
                Room from = answer.get(i);
                Room to = answer.get(i + 1);

                int from_x = (room_size_x * from.getX()) + boarder_x + (room_size_x - line_width) / 2;
                int from_y = (room_size_y * from.getY()) + boarder_y + (room_size_y - line_width) / 2;

                if (from.getX() == to.getX()) {
                    int to_y = (room_size_y * to.getY()) + boarder_y + (room_size_y - line_width) / 2;
                    g.fillRect(from_x - line_width / 2, Math.min(from_y, to_y), line_width, Math.abs(to_y - from_y));
                }
                if (from.getY() == to.getY()) {
                    int to_x = (room_size_x * to.getX()) + boarder_x + (room_size_x - line_width) / 2;
                    g.fillRect(Math.min(to_x, from_x), from_y - line_width / 2, Math.abs(to_x - from_x), line_width);
                }
            }
        }

        //scratch & finish
        int begin_x = 0;
        int begin_y = 0;
        int end_x = maze.getSize_x() - 1;
        int end_y = maze.getSize_y() - 1;
        int round_x = (int) (room_size_x * 0.6);
        int round_y = (int) (room_size_y * 0.6);
        g.setColor(Color.RED);
        g.fillOval((room_size_x * begin_x) - line_width + round_x / 2 + boarder_x, (room_size_y * begin_y) - line_width + round_y / 2 + boarder_y, round_x, round_y);
        g.setColor(Color.GREEN);
        g.fillOval((room_size_x * end_x) - line_width + round_x / 2 + boarder_x, (room_size_y * end_y) - line_width + round_y / 2 + boarder_y, round_x, round_y);

        g.setColor(Color.BLACK);
    }
}
