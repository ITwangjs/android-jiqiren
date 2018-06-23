package com.example.administrator.jiqiren;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView qiji_tv;
    private TextView me_tv;

    private EditText editText;
    private Button sendbtn;


    private String intput;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String obj = (String) msg.obj;
                try {
                    JSONObject jsonobject = new JSONObject(obj);
                    String output = jsonobject.getString("text");
                    Log.i("info", "handleMessage: ----");
                    qiji_tv.setText(output);
                    me_tv.setText(intput);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);

        qiji_tv = (TextView) findViewById(R.id.jiqi_tv);
        me_tv = (TextView) findViewById(R.id.me_tv);
        editText = (EditText) findViewById(R.id.editText);
        sendbtn = (Button) findViewById(R.id.sendbtn);

        sendbtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        intput = editText.getText().toString();
        editText.setText("");
        httpthread http = new httpthread(handler, intput);
        http.start();

    }
}
