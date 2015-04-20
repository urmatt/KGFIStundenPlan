package student.kgfi.kgfistundenplan;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Urma on 13-Apr-15.
 */
public class JSONRequest {
    static InputStream inputStream = null;
    static JSONObject jsonObject = null;
    static String jsonString = null;

    ArrayList<String> aList;
    ArrayList<HashMap<String, String>> table = new ArrayList<HashMap<String, String>>();

    public static enum Method {GET, POST}

    ;

    public JSONRequest() {
    }

    /**
     *
     * @param url
     * @param method
     * @param columnNames names of Columns that you want get
     * @param arrayName Name of array from JSON
     * @return ArrayList<HashMap<name Of Colmn:String, Value: String>>
     */
    public ArrayList<HashMap<String,String>> getAsList(String url, Method method, String arrayName, String[] columnNames) {
        aList = new ArrayList<String>();
        ArrayList<NameValuePair> params = new ArrayList<>();
        try {
            JSONArray array = httpRequest(url, method, params).getJSONArray(arrayName);
            for(int i = 0; i< array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                HashMap<String, String> row = new HashMap<String, String>();
                for(String na : columnNames){
                    row.put(na, object.getString(na));
                }
                table.add(row);
            }
        } catch (JSONException ex) {

        }
        return table;
    }

    public static JSONObject httpRequest(String url, Method method, List<NameValuePair> params) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;

            if (method == Method.POST) {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                httpResponse = httpClient.execute(httpPost);
            } else if (method == Method.GET) {
                String paramsString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramsString;
                HttpGet httpGet = new HttpGet(url);
                httpResponse = httpClient.execute(httpGet);
            }
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            /**
             * Getting String from InputStream width BufferedReader
             */
            BufferedReader bfReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bfReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            inputStream.close();
            jsonString = stringBuilder.toString();

            /**
             * Getting JSONObject from result String
             */

            jsonObject = new JSONObject(jsonString);
        } catch (UnsupportedEncodingException ueEx) {
            ueEx.printStackTrace();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } catch (JSONException jsonEx) {
            jsonEx.printStackTrace();
            Log.e("JSONParser", "Error parsing data " + jsonEx.toString());
        }
        return jsonObject;
    }
}
