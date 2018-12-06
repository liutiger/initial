package com.liuxl.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.liuxl.advisor.UnifiedLogAdvisor;
import com.liuxl.constant.CharacterConstant;
import com.liuxl.enumType.RequsetTypeEnum;
import com.liuxl.model.UnifiedLogDO;
import com.liuxl.utils.DateUtil;
import com.liuxl.utils.StringUtil;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.UnifiedLogRollingFileAppender;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5x
 */
public class UnifiedLogLogger {

    private static Logger logger;
    private static Logger errorLogger;
    private static Logger resultLogger;

    private final static String UNIFIED_LOG_NAME = "unifiedLog";
    private final static String UNIFIED_LOG_FILE = "unifiedLog.log";

    private final static String ERROR_UNIFIED_LOG_NAME = "errorUnifiedLog";
    private final static String ERROR_UNIFIED_LOG_FILE = "error_unifiedLog.log";
    private final static String RESULT_UNIFIED_LOG_NAME = "resultUnifiedLog";
    private final static String RESULT_UNIFIED_LOG_FILE = "result_unifiedLog.log";


    private final static String UNIFIED_LOG_START = "INITIAL_UNIFIED_LOG_START_**********:";
    //%-d{yyyy-MM-dd HH:mm:ss}  %m%n
    private final static String PATTERN_LAYOUT = "%m%n";

    public static SerializerFeature[] features = {SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse};

    public static SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
    public static SimplePropertyPreFilter removeResultFilter = new SimplePropertyPreFilter();

    static {
        filter.getExcludes().add("param");
        removeResultFilter.getExcludes().add("result");
    }

    public static Logger getLogger() {
        return logger;
    }

    public static class UnifiedLogTask implements Runnable {
        private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

        private void resetAppenderByLogName(UnifiedLogRollingFileAppender appender) {
            try {

                if (appender != null) {
                    getSysLogger().warn("appendName:" + appender.getName());
                    appender.setNow();
                    appender.rollOver();
                } else {
                    getSysLogger().warn("not find appender: ");
                }
            } catch (Throwable e) {
                getSysLogger().error(e.getMessage(), e);
            }
        }

        @Override
        public void run() {
            getSysLogger().warn(" UnifiedLogRollingFileAppender start !");
            resetAppenderByLogName((UnifiedLogRollingFileAppender) logger.getAppender(UNIFIED_LOG_NAME));
            resetAppenderByLogName((UnifiedLogRollingFileAppender) errorLogger.getAppender(ERROR_UNIFIED_LOG_NAME));
            resetAppenderByLogName((UnifiedLogRollingFileAppender) resultLogger.getAppender(RESULT_UNIFIED_LOG_NAME));

        }

        public static void executorTask() {

            getSysLogger().warn(" UnifiedLogRollingFileAppender executorTask start !");

            Calendar calendar = Calendar.getInstance();
            //凌晨0点
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 1);
            Date date = calendar.getTime();
            if (date.before(new Date())) {
                date = DateUtil.addDay(date, 1);
            }

