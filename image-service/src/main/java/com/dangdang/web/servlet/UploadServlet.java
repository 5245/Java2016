package com.dangdang.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.dangdang.common.util.ConfigPropertiesUtil;
import com.dangdang.common.util.UUIDUtilis;

/**
 * @author skp
 * @description 图片上传 servlet
 * @date 2017年1月23日
 */
public class UploadServlet extends HttpServlet {

    private static final long   serialVersionUID = 1L;
    // 文件资源根目录
    private static final String RESOURCES_ROOT   = ConfigPropertiesUtil.getInstance().get("resourcesRoot");
    // 上传秘钥
    private static final String SCERECT_KEY      = ConfigPropertiesUtil.getInstance().get("scerectKey");
    //默认编码
    private static final String DEFAULT_CHARSET  = "UTF-8";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding(DEFAULT_CHARSET);
        String scerectKeyRequest = request.getParameter("scerectKey");
        if (null == scerectKeyRequest || !SCERECT_KEY.equals(scerectKeyRequest)) {
            response.setStatus(500);
            response.getWriter().println("no permission!");
            return;
        }
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            response.setStatus(500);
            response.getWriter().println("no file!");
            return;
        }
        Map<String, Object> params = null;
        try {
            params = getParams4MultipartFile(request);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().println("no file!");
            return;
        }

        String folder = RESOURCES_ROOT + (params.containsKey("folder") ? params.get("folder").toString() + "/" : "pic/");
        File tempFile = null;
        String fileName = null;
        String newFileName = null;
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            Object val = params.get(key);
            if (val instanceof File && "file".equals(key)) {
                tempFile = (File) val;
            } else if ("file@fileName".equals(key)) {
                fileName = val.toString();
            } else if ("newFileName".equals(key)) {
                newFileName = val.toString();
            }
        }
        if (tempFile != null) {
            String suffix = fileName.substring(fileName.lastIndexOf('.'));
            if (StringUtils.isEmpty(newFileName)) {
                newFileName = UUIDUtilis.uuid32() + suffix;
            } else {
                newFileName = newFileName + suffix;
            }
            String fileUrl = folder + newFileName;
            FileUtils.copyFile(tempFile, new File(fileUrl));
            FileUtils.deleteQuietly(tempFile);
            response.setStatus(200);
            response.getWriter().println("relative imgage url:" + fileUrl);
            return;
        }
        response.setStatus(500);
        response.getWriter().println("no file!");
    }

    /**
     * 从request读取文件信息 
     * @param request
     * @return
     * @throws FileUploadException  
     * @throws UnsupportedEncodingException 
     * @throws Exception
     */
    private Map<String, Object> getParams4MultipartFile(HttpServletRequest request) throws FileUploadException, UnsupportedEncodingException {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Configure a repository (to ensure a secure temp location is used)
        ServletContext servletContext = this.getServletConfig().getServletContext();
        FileCleaningTracker fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(servletContext);

        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        factory.setSizeThreshold(1024 * 1);
        factory.setFileCleaningTracker(fileCleaningTracker);

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        Map<String, Object> params = new HashMap<String, Object>();
        // Parse the request
        List<?> items = upload.parseRequest(request);
        for (Object obj : items) {
            DiskFileItem item = (DiskFileItem) obj;
            if (item.isFormField()) {
                if (params.containsKey(item.getFieldName())) {
                    List<Object> valueList = new ArrayList<Object>();
                    Object oldValue = params.get(item.getFieldName());
                    if (oldValue instanceof List) {
                        valueList.addAll((List<?>) oldValue);
                    } else {
                        valueList.add(oldValue);
                    }
                    valueList.add(item.getString(DEFAULT_CHARSET));
                    params.put(item.getFieldName(), valueList);
                } else {
                    params.put(item.getFieldName(), item.getString(DEFAULT_CHARSET));
                }
            } else {
                if (item.getSize() > 0) {
                    params.put(item.getFieldName(), item.getStoreLocation());
                    params.put(item.getFieldName() + "@fileName", item.getName());
                }
            }
        }
        return params;
    }

}
