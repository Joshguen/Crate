package com.example.crate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.crate.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ListView listView;

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


//        Button tempBtn = findViewById(R.id.tempBtn);
//        tempBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                craftName.setText(cursor.getColumnName(0));
//
//                craftName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
////                cursor.moveToNext();
//                craftClient.setText(String.valueOf(cursor.getPosition()));
//
//                AlertDialog alertDialog = new MaterialAlertDialogBuilder(MainActivity.this ).setTitle("ALERT!")
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent main = new Intent(MainActivity.this, MainActivity.class);
//                                startActivity(main);
//                                dialogInterface.dismiss();
//                            }
//                        }).create();
//                alertDialog.show();
//            }
//        });


    }

}