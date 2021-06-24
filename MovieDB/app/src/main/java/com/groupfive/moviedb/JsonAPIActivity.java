package com.groupfive.moviedb;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonAPIActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    private static String url = "https://api.androidhive.info/contacts";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_api);

        contactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.listview);

        new GetContact().execute();
    }

    private  class GetContact extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            // Showing progress dialog

            pDialog = new ProgressDialog(JsonAPIActivity.this);
            pDialog.setMessage("Please wait");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0){
            HttpHandler sh = new HttpHandler();

            // Membuat sebuah request ke url dan mendapatkan respon
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Respon dari URL: "+jsonStr);

            if (jsonStr != null){
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Mendapatkan JSON array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // perulangan melalui/untuk semua kontak
                    for (int i = 0; i<contacts.length(); i++){
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        // phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e){
                    Log.e(TAG, "Json parsing error:" + e.getMessage());
                    runOnUiThread(new Runnable(){
                       @Override
                       public void run(){
                           Toast.makeText(getApplicationContext(),
                                   "Json Parsing errpr: "+ e.getMessage(),
                                   Toast.LENGTH_LONG).show();
                       }
                    });
                }
            } else {
                Log.e(TAG, "Tidak bisa ambil json dari server");
                runOnUiThread(new Runnable(){
                   @Override
                   public void run(){
                       Toast.makeText(getApplicationContext(),
                               "Tidak bisa ambil json dari server, cek LogCat untuk kemungkinan error",
                               Toast.LENGTH_LONG).show();
                   }
                });
            }
            return null;
        }

        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            // Memberhentikan progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            // Updateing parsed Json data into listview
            ListAdapter adapter = new SimpleAdapter(
                    JsonAPIActivity.this, contactList,
                    R.layout.list_item, new String[]{"name", "email", "mobile"},
                    new int[]{R.id.name, R.id.email, R.id.mobile});
            lv.setAdapter(adapter);
        }
    }
}
