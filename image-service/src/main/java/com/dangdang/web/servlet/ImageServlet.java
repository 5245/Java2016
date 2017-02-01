package com.dangdang.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.dangdang.common.util.ConfigPropertiesUtil;
import com.dangdang.common.util.ImageUtils;

/**
 * @author skp
 * @description 图片读取 servlet
 * @date 2017年1月23日
 */
public class ImageServlet extends HttpServlet {

    private static final long   serialVersionUID = 1L;
    // 文件资源根目录
    private static final String resourcesRoot    = ConfigPropertiesUtil.getInstance().get("resourcesRoot");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取相对路径
        String resource = request.getPathInfo();

        File outFile = File.createTempFile("img", "");
        try {
            ImageInfo info = processInfo(resource);
            File inFile = new File(new File(resourcesRoot, info.path), info.oriFileName);
            if (!inFile.exists()) {
                response.setStatus(404);
                response.getWriter().println("not found!");
                return;
            }
            processImage(info, inFile, outFile);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().println("server error!");
            return;
        }

        response.addHeader("Accept-Ranges", "bytes");
        response.setContentType("image/jpeg");
        response.setContentLength((int) outFile.length());

        OutputStream out = response.getOutputStream();
        FileUtils.copyFile(outFile, out);
        out.flush();
        FileUtils.deleteQuietly(outFile);
    }

    /**
     * 提取url切割信息
     * @param resource
     * @return
     */
    private ImageInfo processInfo(String resource) {
        ImageInfo info = new ImageInfo();
        info.path = resource.substring(0, resource.lastIndexOf('/'));
        info.fileName = resource.substring(resource.lastIndexOf('/') + 1);

        int s = info.fileName.lastIndexOf('_');
        int x = info.fileName.lastIndexOf('x');
        int e = info.fileName.lastIndexOf('.');
        if (s != -1) {
            info.oriFileName = (info.fileName.substring(0, s) + info.fileName.substring(e));
            info.name = info.fileName.substring(0, s);
        } else {
            info.oriFileName = info.fileName;
            info.name = info.fileName.substring(0, e);
        }
        info.extName = info.fileName.substring(e + 1);

        if ((s != -1) && (x != -1)) {
            String wxh = info.fileName.substring(s + 1, e);
            int xx = wxh.indexOf('x');
            if (xx == 0) {
                info.width = -1;
                info.height = Integer.valueOf(wxh.substring(1)).intValue();
            } else if (xx == wxh.length() - 1) {
                info.width = Integer.valueOf(wxh.substring(0, wxh.length() - 1)).intValue();
                info.height = -1;
            } else {
                info.width = Integer.valueOf(wxh.substring(0, xx)).intValue();
                info.height = Integer.valueOf(wxh.substring(xx + 1)).intValue();
            }
        } else {
            info.width = -1;
            info.height = -1;
        }
        return info;
    }

    /**
     * 图片切割
     * @param info
     * @param inFile
     * @param outFile
     * @throws Exception
     */
    private void processImage(ImageInfo info, File inFile, File outFile) throws Exception {
        double quality = 85.0D;
        if ((info.width != -1) && (info.height != -1))
            ImageUtils.cropCenter(inFile.getAbsolutePath(), outFile.getAbsolutePath(), info.width, info.height, quality);
        else if (info.width != -1)
            ImageUtils.resize(inFile.getAbsolutePath(), outFile.getAbsolutePath(), info.width + "x", quality);
        else if (info.height != -1)
            ImageUtils.resize(inFile.getAbsolutePath(), outFile.getAbsolutePath(), "x" + info.height, quality);
        else
            FileUtils.copyFile(inFile, outFile);
    }

    protected static class ImageInfo {
        String path;
        String fileName;
        String oriFileName;
        String name;
        String extName;
        int    width;
        int    height;
    }
}
