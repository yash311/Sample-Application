package com.example.myapplication;

class Person {
    String fName;
    String lName;
    String gender;
    double lat;
    double lon;
    int id;

    public Person(int i, String firstName, String lastName, String gen, double latitude, double longitude) {
        fName = firstName;
        lName = lastName;
        gender = gen;
        lat = latitude;
        lon = longitude;
        id = i;
    }

    public String toString() {
        return id + ", " + fName + ", " + lName + ", " + gender + ", lat:" + lat + ", lon:" + lon;
    }
}