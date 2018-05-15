package edu.asu.bsse.jstokke.assign1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

/*
* Copyright 2018 James Stokke
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* @author James Stokke mailto:James.Stokke@asu.edu
* @version March 20, 2018
*
* This is the first assignments MainActivity file
*/


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Serializable {
    private Spinner placeSpinner;
    private Spinner firstDistanceSpinner;
    private Spinner secondDistanceSpinner;
    private ArrayAdapter<String> adapter;
    protected PlaceLibrary currentLib;
    private int clicks = 0;
    static Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appContext = getApplicationContext();
        // Create PlaceLibrary to hold places
        currentLib = new PlaceLibrary(this);
        // Get any possible intents and process them
        Intent i = getIntent();
        int number = i.getIntExtra("number", -1);
        String[] updatedVals = i.getStringArrayExtra("valArray");
        // For remove place action
        boolean toRemove = i.getBooleanExtra("remove", false);
        // If array was set with intent, it is an update to value or new value
        if (updatedVals != null){
            // Update Place Values by index
            if (number >-1){
                String[] savedValues = currentLib.getPlace(number, updatedVals);
                currentLib.removePlace(number, updatedVals);
                currentLib.setValues(number, savedValues);
            }
            // New Place
            else {
                currentLib.addPlace(updatedVals);
            }
        }
        //If intent to remove is true, remove the value by index
        if (toRemove == true){
            currentLib.removePlace(number, updatedVals);
        }
        // Spinner for Details
        placeSpinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<>(Arrays.asList(currentLib.getNames())));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(adapter);
        placeSpinner.setOnItemSelectedListener(this);
        //TextView test = (TextView) findViewById(R.id.test);
        //test.setText(currentLib.getItem(1));

        // Spinner for Location 1
        firstDistanceSpinner = (Spinner) findViewById(R.id.spinner2);
        firstDistanceSpinner.setAdapter(adapter);

        // Spinner for Location 2
        secondDistanceSpinner = (Spinner) findViewById(R.id.spinner3);
        secondDistanceSpinner.setAdapter(adapter);
    }

    // Create Options
    public boolean onCreateOptionsMenu(Menu options){
        android.util.Log.d(this.getClass().getSimpleName(), "called onCreateOptionsMenu()");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actions, options);
        return super.onCreateOptionsMenu(options);
    }

    /* If change to spinner has been made save values of selected place into array
    /  Then create new intent and pass index of place, boolean to create place as
    /  well as array with values to be displayed in PlaceActivity.
    */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (++clicks > 1){
            int index = -1;
            String placeString = placeSpinner.getSelectedItem().toString();
            String[] values = new String[9];
            android.util.Log.d(this.getClass().getSimpleName(),"Spinner selection: "+
                    placeString);
            for (int i=0;i<currentLib.placeList.size();i++){
                if (placeString.equals(currentLib.placeList.get(i).name)){
                    values[0] = currentLib.placeList.get(i).id;
                    android.util.Log.d(this.getClass().getSimpleName(),"Name: "+currentLib.placeList.get(i).name+" ID: "+
                            currentLib.placeList.get(i).id);
                    values[1] = currentLib.placeList.get(i).name;
                    values[2] = currentLib.placeList.get(i).description;
                    values[3] = currentLib.placeList.get(i).category;
                    values[4] = currentLib.placeList.get(i).addressTitle;
                    values[5] = currentLib.placeList.get(i).addressStreet;
                    values[6] = currentLib.placeList.get(i).elevation;
                    values[7] = currentLib.placeList.get(i).latitude;
                    values[8] = currentLib.placeList.get(i).longitude;
                    index = parseInt(currentLib.placeList.get(i).id);
                }
            }
            Intent intent = new Intent(this, PlaceActivity.class);
            intent.putExtra("array", values);
            intent.putExtra("add", false);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
    // Add button from Action Bar sends intent to NameActivity class which is just used to add name, then
    // goes to Place Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        android.util.Log.d(this.getClass().getSimpleName(), "called onOptionsItemSelected() - add");
        Intent addPlace = new Intent(this, NameActivity.class);
        addPlace.putExtra("add", true);
        startActivity(addPlace);
        return true;
    }

    /*  Finds latitude and longitude values for both places,
    /   then calls functions to calculate distance and heading.
    /   Stores values into TextFields
     */
    public void getCoordinates(View v){
        String[] coords = new String[4];
        double latOne = 0;
        double lonOne = 0;
        double latTwo = 0;
        double lonTwo = 0;
        double distance;
        String heading = "";
        String placeOne = firstDistanceSpinner.getSelectedItem().toString();
        String placeTwo = secondDistanceSpinner.getSelectedItem().toString();
        for (int i = 0; i < currentLib.placeList.size(); i++){
            if (placeOne.equals(currentLib.placeList.get(i).name)){
                coords[0] = currentLib.placeList.get(i).getLatitude();
                coords[1] = currentLib.placeList.get(i).getLongitude();
            }
            if (placeTwo.equals(currentLib.placeList.get(i).name)){
                coords[2] = currentLib.placeList.get(i).getLatitude();
                coords[3] = currentLib.placeList.get(i).getLongitude();
            }
        }
        try{
            latOne = Double.parseDouble(coords[0]);
            lonOne = Double.parseDouble(coords[1]);
            latTwo = Double.parseDouble(coords[2]);
            lonTwo = Double.parseDouble(coords[3]);
        }
        catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),
                "error converting coordinates");
        }
        distance = currentLib.getDistance(latOne, latTwo, lonOne, lonTwo);
        heading = currentLib.getHeading(latOne, latTwo, lonOne, lonTwo);

        TextView distanceTV = findViewById(R.id.distance);
        distanceTV.setText(String.valueOf(distance)+" km");
        TextView headingTV = findViewById(R.id.heading);
        headingTV.setText(String.valueOf(heading));
    }
}
