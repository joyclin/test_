package sort;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;

/**
 * @author 李林
 * @date 2019/2/26
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {

    URL url = Main.class.getClassLoader().getResource("test");
    String filePath = url.getFile();
    for (int i = 0; i < 10; i++) {
      //测试十次
      sort(filePath);
      System.out.println("====================");
    }
    //sort(filePath);
  }

  private static void sort(String filePath) throws InterruptedException {
    File dir = new File(filePath);
    if (dir.isDirectory()) {
      File[] files = dir.listFiles();
      if (files != null && files.length > 0) {
        CountDownLatch latch = new CountDownLatch(files.length + 1);
        Manager manager = new Manager();
        for (File file : files) {
          manager.getExecutorService().execute(new Producer(file, manager.getQueue(), latch));
        }
        Consumer consumer = new Consumer(manager.getQueue(), latch, manager.getMap());
        //独立线程排序
        new Thread(consumer).start();
        latch.await();
        manager.getExecutorService().shutdown();
        Collection<Item> values = manager.getMap().values();
        for (Item item : values) {
          System.out.println(item);
        }
      }
    }
  }
}
