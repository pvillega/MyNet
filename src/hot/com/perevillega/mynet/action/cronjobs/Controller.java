package com.perevillega.mynet.action.cronjobs;

import java.io.Serializable;
import java.util.Date;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.async.QuartzTriggerHandle;
import org.jboss.seam.log.Log;

@Name("controller")
@AutoCreate
public class Controller implements Serializable {

	private static final long serialVersionUID = 7609983147081676186L;

	@In
	CronProcessor processor;

	@Logger
	Log log;

	@SuppressWarnings("unused")
	private QuartzTriggerHandle quartzTestTriggerHandle;
	
	//we run jobs every 30 minutes
	private static String CRON_INTERVAL = "0 30 * * * ?";
	//test string, runs each minute
	//private static String CRON_INTERVAL = "0 0/5 * * * ?";

	public void scheduleTimer() {
		quartzTestTriggerHandle = processor.runJobs(new Date(),CRON_INTERVAL);		
	}
}