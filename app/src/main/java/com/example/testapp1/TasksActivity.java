package com.example.testapp1;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class TasksActivity extends AppCompatActivity {
    Button next;
    Button done;
    EditText task;
    boolean isSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        initializeVariables();
        setOnClicks();
    }

    private void setOnClicks() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
                task.setText("");
                isSaved = false;
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
                saveData();
                OpenGamesListActivity();
                finish();
            }
        });
    }

    private void initializeVariables() {
        next = findViewById(R.id.button_nextTask);
        done = findViewById(R.id.button_Done);
        task = findViewById(R.id.editText_Task);
    }


    private void OpenGamesListActivity() {
        Intent intent = new Intent(this, GamesListActivity.class);
        startActivity(intent);
    }



    private void saveTask() {
        if (isSaved == false && task.getText().toString().length() > 0) {
            MainActivity.games.get(MainActivity.games.size()-1).AddTask(new TasksClass(task.getText().toString()));
        }
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.games);
        editor.putString("games list", json);
        editor.apply();
    }
}
