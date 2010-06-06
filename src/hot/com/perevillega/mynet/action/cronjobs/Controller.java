/**
* My Net is free software, you can redistribute it and/or modify
* it under the terms of GNU Affero General Public License
* as published by the Free Software Foundation, either version 3
* of the License, or (at your option) any later version.
*
* You should have received a copy of the the GNU Affero
* General Public License, along with My Net. If not, see
*
* http://www.gnu.org/licenses/agpl.txt
*/

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