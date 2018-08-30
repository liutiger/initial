package com.liuxl.scaffold.common.log;

import com.alibaba.fastjson.JSON;
import com.liuxl.sacffold.common.util.StringUtil;
import com.liuxl.sacffold.domain.core.Result;
import com.liuxl.sacffold.domain.dto.BaseDTO;
import com.liuxl.sacffold.domain.query.BaseQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:日志
 *
 * @author liuxl
 * @date 2018/8/30
 */
public class CenterLogger {
    public static Logger getLogger() {
        return LogManager.getLogger("centerLog");
    }
    public static void record(String logMsg, Result result) {
        if (result.isSuccess()) {
            getLogger().warn(logMsg);
        } else {
            getLogger().error(logMsg);
        }

    }

    public static Logger getAopMonitorLog() {
        return LogManager.getLogger("aopMonitorLog");
    }

    @SuppressWarnings("rawtypes")
    public static void record(String service, Result result, BaseDTO param) {
        record(service, result, param, 0);
    }


    public static void recordForQuery(String service, Result result, BaseQuery param, long useTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("**********User " + service + "**********\n");
        sb.append("param:\n");
        sb.append("    " + JSON.toJSON(param) + "\n");
        if (!result.isSuccess()) {
            sb.append("errCode:\n");
            sb.append("    " + result.getErrCode() + "\n");
            sb.append("errMsg:\n");
            sb.append("    " + result.getErrMsg() + "\n");
            sb.append("result:\n");
            sb.append("    " + service + " is fail\n");
        } else {
            sb.append("result:\n");
            if (StringUtil.isNotBlank(result.getErrCode())) {
                sb.append("errCode:\n");
                sb.append("    " + result.getErrCode() + "\n");
            }
            sb.append("    " + service + " is success\n");
        }
        if (useTime > 0) {
            sb.append(service).append(" useTime: ").append(useTime).append("ms \n");
        }
        sb.append("**********");
        getLogger().warn(sb);
    }

    public static void record(String service, Result result, BaseDTO param, long useTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("**********User " + service + "**********\n");
        sb.append("param:\n");
        sb.append("    " + JSON.toJSON(param) + "\n");
        if (!result.isSuccess()) {
            sb.append("errCode:\n");
            sb.append("    " + result.getErrCode() + "\n");
            sb.append("errMsg:\n");
            sb.append("    " + result.getErrMsg() + "\n");
            sb.append("result:\n");
            sb.append("    " + service + " is fail\n");
        } else {
            sb.append("result:\n");
            if (StringUtil.isNotBlank(result.getErrCode())) {
                sb.append("errCode:\n");
                sb.append("    " + result.getErrCode() + "\n");
            }
            sb.append("    " + service + " is success\n");
        }
        if (useTime > 0) {
            sb.append(service).append(" useTime: ").append(useTime).append("ms \n");
        }
        sb.append("**********");
        getLogger().warn(sb);
    }

    public static void record(String service, Result result, BaseDTO param, Logger logger) {
        record(service, result, param, logger, 0);

    }

    public static void record(String service, Result result, BaseDTO param, Logger logger, long useTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("**********User " + service + "**********\n");
        sb.append("param:\n");
        sb.append("    " + JSON.toJSON(param) + "\n");
        if (!result.isSuccess()) {
            sb.append("errCode:\n");
            sb.append("    " + result.getErrCode() + "\n");
            sb.append("errMsg:\n");
            sb.append("    " + result.getErrMsg() + "\n");
            sb.append("result:\n");
            sb.append("    " + service + " is fail\n");
        } else {
            sb.append("result:\n");
            if (StringUtil.isNotBlank(result.getErrCode())) {
                sb.append("errCode:\n");
                sb.append("    " + result.getErrCode() + "\n");
            }
            sb.append("    " + service + " is success\n");
        }
        if (useTime > 0) {
            sb.append(service).append(" useTime: ").append(useTime).append("ms \n");
        }

        sb.append("**********");
        if (result.isSuccess()) {
            logger.warn(sb);
        } else {
            logger.error(sb);
        }

    }
}
