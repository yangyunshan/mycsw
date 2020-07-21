package com.csw.file.util;

import org.apache.commons.fileupload.FileItem;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileOperationUtil {

    /**
     * Web项目打包以后classes文件在磁盘中的绝对路径
     * */
    public static String getWebPath() {
        File classFile = new File(FileOperationUtil.class.getClassLoader().getResource("").getPath());
        String webPath = classFile.getParent()+File.separator+"classes"+File.separator;
        return webPath;
    }

    public static String readFileContent(String filePath, String encodingType) {
        String content = "";
        if (encodingType==null) {
            encodingType = "UTF-8";
        }
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(filePath)),encodingType);
            BufferedReader br = new BufferedReader(isr);
            String tempContent = "";
            while ((tempContent=br.readLine())!=null) {
                content += tempContent;
            }
            isr.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 	将sensorML内容写入到相应的文件中
     * @param str: 需要写入的字符串文档内容
     * @param filePath: 文档内容写入的文件路径
     * @return void
     * */
    public static void writeToFile(String str, String filePath) {
        try {
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(str);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件，上传成功返回上传后的路径，失败返回null
     * */
    public static String processUploadFile(FileItem fileItem, PrintWriter printWriter, String filePath) {
        String result = "";
        String fileName = fileItem.getName();
        System.out.println("FileName: "+fileName);
        int index = fileName.lastIndexOf("\\");
        fileName = fileName.substring(index+1);
        long fileSize = fileItem.getSize();

        if ("".equals(fileName)&&fileSize==0) {
            System.out.println("fileName is null");
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()&&!file.isDirectory()) {
            System.out.println("存储路径不存在，需要创建");
            boolean flag = file.mkdirs();
            if (flag) {
                System.out.println("存储路径创建成功");
            } else {
                System.out.println("存储路径创建失败");
            }
        }
        try {
            String path = filePath+"/"+fileName;
            File uploadFile = new File(path);
            if (uploadFile.exists()) {
                System.out.println("文件已存在，上传失败");
                return "";
            }
            fileItem.write(uploadFile);
//            printWriter.println(fileName+"文件保存完毕，文件大小为:"+fileSize);
            fileItem.delete();
            result = path;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }

    /**
     * 处理FormData表单数据，并将表单数据以键值对的形式保存在Map中返回
     * */
    public static Map<String,String> processFormData(List<FileItem> fileItems) {
        Map<String,String> result = new HashMap<String, String>();
        for (FileItem fileItem : fileItems) {
            if (fileItem.isFormField()) {
                try {
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString("UTF-8");
                    result.put(name,value);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return result;
    }

    /**
     * 从HttpServletRequest request, 中读取数据流中的内容
     * */
    public static String getContentFromRequest(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line=bf.readLine())!=null) {
                stringBuilder.append(line);
            }
            content = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 根据文件路径删除文件
     * */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;

        File file = new File(filePath);
        if (file.isFile()&&file.exists()) {
            file.delete();
            flag = true;
        }

        return flag;
    }
}
