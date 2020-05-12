package com.farhoudtalebi.mazesolver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ////*********TO CHANGE NUMBER OF CELLS:************\\\\
    /////////JUST CHANGE THESE TWO VARIABLES\\\\\\\\\
    private  static final int NUM_ROWS = 11;
    private  static final int NUM_COLS = 10;


    // Initial Start and Destination locations
    private  static final int INITIAL_START_ROW = 0;
    private  static final int INITIAL_START_COL = 0;
    private  static final int INITIAL_DESTINATION_ROW = NUM_ROWS - 1;
    private  static final int INITIAL_DESTINATION_COL = NUM_COLS - 1;


    private  Button solveMazeButton;
    private Maze model; //not needed for tutorial

    Button buttons[][] = new Button[NUM_COLS][NUM_ROWS];

    TableRow tableRow;
    Button button;

    ThreadWithControl mThread1;

    //To show if start and destination tiles are being chosen
    boolean startToggle = false, destinationToggle = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Creating a model for maze
        model = new Maze(NUM_ROWS, NUM_COLS, this); //for the assignment

        // Adding buttons with UI Threads
        TableLayout gameLayout = findViewById(R.id.gameTable);

        for(int i=0; i<NUM_COLS; i++) {
            tableRow = new TableRow(MainActivity.this);
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 13f);
            tableRow.setLayoutParams(rowParams);
            gameLayout.addView(tableRow);

            for(int j=0; j<NUM_ROWS; j++) {
                button = new Button(MainActivity.this);
                button.setText(R.string.empty);
                TableRow.LayoutParams param = new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f
                );
                button.setBackgroundColor(getResources().getColor(R.color.empty));
                button.setLayoutParams(param);
                tableRow.addView(button);
                buttons[i][j] = button;
                button.setTag(R.string.TAG_X_CORD, i);
                button.setTag(R.string.TAG_Y_CORD, j);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button b = (Button) v;
                        int cID = ((ColorDrawable) b.getBackground()).getColor();
                        Object tag;
                        int xCord=0, yCord=0;
                        tag = b.getTag(R.string.TAG_X_CORD);
                        xCord = (Integer) tag;
                        tag = b.getTag(R.string.TAG_Y_CORD);
                        yCord = (Integer) tag;
                        if (startToggle) {
                            b.setBackgroundColor(getResources().getColor(R.color.start));
                            b.setText(R.string.start);
                            model.setStart(xCord, yCord);
                            startToggle = false;
                        } else if (destinationToggle) {
                            b.setBackgroundColor(getResources().getColor(R.color.destination));
                            b.setText(R.string.destination);
                            model.setFinish(xCord, yCord);
                            destinationToggle = false;
                        } else {
                            if (cID == getResources().getColor(R.color.wall)) {
                                b.setBackgroundColor(getResources().getColor(R.color.empty));
                                b.setText(R.string.empty);
                                model.getCell(xCord,yCord).toggleWall();
                            } else if (cID == getResources().getColor(R.color.empty)) {
                                b.setBackgroundColor(getResources().getColor(R.color.wall));
                                b.setText(R.string.wall);
                                model.getCell(xCord,yCord).toggleWall();
                            } else if (cID == getResources().getColor(R.color.start)) {
                                b.setBackgroundColor(getResources().getColor(R.color.empty));
                                b.setText(R.string.empty);
                                model.setStart(0,0);
                                startToggle = true;
                            } else if (cID == getResources().getColor(R.color.destination)) {
                                b.setBackgroundColor(getResources().getColor(R.color.empty));
                                b.setText(R.string.empty);
                                model.setFinish(NUM_COLS, NUM_ROWS);
                                destinationToggle = true;
                            }
                        }
                    }
                });
            }
        }


        buttons[INITIAL_START_COL][INITIAL_START_ROW].setBackgroundColor(getResources().getColor(R.color.start));
        buttons[INITIAL_START_COL][INITIAL_START_ROW].setText(R.string.start);
        model.setStart(INITIAL_START_COL,INITIAL_START_ROW);
        buttons[INITIAL_DESTINATION_COL][INITIAL_DESTINATION_ROW].setBackgroundColor(getResources().getColor(R.color.destination));
        buttons[INITIAL_DESTINATION_COL][INITIAL_DESTINATION_ROW].setText(R.string.destination);
        model.setFinish(INITIAL_DESTINATION_COL, INITIAL_DESTINATION_ROW);

        solveMazeButton = (Button) findViewById(R.id.button_solve_maze);
        solveMazeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                Toast t = Toast.makeText(MainActivity.this, b.getText(), Toast.LENGTH_SHORT);
                t.show();
                model.setNeighbours();

                for(int i=0; i<NUM_COLS; i++) {
                    for(int j=0; j<NUM_ROWS; j++) {
                        buttons[i][j].setEnabled(false);
                    }
                }
                mThread1 = new ThreadWithControl(model, MainActivity.this, buttons);
                mThread1.start();
            }

        });


    }
}
