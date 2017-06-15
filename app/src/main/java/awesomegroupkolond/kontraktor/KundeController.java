package awesomegroupkolond.kontraktor;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by maria on 15-06-2017.
 */

public class KundeController {

    FirebaseHandler fbHandler = new FirebaseHandler();
    ArrayList<Kunde> kunder = new ArrayList<>();

    public KundeController(){
        kunder = fbHandler.getCustomers();
    }

    public void createNewCustomer(Kunde kunde){
        kunder.add(kunde);
    }

    public Kunde getCustomer(int id){
        return kunder.get(id);
    }

    public ArrayList<Kunde> getCustomers(){
        return kunder;
    }
}
