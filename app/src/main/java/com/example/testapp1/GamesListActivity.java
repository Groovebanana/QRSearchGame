package com.example.testapp1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class GamesListActivity extends AppCompatActivity {
    ListView listView;
    ImageButton crGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        initializeVariables();
        setOnClicks();
        refreshGamesList();
    }

    private void initializeVariables() {
        crGame = findViewById(R.id.button_createGame);
        listView = findViewById(R.id.listView_gamesList);
    }

    private void setOnClicks() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OptionsOnItemDialog(position);
            }
        });

        crGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateGameActivity();
            }
        });
    }

    private void openCreateGameActivity() {
        Intent intent = new Intent(this, CreateGameActivity.class);
        startActivity(intent);
    }

    void refreshGamesList() {

        GamesInArrayListAdapter adapter = new GamesInArrayListAdapter(GamesListActivity.this, MainActivity.games);
        listView.setAdapter(adapter);
    }

    private void OpenTasksActivity() {
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }

    private void OpenQRsGridActivity(int index) {
        Intent intent1 = new Intent(this, QRsGridActivity.class);
        intent1.putExtra("index",index);
        startActivity(intent1);
    }

    private void OptionsOnItemDialog(final int index) {
        String name = MainActivity.games.get(index).getName();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.gameslist_dialog_layout);
        TextView message = dialog.findViewById(R.id.textView_GamesList_dialog_gameName);
        message.setText(name);
        Button show = dialog.findViewById(R.id.button_gamesListDialog_showQR);
        Button delete = dialog.findViewById(R.id.button_gamesListDialog_delete);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenQRsGridActivity(index);
                dialog.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.games.remove(index);
                refreshGamesList();
                saveData();
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
