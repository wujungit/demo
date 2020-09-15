package com.example.demo.designpattern.singleton;

public class Computer {
    // 1、私有化Computer构造函数
    private Computer() {
        System.out.println("私有化Computer构造函数");
    }

    // 2、调用私有化Computer构造函数并将computer属性设置为static
    // volatile可以保证对线程下的可见性，即保证了子线程会跟子线程的一致
    private volatile static Computer computer;

    // 3、提供get()方法便于调用
    public Computer getComputer() {
        // 饿汉式，按需创建，即第一次使用的时候进行实例化对象
        if (computer == null) {
            synchronized (Computer.class) {
                if (computer == null) {
                    computer = new Computer();
                }
            }
        }
        return computer;
    }

    public void printInfo() {
        System.out.println("...");
    }
}
