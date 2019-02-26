package lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Assert;
import org.junit.Test;

public class TicketLockTest {

  private static int num = 0;

  @Test
  public void ticketLockTest() throws InterruptedException {
    test(100);
  }

  private void test(int poolSize) throws InterruptedException {
    ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);
    CountDownLatch start = new CountDownLatch(1);
    CountDownLatch end = new CountDownLatch(poolSize);
    TicketLock lock = new TicketLock();
    for (int i = 0; i < poolSize; i++) {
      threadPool.execute(() -> {
        try {
          start.await();
          lock.lock();
          increment(poolSize);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          lock.unlock();
          end.countDown();
        }
      });
    }
    start.countDown();
    end.await();
    threadPool.shutdown();
    Assert.assertEquals(num, poolSize * poolSize * 10);
  }

  private static void increment(int n) throws InterruptedException {
    //为测试线程安全，这里扩大循环次数
    int size = 10 * n;
    for (int i = 0; i < size; i++) {
      num++;
    }
  }

}