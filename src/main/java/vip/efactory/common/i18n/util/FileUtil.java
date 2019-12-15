package vip.efactory.common.i18n.util;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.*;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static final String SUFFIX_JPG = "jpg";
    public static final String SUFFIX_PNG = "png";
    public static final String SUFFIX_BMP = "bmp";
    public static final String SUFFIX_JPEG = "jpeg";
    public static final String SUFFIX_GIF = "gif";

    public static final Comparator<File> comparator = new Comparator<File>() {
        public int compare(File file, File newFile) {
            if (file.lastModified() > newFile.lastModified()) {
                return 1;
            } else if (file.lastModified() == newFile.lastModified()) {
                return 0;
            } else {
                return -1;
            }

        }
    };

    /**
     * 计算传入的文件大小
     *
     * @param file
     * @return
     */
    public static double getDirSize(File file) {
        // 判断文件是否存在
        if (null != file && file.exists()) {
            // 如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                if (children != null) {
                    for (File f : children)
                        size += getDirSize(f);
                }
                return size;
            } else {// 如果是文件则直接返回其大小,以“兆”为单位
                double size = (double) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            return 0.0;
        }
    }

    public static String getFileFormatSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 根据操作时间从早到晚排序
     *
     * @param files
     * @return
     */
    public static List<File> getFilesOrderByDate(File[] files) {
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, comparator);
        return fileList;
    }

    /**
     * 获得指定后缀名的文件集合
     *
     * @param dir
     * @param extend
     * @return
     */
    public static File[] getExtendFiles(File dir, final String extend) {
        FilenameFilter filenameFilter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(extend);
            }
        };
        File[] files = dir.listFiles(filenameFilter);
        return files;
    }

    public static List<File> getAllFiles(File dir) throws Exception {
        List<File> files = new ArrayList<File>();
        if (dir.isDirectory()) {// 如果是目录
            File[] documentArr = dir.listFiles();// 取目录下的所有文件
            files.add(dir);
            if (documentArr != null) {
                // 遍历目录下所有文件 执行递归
                for (File document : documentArr) {
                    files.addAll(getAllFiles(document));
                }
            }
        } else {// 如果是文件 加入到list中
            files.add(dir);
        }
        return files;
    }

    /**
     * 删除某个文件夹下的所有文件夹和文件
     *
     * @return boolean
     */
    public static boolean deletefile(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deletefile(new File(file, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return file.delete();
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     *
     * @param fileName 文件名
     */
    public static List<String> readFileByLines(String fileName) {
        List<String> lines = new Vector<String>();
        File file = new File(fileName);
        BufferedReader reader = null;
        readFileLines(file, lines, reader);
        return lines;
    }

    /**
     * 根据文件读取内容
     *
     * @param file
     * @return
     */
    public static String readFileByFile(File file) {
        List<String> lines = new Vector<String>();
        BufferedReader reader = null;
        readFileLines(file, lines, reader);
        StringBuilder sb = new StringBuilder();
        for (String string : lines) {
            sb.append(string);
        }
        return sb.toString();
    }

    public static String readFileByIs(InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = "";
        try {
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return sb.toString();
    }

    private static void readFileLines(File file, List<String> lines, BufferedReader reader) {
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                lines.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            logger.error("readFileByLines", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    logger.error("Error to close the reader", e1);
                }
            }
        }
    }

    public static boolean writeFile(String content, String pathanme) {
        File file = new File(pathanme);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(content);
            bw.flush();
            return true;
        } catch (IOException e) {
            logger.error("writeFileByLines", e);
            return false;
        } finally {
            IOUtils.closeQuietly(fw);
            IOUtils.closeQuietly(bw);
        }
    }

    public static boolean writeFileByLines(List<String> lines, String pathanme) {
        File file = new File(pathanme);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {

            // if file doesnt exists, then create it
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }

            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            for (String line : lines) {
                bw.write(line);
                bw.flush();
            }
            return true;
        } catch (IOException e) {
            logger.error("writeFileByLines", e);
            return false;
        } finally {
            IOUtils.closeQuietly(fw);
            IOUtils.closeQuietly(bw);
        }
    }

    public static String readFile(String fileName) {
        List<String> strings = readFileByLines(fileName);
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string);
        }
        return sb.toString();
    }

    public static long getFileLastModified(String pathname) {
        if (null == pathname) {
            return -1L;
        }
        File file = new File(pathname);
        if (!file.exists() || file.isDirectory()) {
            return -1L;
        }
        return file.lastModified();
    }

    public static boolean mkdir(String dir, String filename) {
        File f = new File(dir, filename);
        return mkdir(f);
    }

    public static boolean mkdir(String pathname) {
        File f = new File(pathname);
        return mkdir(f);
    }

    public static boolean mkdir(File f) {
        if (null == f) {
            return false;
        }
        if (!f.getParentFile().exists()) {
            mkdir(f.getParentFile());
        }
        return f.mkdirs();
    }

    /**
     * 复制单个文件
     *
     * @param srcFileName  待复制的文件名
     * @param descFileName 目标文件名
     * @param overlay      如果目标文件存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
        File srcFile = new File(srcFileName);

        // 判断源文件是否存在
        if (!srcFile.exists()) {
            // MESSAGE = "源文件：" + srcFileName + "不存在！";
            // JOptionPane.showMessageDialog(null, MESSAGE);
            return false;
        } else if (!srcFile.isFile()) {
            // MESSAGE = "复制文件失败，源文件：" + srcFileName + "不是一个文件！";
            // JOptionPane.showMessageDialog(null, MESSAGE);
            return false;
        }

        // 判断目标文件是否存在
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            // 如果目标文件存在并允许覆盖
            if (overlay) {
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
                new File(destFileName).delete();
            }
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败：创建目标文件所在目录失败
                    return false;
                }
            }
        }

        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            logger.error("Error to read the input stream", e);
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                logger.error("can not close input stream", e);
            }
        }
    }

    /**
     * 复制整个目录的内容
     *
     * @param srcDirName  待复制目录的目录名
     * @param destDirName 目标目录名
     * @param overlay     如果目标目录存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectory(String srcDirName, String destDirName, boolean overlay) {
        // 判断源目录是否存在
        File srcDir = new File(srcDirName);
        if (!srcDir.exists()) {
            // MESSAGE = "复制目录失败：源目录" + srcDirName + "不存在！";
            // JOptionPane.showMessageDialog(null, MESSAGE);
            return false;
        } else if (!srcDir.isDirectory()) {
            // MESSAGE = "复制目录失败：" + srcDirName + "不是目录！";
            // JOptionPane.showMessageDialog(null, MESSAGE);
            return false;
        }

        // 如果目标目录名不是以文件分隔符结尾，则加上文件分隔符
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        File destDir = new File(destDirName);
        // 如果目标文件夹存在
        if (destDir.exists()) {
            // 如果允许覆盖则删除已存在的目标目录
            if (overlay) {
                new File(destDirName).delete();
            } else {
                // MESSAGE = "复制目录失败：目的目录" + destDirName + "已存在！";
                // JOptionPane.showMessageDialog(null, MESSAGE);
                return false;
            }
        } else {
            // 创建目的目录
            // System.out.println("目的目录不存在，准备创建。。。");
            if (!destDir.mkdirs()) {
                // System.out.println("复制目录失败：创建目的目录失败！");
                return false;
            }
        }

        boolean flag = true;
        File[] files = srcDir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                // 复制文件
                if (files[i].isFile()) {
                    flag = copyFile(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
                    if (!flag)
                        break;
                } else if (files[i].isDirectory()) {
                    flag = copyDirectory(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
                    if (!flag)
                        break;
                }
            }
        }
        if (!flag) {
            // MESSAGE = "复制目录" + srcDirName + "至" + destDirName + "失败！";
            // JOptionPane.showMessageDialog(null, MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    public static String joinPath(String... paths) {
        if (null == paths || paths.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (String path : paths) {
            if (null == path) {
                continue;
            }
            sb.append(path);
            sb.append("/");
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        String result = sb.toString();
        return result.replace("//", "/").replace("\\\\", "\\");
    }

    /**
     * @param httpUrl
     * @param saveFile
     * @return
     */
    public static boolean httpDownload(String httpUrl, String saveFile) {
        int byteread = 0;
        URL url = null;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e) {
            logger.error("", e);
            return false;
        }
        FileOutputStream fs = null;
        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            buildParentFile(new File(saveFile));
            fs = new FileOutputStream(saveFile);

            byte[] buffer = new byte[1024];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
                fs.flush();
            }
            return true;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        } finally {
            IOUtils.closeQuietly(fs);
        }
    }

    public static boolean isExist(String pathname) {
        return isExist(pathname, false);
    }

    public static boolean isExist(String pathname, boolean buildParent) {
        File f = new File(pathname);
        if (f.exists()) {
            return true;
        }

        if (buildParent) {
            buildParentFile(f);
        }
        return false;
    }

    private static void buildParentFile(File f) {
        if (null == f) {
            return;
        }
        File parentFile = f.getParentFile();
        if (!parentFile.exists()) {
            FileUtil.mkdir(parentFile);
        }
    }

    /**
     * save string content to file
     *
     * @param directory file directory
     * @param fileName  file name
     * @param content   file content
     * @return
     * @author wkxu
     * @date 2016/01/22
     */
    public static boolean saveFile(String directory, String fileName,
                                   String content) {
        if (null == directory || "".equals(directory) || null == fileName || "".equals(fileName)
                || null == content || "".equals(content)) {
            logger.error("Save file param is invalid.");
            return false;
        }
        FileWriter write = null;
        BufferedWriter bw = null;
        try {
            File file = new File(directory);
            if (!file.exists()) {
                file.mkdirs();
            }
            File saveFile = new File(directory, fileName);
            write = new FileWriter(saveFile);
            bw = new BufferedWriter(write);
            bw.write(content);
            bw.flush();
        } catch (IOException e) {
            logger.error("Save file failed.", e);
            return false;
        } finally {
            IOUtils.closeQuietly(bw);
            IOUtils.closeQuietly(write);
        }
        return true;
    }

    /**
     * rename file
     *
     * @param directory
     * @param oldFileName
     * @param newFileName
     * @return
     * @author wkxu
     * @date 2016/01/22
     */
    public static boolean renameFile(String directory, String oldFileName,
                                     String newFileName) {
        if (null == directory || "".equals(directory) || null == oldFileName
                || "".equals(oldFileName) || null == newFileName
                || "".equals(newFileName)) {
            logger.error("Rename file param is invalid.");
            return false;
        }
        try {
            File saveFile = new File(directory, oldFileName);
            if (!saveFile.exists()) {
                logger.error("File [{}]  is not exist.", oldFileName);
                return false;
            }
            File newFile = new File(directory, newFileName);
            if (newFile.exists()) {
                newFile.delete();
            }
            saveFile.renameTo(newFile);
        } catch (Exception e) {
            logger.error("Rename file failed.", e);
            return false;
        }
        return true;
    }

    /**
     * Description: if utf8 file contain BOM flag,remove the BOM
     *
     * @param fileName
     * @return void
     * @date 2016年4月23日
     */
    public static void trimBom4Utf8(String fileName) throws IOException {
        if (isNoBomFile(fileName)) {
            //System.out.println("File is not contain BOM ,so skip :" + fileName);
            return;
        }
        FileInputStream fin = new FileInputStream(fileName);
        // 开始写临时文件
        InputStream in = getIS4Utf8(fin);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte b[] = new byte[4096];

        int len = 0;
        while (in.available() > 0) {
            len = in.read(b, 0, 4096);
            // out.write(b, 0, len);
            bos.write(b, 0, len);
        }

        in.close();
        fin.close();
        bos.close();

        // 临时文件写完，开始将临时文件写回本文件。
        //System.out.println("[" + fileName + "]");
        FileOutputStream out = new FileOutputStream(fileName);
        out.write(bos.toByteArray());
        out.close();
        System.out.println("Trim BOM for UTF8 file: " + fileName);
    }

    /**
     * Description:check the UTF-8 file contain BOM flag or not
     *
     * @param fileName
     * @return true, not contain;false ,contain BOM
     * @date 2016年4月23日
     */
    private static boolean isNoBomFile(String fileName) throws IOException {
        FileInputStream fin = new FileInputStream(fileName);
        PushbackInputStream testin = new PushbackInputStream(fin);
        int ch = testin.read();
        int ch2 = testin.read();
        int ch3 = testin.read();
        testin.close();
        if (ch == 0xEF && ch2 == 0xBB && ch3 == 0xBF) {
            return false; // is bom file
        } else {
            return true;
        }
    }


    /**
     * Description:读取流中前面的字符，看是否有bom，如果有bom，将bom头先读掉丢弃
     * method use for UTF-8 charset
     *
     * @param in
     * @return InputStream
     * @throws IOException
     * @date 2016年4月23日
     */
    public static InputStream getIS4Utf8(InputStream in) throws IOException {
        PushbackInputStream testin = new PushbackInputStream(in);
        int ch = testin.read();
        if (ch != 0xEF) {
            testin.unread(ch);
        } else if ((ch = testin.read()) != 0xBB) { // if ch==0xef  
            testin.unread(ch);
            testin.unread(0xef);
        } else if ((ch = testin.read()) != 0xBF) { // if ch ==0xbb  
            throw new IOException("错误的UTF-8格式文件");
        } else { // if ch ==0xbf  
            // 不需要做，这里是bom头被读完了  
            // // System.out.println("still exist bom");  
        }
        return testin;

    }

    /**
     * Description:获取文件名的扩展名，比如：abc.txt，扩展名为txt
     *
     * @param [fileName]
     * @return java.lang.String
     * @author dbdu
     * @date 18-2-24 下午2:03
     */
    public static String getExtension(String fileName) {
        if (fileName == null || "".equals(fileName)) {
            return null;
        } else {
            //最后一个点的位置
            int potIndex = fileName.lastIndexOf(".");
            if (potIndex != -1) {
                String extension = fileName.substring(potIndex + 1, fileName.length());
                return extension;
            } else {
                return null;
            }
        }
    }


    public static void main(String[] args) {
        System.out.println("adfgg的后缀：" + getExtension("adfgg"));
        System.out.println("adfgg.gg.doc 的后缀：" + getExtension("adfgg.gg.doc"));
    }


}
