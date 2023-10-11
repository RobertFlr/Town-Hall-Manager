package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

//class GigaPair{
//    Functionar functionar;
//    FileWriter fr;
//    GigaPair(Functionar functionar, FileWriter fileWriter){
//
//    }
//}

class Pair implements Comparable<Pair>{
    User user;
    Cerere cerere;
    Pair(Cerere cerere, User user){
        this.cerere = cerere;
        this.user = user;
    }
    public int compareTo(Pair p2){
        if(this.cerere.prio < p2.cerere.prio)
            return 1;
        if(this.cerere.prio > p2.cerere.prio)
            return -1;
        return (this.cerere).compareTo(p2.cerere);
    }
}

class Functionar{
    String nume;
    boolean opened_file = false;
    FileWriter fr;
    Functionar(String nume) throws IOException {
        this.nume = nume;
    }
}

public class Birou {
    int type;

    List<Pair> lista_perechi = new ArrayList<Pair>();
    List<Functionar> lista_functionari = new ArrayList<Functionar>();
    Birou(int type){
        this.type = type;
    }
    public void add_functionar(Functionar f){
        lista_functionari.add(f);
    }

    public void add_pereche(Cerere cerere, User user){
        Pair pereche_noua = new Pair(cerere, user);
        lista_perechi.add(pereche_noua);
    }
}
