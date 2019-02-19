package com.example.testapp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button buttonMyGames;
    Button play;

    public static ArrayList<GameClass> games;

    public static ArrayList<GameClass> getGames() {
        return games;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        initializeVariables();
        setOnClicks();
    }

    private void initializeVariables() {
        if (games == null) {
            games = new ArrayList<>(30);
        }
        buttonMyGames = findViewById(R.id.button_MyGames);
        play = findViewById(R.id.buttonPlay);
    }

    private void setOnClicks() {
        buttonMyGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGamesListActivity();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayGameActivity();
            }
        });
    }

    private void openPlayGameActivity() {
        Intent intent = new Intent(this, PlayGameActivity.class);
        startActivity(intent);
    }

    private void openCreateGameActivity() {
        Intent intent = new Intent(this, CreateGameActivity.class);
        startActivity(intent);
    }

    private void openGamesListActivity() {
        Intent intent = new Intent(this, GamesListActivity.class);
        startActivity(intent);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("games list", null);
        Type type = new TypeToken<ArrayList<GameClass>>() {}.getType();
        games = gson.fromJson(json,type);
    }






}
