package liuxl.unifiedLog.dao.impl;

import liuxl.unifiedLog.dao.UnifiedDao;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/6
 */
public class UnifiedDaoImpl implements UnifiedDao {
    @Override
    public String out(long id) {
        System.out.println("UnifiedDaoImpl.dao id :" + id);
        try {
            Thread.sleep(210l);
        } catch (Throwable e) {

        }
        return "UnifiedDaoImpl.dao id : " + id;
    }
}
