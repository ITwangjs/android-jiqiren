package com.example.administrator.jiqiren;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/8/7.
 */

public class httpthread extends Thread {

   private  static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String API_KEY = "df4d241cb6864cc1ba59aef3398ffa10";

    private Handler handler;
    private String intput;

    public httpthread (Handler handlers ,String intputs){
        this.handler =handlers;
        this.intput =intputs;
    }

    @Override
    public void run() {
        String urlstring = setParams(intput);
//        InputStream is = null;
//        ByteArrayOutputStream baos = null;

        try {
            java.net.URL url =new URL(urlstring);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
/*
* 两个方法都行的
* */
//            is = conn.getInputStream();
//            baos = new ByteArrayOutputStream();
//            int len = -1;
//            byte[] buff = new byte[1024];
//            while ((len = is.read(buff)) != -1) {
//                baos.write(buff, 0, len);
//            }
//            baos.flush();
//         String result = new String(baos.toByteArray());

//            Message msg = new Message();
//            msg.what = 0;
//            msg.obj = result;
//            handler.sendMessage(msg);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            StringBuffer jsondata = new StringBuffer();
            while ((str = reader.readLine()) != null) {
                jsondata.append(str);
            }
            Message msg = new Message();
            msg.what = 0;
            msg.obj = jsondata.toString();
            handler.sendMessage(msg);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //请求的连接
    private static String setParams(String msg)
    {
        String url = "";
        try
        {
            url = URL + "?key=" + API_KEY + "&info="
                    + URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return url;
    }

}
