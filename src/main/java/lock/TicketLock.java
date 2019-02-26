package lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Q：互斥锁和自旋锁有什么差别
 * 互斥锁：当一个线程获取另一个线程持有的锁时，cpu会进入上下文切换，当前线程休眠，置入等待队列中;
 * 自旋锁：当一个线程获取另一个线程持有的锁时，当前线程不会让出cpu，一直运行，知道得到这个锁为止;
 *
 * @author 李林
 * @date 2019/2/26
 */
public class TicketLock {

  private AtomicInteger service;
  private AtomicInteger owner;
  private ThreadLocal<Integer> local;

  public TicketLock() {
    service = new AtomicInteger();
    owner = new AtomicInteger();
    local = new ThreadLocal<>();
  }


  public void lock() {
    int ownerNum = owner.getAndIncrement();
    while (ownerNum != service.get()) {
      //do nothing
    }
    local.set(ownerNum);

  }

  public void unlock() {
    Integer ownerNum = local.get();
    service.compareAndSet(ownerNum, ++ownerNum);
  }

}
