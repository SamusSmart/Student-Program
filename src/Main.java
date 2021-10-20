/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Toby
 * @author Owen
 */

import java.io.*;
import java.util.Scanner;

public class Main {

    private final int maxStudents = 20;  // max number of students 
    private Student[] students;
    Scanner input = new Scanner(System.in);
    public static void main(String[] args) { // main method
        Main instance = new Main(); // create an object
        instance.start();   // call start function
    }

    private void start() { 
        this.students = new Student[maxStudents]; // make the maximum student objects in the student class
        this.studentHandler(); // read stutent text file and append to temporary data format
        this.menu();
    }

    private void studentHandler() { //read student file as mentioned
        try {
            String[] studentData = new String[maxStudents];
            int i = 0;
            File studentDetails = new File("StudentDetails.txt");
            Scanner myReader = new Scanner(studentDetails);
            while (myReader.hasNextLine()) {
                studentData[i] = myReader.nextLine();
                i++;
            }
            myReader.close();
            importStudent(studentData);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void importStudent(String[] studentData) { //transfer to acceptable temporary data format
        students = new Student[maxStudents];
        Student.reset();
        for (String student : studentData) {
            if (student == null) continue;
            String[] details = student.split(", ");
            this.students[Student.getStudentCount()] = new Student(details[0], details[1], details[2], details[3]);
        }
    }

    private void courseDetails() { //read in course details 
        try {
            File courseDetails = new File("CourseDetails.txt");
            Scanner myScanner = new Scanner(courseDetails);
            writeCourse();
            while (myScanner.hasNextLine()) {
                String data = myScanner.nextLine();
                System.out.println(data);
            }
            myScanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void writeCourse() {  //overwrite previous data with appended information
        try {
            FileWriter writeToCourse = new FileWriter("CourseDetails.txt");
            writeToCourse.write("Course Details \n");
            writeToCourse.write("--------------------");
            writeToCourse.write("\nCourse Name: Java");
            writeToCourse.write("\nCourse Lecturer: Keith");
            writeToCourse.write("\nThe number of students in this course is: " + Student.getStudentCount());
            writeToCourse.write("\nThe percentage of males in the course is: " + Student.genderBomb()[0] + "%");
            writeToCourse.write("\nThe percentage of females in the course is: " + Student.genderBomb()[1] + "%");
            writeToCourse.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void menu() { // sout block menu to display options and ask user for input
        System.out.println("Options currently available:");                 
        System.out.println("    1. ADD STUDENT");
        System.out.println("    2. REMOVE STUDENT");           
        System.out.println("    3. Search for a student by name");
        System.out.println("    4. Print a list of course information");
        System.out.println("    5. Save contents and exit runtime.\n");
        System.out.println("Please select the number of the function you want to execute:");
        this.functionList();
    }

    private void returnToMenu() { //a function that can be called after the completion of a task, asking if they want to continue or exit
        System.out.println("\nPlease select the appropriate number to preform one of the following options:");
        System.out.println("    1. RETURN TO MENU");
        System.out.println("    2. EXIT PROGRAM");
        int callMenu = input.nextInt();
        if (callMenu == 1) {
            menu();
        } else if (callMenu == 2) {
            try {
                writeToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Runtime.getRuntime().exit(0);
        }
    }


    private void functionList() { // switch statement containing actual functions for each menu option
        int menuSelection = input.nextInt();
        switch (menuSelection) {
            case 1:{
                addStudent();
                returnToMenu();
            }
            case 2:{
                removeStudent();
                returnToMenu();
            }
            case 3:{
                printStudent();
                returnToMenu();
            }
            case 4:{
                courseDetails();
                returnToMenu();
            }
            case 5:{
                try {
                    writeToFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Runtime.getRuntime().exit(0);
            }
        }
    }

    private void addStudent() { // function that can be called allowing the user to add a new student to the temporary data format
        int studentCount = Student.getStudentCount();
        if (studentCount < students.length) {
            Scanner studentInput = new Scanner(System.in);
            String[] details = new String[4];
            System.out.println("Enter name of Student:");
            details[0] = studentInput.nextLine();
            System.out.println("Enter Student D.O.B in the format 'dd/mm/yyyy':");
            details[1] = studentInput.nextLine();
            System.out.println("Enter Student Address:");
            details[2] = studentInput.nextLine();
            System.out.println("Enter Student Gender in the format 'Male' or 'Female':");
            details[3] = studentInput.nextLine();
            if (details[3].equals("Male")) {
                details[3] = "Male";
            } else if (details[3].equals("Female")) {
                details[3] = "Female";
            } else {
                details[3] = "Unknown";
            }
            this.students[studentCount] = new Student(details[0], details[1], details[2], details[3]);
            if (!students[studentCount].getSave()) {
                System.out.println("Error occurred. Student will not be saved.");
            }
        } else {
            System.out.println("Student Limit exceeded");
            returnToMenu();
        }
    }

    private Student searchStudents(String userSearch) { // function allowing the user to search and return student objects
        for (Student student : students) {
            if (student != null) {
                if (student.getName().contains(userSearch)) {
                    return student;
                }
            }
        }
        System.out.println("No student found.");
        return null;
    }

    private void printStudent(){ // print out a returned student object to the user
        System.out.println("Please enter the name of the student you want to search:");
        Scanner userInput = new Scanner(System.in);
        String userSearch = userInput.nextLine();
        Student student = searchStudents(userSearch);
        System.out.println(student != null ? student : "");
    }

    private void removeStudent(){ // remove a returned student object from the temporary data format
        String[] studentData = new String[maxStudents];
        System.out.println("Please enter the name of the student you want to remove:");
        Scanner userInput = new Scanner(System.in);
        String userSearch = userInput.nextLine();
        Student student = searchStudents(userSearch);
        if (student != null) {
            students[student.getID()] = null;
        }
        for (int i = 0; i < students.length; i++){  //students.length = number of sudents saved
            if (students[i] == null) continue;
            studentData[i] = students[i].toString();
        }
        System.out.println("Student successfully eradicated.");
        importStudent(studentData);
    }

    private void writeToFile() throws IOException { // overwrite old student text file with data from the temporary format
        int studentCount = Student.getStudentCount();
        FileWriter fw = new FileWriter("StudentDetails.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < studentCount; i++) {
            if (students[i] == null || students[i].getSave() == false) continue;
            String student = String.valueOf(students[i]);
            bw.write(student);
            bw.newLine();
        }
        bw.close();
    }
}
