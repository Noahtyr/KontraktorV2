package awesomegroupkolond.kontraktor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maria on 15-06-2017.
 */

public class FirebaseHandler {

    FirebaseDatabase fbCon = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = fbCon.getReference();
    DatabaseReference customerRef = rootRef.child("Kunder");
    DatabaseReference projectRef = rootRef.child("Projekter");
    DatabaseReference contractRef = rootRef.child("Kontrakter");
    DatabaseReference workerRef = rootRef.child("Medarbejdere");
    DatabaseReference branchRef = rootRef.child("Afdelinger");
    DatabaseReference companyRef = rootRef.child("Firma");
    DataSnapshot dataSnapshot;


public FirebaseHandler(){


}

public void addCustomers(ArrayList<Kunde> kunde){
    Map kundeMap = new HashMap();
    for (Kunde k : kunde){
        kundeMap.put(customerRef,kunde);
    }
    rootRef.getRef().updateChildren(kundeMap);
}

    public ArrayList<Kunde> getCustomers() {
        //get a snapshot of current data in "Kunder" in the database
        DataSnapshot kundeSnapshot = dataSnapshot.child(customerRef.toString());
        //make the children of the kundeSnapshot iterable
        Iterable<DataSnapshot> kundeChildren = kundeSnapshot.getChildren();
        //Create an arrayList to contain the Kunde objects
        ArrayList<Kunde> kunder = new ArrayList<>();

        //Iterates through the Kunde objects
        for (DataSnapshot kunde : kundeChildren){
            Kunde k = kunde.getValue(Kunde.class);
            kunder.add(k);
        }


        return kunder;
    }


    public void addProjects(HashMap map){
        rootRef.getRef().updateChildren(map);
    }


    public ArrayList<Projekt> getProjects(){
//get a snapshot of current data in "Projekt" in the database
        DataSnapshot projektSnapshot = dataSnapshot.child(projectRef.toString());
        //make the children of the projektSnapshot iterable
        Iterable<DataSnapshot> projektChildren = projektSnapshot.getChildren();
        //Create an arrayList to contain the Projekt objects
        ArrayList<Projekt> projekter = new ArrayList<>();

        //Iterates through the Projekt objects
        for (DataSnapshot projekt : projektChildren){
            Projekt p = projekt.getValue(Projekt.class);
            projekter.add(p);
        }


        return projekter;
    }


    public void addContracts(HashMap map){
        rootRef.getRef().updateChildren(map);
    }

    public ArrayList<Kontrakt> getContracts(){
//get a snapshot of current data in "kontrakter" in the database
        DataSnapshot kontraktSnapshot = dataSnapshot.child(contractRef.toString());
        //make the children of the kontraktSnapshot iterable
        Iterable<DataSnapshot> kontraktChildren = kontraktSnapshot.getChildren();
        //Create an arrayList to contain the kontrakt objects
        ArrayList<Kontrakt> kontrakter = new ArrayList<>();

        //Iterates through the Kontrakt objects
        for (DataSnapshot kontrakt : kontraktChildren){
            Kontrakt k = kontrakt.getValue(Kontrakt.class);
            kontrakter.add(k);
        }


        return kontrakter;
    }


    public void addCWorkers(HashMap map){
        rootRef.getRef().updateChildren(map);
    }


    public ArrayList<Medarbejder> getWorkers(){
//get a snapshot of current data in "Medarbejder" in the database
        DataSnapshot medarbejderSnapshot = dataSnapshot.child(workerRef.toString());
        //make the children of the MedarbejderSnapshot iterable
        Iterable<DataSnapshot> medarbejderChildren = medarbejderSnapshot.getChildren();
        //Create an arrayList to contain the Medarbejder objects
        ArrayList<Medarbejder> medarbejdere = new ArrayList<>();

        //Iterates through the Medarbejder objects
        for (DataSnapshot kunde : medarbejderChildren){
            Medarbejder m = kunde.getValue(Medarbejder.class);
            medarbejdere.add(m);
        }


        return medarbejdere;
    }


    public void addBranches(HashMap map){
        rootRef.getRef().updateChildren(map);
    }

    public ArrayList<Afdeling> getBranches(){
//get a snapshot of current data in "Afdeling" in the database
        DataSnapshot afdelingSnapshot = dataSnapshot.child(branchRef.toString());
        //make the children of the afdelingSnapshot iterable
        Iterable<DataSnapshot> afdelingChildren = afdelingSnapshot.getChildren();
        //Create an arrayList to contain the Kunde objects
        ArrayList<Afdeling> afdelinger = new ArrayList<>();

        //Iterates through the Afdeling objects
        for (DataSnapshot afdeling : afdelingChildren){
            Afdeling a = afdeling.getValue(Afdeling.class);
            afdelinger.add(a);
        }


        return afdelinger;
    }

    public void addCompany(HashMap map){
        rootRef.getRef().updateChildren(map);
    }

    public ArrayList<Firma> getCompany(){
//get a snapshot of current data in "Firma" in the database
        DataSnapshot firmaSnapshot = dataSnapshot.child(companyRef.toString());
        //make the children of the FirmaSnapshot iterable
        Iterable<DataSnapshot> firmaChildren = firmaSnapshot.getChildren();
        //Create an arrayList to contain the Kunde objects
        ArrayList<Firma> firmaer = new ArrayList<>();

        //Iterates through the Firma objects
        for (DataSnapshot firma : firmaChildren){
            Firma f = firma.getValue(Firma.class);
            firmaer.add(f);
        }


        return firmaer;
    }


}
