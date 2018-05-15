package edu.asu.bsse.jstokke.assign1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
 * This is the places App PlaceActivity file
 * The purpose is to display all fields for the selected place
 * and give delete option for the place.
 */

public class PlaceActivity extends AppCompatActivity {
    private TextView locNameTV;
    private EditText locDescriptTV;
    private EditText locCategoryTV;
    private EditText locAddTitTV;
    private EditText locAddStrTV;
    private EditText locElevationTV;
    private EditText locLatitudeTV;
    private EditText locLongitudeTV;
    PlaceDescription currentPlace;
    private int index;
    private String[] vals;
    private String[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        Intent intent = getIntent();
        values = intent.getStringArrayExtra("array");
        Boolean addItem = intent.getExtras().getBoolean("add");
        index = intent.getIntExtra("index", -1);
        String placeName = "";
        int idNum = intent.getIntExtra("idCount", 0);
        placeName = intent.getStringExtra("placeName");
        //currentPlace = new PlaceDescription();
        if (addItem == true) {
            currentPlace = new PlaceDescription(placeName);
            //currentPlace.addCount();
        }
        else {
            currentPlace = new PlaceDescription(values);
        }
        locNameTV = findViewById(R.id.nameField);
        locNameTV.setText(currentPlace.getName());
        locDescriptTV = findViewById(R.id.descriptionField);
        locDescriptTV.setText(currentPlace.getDescription());
        locCategoryTV = findViewById(R.id.categoryField);
        locCategoryTV.setText(currentPlace.getCategory());
        locAddTitTV = findViewById(R.id.addressTitleField);
        locAddTitTV.setText(currentPlace.getAddressTitle());
        locAddStrTV = findViewById(R.id.addressField);
        locAddStrTV.setText(currentPlace.getAddressStreet());
        locElevationTV = findViewById(R.id.elevationField);
        locElevationTV.setText(currentPlace.getElevation());
        locLatitudeTV = findViewById(R.id.latitudeField);
        locLatitudeTV.setText(currentPlace.getLatitude());
        locLongitudeTV = findViewById(R.id.longitudeField);
        locLongitudeTV.setText(currentPlace.getLongitude());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu options){
        android.util.Log.d(this.getClass().getSimpleName(), "called onCreateOptionsMenu()");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.place_actions, options);
        return super.onCreateOptionsMenu(options);
    }

    //Saves EditText fields values in place and writes them to array that gets passed for db updates
    public void updatePlace(View v){
        vals = new String[9];

        if (currentPlace.id == null){
            currentPlace.id = String.valueOf(currentPlace.getIdCount());
        }
        vals[0] = currentPlace.id;
        android.util.Log.d(this.getClass().getSimpleName(), "updatePlace assigned ID:"+currentPlace.id+"  Value from: "+String.valueOf(currentPlace.getIdCount()));
        android.util.Log.d(this.getClass().getSimpleName(),"button clicked");
        TextView nameField = (TextView)findViewById(R.id.nameField);
        String nameString = nameField.getText().toString();
        currentPlace.setName(nameString);
        android.util.Log.w(this.getClass().getSimpleName(),"NameString: "+nameString);
        vals[1] = nameString;
        EditText descriptionField = (EditText)findViewById(R.id.descriptionField);
        String descriptionString = descriptionField.getText().toString();
        currentPlace.setDescription(descriptionString);
        vals[2] = descriptionString;
        EditText categoryField = (EditText)findViewById(R.id.categoryField);
        String categoryString = categoryField.getText().toString();
        android.util.Log.w(this.getClass().getSimpleName(),"CategoryString: "+categoryString);
        currentPlace.setCategory(categoryString);
        vals[3] = categoryString;
        android.util.Log.w(this.getClass().getSimpleName(),"vals[2]: "+vals[2]);
        EditText addressTitleField = (EditText)findViewById(R.id.addressTitleField);
        String addressTitleString = addressTitleField.getText().toString();
        currentPlace.setAddressTitle(addressTitleString);
        vals[4] = addressTitleString;
        EditText addressField = (EditText)findViewById(R.id.addressField);
        String addressString = addressField.getText().toString();
        currentPlace.setAddressStreet(addressString);
        vals[5] = addressString;
        EditText elevationField = (EditText)findViewById(R.id.elevationField);
        String elevationString = elevationField.getText().toString();
        currentPlace.setElevation(elevationString);
        vals[6] = elevationString;
        EditText latitudeField = (EditText)findViewById(R.id.latitudeField);
        String latitudeString = latitudeField.getText().toString();
        currentPlace.setLatitude(latitudeString);
        vals[7] = latitudeString;
        EditText longitudeField = (EditText)findViewById(R.id.longitudeField);
        String longitudeString = longitudeField.getText().toString();
        currentPlace.setLongitude(longitudeString);
        vals[8] = longitudeString;
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("number", index);
        i.putExtra("valArray", vals);
        startActivity(i);
    }

    // Delete place from action bar menu.  Intent goes back to MainActivity with info necessary to remove from db and Library
    public boolean onOptionsItemSelected(MenuItem item) {
        android.util.Log.d(this.getClass().getSimpleName(), "called onOptionsItemSelected() - delete");
        Intent removePlace = new Intent(this, MainActivity.class);
        removePlace.putExtra("number", index);
        removePlace.putExtra("remove", true);
        removePlace.putExtra("valArray", values);
        startActivity(removePlace);
        return true;
    }

}