package br.liveo.ndrawer.ui.activity;

/**
 * Created by Administrator on 9/3/2015.
 */
public class Friend_Naminee {

    private String Name;
    private String Address;
    private String Relation;
    private String Dateof_Birth;
    private String Age;
    public String Name1;



   /* public Friend(String name, boolean gender, String job) {
        this.name = name;
        this.gender = gender;
        this.job = job;
    }*/


    public Friend_Naminee(String Name) {
       this.Name = Name;
       this.Address = Address;
       this.Relation = Relation;
        this.Dateof_Birth = Dateof_Birth;
        this.Age = Age;
   }

    public Friend_Naminee() {

    }

    public String getName() {
        return Name;
    }

    public String getJob() {
        return Address;
    }

    public String getRelation() {
        return Relation;
    }

    public String getDateof_Birth() {
        return Dateof_Birth;
    }

    public String getAge() {
        return Age;
    }
}
