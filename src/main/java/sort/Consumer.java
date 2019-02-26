package sort;

import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * 排序item，消费者
 *
 * @author 李林
 * @date 2019/2/26
 */
public class Consumer implements Runnable {

  private BlockingQueue<Item> queue;

  private CountDownLatch latch;

  private TreeMap<String, Item> map;

  public Consumer(BlockingQueue<Item> queue, CountDownLatch latch, TreeMap<String, Item> map) {
    this.queue = queue;
    this.latch = latch;
    this.map = map;
  }

  @Override
  public void run() {
    try {
      while (true) {
        if (!queue.isEmpty()) {
          Item item = queue.take();
          Item mapItem = map.get(item.getGroupId());
          if (mapItem == null) {
            map.put(item.getGroupId(), item);
          } else {
            if (item.getQuota().compareTo(mapItem.getQuota()) < 0) {
              //存入quota较小的数据
              map.put(item.getGroupId(), item);
            }
          }
        }
        if (latch.getCount() <= 1) {
          latch.countDown();
          break;
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
