package liuxl.task;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:测试定时任务
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class ExecutorTask {

    //时间间隔(一天)
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

    public ExecutorTask() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21); //凌晨1点
        calendar.set(Calendar.MINUTE, 12);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime(); //第一次执行定时任务的时间

        //如果第一次执行定时任务的时间 小于当前的时间
        //此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
//        if (date.before(new Date())) {
//            date = this.addDay(date, 1);
//        }
        Task task = new Task();
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("executorTask-unifiedLog-pool-%d").daemon(true).build());
        executorService.scheduleAtFixedRate(task, date.getTime(), PERIOD_DAY, TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(100000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 增加或减少天数
    public Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    public static void main(String[] args) {
        new ExecutorTask();
    }
}
