package edu.asu.bsse.jstokke.assign1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
 * @version March 28, 2018
 *
 * This is the NameActivity file
 * It's purpose is to save a name for a new place since the name fields should not be editable
 */
public class NameActivity extends AppCompatActivity {
    private EditText nameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        Intent addName = getIntent();
        TextView nameTV = findViewById(R.id.text_name_screen);
        nameET = findViewById(R.id.edit_name_screen);
    }

    public void addName(View v){
        String placeName = nameET.getText().toString();
        Intent passName = new Intent(this, PlaceActivity.class);
        passName.putExtra("placeName", placeName);
        passName.putExtra("add", true);
        //int id = idCount;
        //idCount += 1;
        //passName.putExtra("idCount",id);
        startActivity(passName);
    }
}
