package com.example.crate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddCraftActivity extends Activity implements OnClickListener {

    private Button addTodoBtn;
    private EditText subjectEditText;
    private EditText descEditText;
    private EditText startDateEditText;
    private EditText endDateEditText;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Record");

        setContentView(R.layout.activity_add_record);

        subjectEditText = (EditText) findViewById(R.id.subject_edittext);
        descEditText = (EditText) findViewById(R.id.description_edittext);
        startDateEditText = (EditText) findViewById(R.id.start_date_edittext);
        endDateEditText = (EditText) findViewById(R.id.end_date_edittext);

        addTodoBtn = (Button) findViewById(R.id.add_record);

        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            R.id.add_record
            case 0:

                final String name = subjectEditText.getText().toString();
                final String desc = descEditText.getText().toString();
                final String startDate = startDateEditText.getText().toString();
                final String endDate = endDateEditText.getText().toString();

                dbManager.insert(name, desc, startDate, endDate);

                Intent main = new Intent(AddCraftActivity.this, CraftListActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }
    }
}
