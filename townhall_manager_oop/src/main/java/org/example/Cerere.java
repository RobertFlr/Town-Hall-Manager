package org.example;
//
// TYPES :
// 1 -> ID Replacement
// 2 -> Driver's License Replacement
// 3 -> Income Papers
// 4 -> Senior Coupons form
// 5 -> Student ID Replacement
// 6 -> Constitutional Act Request
// 7 -> Authorization Renewal
//
public class Cerere implements Comparable<Cerere> {
    //Parameters, getters and setters
    int prio;
    int type;
    String date;
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getPrio() {
        return prio;
    }
    public void setPrio(int prio) {
        this.prio = prio;
    }
    //Constructors
    Cerere(int prio, int type, String date){
        this.prio = prio;
        this.type = type;
        this.date = date;
    }
    Cerere(){}
    //Methods
    public String generareCerere(User user){
        String cerere = "Subsemnatul " ;

        if(user instanceof Persoana){
            cerere += user.getName() + ", va rog sa-mi aprobati urmatoarea solicitare:";
        }

        if(user instanceof Angajat) {
            cerere += user.getName() + ", angajat la compania " + ((Angajat) user).companie +
                    ", va rog sa-mi aprobati urmatoarea solicitare:";
        }

        if(user instanceof Pensionar) {
            cerere += user.getName() + ", va rog sa-mi aprobati urmatoarea solicitare:";
        }

        if(user instanceof Elev) {
            cerere += user.getName() + ", elev la scoala " + ((Elev) user).scoala +
                    ", va rog sa-mi aprobati urmatoarea solicitare:";
        }

        if(user instanceof EntitateJuridica) {
            cerere += ((EntitateJuridica) user).reprezentant + ", reprezentant legal al companiei " +
                    user.getName() + ", va rog sa-mi aprobati urmatoarea solicitare:";
        }
        switch (this.type) {
            case 1:  cerere += " inlocuire buletin";
                break;
            case 2:  cerere += " inlocuire carnet de sofer";
                break;
            case 3:  cerere += " inregistrare venit salarial";
                break;
            case 4:  cerere += " inregistrare cupoane de pensie";
                break;
            case 5:  cerere += " inlocuire carnet de elev";
                break;
            case 6:  cerere += " creare act constitutiv";
                break;
            case 7:  cerere += " reinnoire autorizatie";
                break;
            default: cerere += " cerere invalida";
                break;
        }
        return cerere;
    }
    @Override
    public int compareTo(Cerere c2) {
        //compare year
        int an_1 = Integer.parseInt(this.getDate().substring(7,11));
        int an_2 = Integer.parseInt(c2.getDate().substring(7,11));
        if(an_1 > an_2)
            return 1;
        if(an_1 < an_2)
            return -1;
        //compare month
        String luna_1 = this.getDate().substring(3,6);
        int nr_1 = 0;
        String luna_2 = c2.getDate().substring(3,6);
        int nr_2 = 0;
        switch (luna_1) {
            case "Jan":
                nr_1 = 1;
                break;
            case "Feb":
                nr_1 = 2;
                break;
            case "Mar":
                nr_1 = 3;
                break;
            case "Apr":
                nr_1 = 4;
                break;
            case "May":
                nr_1 = 5;
                break;
            case "Jun":
                nr_1 = 6;
                break;
            case "Jul":
                nr_1 = 7;
                break;
            case "Aug":
                nr_1 = 8;
                break;
            case "Sep":
                nr_1 = 9;
                break;
            case "Oct":
                nr_1 = 10;
                break;
            case "Nov":
                nr_1 = 11;
                break;
            case "Dec":
                nr_1 = 12;
                break;
            default:
                break;
        }
        switch (luna_2) {
            case "Jan":
                nr_2 = 1;
                break;
            case "Feb":
                nr_2 = 2;
                break;
            case "Mar":
                nr_2 = 3;
                break;
            case "Apr":
                nr_2 = 4;
                break;
            case "May":
                nr_2 = 5;
                break;
            case "Jun":
                nr_2 = 6;
                break;
            case "Jul":
                nr_2 = 7;
                break;
            case "Aug":
                nr_2 = 8;
                break;
            case "Sep":
                nr_2 = 9;
                break;
            case "Oct":
                nr_2 = 10;
                break;
            case "Nov":
                nr_2 = 11;
                break;
            case "Dec":
                nr_2 = 12;
                break;
            default:
                break;
        }
        if(nr_1 > nr_2)
            return 1;
        if(nr_1 < nr_2)
            return -1;
        //compare day
        int zi_1 = Integer.parseInt(this.getDate().substring(0,2));
        int zi_2 = Integer.parseInt(c2.getDate().substring(0,2));
        if(zi_1 > zi_2)
            return 1;
        if(zi_1 < zi_2)
            return -1;
        //compare hour
        String[] parts_1 = this.getDate().substring(12).split(":");
        String[] parts_2 = c2.getDate().substring(12).split(":");
        int ora_1 = Integer.parseInt(parts_1[0]);
        int ora_2 = Integer.parseInt(parts_2[0]);
        if(ora_1 > ora_2)
            return 1;
        if(ora_1 < ora_2)
            return -1;
        //compare minute
        int min_1 = Integer.parseInt(parts_1[1]);
        int min_2 = Integer.parseInt(parts_2[1]);
        if(min_1 > min_2)
            return 1;
        if(min_1 < min_2)
            return -1;
        int sec_1 = Integer.parseInt(parts_1[2]);
        int sec_2 = Integer.parseInt(parts_2[2]);
        if(sec_1 > sec_2)
            return 1;
        if(sec_1 < sec_2)
            return -1;
        return 0;
    }
}
