package utils;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-12
 * Time: 上午10:26
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesUtils {

    private static Properties properties = null;

    static {
        properties = new Properties();
        try {
            properties.load(FileUtils.getClassPathFileAsStream("mailcfg.properties"));
        } catch (IOException e) {
            LogUtils.error(e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static String getString(String key) {
        String value = (String) properties.get(key);
        return value == null ? null : value.trim();
    }

    public static Set<String> getKeys() {
        return properties.stringPropertyNames();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getString("emailsFile"));
        properties.put("fileName", "测试.xls");
        properties.put("fileName2", "测试.xls");

        System.out.println(FileUtils.getClassPathFile("mailcfg.properties"));
        FileWriter writer = new FileWriter(FileUtils.getClassPathFile("mailcfg.properties")) ;
        try{
            properties.store(writer, "描述");

//writer.write("#");
        }finally {

            writer.close();
        }
    }
}
