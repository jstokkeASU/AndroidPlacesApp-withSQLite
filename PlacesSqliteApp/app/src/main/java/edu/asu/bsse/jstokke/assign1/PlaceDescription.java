package edu.asu.bsse.jstokke.assign1;

import android.app.Activity;

import org.json.JSONObject;

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
 * This is the first assignments PlaceDescription Class
 * It has all necessary parameters for the location and includes getters and setters
 * It also includes methods to serialize and deserialize from json
 */

public class PlaceDescription extends Activity {
    public static int idCount;
    protected String id;
    protected String name;
    protected String description;
    protected String category;
    protected String addressTitle;
    protected String addressStreet;
    protected String elevation;
    protected String latitude;
    protected String longitude;

    PlaceDescription(){
        idCount+=1;
    }

    PlaceDescription(String placeName){
        //int newCount = idCount+1;
        idCount+=1;
        id = String.valueOf(idCount);
        name = placeName;
    }

    PlaceDescription (String[] values){
        idCount+=1;
        id = values[0];
        name = values[1];
        description = values[2];
        category = values[3];
        addressTitle = values[4];
        addressStreet = values[5];
        elevation = values[6];
        latitude = values[7];
        longitude = values[8];
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public String getElevation() {
        return elevation;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void addCount (){
        idCount+=1;
        android.util.Log.d(this.getClass().getSimpleName(),"Count: "+idCount);
    }

    public void setCount(int number){
        idCount = number;
    }

    public int getIdCount(){
        return idCount;
    }




}
