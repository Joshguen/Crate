package com.example.crate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ModifyCraftActivity extends Activity implements OnClickListener {

    private Button updateBtn, deleteBtn;
    private EditText titleText;
    private EditText descText;
    private EditText startDateText;
    private EditText endDateText;

    private long _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.activity_modify_record);

        dbManager = new DBManager(this);
        dbManager.open();

        titleText = (EditText) findViewById(R.id.subject_edittext);
        descText = (EditText) findViewById(R.id.description_edittext);
        startDateText = (EditText) findViewById(R.id.start_date_edittext);
        endDateText = (EditText) findViewById(R.id.end_date_edittext);

        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String startDate = intent.getStringExtra("startDate");
        String endDate = intent.getStringExtra("endDate");

        _id = Long.parseLong(id);

        titleText.setText(name);
        descText.setText(desc);
        startDateText.setText(startDate);
        endDateText.setText(endDate);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_update) {
            String title = titleText.getText().toString();
            String desc = descText.getText().toString();
            String startDate = startDateText.getText().toString();
            String endDate = endDateText.getText().toString();

            dbManager.update(_id, title, desc, startDate, endDate);
            this.returnHome();
        } else if (v.getId() == R.id.btn_delete) {
            dbManager.delete(_id);
            this.returnHome();
        }

    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}