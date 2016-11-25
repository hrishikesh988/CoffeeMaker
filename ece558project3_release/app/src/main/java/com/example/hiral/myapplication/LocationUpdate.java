package com.example.hiral.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * LocationUpdate.java handles the UI appearing on Sign In
 *
 */
public class LocationUpdate extends AppCompatActivity{

    private EditText send_message;
    private Button send;
    int Place_Picker_request=1;
    Editable message;
    LoginDataBaseAdapter loginData;
    public double latitude,longitude,modalat,modalon;


    public static Intent newIntent(Context packageContext, String username) {
        Intent i = new Intent( packageContext, LocationUpdate.class);
        i.putExtra("tx_user_name", username);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_update);
        send_message = (EditText) findViewById(R.id.Send_Message);


        /**
         * TODO: Define onClick Listener for "get_location" text box to get user's current location
         * Hint: Need to define IntentBuilder for PlacePicker built-in UI widget
         * Reference : https://developers.google.com/places/android-api/placepicker
         */

        /**
         * TODO: Define TextView field to display the address of current location on the UI
         *
         */

        /**
         * TODO: Define onClick Listener for "Current_Location Button"
         * Hint: OnClick event should set the text for TextView field defined above
         */


        /**
         * Do not edit the code below as it is dependent on server just fill the required snippets
         *
         */
        send_message = (EditText) findViewById(R.id.Send_Message);
        send = (Button) findViewById(R.id.Send_Button);
        loginData = new LoginDataBaseAdapter(this);
        loginData = loginData.open();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * OnClick event for send button gets username and location details
                 */
                message=send_message.getText();
                Bundle extras=getIntent().getExtras();
                String rx_username=extras.getString("tx_user_name");

                /**
                 * TODO: Enable the code below after defining getLat() and getLng()
                 * TODO: methods in LoginDataBaseAdapter
                 */
                //String rx_lat=loginData.getLat(rx_username);
                //String rx_lon=loginData.getLng(rx_username);

                /**
                 * store in latitude , longitude variables to pass to json object
                 */
                modalat=Double.parseDouble("Pass Latitude over here named:rx_lat");
                modalon=Double.parseDouble("Pass Longitude over here named:rx_lon");

                try {

                    /**
                     * Creates a JSON object and uses toSend.put to send home, current location along with message
                     *Pass data as name/value pair where you cannot edit name written
                     *in " " ex:"home_lat" as this are hard coded on server side.
                     *You can change the variable name carrying value ex:modalat
                     */
                    JSONObject toSend = new JSONObject();
                    toSend.put("home_lat", modalat);
                    toSend.put("home_lon",modalon);
                    toSend.put("c_lat", latitude);
                    toSend.put("c_lon",longitude);
                    toSend.put("message",message);

                    /**
                     * Creates transmitter object to send data to server
                     */
                    JSONTransmitter transmitter = new JSONTransmitter();
                    transmitter.execute(new JSONObject[] {toSend});

                    /**
                     * Receives a message from the server which is displayed as toast
                     */
                    JSONObject output=transmitter.get();
                    String op=output.getString("message");
                    Toast.makeText(LocationUpdate.this,op, Toast.LENGTH_LONG).show();

                }
                //To handle exceptions
                catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                catch (ExecutionException e)
                {
                    e.printStackTrace();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * TODO: Define onActivityResult() method which would take Place_Picker_request
     * and extract current Latitude, Longitude and address string
     * Hint : Set the address String to "get_location" text box
     * Reference : https://developers.google.com/places/android-api/placepicker
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

