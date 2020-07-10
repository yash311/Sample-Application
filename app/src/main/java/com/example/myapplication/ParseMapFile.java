package com.example.myapplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ParseMapFile {
    String file;
    ArrayList<Person> maleList;
    ArrayList<Person> femaleList;

    public ParseMapFile(String filePath) {
        file = filePath;
        maleList = new ArrayList<>();
        femaleList = new ArrayList<>();
        parse();
    }

    private void parse() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            int count = 0;
            br.readLine();
            String line = br.readLine();

            while (line != null) {
                String[] s = line.split(",");
                if (s[3].equals("Female")) {
                    femaleList.add(new Person(Integer.parseInt(s[0]), s[1], s[2], s[3], Double.parseDouble(s[4]), Double.parseDouble(s[5])));
                } else if (s[3].equals("Male")) {
                    maleList.add(new Person(Integer.parseInt(s[0]), s[1], s[2], s[3], Double.parseDouble(s[4]), Double.parseDouble(s[5])));
                }
                line = br.readLine();
                count++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Person> getMaleList() {
        return maleList;
    }

    public ArrayList<Person> getFemaleList() {
        return femaleList;
    }
}
