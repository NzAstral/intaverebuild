package de.jpx3.intave.event.feedback;

public final class Request<T> {
  private final Callback<T> Callback;
  private final T obj;
  private final short key;
  private final long time;
  private final long num;

  Request(Callback<T> Callback, T obj, short key, long num) {
    this.Callback = Callback;
    this.obj = obj;
    this.key = key;
    this.num = num;
    this.time = System.currentTimeMillis();
  }

  public Callback<T> callback() {
    return Callback;
  }

  public T obj() {
    return obj;
  }

  public long passedTime() {
    return System.currentTimeMillis() - this.time;
  }

  public short key() {
    return key;
  }

  public long num() {
    return num;
  }

  public long requested() {
    return time;
  }
}