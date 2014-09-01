package utils;

import java.io.*;
import java.net.URL;

/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-13
 * Time: 上午9:47
 * To change this template use File | Settings | File Templates.
 */
public class FileUtils {

    public static String getClassPathFilePath(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        if (url == null) {
            url = ProjectUtil.class.getClassLoader().getResource(fileName);
        }
        if (url != null) {
            return url.getFile();
        } else {
            return null;
        }
    }

    public static File getClassPathFile(String fileName) {
        return new File(getClassPathFilePath(fileName));
    }

    public static InputStream getClassPathFileAsStream(String fileName) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        if (is == null) {
            is = ProjectUtil.class.getClassLoader().getResourceAsStream(fileName);
        }
        if (is != null) {
            return is;
        } else {
            return null;
        }
    }

    /**
     * 得到根目录
     *
     * @return
     */
    public static String getRootPath() {
        String s = getClassesPath();
        return s.split("WEB-INF")[0];
    }

    /**
     * 得到classes目录
     *
     * @return
     */
    public static String getClassesPath() {
        return getClassPathFilePath("");
    }

    /**
     * 读取文件内容到字符串
     *
     * @param file
     * @return 文件的内容
     * @throws IOException
     */
    public static String readFileToString(File file) throws IOException {
        Reader is = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuffer sb = new StringBuffer();
        int len;
        char[] buf = new char[1024 * 4];
        while ((len = is.read(buf)) != -1) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /**
     * 读取文件内容到字符串
     *
     * @param filepath 文件路径
     * @return 文件内容
     * @throws IOException
     */
    public static String readFileToString(String filepath) throws IOException {
        return readFileToString(new File(filepath));
    }

    private static String logDir = null;

    public static String getLogDir() {
        if (logDir == null) {
            String path = FileUtils.getClassesPath();
            if (path == null) return null;
            path = path.substring(0, path.length() - 1);
            logDir = path.substring(0, path.lastIndexOf("/")) + "/logs";
        }
        return logDir;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(readFileToString(FileUtils.getClassPathFilePath("code.txt")));
    }
}
