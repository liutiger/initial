package liuxl.task;

import com.liuxl.utils.DateUtil;

import java.util.Date;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class Task implements Runnable {

    Task() {

    }

    @Override
    public void run() {
        System.out.println(" my name is liuxl ! I  Love  You ! this is time " + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}
