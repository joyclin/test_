package sort;

import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author joyce
 * @date 2019/2/26
 */
public class Manager {

  private final static int POOL_SIZE = 10;

  private BlockingQueue<Item> queue;

  private TreeMap<String, Item> map;

  private ExecutorService executorService;

  public Manager() {
    this.queue = new LinkedBlockingQueue<>();
    this.map = new TreeMap<String, Item>();
    executorService = Executors.newFixedThreadPool(POOL_SIZE);
  }

  public BlockingQueue<Item> getQueue() {
    return queue;
  }

  public void setQueue(BlockingQueue<Item> queue) {
    this.queue = queue;
  }

  public TreeMap<String, Item> getMap() {
    return map;
  }

  public void setMap(TreeMap<String, Item> map) {
    this.map = map;
  }

  public ExecutorService getExecutorService() {
    return executorService;
  }

  public void setExecutorService(ExecutorService executorService) {
    this.executorService = executorService;
  }
}
