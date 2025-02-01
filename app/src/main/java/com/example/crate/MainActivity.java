package com.example.crate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.crate.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private DBManager dbManager;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.SUBJECT, DatabaseHelper.DESC };

    final int[] to = new int[] { R.id.id, R.id.title, R.id.desc };

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_materials, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        TextView craftName = findViewById(R.id.CurrentCraftTitle);
        TextView craftClient = findViewById(R.id.CurrentCraftClient);

        ArrayList allCraftNames = new ArrayList();
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            allCraftNames.add(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
            cursor.moveToNext();
        }
        Spinner craftSelector = findViewById(R.id.craftSelector);

        ArrayAdapter<String> craftSelectorArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        allCraftNames); //selected item will look like a spinner set from XML

        craftSelectorArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        craftSelector.setAdapter(craftSelectorArrayAdapter);

        cursor.moveToFirst();

        craftName.setText(cursor.getString(1));
        craftClient.setText(String.valueOf(cursor.getPosition()));

        Button tempBtn = findViewById(R.id.tempBtn);
        tempBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                craftName.setText(cursor.getColumnName(0));

                craftName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
//                cursor.moveToNext();
                craftClient.setText(String.valueOf(cursor.getPosition()));

                AlertDialog alertDialog = new MaterialAlertDialogBuilder(MainActivity.this ).setTitle("ALERT!")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent main = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(main);
                                dialogInterface.dismiss();
                            }
                        }).create();
                alertDialog.show();
            }
        });

        Button newCraftbtn = findViewById(R.id.newCraftButton);
        newCraftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_newcraft, null);
                TextInputEditText typedCraftName = view1.findViewById(R.id.craftName);
                TextInputEditText typedCraftClient = view1.findViewById(R.id.craftClient);
                AlertDialog alertDialog = new MaterialAlertDialogBuilder(MainActivity.this )
                        .setTitle("New Craft!")
                        .setView(view1)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Temp
                                craftName.setText(typedCraftName.getText().toString());
                                craftClient.setText(typedCraftClient.getText().toString());

                                final String name = craftName.getText().toString();
                                final String desc = craftClient.getText().toString();

                                dbManager.insert(name, desc);
                                adapter.notifyDataSetChanged();
                                //Refreshes the page
                                Intent main = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(main);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create();
                alertDialog.show();
            }
        });

        // OnCLickListiner For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = view.findViewById(R.id.id);
                TextView titleTextView = view.findViewById(R.id.title);
                TextView descTextView = view.findViewById(R.id.desc);

                String id = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String desc = descTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModifyCountryActivity.class);
                modify_intent.putExtra("title", title);
                modify_intent.putExtra("desc", desc);
                modify_intent.putExtra("id", id);

                startActivity(modify_intent);
            }
        });
    }

}