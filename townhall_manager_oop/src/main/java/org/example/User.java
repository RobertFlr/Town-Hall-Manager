package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public abstract class User {
    private String name;
    List<Cerere> pq = new ArrayList<Cerere>();
    List<Cerere> fn = new ArrayList<Cerere>();

    public void addpq(Cerere cerere){
        this.pq.add(cerere);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

class Persoana extends User {
    Persoana(String name){
        this.setName(name);
    }
}
class Angajat extends User {
    String companie;
    Angajat(String name, String companie){
        this.setName(name);
        this.companie = companie;
    }
}
class Pensionar extends User {
    Pensionar(String name){
        this.setName(name);
    }
}
class Elev extends User {
    String scoala;
    Elev(String name, String scoala){
        this.setName(name);
        this.scoala = scoala;
    }
}
class EntitateJuridica extends User {
    String reprezentant;
    EntitateJuridica(String name, String reprezentant){
        this.setName(name);
        this.reprezentant = reprezentant;
    }
}