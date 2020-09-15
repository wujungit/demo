package com.example.demo.thread;

/**
 * 创建三个窗口卖票，总票数为100张，使用继承自Thread方式
 * 用静态变量保证三个线程的数据独一份
 * <p>
 * 存在线程的安全问题，有待解决
 */
public class Window extends Thread {
    private static int ticket = 100; //将其加载在类的静态区，所有线程共享该静态变量

    @Override
    public void run() {
        while (true) {
            if (ticket > 0) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getName() + "当前售出第" + ticket + "张票");
                ticket--;
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Window t1 = new Window();
        Window t2 = new Window();
        Window t3 = new Window();

        t1.setName("售票口1");
        t2.setName("售票口2");
        t3.setName("售票口3");

        t1.start();
        t2.start();
        t3.start();
    }
}
