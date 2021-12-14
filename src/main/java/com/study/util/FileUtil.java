package com.study.util;

import org.springframework.boot.system.ApplicationHome;

import java.io.*;
import java.nio.channels.FileChannel;

//文件工具类
public class FileUtil {


    /**
     * 创建文件
     *
     * @param filePath 文件路径
     * @param fileType 1 文件类型  其他为文件夹
     * @return
     * @throws Exception
     */
    public static File createFile(String filePath, int fileType) throws Exception {
        if (filePath == null) throw new Exception("文件路径不能为空！");
        File file = new File(filePath);
        if (!file.exists()) {
            if (fileType == 1) {
                file.createNewFile();
            } else {
                file.mkdirs();
            }
        }
        return file;
    }

    /**
     * 读取文件内容
     *
     * @param filePaht
     * @return
     * @throws Exception
     */
    public static String readFileContent(String filePaht, int type) throws Exception {
        File file = createFile(filePaht, type);

        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            ;//new BufferedReader(new FileReader(file));
            String content;
            while ((content = bufferedReader.readLine()) != null) {
                stringBuffer.append(content).append("\r\n");
            }
            bufferedReader.close();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return stringBuffer.toString();
    }


    /**
     * @Auther 吕伟林
     * @Des 写入文件内容，多线程情况下需要需要考虑读写锁
     * @Date 2021/6/24 18:06
     */
    public static void writeFileContent(String filePath, String content, int type) throws Exception {
        File file = createFile(filePath, type);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        writer.write(content);//写入文件
        writer.flush();//清空缓冲区数据
        writer.close();//关闭读写流
    }

    //删除文件
    public static void delFile(String filePath) throws Exception {

    }


    //递归删除文件
    public static boolean deleteRecursionFile(String delpath) {
        File file = new File(delpath);
        if (!file.isDirectory()) {
            file.delete();
        } else if (file.isDirectory()) {
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File delfile = new File(delpath + File.separator + filelist[i]);
                if (!delfile.isDirectory()) {
                    delfile.delete();
                } else if (delfile.isDirectory()) {
                    deleteRecursionFile(delpath + File.separator + filelist[i]);
                }
            }
            file.delete();
        }
        return true;
    }

    /**
     * 递归复制 文件夹（包括文件夹中全部文件）
     *
     * @param resPath 资源路径
     * @param desPath 目标路径
     */
    public static void copyRecursionFile(String resPath, String desPath) throws Exception {
        File resFile = new File(resPath);
        if (!resFile.exists()) {
            throw new Exception(resFile.getPath() + "资源文件不存在");
        }
        File desFile = new File(desPath);
        if (resFile.isDirectory()) {
            if (!desFile.exists()) {
                desFile.mkdirs();
            }
            String[] filelist = resFile.list();
            for (int i = 0; i < filelist.length; i++) {
                String rp = resPath + File.separator + filelist[i];
                String dp = desPath + File.separator + filelist[i];
                copyRecursionFile(rp, dp);
            }
        } else {
            resFile.renameTo(desFile);
        }
    }

    /**
     * @Auther 吕伟林
     * @Des 使用FileChannel 复制文件
     * @Date 2021/7/6 17:09
     */
    public static void copyFileUsingFileChannels(File source, File dest)
            throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    /**
     * @Auther 吕伟林
     * @Des 两路径中必须存在文件
     * @Date 2021/7/7 15:39
     */
    public static void copyFileUsingFileChannels(String sourcePaht, String destPaht)
            throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(new File(sourcePaht)).getChannel();
            outputChannel = new FileOutputStream(new File(destPaht)).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            if (inputChannel != null) {
                inputChannel.close();
            }
            if (outputChannel != null) {
                outputChannel.close();
            }

        }
    }

    public static String getWebAppsPath() {
        ApplicationHome home = new ApplicationHome(FileUtil.class);
        File sysfile = home.getSource();
        String reulst = sysfile.getParentFile().getParentFile().getParentFile().toString();
        return reulst;
    }

    public static void main(String[] args) throws IOException {
        //坦克考场数据转化
        File y1 = new File("D:/data/tklocation1.txt");
        File y2 = new File("D:/data/tklocation1.txt");
        File m1 = new File("D:/data/data1.json");
        File m2 = new File("D:/data/data2.json");
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("11111文件1写入到文件2开始");
                    FileUtil.copyFileUsingFileChannels(y1, m1);
                    System.out.println("11111文件1写入到文件2结束");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("222222文件1写入到文件2开始");
                    FileUtil.copyFileUsingFileChannels(y2, m1);
                    System.out.println("222222文件1写入到文件2结束");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        th1.start();
        th2.start();

    }


}
