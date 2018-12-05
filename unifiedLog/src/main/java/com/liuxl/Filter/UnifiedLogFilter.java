package com.liuxl.Filter;

import com.alibaba.fastjson.JSON;

import com.liuxl.constant.CharacterConstant;
import com.liuxl.enumType.RequsetTypeEnum;
import com.liuxl.model.UnifiedLogDO;
import com.liuxl.model.http.EWebContext;
import com.liuxl.model.http.EWebRequestDTO;
import com.liuxl.model.http.EWebServletContext;
import com.liuxl.model.http.Result;
import com.liuxl.util.LocalThreadPool;
import com.liuxl.util.UnifiedLogLogger;
import com.liuxl.utils.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class UnifiedLogFilter implements Filter {

    public final static String UNIFIED_REQUEST_ID_CODE_FOR_HTTP = "_unified_request_id_";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private String getHeadersInfo(HttpServletRequest httpRequest) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = httpRequest.getHeader(key);
            map.put(key, value);
        }
        return JSON.toJSONString(map, UnifiedLogLogger.features);
    }

    private void initUnifiedLogRequestId(String requestId, String clientIp) {

        LocalThreadPool.setRequestId((requestId == null || equals(requestId)) ? UUIDUtil.getUUID(true) : requestId);
        LocalThreadPool.setRequestType(RequsetTypeEnum.HTTP.getType());
        LocalThreadPool.setClientIp(clientIp);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        EWebContext<EWebRequestDTO, String> webContext = null;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        UnifiedLogDO unifiedLogDO = null;
        long start = System.currentTimeMillis();

        //单独try catch 有异常不影响正常逻辑
        try {
            if (EWebServletContext.getEWebContext() != null) {
                webContext = EWebServletContext.getEWebContext();
            } else {
                EWebRequestDTO eWebRequestDTO = new EWebRequestDTO(httpRequest.getRequestURL().toString());
                webContext = new EWebContext(eWebRequestDTO, new Result<String>(), httpRequest, httpResponse);
                initParamContext(webContext, httpRequest);
            }

            String clientIp = ServletRequestUtil.getIpAddr((HttpServletRequest) request);
            initUnifiedLogRequestId((String) webContext.getParam().getRequestParam().get(UNIFIED_REQUEST_ID_CODE_FOR_HTTP), clientIp);

            unifiedLogDO = new UnifiedLogDO(getHeadersInfo(httpRequest), httpRequest.getRequestURL().toString(), webContext.getParam(), RequsetTypeEnum.HTTP.getType());
        } catch (Throwable e1) {

        }

        Throwable failed = null;
        try {
            chain.doFilter(httpRequest, httpResponse);
        } catch (Throwable e) {
            failed = e;
            throw e;
        } finally {
            unifiedLogDO.setUseTime((System.currentTimeMillis() - start));
            try {
                if (failed != null) {
                    unifiedLogDO.setExceptionCode(failed.getMessage());
                    UnifiedLogLogger.record(unifiedLogDO, "", failed);
                } else {
                    UnifiedLogLogger.record(unifiedLogDO, "");
                }
            } catch (Throwable e) {
                UnifiedLogLogger.record(e.getMessage(), e);
            } finally {
                LocalThreadPool.remove();
            }
        }

    }

    private void initParamContext(EWebContext ctx, HttpServletRequest request) {
        ctx.put(CharacterConstant.CTX_PATH, request.getContextPath());
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        ctx.put(CharacterConstant.BASE_PATH, basePath);
        ctx.put(CharacterConstant.SESSION_ID, request.getSession().getId());

        if (!CheckUtil.isMultipartFormData(request.getHeader(CharacterConstant.CONTENT_TYPE))) {
            requestToContext(request, ctx);
            return;
        }

        try {

            DiskFileItemFactory fac = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(fac);
            upload.setHeaderEncoding("utf-8");
            List fileList = null;
            fileList = upload.parseRequest(request);

            if (fileList == null || fileList.size() == 0) {
                return;
            }

            String savePath = FileUtil.getEwebFilePath();
            File f1 = new File(savePath);
            if (!f1.exists()) {
                f1.mkdirs();
            }

            Map<String, List<File>> fileMap = new HashMap<String, List<File>>();

            Iterator<FileItem> it = fileList.iterator();
            String name = "";
            while (it.hasNext()) {
                FileItem item = it.next();
                //不存储附件  但是记录附件名称
                if (!item.isFormField()) {
                    name = StringUtil.getLastArrStr(item.getName(), "\\\\");
                    File file = new File(savePath + File.separatorChar + name);
                    if (file.exists()) {
                        file.delete();
                    }
                    item.write(file);
                    item.delete(); //释放输出流

                    List list = fileMap.get(item.getFieldName());
                    if (list == null) {
                        list = new ArrayList();
                        list.add(file.getName());
                        fileMap.put(item.getFieldName(), list);
                    } else {
                        list.add(file.getName());
                    }

                } else {
                    ctx.put(item.getFieldName(), item.getString("UTF-8"));
                }
            }
            ctx.put("fileData", fileMap);
        } catch (Throwable e) {

        }
    }

    @Override
    public void destroy() {

    }

    private static void requestToContext(HttpServletRequest request, EWebContext ctx) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Iterator i$ = parameterMap.keySet().iterator();

        while(true) {
            while(i$.hasNext()) {
                String key = (String)i$.next();
                String[] value = (String[])parameterMap.get(key);
                if (value != null && value.length == 1) {
                    ctx.put(key, value[0]);
                } else {
                    ctx.put(key, value);
                }
            }
            return;
        }
    }
}
