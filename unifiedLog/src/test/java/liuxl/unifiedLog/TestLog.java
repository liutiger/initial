package liuxl.unifiedLog;

import com.liuxl.util.UnifiedLogLogger;
import org.apache.log4j.Logger;
import org.apache.log4j.UnifiedLogRollingFileAppender;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class TestLog {


    public static void main(String[] args) throws Throwable {

        final Logger logger = UnifiedLogLogger.getLogger();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    while (true) {
                        Thread.sleep(3000l);
                        logger.warn("cesss sssss ");
                        UnifiedLogRollingFileAppender appender = (UnifiedLogRollingFileAppender) logger.getAppender("unifiedLog");
                        appender.setNow();
                        appender.rollOver();
                        try {
                            throw new RuntimeException("dddddd");
                        } catch (Throwable e) {
                            logger.warn(e.getMessage(), e);
                            logger.error("error:" + e.getMessage(), e);

                        }


                        System.out.println("createLog success! ");
                    }
                } catch (Throwable e) {
                    e.printStackTrace();

                }
            }
        });

        thread.start();

        System.in.read();


    }

}
