package awesomegroupkolond.kontraktor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Betaling extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betaling);

    }
    public void sendMessage(View view)
    {
        Intent intent = new Intent(Betaling.this, Sikkerhedsstillelse.class);
        startActivity(intent);
    }



}
