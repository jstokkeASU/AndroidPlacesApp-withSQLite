package edu.asu.bsse.jstokke.assign1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static edu.asu.bsse.jstokke.assign1.PlaceDescription.idCount;

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
 * @version April 14, 2018
 *
 * This is application's PlaceDescription Class
 * It has uses an ArrayList to store PlaceDescriptions
 * It utilizes the DBHelper to update, add, and delete from the sqlite db
 */
public class PlaceLibrary extends Activity {
    protected ArrayList<PlaceDescription> placeList = new ArrayList<PlaceDescription>();
    public int number;

    PlaceLibrary(Context context){
    try {
        DBHelper db = new DBHelper(MainActivity.appContext);
        SQLiteDatabase placeDB = db.openDB();
        Cursor cursor = placeDB.rawQuery("select * from place;", new String[]{});
        try {
            if (idCount < 2){
                idCount = 2;
            }
            boolean exists = false;
            int columnCount = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                String[] values = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    values[i] = cursor.getString(i);
                }
                for (int j = 0; j < placeList.size(); j++) {
                    android.util.Log.w(this.getClass().getSimpleName(),"placeList["+j+"]:"+placeList.get(j).name.toString()
                            +" Values[1]: "+values[1]);
                    if (placeList.get(j).name.toString().equals(values[1])) {
                        exists = true;
                    }
                }
                if (exists == false){
                    PlaceDescription newPlace = new PlaceDescription(values);
                    placeList.add(newPlace);
                }
            }
        }
        catch (Exception e){
            android.util.Log.w(this.getClass().getSimpleName(),"Cursor failed");
        }
        finally {
            cursor.close();
            context.deleteDatabase("s");
            placeDB.close();
            db.close();
        }
    }
    catch (Exception e){
        android.util.Log.w(this.getClass().getSimpleName(),"unable to add places from DB.");
        }
    }

    public String[] getNames(){
        String[] names = new String [placeList.size()];
        for (int i=0; i<placeList.size(); i++){
            names[i] = placeList.get(i).name.toString();
        }
        return names;
    }

    // Adds new PlaceDescription and sets values
    // Writes values to DB
    public void setValues(int index, String[] array){
        PlaceDescription newPlace = new PlaceDescription();
        newPlace.id=array[0];
        newPlace.name = array[1];
        newPlace.description = array[2];
        newPlace.category = array[3];
        newPlace.addressTitle = array[4];
        newPlace.addressStreet = array[5];
        newPlace.elevation = array[6];
        newPlace.latitude = array[7];
        newPlace.longitude = array[8];
        placeList.add(newPlace);
        try {
            DBHelper db = new DBHelper(MainActivity.appContext);
            SQLiteDatabase placeDB = db.openDB();
            android.util.Log.w(this.getClass().getSimpleName(),"DB opened to write");
            ContentValues values = new ContentValues();
            values.put("id", array[0]);
            values.put("name", array[1]);
            values.put("description", array[2]);
            values.put("category", array[3]);
            values.put("address_title", array[4]);
            values.put("address_street", array[5]);
            values.put("elevation", array[6]);
            values.put("latitude", array[7]);
            values.put("longitude", array[8]);
            placeDB.insert("place", null, values);
            placeDB.close();
            db.close();
        }
        catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Failed to add value");
        }

    }
    /*
    /   Creates new PlaceDescription and fills it in with values of array
    /   Then writes values to db using listarray size as id
     */
    public void addPlace(String[] array){
        PlaceDescription newPlace = new PlaceDescription();
        newPlace.id = array[0];
        newPlace.name = array[1];
        newPlace.description = array[2];
        newPlace.category = array[3];
        newPlace.addressTitle = array[4];
        newPlace.addressStreet = array[5];
        newPlace.elevation = array[6];
        newPlace.latitude = array[7];
        newPlace.longitude = array[8];
        placeList.add(newPlace);
        try {
            DBHelper db = new DBHelper(MainActivity.appContext);
            SQLiteDatabase placeDB = db.openDB();
            android.util.Log.w(this.getClass().getSimpleName(),"DB opened to write");
            ContentValues values = new ContentValues();
            values.put("id", array[0]);
            values.put("name", array[1]);
            values.put("description", array[2]);
            values.put("category", array[3]);
            values.put("address_title", array[4]);
            values.put("address_street", array[5]);
            values.put("elevation", array[6]);
            values.put("latitude", array[7]);
            values.put("longitude", array[8]);
            placeDB.insert("place", null, values);
            placeDB.close();
            db.close();
        }
        catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Failed to add value");
        }

    }

    // Removes placeDescription from Library List array and deletes it from db
    public void removePlace(int number, String[] array){
        String name = array[1];
        android.util.Log.w(this.getClass().getSimpleName(),"removePlace array1 value: "+array[1]);
        int listIndex = -1;
        for (int i = 0; i<placeList.size();i++){
            android.util.Log.w(this.getClass().getSimpleName(),"removePlace placeList Name "+placeList.get(i).name+
                    ".  PlaceList Id: "+placeList.get(i).id);
            if (name.equals(placeList.get(i).name)){
                listIndex = i;
            }
        }
        placeList.remove(listIndex);
        try {
            DBHelper db = new DBHelper(MainActivity.appContext);
            SQLiteDatabase placeDB = db.openDB();
            placeDB.execSQL("delete from place where place.id=?", new String[]{array[0]});
            android.util.Log.w(this.getClass().getSimpleName(),"Deleting id number: "+array[0]);
        }
        catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Failed to remove place");
        }
    }

    // Saves values of PlaceDescription in an array to be used to add it back in after deletion
    // Used for updating PlaceDescriptions
    public String[] getPlace(int number, String[] array){
        String[] attributes = new String[9];
        for (int i=0; i<array.length; i++) {
            attributes[i] = array[i];
        }
        return attributes;
    }

    //Calculates Great Circle Distance between two points in km
    public double getDistance(double latOne, double latTwo, double lonOne, double lonTwo){
        DecimalFormat form = new DecimalFormat("#.######");
        form.setRoundingMode(RoundingMode.CEILING);
        double distance = 0;
        latOne = toRadians(latOne);
        latTwo = toRadians(latTwo);
        lonOne = toRadians(lonOne);
        lonTwo = toRadians(lonTwo);
        double radius = 6371;
        double latDif = latTwo-latOne;
        double lonDif = lonTwo-lonOne;
        double a = Math.pow((Math.sin(latDif/2)), 2) +
                Math.cos(latOne) * Math.cos(latTwo) *
                Math.pow((Math.sin(lonDif/2)), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        distance = radius * c;
        String distString = form.format(distance);
        distance = Double.parseDouble(distString);
        return distance;
    }

    // Calculates initial heading from one place to another given lat and lon
    public String getHeading(double latOne, double latTwo, double lonOne, double lonTwo){
        String heading = "";
        double angle = 0;
        latOne = toRadians(latOne);
        latTwo = toRadians(latTwo);
        lonOne = toRadians(lonOne);
        lonTwo = toRadians(lonTwo);
        double y = Math.sin(lonTwo-lonOne) * Math.cos(latTwo);
        double x = Math.cos(latOne) * Math.sin(latTwo) -
                Math.sin(latOne) * Math.cos(latTwo) * Math.cos(lonTwo-lonOne);
        angle = Math.atan2(y,x);
        heading = toDegrees(angle);
        return heading;
    }

    //Converts angle from degrees to Radians
    public double toRadians(double degrees){
        double number = 0;
        number = (degrees * Math.PI)/180;
        return number;
    }

    // Converts angle from radians into degrees, minutes, seconds
    public String toDegrees(double radians){
        DecimalFormat form = new DecimalFormat("##");
        form.setRoundingMode(RoundingMode.CEILING);
        String heading = "";
        double degrees = (radians*180)/Math.PI;
        if (degrees < 0){
            degrees +=360 ;
        }
        int intNum = (int) degrees;
        double fraction = degrees - intNum;
        double dMinutes = (fraction*60);
        int minutes = (int) (dMinutes);
        double dubMinutes = (double)minutes;
        double test = dubMinutes/60;
        double dSeconds = (fraction - test)*3600;
        int seconds = parseInt(form.format(dSeconds));
        String stringMinutes = String.format("%02d", minutes);
        String stringSeconds = String.format("%02d", seconds);
        heading = String.valueOf(intNum)+"\u00b0"+stringMinutes+"' "+stringSeconds+"\"";
       // heading = String.valueOf(fraction);
        return heading;
    }

}
