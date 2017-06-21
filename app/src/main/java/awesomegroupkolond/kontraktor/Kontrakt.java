package awesomegroupkolond.kontraktor;


import java.util.ArrayList;

public class Kontrakt {

    //Will be created from "Arbejderkontrakt" in Main menu

    private int id;
    private Projekt projekt;
    private Medarbejder tilsynsførende;
    private Afdeling afdeling;
    private boolean status;
    private ArrayList<String> opdateringer  = new ArrayList<>();
    private ArrayList<Medarbejder> arbejdere = new ArrayList<>();

    public Kontrakt(int id, Projekt projekt, Medarbejder tilsynsførende,
                    Afdeling afdeling, ArrayList<Medarbejder> arbejdere){

        this.id = id;
        this.projekt = projekt;
        this.tilsynsførende = tilsynsførende;
        this.afdeling = afdeling;
        this.arbejdere = arbejdere;
        //Start out with the status as not completed (false)
        status = false;
        //Add the description of the project as the first update
        opdateringer.add(projekt.getBeskrivelse());

    }



}
