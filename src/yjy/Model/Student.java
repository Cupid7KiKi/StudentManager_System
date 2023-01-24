package yjy.Model;

import javafx.beans.property.SimpleStringProperty;

public class Student {
    private SimpleStringProperty stuID;
    private SimpleStringProperty stuClass;
    private SimpleStringProperty stuName;
    private SimpleStringProperty stuSex;
    private SimpleStringProperty stuBirth;
    private SimpleStringProperty stuMajor;

    public Student(String stuID,String stuClass,String stuName,String stuSex,String stuBirth,String stuMajor) {
        this.stuID = new SimpleStringProperty(stuID);
        this.stuClass = new SimpleStringProperty(stuClass);
        this.stuName = new SimpleStringProperty(stuName);
        this.stuSex = new SimpleStringProperty(stuSex);
        this.stuBirth = new SimpleStringProperty(stuBirth);
        this.stuMajor = new SimpleStringProperty(stuMajor);
    }

    public String getStuID() {
        return stuID.get();
    }

    public SimpleStringProperty stuIDProperty() {
        return stuID;
    }

    public void setStuID(String stuID) {
        this.stuID.set(stuID);
    }

    public String getStuClass() {
        return stuClass.get();
    }

    public SimpleStringProperty stuClassProperty() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass.set(stuClass);
    }

    public String getStuName() {
        return stuName.get();
    }

    public SimpleStringProperty stuNameProperty() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName.set(stuName);
    }

    public String getStuSex() {
        return stuSex.get();
    }

    public SimpleStringProperty stuSexProperty() {
        return stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex.set(stuSex);
    }

    public String getStuBirth() {
        return stuBirth.get();
    }

    public SimpleStringProperty stuBirthProperty() {
        return stuBirth;
    }

    public void setStuBirth(String stuBirth) {
        this.stuBirth.set(stuBirth);
    }

    public String getStuMajor() {
        return stuMajor.get();
    }

    public SimpleStringProperty stuMajorProperty() {
        return stuMajor;
    }

    public void setStuMajor(String stuMajor) {
        this.stuMajor.set(stuMajor);
    }
}
