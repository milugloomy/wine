package com.wine.back.controller;

import java.text.ParseException;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wine.base.common.MyResEntity;

import batch.common.BatchTask;
import batch.service.BatchManager;

@RestController
@RequestMapping("/batch")
public class BatchController{

	@Autowired
	private BatchManager batchManager;
	
	@RequestMapping(value = "/batchList")
	public MyResEntity batchList(){
		List<BatchTask> list=batchManager.getTaskList();
		return new MyResEntity(list);
	}
	
	@RequestMapping(value = "/pause")
	public MyResEntity pause(int id) throws SchedulerException{
		batchManager.pauseTask(id);
		return new MyResEntity();
	}
	
	@RequestMapping(value = "/resume")
	public MyResEntity resume(int id) throws SchedulerException{
		batchManager.resumeTask(id);
		return new MyResEntity();
	}
	
	@RequestMapping(value = "/runOnce")
	public MyResEntity runOnce(int id) throws SchedulerException{
		batchManager.runOnce(id);
		return new MyResEntity();
	}
	
	@RequestMapping(value = "/update")
	public void update(int id,String cron) throws ParseException, SchedulerException{
		batchManager.modifyTask(id, cron);
	}
	
}
