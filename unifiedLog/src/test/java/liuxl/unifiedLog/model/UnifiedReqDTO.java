package liuxl.unifiedLog.model;

import com.liuxl.model.UnifiedBaseDO;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/6
 */
public class UnifiedReqDTO extends UnifiedBaseDO {
    /**
     *
     */
    private String name;
    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String note;

    private Long  sleepTime;

    public Long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
