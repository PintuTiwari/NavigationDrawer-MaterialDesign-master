package br.liveo.ndrawer.ui.activity;

import android.util.Log;
import android.widget.EditText;

/**
 * Created by Administrator on 9/3/2015.
 */
public class User_Pojo {

    private String Name;
    private String Address;
    private String Relation;
    private String Dateof_Birth;
    private String Age;
    private String Degree,
                   Year_ofpassing,
                   Institution_name,
                   Grade_class;
    private String  Name_Nominee,Address_Nominee,Nominee_Relation, Amount_Paidto_Nominee,Dateof_Birth_Nominee, NameAddress_ofGuardian,Age_Nominee;


    public User_Pojo(String Name, String Address, String Relation, String Dateof_Birth, String Age) {
       this.Name = Name;
       this.Address = Address;
       this.Relation = Relation;
        this.Dateof_Birth = Dateof_Birth;
        this.Age = Age;
   }


    public User_Pojo(String Degree, String Year_ofpassing, String Institution_name, String Grade_class) {
        this.Degree = Degree;
        this.Year_ofpassing = Year_ofpassing;
        this.Institution_name = Institution_name;
        this.Grade_class = Grade_class;
    }

    public User_Pojo(String Degree) {
        this.Degree = Degree;

    }

    public User_Pojo(String Name_Nominee, String Address_Nominee, String Nominee_Relation, String Amount_Paidto_Nominee,
                     String Dateof_Birth_Nominee, String NameAddress_ofGuardian, String Age_Nominee) {

        this.Name_Nominee = Name_Nominee;
        this.Address_Nominee = Address_Nominee;
        this.Nominee_Relation = Nominee_Relation;
        this.Amount_Paidto_Nominee = Amount_Paidto_Nominee;
        this.NameAddress_ofGuardian = NameAddress_ofGuardian;
        this.Age_Nominee = Age_Nominee;
        this.Dateof_Birth_Nominee = Dateof_Birth_Nominee;
    }

    public String getName_Nominee() {
        return Name_Nominee;
    }

    public String getAddress_Nominee() {
        return Address_Nominee;
    }
    public String getNominee_Relation() {
        return Nominee_Relation;
    }
    public String getAmount_Paidto_Nominee() {
        return Amount_Paidto_Nominee;
    }

    public String getDateof_Birth_Nominee() {
        return Dateof_Birth_Nominee;
    }
    public String getAge_Nominee() {
        return Age_Nominee;
    }

    public String getNameAddress_ofGuardian() {
        return NameAddress_ofGuardian;
    }


    public String getDegree1() {
        return Degree;
    }




    public String getYear_ofpassing() {
        return Year_ofpassing;
    }

    public String getInstitution_name() {
        return Institution_name;
    }

    public String getGrade_class() {
        return Grade_class;
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
