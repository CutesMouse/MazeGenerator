package com.cutesmouse.maze;

import java.util.*;

public class Maze {
    public HashMap<Room, Integer> disjoint_set;
    public HashMap<Integer, Room> rooms;
    public ArrayList<Wall> walls;

    private int size_x;
    private int size_y;

    private ArrayList<Room> answer;

    public Maze(int size_x, int size_y) {

        rooms = new HashMap<>();
        walls = new ArrayList<>();
        disjoint_set = new HashMap<>();
        this.size_x = size_x;
        this.size_y = size_y;

        for (int x = 0; x < size_x; x++) {
            for (int y = 0; y < size_y; y++) {
                Room r = new Room(x, y);
                rooms.put(r.hashCode(), r);
            }
        }

        for (int x = 0; x < size_x; x++) {
            for (int y = 0; y < size_y - 1; y++) {
                Room a = rooms.get(Room.getHashcode(x, y));
                Room b = rooms.get(Room.getHashcode(x, y + 1));
                walls.add(new Wall(a, b));
            }
        }

        for (int x = 0; x < size_x - 1; x++) {
            for (int y = 0; y < size_y; y++) {
                Room a = rooms.get(Room.getHashcode(x, y));
                Room b = rooms.get(Room.getHashcode(x + 1, y));
                walls.add(new Wall(a, b));
            }
        }
    }

    public void generate() {
        Collections.shuffle(walls);
        int ptr = 0;
        while (!connected()) {
            Wall remove = walls.get(ptr);
            ptr++;
            Room a = remove.getConnected_A();
            Room b = remove.getConnected_B();
            if (isGrouped(a, b)) continue;
            link(a, b);
            a.setAttach(b);
            b.setAttach(a);
            walls.set(ptr - 1, null);
        }
    }

    public ArrayList<Room> getAnswer() {
        if (answer != null) return answer;
        HashMap<Room, Integer> steps = new HashMap<>();
        steps.put(rooms.get(0), 0);
        Queue<Room> bfs = new LinkedList<>();
        ArrayList<Integer> walked = new ArrayList<>();
        bfs.add(rooms.get(0));
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};
        while (!bfs.isEmpty()) {
            Room current = bfs.poll();
            int step = steps.getOrDefault(current, 0);
            for (int i = 0; i < 4; i++) {
                int nx = current.getX() + dx[i];
                int ny = current.getY() + dy[i];
                if (nx < 0 || ny < 0 || nx >= size_x || ny >= size_y) continue;
                if (walked.contains(Room.getHashcode(nx, ny))) continue;
                Room new_room = rooms.get(Room.getHashcode(nx, ny));
                if (!current.isAttach(new_room)) continue;
                walked.add(Room.getHashcode(nx, ny));
                steps.put(new_room, step + 1);
                bfs.add(new_room);
                if (nx == size_x - 1 && ny == size_y - 1) break;
            }
        }
        answer = new ArrayList<>();
        Room current = rooms.get(Room.getHashcode(size_x-1, size_y-1));
        answer.add(current);
        while (current != rooms.get(0)) {
            int step = steps.getOrDefault(current, -1);
            if (step == -1) break;
            if (step == 1) break;
            for (int i = 0; i < 4; i++) {
                int nx = current.getX() + dx[i];
                int ny = current.getY() + dy[i];
                if (nx < 0 || ny < 0 || nx >= size_x || ny >= size_y) continue;
                Room new_room = rooms.get(Room.getHashcode(nx, ny));
                if (!steps.containsKey(new_room)) continue;
                if (steps.get(new_room) == step-1 && new_room.isAttach(current)) {
                    answer.add(new_room);
                    current = new_room;
                    break;
                }
            }
        }
        answer.add(rooms.get(0));
        return answer;
    }

    public boolean connected() {
        Room first = rooms.get(0);
        for (int x = 0; x < size_x; x++) {
            for (int y = 0; y < size_y; y++) {
                if (!isGrouped(first, rooms.get(Room.getHashcode(x, y)))) return false;
            }
        }
        return true;
    }

    public int getSize_x() {
        return size_x;
    }

    public int getSize_y() {
        return size_y;
    }

    private void link(Room a, Room b) {
        if (isGrouped(a, b)) return;
        disjoint_set.put(rooms.get(find_group_name(b)), a.hashCode());
    }

    private boolean isGrouped(Room a, Room b) {
        if (a.equals(b)) return true;
        return find_group_name(a) == find_group_name(b);
    }

    private int find_group_name(Room room) {
        if (!disjoint_set.containsKey(room)) {
            disjoint_set.put(room, room.hashCode());
            return room.hashCode();
        }
        if (disjoint_set.get(room) == room.hashCode()) return room.hashCode();
        disjoint_set.put(room, find_group_name(rooms.get(disjoint_set.get(room))));
        return disjoint_set.get(room);
    }
}
