package single;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Assert;
import org.junit.Test;

public class SingletonTest {

  @Test
  public void singletonTest() throws InterruptedException {
    test(50);
  }

  private static void test(int poolSize) throws InterruptedException {
    int singleton0HashCode = System.identityHashCode(Singleton.getInstance());
    ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);
    CountDownLatch start = new CountDownLatch(1);
    CountDownLatch end = new CountDownLatch(poolSize);
    for (int i = 0; i < poolSize; i++) {
      threadPool.execute(()->{
        try {
          start.await();
          Singleton instance = Singleton.getInstance();
          Assert.assertEquals(System.identityHashCode(instance), singleton0HashCode);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }finally {
          end.countDown();
        }
      });
    }
    start.countDown();
    end.await();
    threadPool.shutdown();
  }

}