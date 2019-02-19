package com.example.testapp1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class PlayGameActivity extends AppCompatActivity {

    Button scan;
    TextView task;
    String firstTask;
    String secondTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        initializeVariables();
        setOnClicks();
    }

    private void setOnClicks() {
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scan();
            }
        });
    }

    private void initializeVariables() {
        scan = findViewById(R.id.button_Scan);
        task = findViewById(R.id.textView_PlayGame_Task);
        firstTask = null;
        secondTask = null;
    }

    private void Scan() {
        IntentIntegrator integrator = new IntentIntegrator(PlayGameActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Сканирование");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents()!=null) {
                String strResult = result.getContents();
                byte[] inputData = Base64.decode(strResult, Base64.DEFAULT);
                String finalResult;
                try {
                    finalResult = new String(inputData, "UTF-8");
                    checkResult(finalResult);
                } catch (UnsupportedEncodingException e) {
                    Toast.makeText(this, "Упс, что то пошло не так :с", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Вы вышли из сканирования", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void checkResult(String inputResult) {

        if (inputResult.contains("133719841337")) {
            String[] FSTasks = inputResult.split("133719841337");
            if (task.getText().toString().equals("В данный момент у вас нет активных задач, просканируйте первый qr код")) {
                firstTask = FSTasks[0];
                secondTask = FSTasks[1];
                task.setText(firstTask);
            } else {
                if (FSTasks[0].equals(secondTask)) {
                    firstTask = FSTasks[0];
                    secondTask = FSTasks[1];
                    task.setText(firstTask);
                    showCorrectDialog();
                } else {
                    showWrongDialog();
                }
            }
        } else if (inputResult.equals("88005553535")) {
            if (secondTask == null){
                showWrongDialog();
            } else {
                if (secondTask.equals("88005553535")) {
                    firstTask = null;
                    secondTask = null;
                    task.setText("В данный момент у вас нет активных задач, просканируйте первый qr код");
                    showVictoryDialog();
                } else {
                    showWrongDialog();
                }
            }

        }else {
            showWrongDialog();
        }
    }

    private void showCorrectDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.qr_answer_layout);
        dialog.show();
    }

    private void showVictoryDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.qr_answer_layout);
        TextView textView = dialog.findViewById(R.id.textView_correctAnswer);
        textView.setText("ВЫ ПОБЕДИЛИ !");
        dialog.show();
    }

    private void showWrongDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.wrong_answer_dialog);
        dialog.show();
    }
}
