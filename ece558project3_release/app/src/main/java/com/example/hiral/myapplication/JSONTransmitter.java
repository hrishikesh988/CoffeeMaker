package com.example.hiral.myapplication;
import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

/**
 * Created by Dheeraj chand V on 8/31/2016.
 */
public class JSONTransmitter extends AsyncTask <JSONObject,JSONObject, JSONObject > {


    /**The url is specific to each team to connect it to server
     * you have to use the one assigned to your team
     * Edit the url string " http://76.105.208.49:8080/Your Team Name.php"
     */
    String url = "http://76.105.208.49:8080/Your_Team_Name.php";

    @Override
    protected JSONObject doInBackground(JSONObject... data) {
        JSONObject json = data[0];
		//Creating client object to setConnectionTimeout
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);

        JSONObject jsonResponse = null;
		//Post the url to the server
        HttpPost post = new HttpPost(url);
        try {
            StringEntity se = new StringEntity("json=" + json.toString());
			//sets headder of json to mimic a browser request
            //Use this header for posting http data to the server.
            post.addHeader("content-type", "application/x-www-form-urlencoded");
            post.setEntity(se);
            HttpResponse response;
			//After verification it executes the url posted
            response = client.execute(post);
            //getting response from the server
            String resFromServer = org.apache.http.util.EntityUtils.toString(response.getEntity());
            // store the response as a json object
            jsonResponse = new JSONObject(resFromServer);
            return jsonResponse;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonResponse;
    }
}
