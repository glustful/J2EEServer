package com.dunbian.jujiabao.wcpay.token;

import com.alibaba.fastjson.JSONObject;
import com.dunbian.jujiabao.wcpay.Config;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/14 0014.
 */
public class GetAccessToken {
    public static String getAccessToken(HttpServletRequest request) {
        String accessToken = null;
        String lastTime = null;
        String filepath = getPath(request);//获取并创建路径；
        try {
            Document document = new SAXReader().read(filepath);
            Map<String, String> map = new HashMap<String, String>();// 将解析结果存储在HashMap中
            Element root = document.getRootElement();  // 得到xml根元素
            List<Element> elementList = root.elements();// 得到根元素的全部子节点
            for (Element e : elementList) { // 遍历全部子节点
                map.put(e.getName(), e.getText());
            }
            if (elementList.size() == 0) {
                creatNode(filepath, "*", "*");//添加节点
            }
            accessToken = map.get("AccessToken");
            lastTime = map.get("AccessExpires");
            Date now = new Date();
            Date accessExpires = DateUtil.strToDate("yyyy-MM-dd HH:mm:ss", lastTime);
            if (now.getTime() > accessExpires.getTime()) {
                accessToken = getAccessTokenFromWC();
                accessExpires = new Date(now.getTime() + Config.time);
                String nextTime = DateUtil.DateToStr("yyyy-MM-dd HH:mm:ss", accessExpires);
                root.selectSingleNode("AccessToken").setText(accessToken);
                root.selectSingleNode("AccessExpires").setText(nextTime);
                XMLWriter writer = new XMLWriter(new FileWriter(new File(filepath)));
                writer.write(document);
                writer.close();
            }
        } catch (Exception ex) {
            System.out.println("获取文件失败: " + ex.getMessage());
        }
        return accessToken;
    }

    public static String getAccessTokenFromWC() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + Config.appid + "&secret=" + Config.appsecret;
        String accessToken = "*";
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet
                    .openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.parseObject(message);
            accessToken = demoJson.getString("access_token");
            System.out.println(accessToken);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    public static String getPath(HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("/");
        String path1 = path + Config.saveConfigPath;
        String path2 = path + Config.saveConfigPath + Config.saveConfigName;
        try {
            File files = new File(path1);
            if (!files.exists()) {// 判断文件夹是否存在,如果不存在则创建文件夹
                files.mkdir();
            }
            File file = new File(path2);
            if (!file.exists()) {// 创建文件
                try {
                    //file.createNewFile();
                    Document document = DocumentHelper.createDocument();
                    document.addElement("config");
                    try {
                        XMLWriter output = new XMLWriter(new FileWriter(new File(path2)));
                        output.write(document);
                        output.close();
                        creatNode(path2, "*", "*");//创建初始化节点；
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            System.out.println("文件取出失败，错误信息: " + ex.getMessage());
            throw ex;
        }

        return path2;
    }

    public static void creatNode(String path, String value1, String value2)//初始化创建xml
    {
        try {
            Document doc = new SAXReader().read(new File(path));
            Element root = doc.getRootElement();
            //System.out.println(root.getName());
            Element accessToken = root.addElement("AccessToken");
            Element accessExpires = root.addElement("AccessExpires");
            Element jsApiTicket = root.addElement("JsApiTicket");
            Element jsApiTicketExpires = root.addElement("JsApiTicketExpires");
            accessToken.setText(value1);
            accessExpires.setText("1970-01-01 00:00:00");
            jsApiTicket.setText(value2);
            jsApiTicketExpires.setText("1970-01-01 00:00:00");
            OutputFormat opf = new OutputFormat("\t", true, "UTF-8");
            opf.setTrimText(true);
            XMLWriter writer = new XMLWriter(new FileOutputStream(path), opf);
            writer.write(doc);
            writer.close();
        } catch (Exception error) {
            System.out.println("添加节点出错失败: " + error.getMessage());
        }
    }

    public static String updataAccessToken(HttpServletRequest request) {
        String accessToken = null;
        String lastTime = null;
        String filepath = getPath(request);//获取并创建路径；
        try {
            Document document = new SAXReader().read(filepath);
            Map<String, String> map = new HashMap<String, String>();// 将解析结果存储在HashMap中
            Element root = document.getRootElement();  // 得到xml根元素
            List<Element> elementList = root.elements();// 得到根元素的全部子节点
            for (Element e : elementList) { // 遍历全部子节点
                map.put(e.getName(), e.getText());
            }
            if (elementList.size() == 0) {
                creatNode(filepath, "*", "*");//添加节点
            }
            accessToken = map.get("AccessToken");
            lastTime = map.get("AccessExpires");
            Date now = new Date();
            Date accessExpires = DateUtil.strToDate("yyyy-MM-dd HH:mm:ss", lastTime);
            accessToken = getAccessTokenFromWC();
            accessExpires = new Date(now.getTime() + Config.time);
            String nextTime = DateUtil.DateToStr("yyyy-MM-dd HH:mm:ss", accessExpires);
            root.selectSingleNode("AccessToken").setText(accessToken+"c");
            root.selectSingleNode("AccessExpires").setText(nextTime);
            XMLWriter writer = new XMLWriter(new FileWriter(new File(filepath)));
            writer.write(document);
            writer.close();
        } catch (Exception ex) {
            System.out.println("获取文件失败: " + ex.getMessage());
        }
        return accessToken;
    }
}
