package com.itself;
 public class ThreadDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        thread.start();//调用start方法才是开启一个线程去执行里面的输出方法
        thread.run();//此时执行通过main线程运行了Thread里面的输出方法，真正执行的线程还是主线程
    }
}