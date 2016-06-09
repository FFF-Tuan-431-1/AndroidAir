package com.example.rain.testhandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.TextView;

import okhttp3.OkHttpClient;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Switch;

import com.gc.materialdesign.views.ButtonRectangle;
//import com.gc.materialdesign.views.Switch;
import com.gc.materialdesign.views.Slider;
import com.gc.materialdesign.views.Switch;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import okhttp3.*;
import okio.*;

/**
 * Created by rain on 2016/6/5.
 */

public class WorkActivity extends Activity {
    TextView textTemperature, textRoom, textName, textMode, textSpeed, textBill, textCurTem;
    OkHttpClient wOkHttpClient = MainActivity.publicOkHttpClient;
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String hUrl = MainActivity.hUrl;
    String hLogin = "/login";
    String hTemperature = "/temperature";
    String hSetting = "/settings";
    String hBill = "/currentBill";
    NetThread netThread;
    CalThread calThread;
    Customer wCustomer = MainActivity.publicCustomer;
    Temperature wTemperature = new Temperature((float) 26.0);
    Bill bill;
    String celsius = "℃";
    Setting wSetting = new Setting(26, 0, "off" );
    Button temAdd, temsub;
    com.gc.materialdesign.views.ButtonRectangle buttonRectangle;
    Handler wHandler;
    Chronometer ch;
    com.gc.materialdesign.views.ButtonRectangle btnTemAdd, btnTemSub, btnSpeAdd, btnSpeSub, btnModeOff
                                                ,btnModedry, btnModePur, btnBill;
    com.gc.materialdesign.views.Switch wSwitch1, wSwitch2;
    com.gc.materialdesign.views.Slider slider1, slider2;

    int j = 0;
    Gson gson = new Gson();

    class CalThread extends Thread {
        public Handler myHandler;

        public void run() {
            Looper.prepare();
            myHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 0x001) {
                        //textView.setText(msg.getData().getString("data"));
                        //Toast.makeText(WorkActivity.this, "Temperature get success!", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            Looper.loop();
        }
    }//0x124

