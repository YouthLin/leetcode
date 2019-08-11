package com.youthlin.leetcode.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @author : youthlin.chen @ 2019-07-31 21:19
 */
public class PrintInOrder {
    /**
     * 不论先调用哪个方法，始终 first 先于 second 先于 third 运行。
     */
    class Foo {
        private CountDownLatch two = new CountDownLatch(1);
        private CountDownLatch three = new CountDownLatch(1);

        public Foo() {
        }

        public void first(Runnable printFirst) throws InterruptedException {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            two.countDown();
        }

        public void second(Runnable printSecond) throws InterruptedException {
            two.await();
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            three.countDown();
        }

        public void third(Runnable printThird) throws InterruptedException {
            three.await();
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }

    /**
     * 打印 boobar n 次
     */
    class FooBar {
        private int n;

        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; i++) {

                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {

            for (int i = 0; i < n; i++) {

                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
            }
        }
    }
}
