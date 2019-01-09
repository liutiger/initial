package com.liuxl.advisor;

import com.liuxl.enumType.RequsetTypeEnum;
import com.liuxl.model.UnifiedBaseDO;
import com.liuxl.model.UnifiedLogDO;
import com.liuxl.model.UnifiedResultDO;
import com.liuxl.util.LocalThreadPool;
import com.liuxl.util.UnifiedLogLogger;
import com.liuxl.util.UnifiedLogProfiler;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class UnifiedLogAdvisor implements MethodInterceptor {

    private static Set<String> ignoredException = new HashSet<String>();

    private static String localAppName;

    private static boolean asPartPath = false;

    private static int maxFileSize = 7;

    private static long maxUseTime = 3000;

    private final static String SERVICE_CALL_FIELD = "ServiceCall@";

    public static boolean isAsPartPath() {
        return asPartPath;
    }

    public void setAsPartPath(boolean asPartPath) {
        UnifiedLogAdvisor.asPartPath = asPartPath;
    }

    public void setLocalAppName(String localAppName) {
        UnifiedLogAdvisor.localAppName = localAppName;
    }

    public static String getLocalAppName() {
        return localAppName;
    }

    public static String getAppName() {
        return localAppName;
    }

    public static int getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(int maxFileSize) {
        UnifiedLogAdvisor.maxFileSize = maxFileSize;
    }

    private void recordClientCallMessage(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof UnifiedBaseDO) {
                ((UnifiedBaseDO) arg).enterUnifiedLog();
                LocalThreadPool.setRequestIdWhenIsNull(((UnifiedBaseDO) arg).getRequestUnifiedId());
                LocalThreadPool.setClientIpWhenIsNull(((UnifiedBaseDO) arg).getClientIp());
            }
        }
    }

    private void injectionRequestId(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof UnifiedBaseDO) {
                ((UnifiedBaseDO) arg).enterUnifiedLog();
            }
        }

    }

    //如果是接口或者是代理
    public boolean isProxyOrInterface(MethodInvocation methodInvocation) {
        return methodInvocation.getThis().getClass().isInterface() || methodInvocation.getThis().getClass().getName().contains("com.sun.proxy");

    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        String interfaceName = "";
        if (isProxyOrInterface(methodInvocation)) {
            interfaceName = methodInvocation.getMethod().getDeclaringClass().getName() + "." + methodInvocation.getMethod().getName();
        } else {
            interfaceName = methodInvocation.getThis().getClass().getName() + "." + methodInvocation.getMethod().getName();
        }

        //抛弃springframework的类的拦截处理
        if (interfaceName.contains("springframework") || interfaceName.contains("java.lang.Object") || interfaceName.contains(".toString")) {
            try {
                return methodInvocation.proceed();
            } catch (Throwable e) {
                throw e;
            }
        }

        Object result = null;
        Throwable failed = null;

        try {
            //捕获自己的异常  ，防止异常导致正常业务；
            if (UnifiedLogProfiler.getEntry() == null) {
                //第一次进入  需要记录请求的 requestId 和 clientIp
                recordClientCallMessage(methodInvocation.getArguments());
                UnifiedLogDO unifiedLogDO = new UnifiedLogDO(null, interfaceName, methodInvocation.getArguments(),
                        RequsetTypeEnum.SERVICE.getType());
                UnifiedLogProfiler.start(SERVICE_CALL_FIELD + interfaceName, unifiedLogDO);
            } else {
                //如果是接口调用 需要对 UnifiedBaseDO 注入 requestId 和 clientIp
                if (isProxyOrInterface(methodInvocation)) {
                    injectionRequestId(methodInvocation.getArguments());
                }
                UnifiedLogProfiler.enter(interfaceName);
            }
        } catch (Throwable e) {

        }
        try {
            result = methodInvocation.proceed();
        } catch (Throwable e) {
            failed = e;
            throw e;
        } finally {
            try {
                UnifiedLogProfiler.release();
                printMonitorTimeLog(failed, result);
            } catch (Throwable e) {
                UnifiedResultDO unifiedResultDO = new UnifiedResultDO();
                unifiedResultDO.setSuccess(false);
                unifiedResultDO.setErrCode(e.getMessage());
                printMonitorTimeLog(failed, unifiedResultDO);
            } finally {
                UnifiedLogProfiler.reset("ServiceCall@" + interfaceName);
            }
        }

        return result;
    }

    private void printMonitorTimeLog(Throwable e, Object result) {

        UnifiedLogDO unifiedLogDO = null;
        String detail = null;

        try {
            if (UnifiedLogProfiler.getEntry() == null) {
                return;
            }
            if (UnifiedLogProfiler.getEntry().getEndTime() <= 0) {
                return;
            }
            if (UnifiedLogProfiler.getEntry().getUnifiedLogDO() == null) {
                return;
            }
            unifiedLogDO = UnifiedLogProfiler.getEntry().getUnifiedLogDO();

            detail = UnifiedLogProfiler.dump("Detail: ", "        ");
            unifiedLogDO.setUseTime(UnifiedLogProfiler.getDuration());
            unifiedLogDO.setResult(result);
            if (e != null && !isIngoreException(e)) {
                unifiedLogDO.setExceptionCode(e.getMessage());
                UnifiedLogLogger.record(unifiedLogDO, detail, e);
            } else {
                UnifiedLogLogger.record(unifiedLogDO, detail);
            }
        } catch (Throwable unfieldLogE) {

            if(unifiedLogDO!=null){
                unifiedLogDO.setExceptionCode(unfieldLogE.getMessage());
            }


            UnifiedLogLogger.record(unifiedLogDO,detail,unfieldLogE);
        }

    }

    public void setIngoreException(Set<String> ingoreException) {
        UnifiedLogAdvisor.ignoredException = ingoreException;
    }


    private static boolean isIngoreException(Throwable e) {
        if (ignoredException == null || ignoredException.isEmpty()) {
            return false;
        }

        if (e == null) {
            return true;
        }

        return ignoredException.contains(e.getClass().getName());

    }

    public static long getMaxUseTime() {
        return maxUseTime;
    }

    public void setMaxUseTime(int maxUseTime) {
        UnifiedLogAdvisor.maxUseTime = maxUseTime;
    }
}
