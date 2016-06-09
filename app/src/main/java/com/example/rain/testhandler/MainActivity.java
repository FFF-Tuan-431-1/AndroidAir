package com.example.rain.testhandler;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
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

public class MainActivity extends AppCompatActivity {

    TextView textView, textUrl;
    EditText editText;
    Button loginbutton;
    static public OkHttpClient publicOkHttpClient;
    static public Customer publicCustomer;
    OkHttpClient mOkHttpClient;
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static public String hUrl = "http://10.206.9.80:3000/api";
    String hLogin = "/login";
    String hTemperature = "/temperature";
    String hSetting = "/settings";


    Gson gson = new Gson();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.rain.testhandler/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.rain.testhandler/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class CalThread extends Thread {
        public Handler myHandler;

        public void run() {
            Looper.prepare();
            myHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 0x134) {
                        Toast.makeText(MainActivity.this, "login!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, WorkActivity.class);
                        /*Bundle bundle = new Bundle();
                        bundle.putString("data", msg.getData().getString("data"));
                        intent.putExtras(bundle);*/
                        startActivity(intent);

                        /*final Request request = new Request.Builder()
                                .url(hUrl+hTemperature)
                                .build();

                        Call call = mOkHttpClient.newCall(request);
                        call.enqueue(new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                               // Toast.makeText(MainActivity.this, "temperature fail ", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                //Temperature temperature = gson.fromJson(response.body().string(), Temperature.class);
                                //Toast.makeText(MainActivity.this, "temperature: " + temperature.getTemperature(), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(MainActivity.this, "temperature: get", Toast.LENGTH_SHORT).show();
                                Message msg = new Message();
                                msg.what = 0x124;
                                Bundle bundle = new Bundle();
                                //Type listType = new TypeToken<LinkedList<Customer>>(){}.getType();
                                //Customer customer = gson.fromJson(response.body().string(), Customer.class);
                                bundle.putString("data", "temperature: get" + response.body().string());
                                //bundle.putString("data", response.body().string());
                                msg.setData(bundle);
                                netThread.myHandler.sendMessage(msg);

                            }
                        });*/
                        //Toast.makeText(MainActivity.this, msg.getData().getString("data"), Toast.LENGTH_SHORT).show();
                    }
                    else if(msg.what == 0x110) {
                        hUrl = "http://" + msg.getData().getString("data") + "/api";
                        Toast.makeText(MainActivity.this, hUrl, Toast.LENGTH_SHORT).show();

                    }
                }
            };
            Looper.loop();
        }
    }//0x124



    class NetThread extends Thread {
        public Handler myHandler;

        public void run() {
            Looper.prepare();
            myHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 0x123) {
                        mOkHttpClient = new OkHttpClient.Builder().cookieJar(new CookieJar() {
                            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
                            //Tip：這裡key必須是String
                            @Override
                            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                                cookieStore.put(url.host(), cookies);
                            }
                            @Override
                            public List<Cookie> loadForRequest(HttpUrl url) {
                                List<Cookie> cookies = cookieStore.get(url.host());
                                return cookies != null ? cookies : new ArrayList<Cookie>();
                            }
                        }).build();

                        publicOkHttpClient = mOkHttpClient;

                        Login login = new Login(msg.getData().getString("data"));
                        String loginpass = gson.toJson(login);

                        RequestBody body = RequestBody.create(JSON, loginpass);
                        Request request = new Request.Builder().url(hUrl+hLogin).post(body).build();
                        Call call = mOkHttpClient.newCall(request);
                        // 开启异步线程访问网络
                        call.enqueue(new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Message msg = new Message();
                                msg.what = 0x134;
                                Bundle bundle = new Bundle();
                                bundle.putString("data", "fail");
                                msg.setData(bundle);
                                calThread.myHandler.sendMessage(msg);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if(response.isSuccessful()){
                                    Message msg = new Message();
                                    msg.what = 0x134;
                                    Bundle bundle = new Bundle();
                                    //Type listType = new TypeToken<LinkedList<Customer>>(){}.getType();
                                    Customer customer = gson.fromJson(response.body().string(), Customer.class);
                                    publicCustomer = customer;
                                    bundle.putString("data", "name:" + customer.getName() + " room:" + customer.getRoom());
                                    //bundle.putString("data", response.body().string());
                                    msg.setData(bundle);
                                    calThread.myHandler.sendMessage(msg);
                                }
                                else{
                                    Message msg = new Message();
                                    msg.what = 0x125;
                                    netThread.myHandler.sendMessage(msg);
                                }


                            }
                        });
                    }
                    else if(msg.what == 0x124){
                        Toast.makeText(MainActivity.this, msg.getData().getString("data"), Toast.LENGTH_SHORT).show();
                    }
                    else if(msg.what == 0x125){
                        Toast.makeText(MainActivity.this, "password is wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            Looper.loop();
        }
    }//0x123

    CalThread calThread;
    NetThread netThread;
    com.gc.materialdesign.views.ButtonRectangle loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        textUrl = (TextView) findViewById(R.id.hostUrlbtn);
        //loginbtn = (ButtonRectangle) findViewById(R.id.loginbtn);
        loginbtn = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.loginbtn);
        editText = (EditText) findViewById(R.id.password);

        textUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = View.inflate(MainActivity.this, R.layout.httpurl, null);
                Toast.makeText(MainActivity.this, "edit", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.ic_action_new)
                        .setTitle("Edit host url")
                        .setView(view)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText temp = (EditText) view.findViewById(R.id.httpUrl);
                                Message msg = new Message();
                                msg.what = 0x110;
                                Bundle bundle = new Bundle();
                                bundle.putString("data", temp.getText().toString());
                                msg.setData(bundle);
                                calThread.myHandler.sendMessage(msg);
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();

            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 0x123;
                Bundle bundle = new Bundle();
                String password = editText.getText().toString();
                bundle.putString("data", password);
                msg.setData(bundle);
                netThread.myHandler.sendMessage(msg);
                //String password = editText.getText().toString();
                //textView.setText(password);
            }
        });


        calThread = new CalThread();
        // while(true)
        calThread.start();

        netThread = new NetThread();
        netThread.start();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
}
