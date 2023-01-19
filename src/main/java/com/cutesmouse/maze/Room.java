package com.cutesmouse.maze;

import java.util.HashMap;
import java.util.regex.Pattern;

import static com.cutesmouse.maze.Facing.LEFT;

public class Room {

    public static final int CARRY = 10000;

    public static int getHashcode(int x, int y) {
        return CARRY*x + y;
    }

    private int x;
    private int y;
    private HashMap<Facing, Boolean> attachment;
    public Room(int x, int y) {
        this.x = x;
        this.y = y;
        attachment = new HashMap<>();
    }

    public boolean isAttach(Room room) {
        if (room.x == x) {
            if (room.y - y == 1) return attachment.getOrDefault(Facing.TOP, false);
            else return attachment.getOrDefault(Facing.BOTTOM, false);
        }
        if (room.y == y) {
            if (room.x - x == 1) return attachment.getOrDefault(Facing.RIGHT, false);
            else return attachment.getOrDefault(LEFT, false);
        }
        return false;
    }

    public void setAttach(Room room) {
        if (room.x == x) {
            if (room.y - y == 1) attachment.put(Facing.TOP, true);
            else attachment.put(Facing.BOTTOM, true);
        }
        if (room.y == y) {
            if (room.x - x == 1) attachment.put(Facing.RIGHT, true);
            else attachment.put(LEFT, true);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return CARRY*x + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Room)) return false;
        return x == ((Room) obj).x && y == ((Room) obj).y;
    }
}
