package com.example.testapp1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class QRsGridActivity extends AppCompatActivity {
    int gameIndex;
    GridView gridView;
    ArrayList<Bitmap> list;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrs_grid);
        initializeVariables();
        setOnClicks();
    }

    private void setOnClicks() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAllQRs(gameIndex, list);
            }
        });
    }

    private void initializeVariables() {
        save = findViewById(R.id.button_saveQrs);
        gameIndex = getIntent().getIntExtra("index", 0);
        list = createQRsList(gameIndex);
        gridView = findViewById(R.id.GridView_Qrs);
        GridAdapter adapter = new GridAdapter(this, list);
        gridView.setAdapter(adapter);
    }

    private ArrayList<Bitmap> createQRsList(int index) {
        String firstTask;
        String secondTask;
        String qrText;

        ArrayList<Bitmap> list = new ArrayList<>();

        int size = MainActivity.getGames().get(index).getTasksCount();

        MultiFormatWriter writer = new MultiFormatWriter();
        BarcodeEncoder encoder = new BarcodeEncoder();

        for (int i = 0; i < size-1; i++) {
            firstTask = MainActivity.getGames().get(index).getTasks().get(i).getTask();
            secondTask = MainActivity.getGames().get(index).getTasks().get(i+1).getTask();
            qrText = firstTask + "133719841337" + secondTask;

            try {
                byte[] data = qrText.getBytes("UTF-8");
                String base64 = Base64.encodeToString(data, Base64.DEFAULT);
                BitMatrix bitMatrix = writer.encode(base64, BarcodeFormat.QR_CODE, 500, 500);
                Bitmap bitmap = encoder.createBitmap(bitMatrix);
                list.add(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        firstTask = MainActivity.getGames().get(index).getTasks().get(size-1).getTask();
        secondTask = "88005553535";
        qrText = firstTask + "133719841337" + secondTask;

        try {
            byte[] data = qrText.getBytes("UTF-8");
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
            BitMatrix bitMatrix = writer.encode(base64, BarcodeFormat.QR_CODE, 500, 500);
            Bitmap bitmap = encoder.createBitmap(bitMatrix);
            list.add(bitmap);

            data = "88005553535".getBytes("UTF-8");
            base64 = Base64.encodeToString(data, Base64.DEFAULT);
            bitMatrix = writer.encode(base64, BarcodeFormat.QR_CODE, 500, 500);
            bitmap = encoder.createBitmap(bitMatrix);
            list.add(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void saveAllQRs(int gameIndex, ArrayList<Bitmap> qrList) {
        String name = MainActivity.getGames().get(gameIndex).getName().replaceAll(" ", "");
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + name);
        if(dir.exists() && dir.isDirectory()) {

        } else {
            dir = new File(Environment.getExternalStorageDirectory(), name);
            dir.mkdir();
        }
        int i = 1;
        String postfix = ".jpg";
        try {
            for (Bitmap bitmap : qrList) {
                String imageName = name + "_" + i + postfix;

                File file = new File(dir, imageName);
                if (file.exists()) file.delete();
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
                    i++;
                }
            showSimpleDialog("Фотографии успешно сохранены на устройстве.");
        } catch (Exception e) {
            e.printStackTrace();
            showSimpleDialog("что-то пошло не по плану");
        }
    }

    private void showSimpleDialog(String message) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.simple_dialog_layout);
        TextView textMessage = dialog.findViewById(R.id.textView_simpleDialog_message);
        textMessage.setText(message);
        Button ok = dialog.findViewById(R.id.button_simpleDialog_Ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
