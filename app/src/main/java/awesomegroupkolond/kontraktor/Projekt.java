package awesomegroupkolond.kontraktor;

import android.util.Base64;

import java.util.ArrayList;

/**
 * Created by maria on 15-06-2017.
 */

public class Projekt {

    private int id;
    private String beskrivelse;
    private ArrayList<Base64> billeder = new ArrayList<>();


    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }
}
