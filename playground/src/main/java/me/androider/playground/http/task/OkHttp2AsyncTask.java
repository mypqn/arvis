package me.androider.playground.http.task;

import android.os.AsyncTask;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Response;

import java.io.IOException;


/**
 * created by Androider on 2019/7/16 14:40
 */
public class OkHttp2AsyncTask extends AsyncTask<Call, Integer, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Call... calls) {
        try {
            Response respose = calls[0].execute();
            if (respose.isSuccessful()) {
                return respose.body().string();
            } else {
                return respose.message();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