    class NetThread extends Thread{
        public Handler myHandler;
        public void run(){
            Looper.prepare();
            myHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what){
                        case  0x551:
                            final Request request = new Request.Builder()
                                    .url(hUrl + hTemperature)
                                    .build();
                            Call call = wOkHttpClient.newCall(request);
                            call.enqueue(new okhttp3.Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }
                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                    if(response.isSuccessful()){
                                        Message msg = new Message();
                                        msg.what = 0x771;
                                        Bundle bundle = new Bundle();
                                        Temperature temperature = gson.fromJson(response.body().string(), Temperature.class);
                                        wTemperature = temperature;
                                        bundle.putString("data", "" + temperature.getTemperature());
                                        msg.setData(bundle);
                                        wHandler.sendMessage(msg);
                                    }

                                }
                            });
                            break;
                        case 0x552:
                            final Request request1 = new Request.Builder()
                                    .url(hUrl + hSetting)
                                    .build();
                            Call call1 = wOkHttpClient.newCall(request1);
                            call1.enqueue(new okhttp3.Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    if(response.isSuccessful()) {
                                        Message msg = new Message();
                                        msg.what = 0x772;
                                        //Bundle bundle = new Bundle();
                                        Setting setting = gson.fromJson(response.body().string(), Setting.class);
                                        wSetting = setting;
                                        wHandler.sendMessage(msg);
                                    }
                                }
                            });
                            break;
                        case 0x553:
                            final Request request553 = new Request.Builder()
                                    .url(hUrl + hBill)
                                    .build();
                            Call call553 = wOkHttpClient.newCall(request553);
                            call553.enqueue(new okhttp3.Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    if(response.isSuccessful()) {
                                        Message msg = new Message();
                                        msg.what = 0x773;
                                        //Bundle bundle = new Bundle();
                                        Bill theBill = gson.fromJson(response.body().string(), Bill.class);
                                        bill = theBill;
                                        wHandler.sendMessage(msg);
                                    }
                                }
                            });
                            break;
                        case 0x661:
                            String set = gson.toJson(wSetting);
                            RequestBody body = RequestBody.create(JSON, set);
                            Request request661 = new Request.Builder().url(hUrl+hSetting).post(body).build();
                            Call call661 = wOkHttpClient.newCall(request661);
                            call661.enqueue(new okhttp3.Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    if(response.isSuccessful()) {
                                        Message msg = new Message();
                                        msg.what = 0x772;
                                        //Bundle bundle = new Bundle();
                                        Setting setting = gson.fromJson(response.body().string(), Setting.class);
                                        wSetting = setting;
                                        wHandler.sendMessage(msg);
                                    }
                                }
                            });
                            break;
                        case 0x662:
                            break;
                        default:
                            break;
                    }
                }
            };
            Looper.loop();
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_windows);
        textTemperature = (TextView) findViewById(R.id.wtemperature);
        textName = (TextView) findViewById(R.id.wname);
        textRoom = (TextView) findViewById(R.id.wroom);
        textMode = (TextView) findViewById(R.id.wMode);
        textSpeed = (TextView) findViewById(R.id.wspeed);
        textBill = (TextView) findViewById(R.id.billList);
        textCurTem = (TextView) findViewById(R.id.curtem);

        btnTemAdd = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.button);
        btnTemSub = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.button2);
        btnSpeAdd = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.button3);
        btnSpeSub = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.button4);
        btnBill = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.button5);
        btnModeOff = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.button6);
        btnModedry = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.button7);
        btnModePur = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.button8);

        wSwitch1 = (com.gc.materialdesign.views.Switch) findViewById(R.id.switch1);
        wSwitch2 = (com.gc.materialdesign.views.Switch) findViewById(R.id.switch2);
        slider1 = (com.gc.materialdesign.views.Slider) findViewById(R.id.sliderTem);
        slider2 = (com.gc.materialdesign.views.Slider) findViewById(R.id.sliderSpe);


        wSwitch1.setOncheckListener(new Switch.OnCheckListener() {
            @Override
            public void onCheck(Switch view, boolean check) {
                if(check) {
                    wSwitch2.setVisibility(View.VISIBLE);
                    wSetting.setMode("off");
                    if (wSetting.getSpeed() == 0)
                    wSetting.setSpeed(1);
                    wSetting.setMode("dry");
                    Message msg = new Message();
                    msg.what = 0x661;
                    netThread.myHandler.sendMessage(msg);

                }
                else {
                    wSwitch2.setVisibility(View.INVISIBLE);
                    wSetting.setSpeed(0);
                }
            }
        });

        //wSwitch1.setOnCheckedChangeListener(listener1);

        wSwitch2.setOncheckListener(new Switch.OnCheckListener() {
            @Override
            public void onCheck(Switch view, boolean check) {
                if(check) {
                    wSetting.setMode("purge");
                    Message msg = new Message();
                    msg.what = 0x661;
                    netThread.myHandler.sendMessage(msg);
                }
                else {
                    wSetting.setMode("dry");
                    Message msg = new Message();
                    msg.what = 0x661;
                    netThread.myHandler.sendMessage(msg);
                }
            }
        });

        slider1.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                Message msg = new Message();
                //textBill.setText("" + (float) s.getValue());
                wSetting.setTemperature((float) value);
                msg.what = 0x661;
                netThread.myHandler.sendMessage(msg);
            }
        });

        slider2.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                Message msg = new Message();
                wSetting.setSpeed(value);
                msg.what = 0x661;
                netThread.myHandler.sendMessage(msg);
            }
        });


        btnTemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                wSetting.temperatureAdd();
                msg.what = 0x661;
                netThread.myHandler.sendMessage(msg);
            }
        });

        btnTemSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                wSetting.temperatureSub();
                msg.what = 0x661;
                netThread.myHandler.sendMessage(msg);
            }
        });

        btnSpeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                wSetting.speedAdd();
                msg.what = 0x661;
                netThread.myHandler.sendMessage(msg);
            }
        });

        btnSpeSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                wSetting.speedSub();
                msg.what = 0x661;
                netThread.myHandler.sendMessage(msg);
            }
        });

        btnModeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wSetting.setMode("off");
                Message msg = new Message();
                msg.what = 0x661;
                netThread.myHandler.sendMessage(msg);
            }
        });

        btnModedry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wSetting.setMode("dry");
                Message msg = new Message();
                msg.what = 0x661;
                netThread.myHandler.sendMessage(msg);
            }
        });

        btnModePur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wSetting.setMode("purge");
                Message msg = new Message();
                msg.what = 0x661;
                netThread.myHandler.sendMessage(msg);
            }
        });

        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 0x553;
                netThread.myHandler.sendMessage(msg);
            }
        });

        ch = (Chronometer) findViewById(R.id.chronometer);

        wHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case  0x771:
                        textCurTem.setText("Current ℃:" + String.valueOf((float)wTemperature.getTemperature())+ "℃");
                        Message msg771 = new Message();
                        msg771.what = 0x772;
                        wHandler.sendMessage(msg771);
                        break;
                    case 0x772:
                        //String x = "" +  wTemperature.getTemperature();
                        //Toast.makeText(WorkActivity.this,String.valueOf((float) wTemperature.getTemperature()), Toast.LENGTH_SHORT).show();
                        textTemperature.setText( String.valueOf((float)wSetting.getTemperature()) + "℃");
                        textName.setText(" N:" + wCustomer.getName());
                        textRoom.setText(" R:" + wCustomer.getRoom());
                        textMode.setText("mode: " + wSetting.getMode());
                        textSpeed.setText("speed:" + wSetting.getSpeed());
                        break;
                    case 0x773:
                        textBill.setText("bill: ￥"+ String.valueOf((float)bill.totalMoney));
                        break;
                    default:
                        break;
                }
            }
        };

        ch.start();

        ch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(j == 0) {
                    Message msg = new Message();
                    msg.what = 0x552;
                    netThread.myHandler.sendMessage(msg);
                    j=1;
                }
                if(SystemClock.elapsedRealtime() - ch.getBase() > 2 * 1000){
                    ch.setBase(SystemClock.elapsedRealtime());
                    Message msg = new Message();
                    msg.what = 0x551;
                    netThread.myHandler.sendMessage(msg);
                }
            }
        });



        netThread = new NetThread();
        netThread.start();

        calThread = new CalThread();
        calThread.start();

    }
}