            UnifiedLogTask task = new UnifiedLogTask();
            ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                    new BasicThreadFactory.Builder().namingPattern("executorTask-unifiedLog-pool-%d").daemon(true).build());
            executorService.scheduleAtFixedRate(task, date.getTime(), PERIOD_DAY, TimeUnit.MILLISECONDS);

        }

    }

    static {
        try {
            init();
        } catch (Throwable e) {
            getSysLogger().error(e.getMessage(), e);
        }

    }

    private static UnifiedLogRollingFileAppender createUnifiedLogRollingFileAppenderByLoggerName(String logName, String logFile) {

        try {
            //additivity
            UnifiedLogRollingFileAppender appender = new UnifiedLogRollingFileAppender();

            String logFilePath = UnifiedLogUtil.getLogFilePath();
            //是否按照应用文件夹存放不同的日志文件：主要是解决多个应用放在同一个服务器上，日志在一个unifiedlog.log文件里面
            if (UnifiedLogAdvisor.isAsPartPath()) {
                logFilePath += UnifiedLogAdvisor.getAppName().concat(CharacterConstant.FORWARD_SLASH).concat(logFile);
            } else {
                logFilePath += logFile;
            }
            appender.setFile(logFilePath);
            appender.setDatePattern("'.'yyyy-MM-dd");

            PatternLayout layout = new PatternLayout(PATTERN_LAYOUT);
            appender.setMaxFileSize(UnifiedLogAdvisor.getMaxFileSize());
            appender.setName(logName);
            appender.setLayout(layout);
            appender.setAppend(true);
            appender.activateOptions();
            appender.rollOver();
            return appender;
        } catch (Throwable e) {
            getSysLogger().error("createUnifiedLoggerByLoggerName error ! ", e);
        }

        return null;
    }

    private static void init() throws Throwable {
        logger = Logger.getLogger(UNIFIED_LOG_NAME);
        logger.setAdditivity(false);
        logger.addAppender(createUnifiedLogRollingFileAppenderByLoggerName(UNIFIED_LOG_NAME, UNIFIED_LOG_FILE));
        logger.setLevel(Level.WARN);


        errorLogger = Logger.getLogger(ERROR_UNIFIED_LOG_NAME);
        errorLogger.setAdditivity(false);
        errorLogger.addAppender(createUnifiedLogRollingFileAppenderByLoggerName(ERROR_UNIFIED_LOG_NAME, ERROR_UNIFIED_LOG_FILE));
        errorLogger.setLevel(Level.WARN);

        resultLogger = Logger.getLogger(RESULT_UNIFIED_LOG_NAME);
        resultLogger.setAdditivity(false);
        resultLogger.addAppender(createUnifiedLogRollingFileAppenderByLoggerName(RESULT_UNIFIED_LOG_NAME, RESULT_UNIFIED_LOG_FILE));
        resultLogger.setLevel(Level.WARN);

        UnifiedLogTask.executorTask();

    }

    public static Logger getSysLogger() {
        return Logger.getLogger(UnifiedLogLogger.class);
    }

    public static void record(UnifiedLogDO unifiedLogDO, String message, Throwable e) {
        try {
            logger.error(UNIFIED_LOG_START + unifiedLogDO.getRequestId() + CharacterConstant.N + JSON.toJSONString(unifiedLogDO, removeResultFilter, features)
                    + (StringUtil.isBlank(message) ? "" : CharacterConstant.N + message), e);

            errorLogger.error(UNIFIED_LOG_START + unifiedLogDO.getRequestId() + CharacterConstant.N + JSON.toJSONString(unifiedLogDO, removeResultFilter, features)
                    + (StringUtil.isBlank(message) ? "" : CharacterConstant.N + message), e);

        } catch (Throwable fastjsone) {
            unifiedLogDO.clearParamAndResult();
            logger.error(UNIFIED_LOG_START + unifiedLogDO.getRequestId() + CharacterConstant.N + JSON.toJSONString(unifiedLogDO, removeResultFilter, features), e);

            unifiedLogDO.setExceptionCode(StringUtil.isBlank(fastjsone.getMessage()) ? fastjsone.toString() : fastjsone.getMessage());
            logger.error(UNIFIED_LOG_START + unifiedLogDO.getRequestId() + CharacterConstant.N
                    + " FAST_JSON_EXCEPTION" + JSON.toJSONString(unifiedLogDO, removeResultFilter, features), fastjsone);

            errorLogger.error(UNIFIED_LOG_START + unifiedLogDO.getRequestId() + CharacterConstant.N + JSON.toJSONString(unifiedLogDO, removeResultFilter, features), e);

            unifiedLogDO.setExceptionCode(StringUtil.isBlank(fastjsone.getMessage()) ? fastjsone.toString() : fastjsone.getMessage());
            errorLogger.error(UNIFIED_LOG_START + unifiedLogDO.getRequestId() + CharacterConstant.N
                    + " FAST_JSON_EXCEPTION" + JSON.toJSONString(unifiedLogDO, removeResultFilter, features), fastjsone);

        }
    }

    public static void record(UnifiedLogDO unifiedLogDO, String message) {
        try {
            if (unifiedLogDO.getResult() != null) {
                resultLogger.warn(UNIFIED_LOG_START + unifiedLogDO.getRequestId()
                        + CharacterConstant.N + JSON.toJSONString(unifiedLogDO, filter, features)
                );
            }

            unifiedLogDO.setResult(null);
            logger.warn(UNIFIED_LOG_START + unifiedLogDO.getRequestId() + CharacterConstant.N + JSON.toJSONString(unifiedLogDO, removeResultFilter, features)
                    + (StringUtil.isBlank(message) ? "" : CharacterConstant.N + message));
            if (unifiedLogDO != null && unifiedLogDO.getUseTime() >= UnifiedLogAdvisor.getMaxUseTime()
                    && RequsetTypeEnum.HTTP.getType().equals(unifiedLogDO.getRequestType())) {
                errorLogger.warn(UNIFIED_LOG_START + unifiedLogDO.getRequestId() + CharacterConstant.N + JSON.toJSONString(unifiedLogDO, removeResultFilter, features)
                        + (StringUtil.isBlank(message) ? "" : CharacterConstant.N + message));
            }

        } catch (Throwable e) {
            unifiedLogDO.clearParamAndResult();
            logger.warn(UNIFIED_LOG_START + unifiedLogDO.getRequestId() + CharacterConstant.N +
                    JSON.toJSONString(unifiedLogDO, features) + (StringUtil.isBlank(message) ? "" : CharacterConstant.N + message));
            unifiedLogDO.setExceptionCode(StringUtil.isBlank(e.getMessage()) ? e.toString() : e.getMessage());
            logger.error(UNIFIED_LOG_START + unifiedLogDO.getRequestId() + CharacterConstant.N +
                    " FAST_JSON_EXCEPTION" + JSON.toJSONString(unifiedLogDO, features) + (StringUtil.isBlank(message) ? "" : CharacterConstant.N + message), e);
            errorLogger.error(UNIFIED_LOG_START + unifiedLogDO.getRequestId() + CharacterConstant.N +
                    " FAST_JSON_EXCEPTION" + JSON.toJSONString(unifiedLogDO, features) + (StringUtil.isBlank(message) ? "" : CharacterConstant.N + message), e);
        }
    }

    public static void record(String message, Throwable e) {
        logger.error(UNIFIED_LOG_START + message, e);
        errorLogger.error(UNIFIED_LOG_START + message, e);
    }

    public static void record(String message) {
        logger.warn(UNIFIED_LOG_START + message);
    }
}
