package awesomegroupkolond.kontraktor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Calendar extends AppCompatActivity {

    private static final String TAG = "Calendar";

    private TextView theDate;
    private Button btnGoCalendar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        theDate = (TextView) findViewById(R.id.lblDate);
        btnGoCalendar = (Button) findViewById(R.id.cmdCalendar);

        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        theDate.setText(date);

        btnGoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(Calendar.this, CalendarActivity.class);
            startActivity(intent);
            }
        });



    }
}
