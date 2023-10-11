package org.example;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class ManagementPrimarie {
    static String outPath = "src/main/resources/output/";
    static String inPath = "src/main/resources/input/";
    public static void main(String[] args) throws IOException, ParseException {

//        File inFile = new File(inPath + "13_birou_cereri_2.txt");
//        //File inFile = new File(inPath + "14_birou_cereri_3.txt");
//        File outFile = new File(outPath + "out_cu_ele.txt");

       File inFile = new File(inPath + args[0]);
       File outFile = new File(outPath + args[0]);

        Scanner scanner = new Scanner(inFile);
        FileWriter writer = new FileWriter(outFile);

        //stores the users
        Set<User> users = new HashSet<User>();
        //store bureaux
        Set<Birou> birouri = new HashSet<Birou>();
        Birou B1 = new Birou(1);    birouri.add(B1);
        Birou B2 = new Birou(2);    birouri.add(B2);
        Birou B3 = new Birou(3);    birouri.add(B3);
        Birou B4 = new Birou(4);    birouri.add(B4);
        Birou B5 = new Birou(5);    birouri.add(B5);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");

            if(parts[0].equals("adauga_utilizator")) {
                //adding a new user
                if(parts[1].equals(" elev")) {
                    Elev elev = new Elev(parts[2].substring(1),parts[3].substring(1));
                    //writer.write("added new user elev ");
                    users.add(elev);
                }
                if(parts[1].equals(" persoana")) {
                    //writer.write("added new user persoana ");
                    Persoana persoana = new Persoana(parts[2].substring(1));
                    users.add(persoana);
                }
                if(parts[1].equals(" pensionar")) {
                    //writer.write("added new user pensionar ");
                    Pensionar pensionar = new Pensionar(parts[2].substring(1));
                    users.add(pensionar);
                }
                if(parts[1].equals(" angajat")) {
                    //writer.write("added new user angajat ");
                    Angajat angajat = new Angajat(parts[2].substring(1), parts[3].substring(1));
                    users.add(angajat);
                }
                if(parts[1].equals(" entitate juridica")) {
                    //writer.write("added new user entitite juridica ");
                    EntitateJuridica entitate = new EntitateJuridica(parts[2].substring(1),
                            parts[3].substring(1));
                    users.add(entitate);
                }
            }

            if(parts[0].equals("cerere_noua")){
                String user_name = parts[1].substring(1);
//                writer.write("Username din cerere este : " + parts[1].substring(1) + "\n");
                //search for user in users set
                boolean found = false;
                User requester = null;
                for (User user : users) {
                    if (user_name.equals(user.getName())) {
                        found = true;
                        requester = user;
                    }
                }
                //if the user exists, proceed with request
                if(!found){
                    continue;
                }
                //fill out the request
                int tip_cerere;
                switch (parts[2]) {
                    case " inlocuire buletin":
                        tip_cerere = 1;
                        break;
                    case " inlocuire carnet de sofer":
                        tip_cerere = 2;
                        break;
                    case " inregistrare venit salarial":
                        tip_cerere = 3;
                        break;
                    case " inregistrare cupoane de pensie":
                        tip_cerere = 4;
                        break;
                    case " inlocuire carnet de elev":
                        tip_cerere = 5;
                        break;
                    case " creare act constitutiv":
                        tip_cerere = 6;
                        break;
                    case " reinnoire autorizatie":
                        tip_cerere = 7;
                        break;
                    default:
                        tip_cerere = 0;
                        break;
                }

                String data_cerere = parts[3].substring(1);

                int prioritate_cerere = Integer.parseInt(parts[4].substring(1));

                Cerere cerere_noua = new Cerere(prioritate_cerere, tip_cerere, data_cerere);

                //check if the request is valid
                String tip_requester = null;
                boolean valid = false;
                int type = 0;
                if(requester instanceof Persoana){
                    tip_requester = "persoana";
                    type = 1;
                    if(tip_cerere == 1 || tip_cerere == 2)
                        valid = true;
                }
                if(requester instanceof Angajat){
                    tip_requester = "angajat";
                    type = 2;
                    if(tip_cerere == 1 || tip_cerere == 2
                    || tip_cerere == 3)
                        valid = true;
                }
                if(requester instanceof Pensionar){
                    tip_requester = "pensionar";
                    type = 3;
                    if(tip_cerere == 1 || tip_cerere == 2
                    || tip_cerere == 4)
                        valid = true;
                }
                if(requester instanceof Elev){
                    tip_requester = "elev";
                    type = 4;
                    if(tip_cerere == 1 || tip_cerere == 5)
                        valid = true;
                }
                if(requester instanceof EntitateJuridica){
                    tip_requester = "entitate juridica";
                    type = 5;
                    if(tip_cerere == 6 || tip_cerere == 7)
                        valid = true;
                }

                //invalid request
                if(!valid){
                    writer.write("Utilizatorul de tip " + tip_requester
                            + " nu poate inainta o cerere de tip" + parts[2] + "\n");
                    continue;
                }
                //store the request
                requester.addpq(cerere_noua);
                //look bor right bureau
                Iterator<Birou> iterator = birouri.iterator();
                while(iterator.hasNext()){
                    Birou B = iterator.next();
                    if(type == B.type){
                        B.add_pereche(cerere_noua, requester);
                    }
                }
            }

            if(parts[0].equals("afiseaza_cereri_in_asteptare")){
                //look for the user
                String username = parts[1].substring(1);
                Iterator<User> iterator = users.iterator();
                while(iterator.hasNext()) {
                    User u = iterator.next();
                    //when user is found print queue
                    if(u.getName().equals(username)){
                        writer.write(u.getName() + " - cereri in asteptare:\n");
                        //Set<Cerere> set = new LinkedHashSet<Cerere>();
                        Collections.sort(u.pq);
                        for(int i = 0; i < u.pq.size(); i++) {
                            writer.write((u.pq.get(i)).getDate() + " - " + (u.pq.get(i)).generareCerere(u) + "\n");
                        }
                    }
                }
            }

            if(parts[0].equals("retrage_cerere")){
                String user_name = parts[1].substring(1);
                String remove_date = parts[2].substring(1);
                //look for user
                User temp = null;
                Iterator<User> iterator = users.iterator();
                while(iterator.hasNext()) {
                    User u = iterator.next();
                    //when user is found search the list
                    if (u.getName().equals(user_name)) {
                        temp = u;
                        //look for request in the list
                        for (int i = 0; i < u.pq.size(); i++) {
                            if(remove_date.equals((u.pq.get(i)).getDate())){
                                u.pq.remove(i);
                                break;
                            }
                        }
                    }
                }
                int type = 0;
                if(temp instanceof Persoana){
                    type = 1;
                }
                if(temp instanceof Angajat){
                    type = 2;
                }
                if(temp instanceof Pensionar){
                    type = 3;
                }
                if(temp instanceof Elev){
                    type = 4;
                }
                if(temp instanceof EntitateJuridica){
                    type = 5;
                }
                Iterator<Birou> i = birouri.iterator();
                while(i.hasNext()) {
                    Birou B = i.next();
                    //find the right bureau
                    if (type == B.type) {
                        for (int j = 0; j < B.lista_perechi.size(); j++) {
                            if (remove_date.equals(B.lista_perechi.get(j).cerere.getDate())) {
                                B.lista_perechi.remove(j);
                                break;
                            }
                        }
                    }
                }
            }

            if(parts[0].equals("afiseaza_cereri")){
                //type birou
                int tip;
                switch (parts[1]) {
                    case " persoana":
                        tip = 1;
                        break;
                    case " angajat":
                        tip = 2;
                        break;
                    case " pensionar":
                        tip = 3;
                        break;
                    case " elev":
                        tip = 4;
                        break;
                    case " entitate juridica":
                        tip = 5;
                        break;
                    default:
                        tip = 0;
                        break;
                }
                Iterator<Birou> iterator = birouri.iterator();
                while(iterator.hasNext()){
                    Birou B = iterator.next();
                    //find the right bureau
                    if(tip == B.type){
                        //sort list before displaying
                        Collections.sort(B.lista_perechi);
                        writer.write(parts[1].substring(1) + " - cereri in birou:\n");
                        //display loop
                        for(int i = 0; i < B.lista_perechi.size(); i++){
                            writer.write(B.lista_perechi.get(i).cerere.prio + " - " +
                                    B.lista_perechi.get(i).cerere.getDate() + " - " +
                                    B.lista_perechi.get(i).cerere.generareCerere(B.lista_perechi.get(i).user) + "\n");
                        }
                    }
                }

            }

            if(parts[0].equals("adauga_functionar")){
                //create instance
                Functionar functionar = new Functionar(parts[2].substring(1));
                int tip;
                switch (parts[1]) {
                    case " persoana":
                        tip = 1;
                        break;
                    case " angajat":
                        tip = 2;
                        break;
                    case " pensionar":
                        tip = 3;
                        break;
                    case " elev":
                        tip = 4;
                        break;
                    case " entitate juridica":
                        tip = 5;
                        break;
                    default:
                        tip = 0;
                        break;
                }
                Iterator<Birou> iterator = birouri.iterator();
                while(iterator.hasNext()) {
                    Birou B = iterator.next();
                    //find the right bureau
                    if (tip == B.type) {
                        //adauga functionar in lista
                        B.add_functionar(functionar);
                    }
                }
            }

            if(parts[0].equals("rezolva_cerere")){
                int tip = 0;
                switch (parts[1]) {
                    case " persoana":
                        tip = 1;
                        break;
                    case " angajat":
                        tip = 2;
                        break;
                    case " pensionar":
                        tip = 3;
                        break;
                    case " elev":
                        tip = 4;
                        break;
                    case " entitate juridica":
                        tip = 5;
                        break;
                    default:
                        tip = 0;
                        break;
                }

                //transfer request between lists on the user side +
                //remove request from the bureau queue
                Iterator<Birou> iterator = birouri.iterator();
                while(iterator.hasNext()) {
                    Birou B = iterator.next();
                    //find the right bureau
                    if (tip == B.type) {
                        Collections.sort(B.lista_perechi);
                        //copy first element's request and user

                        //save the data and remove the element
                        Cerere removed_cerere = B.lista_perechi.get(0).cerere;
                        User user = B.lista_perechi.get(0).user;
                        B.lista_perechi.remove(0);

                        //remove first element from list
                        user.pq.remove(removed_cerere);
                        //add it to the finished list
                        user.fn.add(removed_cerere);

                        //open the file for the office man
                        for(int i = 0; i < B.lista_functionari.size(); i++){
                            Functionar f = B.lista_functionari.get(i);
                            //find the office man
                            if(parts[2].substring(1).equals(f.nume)){
                                try (FileWriter fw = new FileWriter("src/main/resources/output/functionar_"
                                        + f.nume + ".txt", true);
                                     BufferedWriter bw = new BufferedWriter(fw);
                                     PrintWriter out = new PrintWriter(bw)) {
                                    out.println(removed_cerere.getDate() + " - " + user.getName());
                                } catch (IOException e) {
                                    System.out.println("UNLOKO");
                                    throw new IOException();
                                }
                            }
                        }
                    }
                }
            }

            if(parts[0].equals("afiseaza_cereri_finalizate")){
                //look for the user
                String username = parts[1].substring(1);
                Iterator<User> iterator = users.iterator();
                while(iterator.hasNext()) {
                    User u = iterator.next();
                    //when user is found print queue
                    if(u.getName().equals(username)){
                        writer.write(u.getName() + " - cereri in finalizate:\n");
                        //Set<Cerere> set = new LinkedHashSet<Cerere>();
                        Collections.sort(u.fn);
                        for(int i = 0; i < u.fn.size(); i++) {
                            writer.write((u.fn.get(i)).getDate() + " - " + (u.fn.get(i)).generareCerere(u) + "\n");
                        }
                    }
                }
            }



        }





//        Iterator<User> iterator = users.iterator();
//        while(iterator.hasNext()) {
//            Object o = iterator.next();
//            if( o instanceof Elev){
//                writer.write("found elev with name/school " + ((Elev) o).getName() + " / " + ((Elev) o).scoala + "\n");
//            }
//            if( o instanceof Persoana){
//                writer.write("found pesoana with name " + ((Persoana) o).getName() + "\n");
//            }
//        }
        writer.close();
        scanner.close();
    }
}