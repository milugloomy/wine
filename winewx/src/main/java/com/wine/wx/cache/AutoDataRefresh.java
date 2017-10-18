package com.wine.wx.cache;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.PostConstruct;

public abstract class AutoDataRefresh{

	private Long lastLoadTime;
	protected Long period;
	private ReentrantReadWriteLock rwl;
	
	public AutoDataRefresh() {
		lastLoadTime=0l;
		rwl=new ReentrantReadWriteLock();
	}
	
	@PostConstruct
	public abstract void setPeriod();
	public abstract void updateCache();
	
	public void lockCache(){
		if((System.currentTimeMillis()-lastLoadTime)>period){
			rwl.writeLock().lock();
			try{
				lastLoadTime=System.currentTimeMillis();
				updateCache();
			}finally{
				rwl.writeLock().unlock();
			}
		}
		rwl.readLock().lock();
	}
	public void unlockCache(){
		rwl.readLock().unlock();
	}

	public void setLastLoadTime(Long lastLoadTime) {
		this.lastLoadTime = lastLoadTime;
	}

}
