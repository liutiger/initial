package liuxl.unifiedLog.service.impl;

import liuxl.unifiedLog.service.CallService;
import liuxl.unifiedLog.service.UnifiedService;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/6
 */
public class CallServiceImpl implements CallService {

    private UnifiedService unifiedService;

    public void setUnifiedService(UnifiedService unifiedService) {
        this.unifiedService = unifiedService;
    }

    @Override
    public String getCall(String uuid) {
        unifiedService.getUnifiedStr("33333");
        System.out.println("CallServiceImpl.getCall 调用链" + uuid);
        return uuid + "getCall";
    }
}
