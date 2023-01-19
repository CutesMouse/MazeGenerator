package com.cutesmouse.maze;

public class Wall {
    private Room connected_A;
    private Room connected_B;
    public Wall(Room a, Room b) {
        this.connected_A = a;
        this.connected_B = b;
    }

    public Room getConnected_A() {
        return connected_A;
    }

    public Room getConnected_B() {
        return connected_B;
    }
}
