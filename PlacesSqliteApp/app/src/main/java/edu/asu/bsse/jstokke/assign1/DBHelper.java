package edu.asu.bsse.jstokke.assign1;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
 * @version April 13, 2018
 *
 * This is the first assignments DBHelper file for places application
 * The purpose is to provide SQLite DB connections and methods
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static String nameDB = "dbplaces";
    private String pathDB;
    private SQLiteDatabase placeDB;
    private final Context context;

    public DBHelper(Context context){
        super(context,nameDB, null, DATABASE_VERSION);
        this.context = context;
        android.util.Log.d(this.getClass().getSimpleName(), "Path? :"+context.getFilesDir().toString());
        pathDB = context.getFilesDir().getPath()+"/";
        android.util.Log.d(this.getClass().getSimpleName(), "Path for DB: "+pathDB);
    }

    public void createDB() throws IOException {
        this.getReadableDatabase();
        try {
            copyDB();
        } catch (IOException e) {
        android.util.Log.w(this.getClass().getSimpleName(),
                "createDB: Error copying database " + e.getMessage());
        }
    }

    public void copyDB() throws IOException{
        try {
            if(!checkDB()){
                // If db isn't in directory, copy it.
                debug("Copy DB", "DB not in directory yet.");
                InputStream is =  context.getResources().openRawResource(R.raw.dbplaces);
                // make sure the database path exists. if not, create it.
                File file = new File(pathDB);
                /*if(!file.exists()){
                    file.mkdirs();
                }*/
                String os=  pathDB  +  nameDB +".db";
                OutputStream output = new FileOutputStream(os);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer))>0){
                    output.write(buffer, 0, length);
                }
                output.flush();
                output.close();
                is.close();
            }
        } catch (IOException e) {
            android.util.Log.w("Copy DB", "IOException: "+e.getMessage());
        }
    }

    //Check to see if DB is available
    private boolean checkDB(){
        SQLiteDatabase checkDB = null;
        boolean dbAvailable = false;
        try{
            String path = pathDB + nameDB + ".db";
            debug("checkDB: path to db is", path);
            File aFile = new File(path);
            if(aFile.exists()){
                checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
                if (checkDB!=null) {
                    dbAvailable = true;
                    debug("checkDB","opened db at: "+checkDB.getPath());
                }
                else {
                    debug("checkDB","Not Opened");
                }
                return dbAvailable;
            }
        }catch(SQLiteException e){
            android.util.Log.w("checkDB",e.getMessage());
        }
        if(checkDB != null){
            checkDB.close();
        }
        return dbAvailable;
    }

    public SQLiteDatabase openDB() {
        String dbPath = pathDB + nameDB + ".db";
        if(checkDB()) {
            placeDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            debug("openDB", "opened db at path: " + placeDB.getPath());
        }else{
            try {
                this.copyDB();
                placeDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            }catch(Exception ex) {
                android.util.Log.w(this.getClass().getSimpleName(),"unable to copy and open db: "+ex.getMessage());
            }
        }
        return placeDB;
    }

    private void debug(String hdr, String msg){
            android.util.Log.d(hdr,msg);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public synchronized void close() {
        if(placeDB != null)
            placeDB.close();
        super.close();
    }


}
