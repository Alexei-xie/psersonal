package com.itself;/**
 * @Author duJi
 * @Date ${YEAR}/${MONTH}/${DAY}
 */public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        thread.run();
    }
}