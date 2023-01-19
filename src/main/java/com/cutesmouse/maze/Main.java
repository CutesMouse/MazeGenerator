package com.cutesmouse.maze;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        final int SIZE_X = 40; //迷宮寬度
        final int SIZE_Y = 40; //迷宮高度
        final int WINDOW_X = 1000; //視窗寬度
        final int WINDOW_Y = 1000; //視窗高度
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame jf = new JFrame("Maze");
        jf.setSize(WINDOW_X, WINDOW_Y);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Maze maze = new Maze(SIZE_X, SIZE_Y);
        maze.generate();
        final Screen sc = new Screen(maze, 900, 900, 50, 25, 3);
        jf.setContentPane(sc);
        jf.setVisible(true);

        jf.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sc.setAnswerDisplay(!sc.isAnswerDisplay());
                jf.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
