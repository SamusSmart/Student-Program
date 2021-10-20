/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Toby
 * @author Owen
 */

class Student {

    private String name;
    private String dob;
    private String address;
    private String gender;
    private boolean save;

    private int ID;

    private static float maleCount = 0;
    private static float femaleCount = 0;
    private static int studentCount = 0;

    public Student(String name, String dob, String address, String gender){
        save = true;
        if (name == "") save = false;
        if (dob == "") save = false;
        if (address == "") save = false;
        if (gender == "Unknown") save = false;
        this.ID = studentCount;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
        if (this.gender.equals("Male")) {
            maleCount++;
        } else if (this.gender.equals("Female")) {
            femaleCount++;
        }
        studentCount++;
    }

    public static void reset(){
        studentCount = 0;
        maleCount = 0;
        femaleCount = 0;
    }

    public static int getStudentCount(){
        return studentCount;
    }

    public String getName(){
        return name;
    }

    public int getID(){
        return ID;
    }

    public boolean getSave() {
        return save;
    }


    public static float[] genderBomb(){
        float[] percentages = new float[2];
        percentages[0] = Math.round((maleCount/studentCount) * 10000)/100;
        percentages[1] = Math.round((femaleCount/studentCount) * 10000)/100;
        return percentages;
    }

    public String toString() {
        return this.name + ", " + this.dob + ", " + this.address + ", " + this.gender;
    }
}