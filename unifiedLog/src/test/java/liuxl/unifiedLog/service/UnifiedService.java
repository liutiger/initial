package liuxl.unifiedLog.service;

import com.liuxl.model.UnifiedResultDO;
import liuxl.unifiedLog.model.UnifiedReqDTO;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018 /12/6
 */
public interface UnifiedService {

    /**
     * Gets unified.
     *
     * @param reqDTO the req dto
     * @return the unified
     */
    UnifiedResultDO getUnified(UnifiedReqDTO reqDTO);

    /**
     * Gets unified str.
     *
     * @param id the id
     * @return the unified str
     */
    UnifiedResultDO getUnifiedStr(String id);
}
