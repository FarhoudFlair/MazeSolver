package com.farhoudtalebi.mazesolver;

class Maze {

    private int rows, columns;
    private Cell [][] map;
    private Cell start, finish;
    MainActivity mActivity;


    public Maze(int numRows, int numCols, MainActivity mainActivity) {
        rows = numRows;
        columns = numCols;
        map = new Cell[columns][rows];

        for (int i=0; i<columns; i++) {
            for (int j=0; j<rows; j++) {
                map[i][j] = new Cell(i,j,this);
            }
        }
        mActivity = mainActivity;
    }

    public void setStart(int x, int y) {
        if(x>=0 && x<columns && y>=0 && y<rows) {
            start = map[x][y];
        }
    }

    public void setFinish(int x, int y) {
        if(x>=0 && x<columns && y>=0 && y<rows) {
            finish = map[x][y];
        }
    }

    public int getRows() { return rows; }

    public int getColumns() { return columns; }

    public Cell getStart() {
        return start;
    }

    public Cell getFinish() {
        return finish;
    }

    public Cell getCell(int x, int y) {
        return map[x][y];
    }

    public void setNeighbours() {
        for(int i=0; i<columns; i++) {
            for(int j=0; j<rows; j++) {
                if(!map[i][j].isWall()) {
                    map[i][j].setNeighbours();
                }
            }
        }
    }

}
