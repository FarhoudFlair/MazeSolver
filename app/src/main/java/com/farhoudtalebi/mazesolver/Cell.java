package com.farhoudtalebi.mazesolver;

import android.util.Log;

import java.util.ArrayList;

class Cell {
    private boolean isWall = false, inPath = false, visited = false;
    private final int x;
    private final int y;
    private Maze maze;
    private ArrayList<Cell> neighbours = new ArrayList<Cell>();


    public Cell(int x, int y, Maze maze) {
        this.x = x;
        this.y = y;
        this.maze = maze;
    }

    public void toggleWall() {
        if(!this.equals(maze.getStart()) || !this.equals(maze.getFinish())) {
            isWall = !isWall;
        }
    }

    public void togglePath() {
        if(this.isWall == false) {
            inPath = !inPath;
        }
    }

    public boolean isWall() {
        return isWall;
    }

    public void visited() {
        visited = true;
    }

    public boolean isVisited() {
        return visited;
    }

    public int getXCord() {
        return this.x;
    }

    public int getYCord() {
        return this.y;
    }

    public void setNeighbours () {
        neighbours = new ArrayList<Cell>();
        if(x!=0) addNeighbour(x-1, y);
        if(y!=0) addNeighbour(x, y-1);
        if(x<maze.getColumns()-1) addNeighbour(x+1,y);
        if(y<maze.getRows()-1) addNeighbour(x,y+1);
    }

    private void addNeighbour(int x, int y) {
        Cell neighbour = maze.getCell(x,y);
        if(neighbour.isWall() == false) {
            neighbours.add(neighbour);
        }
    }

    public Cell getUnvisitedNeighbour() {
        if (neighbours==null) return null;

        if (neighbours.size() == 0) return null;

        for(int i=0; i<neighbours.size(); i++) {
            Cell next = (Cell) neighbours.get(i);
            if(!next.isVisited()) return next;
        }
        return null;
    }



}
