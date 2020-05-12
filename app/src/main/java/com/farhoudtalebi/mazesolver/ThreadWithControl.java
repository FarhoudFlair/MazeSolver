package com.farhoudtalebi.mazesolver;

import android.util.Log;
import android.widget.Button;

import java.util.Stack;

public class ThreadWithControl extends Thread {

    Maze model;
    MainActivity mainActivity;
    Button[][] mButtons;
    Stack<Cell> pathList;

    public ThreadWithControl(Maze model, MainActivity mainActivity, Button[][] buttons) {
        this.model = model;
        this.mainActivity = mainActivity;
        mButtons = buttons;
        pathList = new Stack<Cell>();
    }

    public void quit() {
        Cell t;

        if(!pathList.isEmpty()){
            pathList.pop();
        }
        while(!pathList.isEmpty()) {
            t = pathList.pop();
            mButtons[t.getXCord()][t.getYCord()].setBackgroundColor(mainActivity.getResources().getColor(R.color.path));
            mButtons[t.getXCord()][t.getYCord()].setText(R.string.path);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
        }
    }

    public void run() {
       boolean check = solve();
       quit();
    }

    public boolean solve() {
        return findPathFrom(model.getStart());
    }

    public boolean findPathFrom(final Cell s) {
        s.visited();
        ///base case
        if(s == model.getFinish()) {
            s.togglePath();
            return true;
        }

        //recursive case
        Cell next = s.getUnvisitedNeighbour();

        while(next != null) {
            if(findPathFrom(next)) {
                s.togglePath();
                pathList.push(s);
                return true;
            }
            next = s.getUnvisitedNeighbour();
        }
        return false;
    }


}
