package com.example.grid;

import androidx.annotation.Nullable;
import  androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grid.databinding.ActivityMainBinding;

import java.util.Random;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    TextView mines;
    Cell[][] cells;
    int WIDTH = 8;
    int HEIGHT = 13;
    int total;
    public static int openedCount = 0;
    int BOMBDENSITY = 5; // higher number - less bombs
    int BombCount = 0;
    Random rnd = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mines = findViewById(R.id.TV);
        generate();

    }

    public void generate(){
        BOMBDENSITY += MainMenu.bombs;
        WIDTH += MainMenu.difficulty;
        HEIGHT += MainMenu.difficulty;
        total = WIDTH*HEIGHT;
        TextView TV = findViewById(R.id.TV);
        GridLayout layout = findViewById(R.id.GRID);
        layout.removeAllViews();
        layout.setColumnCount(WIDTH);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        cells = new Cell[HEIGHT][WIDTH];

        for(int i=0;i<HEIGHT;i++){
            for(int j=0;j<WIDTH;j++){
                cells[i][j] = new Cell(inflater, layout);
                cells[i][j].button.setBackgroundColor(Color.GRAY);
                if (rnd.nextInt()%BOMBDENSITY==1){
                    cells[i][j].setBomb();
                    BombCount++;
                }
            }
        }
        TV.setText("Total Bombs: "+BombCount);
        for(int i=0;i<HEIGHT;i++) {
            for (int j = 0; j < WIDTH; j++) {
                int curBombs = 0;
                if (cells[i][j].hasBomb()) curBombs--;
                for(int i1 = i-1; i1 < i+2; i1++){
                    for(int j1 = j-1; j1 < j+2; j1++){
                        try{
                            if(cells[i1][j1].hasBomb()) curBombs++;
                        }catch(Exception a){}
                    }
                }
                cells[i][j].setBombCount(curBombs);
            }
        }

        for(int i=0;i<HEIGHT;i++){
            for(int j=0;j<WIDTH;j++){
                int finalI = i;
                int finalJ = j;
                cells[i][j].button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View current) {
                        if(cells[finalI][finalJ].enabled && !cells[finalI][finalJ].opened && !cells[finalI][finalJ].flag){
                            cells[finalI][finalJ].open();
                            if(cells[finalI][finalJ].hasBomb()) {
                                Intent intent = new Intent(MainActivity.this,GameOver.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                cells[finalI][finalJ].setText();
                                cells[finalI][finalJ].disable();
                                openAll(finalI,finalJ);
                            }
                            if(openedCount + BombCount == total) {
                                Toast.makeText(getApplicationContext(), "YOU WIN!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, MainMenu.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });
                cells[i][j].button.setOnLongClickListener(new View.OnLongClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public boolean onLongClick(View current) {
                        if(cells[finalI][finalJ].enabled && !cells[finalI][finalJ].opened){
                            if(cells[finalI][finalJ].flag) {
                                cells[finalI][finalJ].delFlag();
                                cells[finalI][finalJ].button.setBackgroundColor(Color.GRAY);
                            }
                            else {
                                cells[finalI][finalJ].setFlag();
                                cells[finalI][finalJ].button.setBackgroundColor(Color.GREEN);
                            }
                        }
                        return true;
                    }
                });
                layout.addView(cells[i][j].button);
            }
        }
    }
    public void openAll(int i, int j){
                for(int i1 = i-1; i1 < i+2; i1++){
                    for(int j1 = j-1; j1 < j+2; j1++) {
                        try {
                            if (cells[i1][j1].bombCount <= 0 && cells[i1][j1].enabled && !cells[i1][j1].hasBomb()) {
                                if ((i1 != i && j1 == j) || (i1 == i && j1 != j)) {
                                    try {
                                        cells[i1][j1].setText();
                                        cells[i1][j1].disable();
                                        openAll(i1, j1);
                                    } catch (Exception a) {
                                    }
                                    try {
                                        if (cells[i1][j1 + 1].enabled && !cells[i1][j1 + 1].hasBomb()) {
                                            cells[i1][j1 + 1].setText();
                                            cells[i1][j1 + 1].disable();
                                        }
                                    } catch (Exception a) {
                                    }
                                    try {
                                        if (cells[i1][j1 - 1].enabled && !cells[i1][j1 - 1].hasBomb()) {
                                            cells[i1][j1 - 1].setText();
                                            cells[i1][j1 - 1].disable();
                                        }
                                    } catch (Exception a) {
                                    }
                                    try {
                                        if (cells[i1 + 1][j1].enabled && !cells[i1 + 1][j1].hasBomb()) {
                                            cells[i1 + 1][j1].setText();
                                            cells[i1 + 1][j1].disable();
                                        }
                                    } catch (Exception a) {
                                    }
                                    try {
                                        if (cells[i1 - 1][j1].enabled && !cells[i1 - 1][j1].hasBomb()) {
                                            cells[i1 - 1][j1].setText();
                                            cells[i1 - 1][j1].disable();
                                        }
                                    } catch (Exception a) {
                                    }
                                }
                            }
                        } catch (Exception a){}
                    }
                }
    }










}