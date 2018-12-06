package liuxl.unifiedLog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.liuxl.model.UnifiedResultDO;
import com.liuxl.utils.UUIDUtil;
import liuxl.unifiedLog.dao.UnifiedDao;
import liuxl.unifiedLog.model.UnifiedReqDTO;
import liuxl.unifiedLog.service.CallService;
import liuxl.unifiedLog.service.UnifiedService;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/6
 */
public class UnifiedServiceImpl implements UnifiedService {

    private UnifiedDao unifiedDao;

    private CallService callService;

    public void setUnifiedDao(UnifiedDao unifiedDao) {
        this.unifiedDao = unifiedDao;
    }

    public void setCallService(CallService callService) {
        this.callService = callService;
    }

    @Override
    public UnifiedResultDO getUnified(UnifiedReqDTO reqDTO) {
        System.out.println("入参:" + JSONObject.toJSONString(reqDTO));
        UnifiedResultDO unifiedResultDO = new UnifiedResultDO();
        unifiedResultDO.setErrCode("NOT_FIND_USER");
        unifiedResultDO.setSuccess(false);
        callService.getCall(UUIDUtil.getUUID(false));
        this.getUnifiedStr("test");
        this.getUnifiedStr("test");
        this.getUnifiedStr("test");
        unifiedDao.out(111);
        try{
            Thread.sleep(reqDTO.getSleepTime());
            System.out.println("UnifiedResultDO getUnified(UnifiedReqDTO userReqDTO) waiting time is over !");
        }catch (Throwable e){

        }
        return unifiedResultDO;
    }

    @Override
    public UnifiedResultDO getUnifiedStr(String id) {
        System.out.println("UnifiedResultDO getUnified(String id):" + id);
        return null;
    }
}
