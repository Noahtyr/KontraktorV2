package awesomegroupkolond.kontraktor;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity
        implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

// Set up click handlers and view item references
        findViewById(R.id.cmdKontrakt).setOnClickListener(this);
        findViewById(R.id.cmdArbejdere).setOnClickListener(this);
        findViewById(R.id.cmdArbejderKontrakt).setOnClickListener(this);
        findViewById(R.id.cmdKalender).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //cmdKontrakt listener
            case R.id.cmdKontrakt:
                Toast.makeText(this, "Kontrakt", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, aktivitet3.class));
                break;
            //cmdArbejder listener
            case R.id.cmdArbejdere:
                Toast.makeText(this, "Arbejdere", Toast.LENGTH_SHORT).show();
                break;
            //cmdArbejderKontraktListener
            case R.id.cmdArbejderKontrakt:
                Toast.makeText(this, "Arbejder Kontrakt", Toast.LENGTH_SHORT).show();
                break;
            //cmdKalendarListener
            case R.id.cmdKalender:
                Toast.makeText(this, "Kalender", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CalendarActivity.class));
                break;

        }
    }
}
