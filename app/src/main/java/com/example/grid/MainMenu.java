package com.example.grid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {
    static int bombs = 5;
    static int difficulty = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        Button play = findViewById(R.id.PLAY);
        RadioButton many = findViewById(R.id.MANY);
        RadioButton average = findViewById(R.id.AVERAGE);
        RadioButton few = findViewById(R.id.FEW);
        RadioButton easy = findViewById(R.id.EASY);
        RadioButton medium = findViewById(R.id.MEDIUM);
        RadioButton hard = findViewById(R.id.HARD);
        few.setChecked(true);
        easy.setChecked(true);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(many.isChecked()) bombs = -2;
                else if(average.isChecked()) bombs = -1;
                else if(few.isChecked()) bombs = 0;
                if(easy.isChecked()) {
                    difficulty = 0;

                }
                else if(medium.isChecked()) {
                    difficulty = 4;
                }
                else if(hard.isChecked()) {
                    difficulty = 10;
                }

                Intent intent = new Intent(MainMenu.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
