package com.theah64.cartwatcher.lab;


import java.io.IOException;

public class MyClass {

    public static void main(String[] args) throws IOException {

        int start = 800;
        int now = 1400;
        int end = 1200;

        System.out.println(TimeUtils.getPercentageFinished(start, now, end));
    }

}
