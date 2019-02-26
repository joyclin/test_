package sort;

/**
 * @author 李林
 * @date 2019/2/26
 */
public class Item implements Comparable<Item> {

  private String id;

  private String groupId;

  private Float quota;

  @Override
  public String toString() {
    return groupId + "," + id + "," + quota;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public Float getQuota() {
    return quota;
  }

  public void setQuota(Float quota) {
    this.quota = quota;
  }

  @Override
  public int compareTo(Item o) {
    return Long.valueOf(groupId).compareTo(Long.valueOf(o.getGroupId()));
  }
}
