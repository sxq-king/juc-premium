package org.example;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        new Thread(() -> {

        }, "t1").start();
    }

}
