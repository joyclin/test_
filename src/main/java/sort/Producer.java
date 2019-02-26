package sort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * 读取txt内容 生产者
 *
 * @author 李林
 * @date 2019/2/26
 */
public class Producer implements Runnable {

  private File file;

  private BlockingQueue<Item> queue;

  private CountDownLatch latch;

  public Producer(File file, BlockingQueue<Item> queue, CountDownLatch latch) {
    this.file = file;
    this.queue = queue;
    this.latch = latch;
  }

  @Override
  public void run() {
    InputStreamReader reader = null;
    BufferedReader br = null;
    try {
      reader = new InputStreamReader(new FileInputStream(file), "utf-8");
      br = new BufferedReader(reader);
      String line;
      while ((line = br.readLine()) != null) {
        String[] itemString = line.split(",");
        Item item = new Item();
        item.setId(itemString[0]);
        item.setGroupId(itemString[1]);
        item.setQuota(Float.valueOf(itemString[2]));
        queue.add(item);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null){
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (br != null){
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      latch.countDown();
    }

  }
}
