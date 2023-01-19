package com.cutesmouse.maze;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        final int SIZE_X = 30; //迷宮寬度
        final int SIZE_Y = 30; //迷宮高度
        final int WINDOW_X = 800; //視窗寬度
        final int WINDOW_Y = 800; //視窗高度
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame jf = new JFrame("Maze");
        jf.setSize(WINDOW_X, WINDOW_Y);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Maze maze = new Maze(SIZE_X, SIZE_Y);
        maze.generate();
        final Screen sc = new Screen(maze, WINDOW_X - 50*2, WINDOW_Y - 25*4, 50, 25, 3);
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
