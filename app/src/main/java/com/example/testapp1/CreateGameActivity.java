package com.example.testapp1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CreateGameActivity extends AppCompatActivity {

    Button button_creNGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        initializeVariables();
        setOnClicks();
    }

    private void setOnClicks() {
        button_creNGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CreateNewGame();
                    saveData();
                    OpenTasksActivity();
                    finish();
                } catch (EmptyFieldsException e) {
                    emptyFieldsDialog();
                }
            }
        });
    }

    private void initializeVariables() {
        button_creNGame = findViewById(R.id.button_createNewGame);
    }

    private void OpenGamesListActivity() {
        Intent intent = new Intent(this, GamesListActivity.class);
        startActivity(intent);
    }

    private  void OpenTasksActivity() {
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }

    private void CreateNewGame() throws EmptyFieldsException {
        EditText name = (EditText) findViewById(R.id.editText_create_game_name);
        String strName = name.getText().toString();
        EditText description = (EditText) findViewById(R.id.editText_create_game_description);
        String strDesc = description.getText().toString();
        if (strName!=null && !strName.equals("")) {
            MainActivity.games.add(new GameClass(strName, strDesc));
        } else {
            throw new EmptyFieldsException();
        }
    }

    private void emptyFieldsDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.simple_dialog_layout);
        TextView textMessage = dialog.findViewById(R.id.textView_simpleDialog_message);
        textMessage.setText("Пожалуйста, заполните, как минимум, поле \"Имя\"");
        Button ok = dialog.findViewById(R.id.button_simpleDialog_Ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
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
