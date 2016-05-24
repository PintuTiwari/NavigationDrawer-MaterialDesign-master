package br.liveo.ndrawer.ui.activity;

/**
 * Created by rupa.dwivedi on 28-01-2016.
 */
public class Adamic_Pojo {
    private String Degree,
            Year_ofpassing,
            Institution_name,
            Grade_class;

    public Adamic_Pojo(String Degree, String Year_ofpassing, String Institution_name, String Grade_class) {
        this.Degree = Degree;
        this.Year_ofpassing = Year_ofpassing;
        this.Institution_name = Institution_name;
        this.Grade_class = Grade_class;
    }

    public String getDegree() {
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


}
