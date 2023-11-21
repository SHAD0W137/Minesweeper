package com.example.grid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.zip.Inflater;

public class Cell {
    public boolean bomb;
    public boolean flag;
    public boolean enabled;
    public boolean opened;
    public int bombCount;
    public Button button;
    Cell(LayoutInflater inflater, GridLayout layout){
        bomb = false;
        flag = false;
        enabled = true;
        opened = false;
        bombCount = 0;
        button = (Button) inflater.inflate(R.layout.cell,layout,false);
    }
    public void setBomb(){
        this.bomb = true;
    }
    public void setFlag(){
        this.flag = true;
    }
    public void delFlag() {
        this.flag = false;
    }
    public void enable(){
        this.enabled = true;
    }
    public void disable(){
        this.enabled = false;
    }
    public void open(){
        this.opened = true;
    }
    public void setBombCount(int a){
        this.bombCount = a;
    }
    public void setText(){
        this.button.setBackgroundColor(Color.WHITE);
        MainActivity.openedCount++;
        if(this.bombCount > 0) {
            this.button.setText("" + bombCount);
        }
    }
    public boolean hasBomb(){
        if(this.bomb == true) return true;
        else return false;
    }
}
