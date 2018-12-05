package com.liuxl.model.http;

import com.liuxl.enumType.ErrorCodeEnum;
import com.liuxl.exception.CommonException;
import com.liuxl.utils.FileUtil;
import com.liuxl.utils.ObjectUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class EWebRequestDTO extends InputDTO {
    static final String loadPathName = "vm";
    private String requestVm = "";
    private String requestUrl = "";
    private String beanId;
    private String moduleName = "";
    private String mainVm = "";
    private String doAction;
    private String doMethod;
    private Map<String, Object> requestParam = null;
    private boolean isHtmRequest = true;
    private String requestUrlName = "";

    public EWebRequestDTO(String requestUrl) {
        this.requestUrl = requestUrl;
        this.isHtmRequest = Pattern.compile("^[\\w\\W]*.htm$").matcher(requestUrl).matches();
        String[] urlArr = this.requestUrl.split("/");
        this.requestUrlName = urlArr[urlArr.length - 1];
        this.beanId = urlArr[urlArr.length - 1].replace(".htm", "");
        this.requestVm = urlArr[urlArr.length - 1].replace(".htm", ".vm");
        this.moduleName = urlArr[urlArr.length - 2];
        this.doAction = this.doAction;
        this.doMethod = this.doMethod;
        this.requestParam = new HashMap();
        if (this.isHtmRequest) {
            this.init();
        }

    }

    public void init() {
        boolean requestFileIsExists = FileUtil.checkFileIsExists(this.getAllVmPath(this.getRequestVmPath()));
        if (!requestFileIsExists) {
            throw new CommonException(ErrorCodeEnum.NOT_FOUND_REQUESTVM.getErrCode());
        } else {
            boolean requestParentFileIsExists = FileUtil.checkFileIsExists(this.getAllVmPath(this.getParentRequestVmPath()));
            if (requestParentFileIsExists) {
                this.mainVm = this.getParentRequestVmPath();
            } else {
                boolean requestDefaultFileIsExists = FileUtil.checkFileIsExists(this.getAllVmPath(this.getDefaultVm()));
                if (requestDefaultFileIsExists) {
                    this.mainVm = this.getDefaultVm();
                } else {
                    boolean requestParentDefaultFileIsExists = FileUtil.checkFileIsExists(this.getAllVmPath(this.getDefaultVM()));
                    if (requestParentDefaultFileIsExists) {
                        this.mainVm = this.getDefaultVM();
                    } else {
                        throw new CommonException(ErrorCodeEnum.NOT_FOUND_REQUESTVM.getErrCode());
                    }
                }
            }
        }
    }

    public String getDefaultVM() {
        return EWebServletContext.getTerminal() + File.separator + "default" + File.separator + "layout" + File.separator + "default.vm";
    }

    public String getMainVm() {
        return this.mainVm;
    }

    public String getDefaultVm() {
        return EWebServletContext.getTerminal() + File.separator + this.moduleName + File.separator + "layout" + File.separator + "default.vm";
    }

    public String getParentRequestVmPath() {
        return EWebServletContext.getTerminal() + File.separator + this.moduleName + File.separator + "layout" + File.separator + this.requestVm;
    }

    public String getBeanId() {
        return this.beanId;
    }

    public String getDoAction() {
        return this.doAction;
    }

    public String getDoMethod() {
        return this.doMethod;
    }

    public boolean canDoAction() {
        return !ObjectUtil.isEmpty(this.doAction);
    }

    public String getAllVmPath(String filePath) {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.WEBPATH);
        sb.append(File.separator);
        sb.append("vm").append(File.separator).append(filePath);
        return sb.toString();
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public String getRequestUrl() {
        return this.requestUrl;
    }

    public String getRequestVm() {
        return this.requestVm;
    }

    public String getRequestVmPath() {
        return EWebServletContext.getTerminal() + this.moduleName + File.separator + "screen" + File.separator + this.requestVm;
    }

    @Override
    void put(String key, Object val) {
        if ("do_action".equals(key)) {
            this.doAction = (String) val;
        }

        if ("do_method".equals(key)) {
            this.doMethod = (String) val;
        }

        this.requestParam.put(key, val);
    }

    @Override
    Object get(String key) {
        return this.requestParam.get(key);
    }

    public String getHttpRequestHtm() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getModuleName()).append("/").append(this.requestUrlName);
        return sb.toString();
    }

    public Map<String, Object> getRequestParam() {
        return this.requestParam;
    }

    public boolean isHtmRequest() {
        return this.isHtmRequest;
    }
}
