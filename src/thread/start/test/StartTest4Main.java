package thread.start.test;

import static util.MyLogger.log;

public class StartTest4Main {
    public static void main(String[] args) {
        Thread threadA = new Thread(
                () -> {
                    while(true){
                        log("A");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, "Thread-A"
        );
        Thread threadB = new Thread(() -> {
            while(true){
                log("B");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Thread-B");
        threadA.start();
        threadB.start();
    }
}
