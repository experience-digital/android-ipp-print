package com.openproject.xiaojie.android_ipprint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.openproject.xiaojie.android_ipprint.print.PrintTask;
import com.openproject.xiaojie.android_ipprint.print.PrintUtil;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static com.openproject.xiaojie.android_ipprint.print.PrintTask.PRINT_COVER_PAPER_ERROR;
import static com.openproject.xiaojie.android_ipprint.print.PrintTask.PRINT_FAILURE;
import static com.openproject.xiaojie.android_ipprint.print.PrintTask.PRINT_OPEN_COVER;
import static com.openproject.xiaojie.android_ipprint.print.PrintTask.PRINT_PAPER_SHORT;
import static com.openproject.xiaojie.android_ipprint.print.PrintTask.PRINT_SUCCESS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PrintTask.PrintResult {

    private PrintUtil printUtil;
    private EditText ipAddress;
    private EditText printText;
    private Button serIp;
    private String ip;
    private Button connectTest;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyToast.init(this);
        printUtil = new PrintUtil();
        ipAddress = (EditText) findViewById(R.id.ip_address);
        printText = (EditText) findViewById(R.id.print_text);
        serIp = (Button) findViewById(R.id.set_ip);
        connectTest = (Button) findViewById(R.id.connect_test);
        serIp.setOnClickListener(this);
        connectTest.setOnClickListener(this);

    }

//    private void initData() {
//        data = new LinkedList<>();
//        for (int i = 0; i < 10; i++) {
//            data.add("TEST " + (i + 1) + "ONE");
//        }
//    }

    private void printSync() {
        //??????????????????????????????????????????????????????????????????????????????????????????????????????WiFi ??????????????????
//        new PrintTask(ip, this).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, data);
    }

    @Override
    public void onClick(View view) {
        ip = ipAddress.getText().toString();

        Toast.makeText(this, "Printing...", Toast.LENGTH_SHORT).show();
        printThread();

//        switch (view.getId()) {
//            case R.id.set_ip:
//                ip = ipAddress.getText().toString();
//                Toast.makeText(this, "IP been SET! to: " + ip, Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.connect_test: //?????????????????????????????????
//                Toast.makeText(this, "Printing...", Toast.LENGTH_SHORT).show();
////                printSync();
//
//                printThread();
//
//                break;
//        }
    }

    private void printThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                Looper.prepare();   //??????????????????handler
                data = printText.getText().toString();
                try {
                    printUtil.open(ip);
                    printUtil.set();
                    printUtil.printTextNewLine(data);
//                    printUtil.printQrCode("123123").printEnter().getStatus();
//                    printUtil.printBarCode("32167");
//                    printUtil.CutPage(4);
                    printUtil.Close();
                } catch (IOException e) {
                    Log.e("MainActivity", "run (93): IO??????", e);
                }
                Looper.loop();
            }
        };
        thread.start();
    }

    @Override
    public void onPrintResult(int result) {
//        switch (result) {
//            case PRINT_SUCCESS:
//                MyToast.showShort("???????????????");
//                break;
//            case PRINT_FAILURE:
//                showFailureDialog("??????????????????????????????");
//                break;
//            case PRINT_OPEN_COVER:
//                showFailureDialog("??????????????????????????????????????????????????????");
//                break;
//            case PRINT_PAPER_SHORT:
//                showFailureDialog("????????????????????????????????????");
//                break;
//            case PRINT_COVER_PAPER_ERROR:
//                showFailureDialog("??????????????????????????????????????????????????????");
//                break;
//            default:
//                showFailureDialog("?????????????????????????????????????????????");
//                break;
//        }
    }

    private void showFailureDialog(String msg) {
        new AlertDialog.Builder(this)
                .setTitle(msg)
                .setMessage("??????????????????????????????")
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        printSync();
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create().show();
    }
}
