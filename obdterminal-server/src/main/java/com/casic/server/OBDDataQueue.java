package com.casic.server;

import com.casic.entity.OBDDataChanged;
import java.util.LinkedList;
import java.util.Queue;

public class OBDDataQueue
{
  private Queue<OBDDataChanged[]> queue;
  private static OBDDataQueue instance;

  private OBDDataQueue(int size)
  {
    this.queue = new LinkedList<OBDDataChanged[]>();
  }
  public static synchronized OBDDataQueue getInstance() {
    if (instance == null) {
      instance = new OBDDataQueue(100);
    }
    return instance;
  }

  public synchronized void add(OBDDataChanged[] data) {
    this.queue.offer(data);
  }

  public synchronized OBDDataChanged[] get()
  {
    OBDDataChanged[] data = (OBDDataChanged[])this.queue.poll();
    return data;
  }

  public synchronized int getLength() {
    return this.queue.size();
  }
}