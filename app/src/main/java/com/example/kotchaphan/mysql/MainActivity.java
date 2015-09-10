package com.example.kotchaphan.mysql;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    EditText edtMajor, edtName, edtTel;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnInsert = (Button) findViewById(R.id.btnInsert);
        edtMajor = (EditText) findViewById(R.id.edtMajor);
        edtTel = (EditText) findViewById(R.id.edtTel);
        edtName = (EditText) findViewById(R.id.edtName);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InsertAsync().execute("http://192.168.13.1/php-service/insert.php");
            }
        });
    }

    class InsertAsync extends AsyncTask<String, Void, Void> {

        String name = edtName.getText().toString();
        String tel = edtTel.getText().toString();
        String major = edtMajor.getText().toString();
        ProgressDialog progressDialog;
        String isSucess = "0";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Pleas wait...");
            progressDialog.setTitle("Warnig");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormEncodingBuilder()
                    .add("txtName", name)
                    .add("txtTel", tel)
                    .add("txtMajor", major).build();

            Request request = new Request.Builder().url(params[0])
                    .post(body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String result = response.body().string();

                JSONObject jsonObject = new JSONObject(result);

                isSucess = jsonObject.getString("success");

                Log.d("isSuccess", isSucess);

            } catch (Exception ex) {
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            edtMajor.setText("");
            edtTel.setText("");
            edtName.setText("");
            edtName.hasFocus();
            if (isSucess.equals("1")) {
                Snackbar snackbar = Snackbar.make(coordinatorLayout , "Success" , Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        }
    }


}
