package org.king2.blogs.locks;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 你爹写的类
 * @description   写文章的锁
 * @date 2020/2/13
 **/

public class BLogsWriteLock {

    public BLogsWriteLock() {
    }

    public static BLogsWriteLock bLogsWriteLock = new BLogsWriteLock();

    public static BLogsWriteLock getInstance(){
        return bLogsWriteLock;
    }

    private ReentrantReadWriteLock lock;

    public ReentrantReadWriteLock getLock(){
        if(lock == null){
            synchronized (this){
                if (lock == null){
                    this.lock = new ReentrantReadWriteLock();
                }
            }
        }
        return lock;
    }

    public void setLock(ReentrantReadWriteLock lock) {
        this.lock = lock;
    }
}
