package com.pig8.api.platform.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.pig8.api.platform.spring.SpringPropertyConfigurer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author navy
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 写文件
     * @param file
     * @param isAppend
     * @return
     */
    public static boolean writeFile(File file, boolean isAppend, byte[] data) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, isAppend);
            out.write(data);
            return true;
        } catch (Exception e) {
            logger.error("writeFile", e);
        } finally {
            try {
                if (null != out)
                    out.close();
            } catch (Exception e2) {
                LogUtils.error(e2);
            }
        }
        return false;
    }

    /**
     * 读取文件
     * @param file
     * @return
     */
    public static byte[] readFile(File file) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            ByteBuffer bb = ByteBuffer.allocate(in.available());
            byte b;
            while (-1 != (b = (byte) in.read())) {
                bb.put(b);
            }
            return bb.array();
        } catch (Exception e) {
            logger.error("writeFile", e);
        } finally {
            try {
                if (null != in)
                    in.close();
            } catch (Exception e2) {
                LogUtils.error(e2);
            }
        }
        return null;
    }

    public static byte[] readFile(InputStream in) {
        ByteArrayOutputStream os = null;
        try {
            short e = 4096;
            byte[] buffer = new byte[e];
            os = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = in.read(buffer))>-1) {
                os.write(buffer, 0, bytesRead);
            }
            return os.toByteArray();
        } catch (Exception e) {
            logger.error("readFile", e);
        } finally {
            try {
                IOUtils.safeClose(os);
                IOUtils.safeClose(in);
            } catch (Exception e2) {
                LogUtils.error(e2);
            }
        }
        return null;
    }

    /**
     * 读取文件一行
     * @param file
     * @return
     */
    public static List<String> readFileLine(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            List<String> list = new ArrayList<String>();
            String tmpStr = null;
            while (null != (tmpStr = reader.readLine())) {
                list.add(tmpStr);
            }
            return list;
        } catch (Exception e) {
            logger.error("readFileLine", e);
        } finally {
            try {
                if (null != reader)
                    reader.close();
            } catch (Exception e2) {
                LogUtils.error(e2);
            }
        }
        return null;
    }

    /**
     * 读取文件一行
     * @param file
     * @return
     */
    public static boolean writeFileLine(File file, List<String> list) {
        BufferedWriter writer = null;
        try {
            if (null != list && list.size()>0) {
                writer = new BufferedWriter(new FileWriter(file));
                for (String str : list) {
                    writer.append(str);
                    writer.append("\r\n");
                }
            }
        } catch (Exception e) {
            logger.error("readFileLine", e);
            return false;
        } finally {
            try {
                if (null != writer)
                    writer.close();
            } catch (Exception e2) {
                LogUtils.error(e2);
            }
        }
        return true;
    }


    public static final String FILE_TYPE_HOME = "home";
    public static final String FILE_TYPE_PRIVATE = "private";


    public static final String uploadFile(final MultipartFile file) {
        return uploadFile(file, "test", null);
    }

    public static final String uploadFile(final MultipartFile file, String type) {
        return uploadFile(file, type, null);
    }

    public static final String uploadFile(final MultipartFile file, String type, Integer userId) {
        if (userId == null)
            userId = 0;
        try {
            String originalFilename = file.getOriginalFilename().toLowerCase();
            boolean isImg = false;

            if (originalFilename.endsWith("png") || originalFilename.endsWith("jpeg")) {
                isImg = true;
            }
            String suffixName =
                    originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            String fileName = "";
            //图片类型用md5做文件名字，避免重复上传
            if (isImg) {
                String md5String = MD5.getMD5String(file.getBytes());
                fileName = md5String + suffixName;
            } else {
                fileName = UUIDUtils.getUUID() + suffixName;
            }
            /*上传文件*/
            InputStream content = new ByteArrayInputStream(file.getBytes());
            //文件保存的文件夹
            fileName = type + "/u_" + (userId % 100) + "/" + userId + "/" + fileName;
            return uploadFileAliyun(content, fileName, type.equals(FILE_TYPE_PRIVATE));
        } catch (Exception e) {
            LogUtils.error(e);
        } finally {

        }
        return "";
    }

    public static final String VIEW_PRIVATE_IMG_URL="/common/private/img";
    public static String getPriveteImgUrl(String host,String imgUrl){
        return host+"/common"+imgUrl;
    }
    /**获取私人文件
     * @param fileName
     * @return
     */
    public static byte[] getPrivateFile(String fileName) {
        //        String key = "ARhKf0RAhCC4zt7S";
        //        String secret = "rrEYGljtZnadLP0fa9szMG1gurGNmN";
        //        String url = "http://oss-cn-shenzhen.aliyuncs.com";
        //        String bucketName = "8pig-private";
        String key = SpringPropertyConfigurer.getContextProperty("ossclient.key");
        String secret = SpringPropertyConfigurer.getContextProperty("ossclient.secret");
        String url = SpringPropertyConfigurer.getContextProperty("ossclient.url");
        String bucketName =
                SpringPropertyConfigurer.getContextProperty("ossclient.private_bucketName");

        OSSClient client = new OSSClient(url, key, secret);
        GetObjectRequest request = new GetObjectRequest(bucketName, fileName);
        OSSObject object = client.getObject(request);
        InputStream is = object.getObjectContent();
        return FileUtils.readFile(is);
    }

    /**上传文件到阿里云
     * @param is
     * @param fileName
     * @param isPrivete
     * @return
     */
    private static String uploadFileAliyun(InputStream is, String fileName, boolean isPrivete) {
        try {
            String key = SpringPropertyConfigurer.getContextProperty("ossclient.key");
            String secret = SpringPropertyConfigurer.getContextProperty("ossclient.secret");
            String url = SpringPropertyConfigurer.getContextProperty("ossclient.url");
            String bucketName = "";
            if (!isPrivete)
                bucketName = SpringPropertyConfigurer.getContextProperty("ossclient.bucketName");
            else
                bucketName =
                        SpringPropertyConfigurer.getContextProperty("ossclient.private_bucketName");

            OSSClient client = new OSSClient(url, key, secret);
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(is.available());
            String host = SpringPropertyConfigurer.getContextProperty("ossclient.host");
            client.putObject(bucketName, fileName, is, meta);
            if (isPrivete)
                return fileName;
            else
                return host + "/" + fileName;
        } catch (Exception e) {
            LogUtils.error(e);
        } finally {

        }
        return "";
    }
}
