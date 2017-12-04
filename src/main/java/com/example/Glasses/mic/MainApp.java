package com.example.Glasses.mic;

public class MainApp {

    public static void main(String[] args) {
        OutputParser parser = new OutputParser();
        System.out.println(parser.parse(3000));
    }
}
