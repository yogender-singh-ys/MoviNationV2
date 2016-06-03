package com.example.yogenders.movinationfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ErrorActivity extends AppCompatActivity {

    Button btn;
    TextView error;
    String errorText = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        btn = (Button) findViewById(R.id.btn_error);
        error = (TextView) findViewById(R.id.error_text);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            errorText = extras.getString("errorText");
        }

        error.setText(errorText);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
