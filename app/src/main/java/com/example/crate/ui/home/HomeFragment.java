package com.example.crate.ui.home;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.widget.AdapterView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.crate.DBManager;
import com.example.crate.DatabaseHelper;
import com.example.crate.MainActivity;
import com.example.crate.ModifyCraftActivity;
import com.example.crate.R;
import android.widget.SimpleCursorAdapter;
import com.example.crate.databinding.FragmentHomeBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.SUBJECT, DatabaseHelper.DESC, DatabaseHelper.STARTDATE, DatabaseHelper.ENDDATE };

    final int[] to = new int[] { R.id.id, R.id.title, R.id.desc, R.id.start_date, R.id.end_date };

    @SuppressLint("Range")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DBManager dbManager = new DBManager(this.getContext());
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        //All this is temporary V
        ListView listView;
        listView = binding.listView;
        listView.setEmptyView(binding.empty);
        adapter = new SimpleCursorAdapter(root.getContext(), R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        //All this is temporary ^

        ArrayList allCraftNames = new ArrayList();
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            allCraftNames.add(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
            cursor.moveToNext();
        }
        Spinner craftSelector = binding.craftSelector;
        ArrayAdapter<String> craftSelectorArrayAdapter = new ArrayAdapter<String>
                (this.getContext(), android.R.layout.simple_spinner_item,
                        allCraftNames); //selected item will look like a spinner set from XML

        craftSelectorArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        craftSelector.setAdapter(craftSelectorArrayAdapter);

        cursor.moveToFirst();

        Button newCraftbtn = binding.newCraftButton;
        newCraftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1 = LayoutInflater.from(HomeFragment.this.getContext()).inflate(R.layout.layout_newcraft, null);
                TextInputEditText typedCraftName = view1.findViewById(R.id.craftName);
                TextInputEditText typedCraftClient = view1.findViewById(R.id.craftClient);
                TextInputEditText typedCraftStartDate = view1.findViewById(R.id.craftStartDate);
                TextInputEditText typedCraftEndDate = view1.findViewById(R.id.craftEndDate);
                AlertDialog alertDialog = new MaterialAlertDialogBuilder(HomeFragment.this.getContext())
                        .setTitle("New Craft!")
                        .setView(view1)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Temp
                                TextView craftName = binding.CurrentCraftTitle;
                                TextView craftClient = binding.CurrentCraftClient;
                                craftName.setText(typedCraftName.getText().toString());
                                craftClient.setText(typedCraftClient.getText().toString());

                                final String name = craftName.getText().toString();
                                final String desc = craftClient.getText().toString();
                                final String startDate = typedCraftStartDate.getText().toString();
                                final String endDate = typedCraftEndDate.getText().toString();

                                dbManager.insert(name, desc, startDate, endDate);
                                adapter.notifyDataSetChanged();

                                Intent main = new Intent(getContext(), MainActivity.class);
                                startActivity(main);
                                //Refreshes the page
//                                Intent main = new Intent(HomeFragment.this.getContext(), HomeFragment.this.getClass());
//                                startActivity(main);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = view.findViewById(R.id.id);
                TextView titleTextView = view.findViewById(R.id.title);
                TextView descTextView = view.findViewById(R.id.desc);
                TextView startDateTextView = view.findViewById(R.id.start_date);
                TextView endDateTextView = view.findViewById(R.id.end_date);

                String id = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String desc = descTextView.getText().toString();
                String startDate = startDateTextView.getText().toString();
                String endDate = endDateTextView.getText().toString();

                Intent modify_intent = new Intent(getContext(), ModifyCraftActivity.class);
                modify_intent.putExtra("title", title);
                modify_intent.putExtra("desc", desc);
                modify_intent.putExtra("id", id);
                modify_intent.putExtra("startDate", startDate);
                modify_intent.putExtra("endDate", endDate);

                startActivity(modify_intent);
            }
        });

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}