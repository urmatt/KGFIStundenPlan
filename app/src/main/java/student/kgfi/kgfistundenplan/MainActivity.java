package student.kgfi.kgfistundenplan;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.GridLayout;
import android.widget.Button;
import android.graphics.drawable.Drawable;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity {


    GridLayout gLayout;
    ArrayList<String> list = new ArrayList<>();
    HashMap<Integer, Button> buttonsMap = new HashMap<Integer, Button>();
    DisplayMetrics displayMetrics = null;
    WebView mainWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gLayout = (GridLayout) findViewById(R.id.mainGrid);
        mainWeb = (WebView) findViewById(R.id.mainWeb);
        mainWeb.loadUrl("http://urmapps.esy.es/mykgfi/getNews.php?group=IG-1-11&news_count=2");
        displayMetrics = getResources().getDisplayMetrics();
        new LoadCategories().execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoadCategories extends AsyncTask<String, String, String> {

        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            try {
                // getting JSON string from URL
                JSONObject jsonObject = JSONRequest.httpRequest("http://urmapps.esy.es/mykgfi/getGroups.php", JSONRequest.Method.POST, params);
                JSONArray jsonArray = jsonObject.getJSONArray("groups");
                Log.d("", jsonArray.toString());
                //
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject oneObject = jsonArray.getJSONObject(i);
                    String name = oneObject.getString("g_name");
//                    Log.d("Categories Name ---", name);
                    list.add(name);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Creating & adding buttons

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            int widthForB;
            int displayW = displayMetrics.widthPixels;
            widthForB = displayW/3 - 30;
            int ids = new Integer(getTaskId());



            for(int i = 0; i < list.size(); i++){
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(gLayout.getLayoutParams());
                params.setMargins(12,12,12,12);
                Button b = new Button(MainActivity.this);
                b.setText(list.get(i));
                b.setClickable(true);
                b.setSelected(true);
                b.setSingleLine();
                b.setTextSize(20);
                b.setLayoutParams(params);
                b.setId(ids);
                b.setTextColor(Color.parseColor("#ffffff"));
                b.setBackgroundResource(R.drawable.main_button);
                b.setWidth(widthForB);
                b.setHeight(widthForB);
                gLayout.addView(b);
                buttonsMap.put(ids, b);
                ids++;
            }

            pDialog.dismiss();
            //////-----------------
            super.onPostExecute(s);
        }
    }
}
