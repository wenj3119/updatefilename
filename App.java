package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author wj
 * @date 20200806
 *批量修改文件以及目录名称
 */
public class App {
    //启动程序入口
    public static void main( String[] args ) throws IOException {
        Scanner scan = new Scanner(System.in);
        //创建一个map容器存放文件路径以及文件流
        Map<String,InputStream> content = new HashMap<>();
        System.out.println("请在下方输入需要修改文件上层目录的绝对路径，例如：D:\\workspace\\2020\\08\\06");
        //读取用户输入路径下文件，将文件路径和文件流存放在map容器中
        Map<String, InputStream> paths = getPaths(scan.nextLine(), content);
        System.out.println("请在下方输入需要替换的旧值");
        String oldValue = scan.nextLine();
        System.out.println("请在下方输入替换的新值");
        String newValue = scan.nextLine();
        scan.close();
        //声明一个文件输出流
        FileOutputStream fos = null;
        //遍历map，替换文件路径中需要替换的值，然后写出，最后关闭流
        for(Map.Entry<String,InputStream> map:paths.entrySet()){
            InputStream is = map.getValue();
            String writePath = map.getKey().replace(oldValue,newValue);
            File writFile = new File(writePath);
            String parent = writFile.getParent();
            File file = new File(parent);
            file.mkdirs();
            fos = new FileOutputStream(writePath);
            byte[] b = new byte[1024];
            int length;
            while((length = is.read(b))>0){
                fos.write(b,0,length);

            }
            is.close();
            fos.close();
        }
        System.out.println("修改完毕！");
    }
    //递归获取目录下所有文件路径和文件流，将其存放到Map容器中，
    private static Map<String, InputStream> getPaths(String path,Map<String ,InputStream> content) throws FileNotFoundException {
        File file = new File(path);
        File[] files = file.listFiles();
        for(File file1 : files){
            if(file1.isDirectory()){
               getPaths(file1.getPath(),content);
            }else {
                content.put(file1.getPath(), new FileInputStream(file1.getPath()));
            }
        }
       return content;
    }
}
