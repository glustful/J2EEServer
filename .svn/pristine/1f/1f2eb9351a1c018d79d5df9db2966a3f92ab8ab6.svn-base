package com.dunbian.jujiabao.mvc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
 
public class GenerateMVCViewName {

    public static final String freemarkerTemplateDir = "src/main/webapp/WEB-INF/views";

    public static final String suffix = ".ftl";

    private static ArrayList<String> filelist = new ArrayList<String>();

    /**
     * @param args
     */
    public static void main(String[] args) {
        String classLoaderPath = Thread.currentThread().getContextClassLoader().getResource("").getFile();
        String targetJavaFile = GenerateMVCViewName.class.getResource("").getFile();
        String path = classLoaderPath.replace("target/classes", freemarkerTemplateDir);
        String javaFile = targetJavaFile.replace("target/classes", "src/main/java") + "MVCViewName.java";

        File folder = new File(path);

        refreshFileList(path);

        StringBuffer sb = new StringBuffer();
        for (String file : filelist) {
            String viewName = file.replace(folder.getAbsolutePath() + File.separator, "").replace(suffix, "");
            String enumItemName = viewName.replace(File.separator, "_").replace("-", "").toUpperCase();
            viewName = viewName.replace(File.separator, "/");

            String item = enumItemName + "(\"/" + viewName + "\"),";
            sb.append("\t" + item);
            sb.append("\n");
        }

        sb.replace(sb.length() - 2, sb.length() - 1, ";");

        String oldJavaSource = readFile(new File(javaFile));

        int start = oldJavaSource.indexOf("@#############") + "@#############".length() + 1;
        int end = oldJavaSource.lastIndexOf("@#############") - 3;
        String newJavaSource = oldJavaSource.substring(0, start) + sb + oldJavaSource.substring(end);
        // 更新Java代码
        writeFile(new File(javaFile), newJavaSource);

        System.out.println("代码已更新，请刷新：" + javaFile);

    }

    public static void writeFile(File file, String newJavaSource) {
        FileOutputStream out = null;
        try {

            out = new FileOutputStream(file);
            out.write(newJavaSource.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void refreshFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();

        if (files == null)
            return;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                refreshFileList(files[i].getAbsolutePath());
            } else {
                if (files[i].getName().endsWith(suffix)) {
                    filelist.add(files[i].getAbsolutePath());
                }
            }
        }
    }

    /**
     * 
     * @param file
     * @return the file content
     */
    public static String readFile(final File file) {
        InputStream in = null;
        StringBuffer sb = new StringBuffer();
        try {

            in = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
