package single;

/**
 * 饿汉式单例
 *
 * @author 李林
 * @date 2019/2/26
 */
public class Singleton {

  private volatile static Singleton instance = null;

  private Singleton() {
  }

  public static Singleton getInstance() {
    if (instance == null) {
      synchronized (Singleton.class) {
        if (instance == null) {
          instance = new Singleton();
        }
      }
    }
    return instance;
  }

}
