package liuxl.unifiedLog;

import com.liuxl.handler.SpringApplicationContextHolder;
import com.liuxl.model.UnifiedResultDO;
import liuxl.unifiedLog.model.UnifiedReqDTO;
import liuxl.unifiedLog.service.CallService;
import liuxl.unifiedLog.service.UnifiedService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/6
 */
public class UnifiedLogTest {
    private static ApplicationContext factory;
    public static void loadSpring(){
        factory = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    public static void main(String[] args) {
        loadSpring();
        UnifiedService unifiedService = (UnifiedService) factory.getBean("unifiedService");
        CallService callService = SpringApplicationContextHolder.getSpringBean("callService");

        UnifiedReqDTO reqDTO = new UnifiedReqDTO();
        reqDTO.setName("liuxl");
        reqDTO.setId(111111L);
        reqDTO.setSleepTime(2000l);
        reqDTO.setNote("这是个测试内容");
        UnifiedResultDO resultDO = unifiedService.getUnified(reqDTO);
        System.out.println(resultDO.getErrCode());

        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
