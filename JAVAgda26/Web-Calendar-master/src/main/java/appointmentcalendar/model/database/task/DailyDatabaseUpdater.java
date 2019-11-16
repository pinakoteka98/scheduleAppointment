package appointmentcalendar.model.database.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * DailyDatabaseUpdater. Perform daily updates at one minute past midnight.
 */
public class DailyDatabaseUpdater implements Job {

	private Logger LOG = LogManager.getLogger();

	private List<DailyTask> dailyTasks;

	public DailyDatabaseUpdater() {
		dailyTasks = new ArrayList<>();
		dailyTasks.add(new AnalyticsUpdateTask());
		dailyTasks.add(new AddDayToRecordTask());
		dailyTasks.add(new CalendarUpdateTask()); // Must run last
	}

	/*
	 * (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext ex) throws JobExecutionException {

		for (DailyTask task : dailyTasks) {
			boolean success = task.performDailyUpdate();

			if (!success) {
				LOG.error("DailyDatabaseUpdater encountered an error executing " + task.getClass().getSimpleName());
				System.out.println("DailyDatabaseUpdater encountered an error executing " + task.getClass().getSimpleName());
			}
		}

		LOG.info("DailyDatabaseUpdater executed.");
		System.out.println("DailyDatabaseUpdater executed.");

	}

}
