package com.example.demo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的方式三：实现callable接口。---JDK 5.0新增
 * 是否多线程？否，就一个线程
 * <p>
 * 比runable多一个FutureTask类，用来接收call方法的返回值。
 * 适用于需要从线程中接收返回值的形式
 * <p>
 * //callable实现新建线程的步骤：
 * 1.创建一个实现callable的实现类
 * 2.实现call方法，将此线程需要执行的操作声明在call（）中
 * 3.创建callable实现类的对象
 * 4.将callable接口实现类的对象作为传递到FutureTask的构造器中，创建FutureTask的对象
 * 5.将FutureTask的对象作为参数传递到Thread类的构造器中，创建Thread对象，并调用start方法启动（通过FutureTask的对象调用方法get获取线程中的call的返回值）
 */

//实现callable接口的call方法
public class Window2 implements Callable {

    private int sum = 0;//

    //可以抛出异常
    @Override
    public Object call() throws Exception {
        for (int i = 0; i <= 100; i++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
                sum += i;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        //new一个实现callable接口的对象
        Window2 numThread = new Window2();

        //通过futureTask对象的get方法来接收futureTask的值
        FutureTask futureTask = new FutureTask(numThread);

        Thread t1 = new Thread(futureTask);
        t1.setName("线程1");
        t1.start();

        try {
            //get返回值即为FutureTask构造器参数callable实现类重写的call的返回值
            Object sum = futureTask.get();
            System.out.println(Thread.currentThread().getName() + ":" + sum);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
