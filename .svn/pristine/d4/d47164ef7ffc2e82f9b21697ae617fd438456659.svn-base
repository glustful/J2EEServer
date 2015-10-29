package com.dunbian.jujiabao.wcpay.weixin;

import com.dunbian.jujiabao.wcpay.Config;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/16 0016.
 */
public class WCReadSetting {
    public static void readSetting(HttpServletRequest request) {

        String filepath = initXMLFile(request);//获取并创建路径；
        try {
            Document document = new SAXReader().read(filepath);
            Map<String, String> map = new HashMap<String, String>();// 将解析结果存储在HashMap中
            Element root = document.getRootElement();  // 得到xml根元素
            List<Element> elementList = root.elements();// 得到根元素的全部子节点
            for (Element e : elementList) { // 遍历全部子节点
                map.put(e.getName(), e.getText());
            }
            if (elementList.size() == 0) {
                initSetting(filepath);//添加节点
            }
            Config.appid = map.get("appid");
            Config.appsecret = map.get("appsecret");
            Config.partner = map.get("partner");
            Config.partnerkey = map.get("partnerkey");
            Config.notifyurl = map.get("notifyurl");
            Config.hostip = map.get("hostip");
            Config.url302 = map.get("url302");
            Config.notifyrechargeurl = map.get("notifyrechargeurl");
            Config.time = Integer.parseInt(map.get("time"));

        } catch (Exception ex) {
            System.out.println("获取文件失败: " + ex.getMessage());
        }
    }

    /**
     * 获取路径，如果没有配置，则创建XML，并加入根节点；
     *
     * @param request
     * @return
     */
    public static String initXMLFile(HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("/");
        String path1 = path + Config.saveConfigPath;
        String path2 = path + Config.saveConfigPath + Config.saveSettingName;
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
                    document.addElement("setting");
                    try {
                        XMLWriter output = new XMLWriter(new FileWriter(new File(path2)));
                        output.write(document);
                        output.close();
                        initSetting(path2);//创建初始化节点；
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

    /**
     * 初始化setting.xml；
     *
     * @param path
     */
    public static void initSetting(String path)//初始化创建xml
    {
        try {
            Document doc = new SAXReader().read(new File(path));
            Element root = doc.getRootElement();

            Element appid=root.addElement("appid");
            appid.setText(Config.appid);
            Element appsecret=root.addElement("appsecret");
            appsecret.setText(Config.appsecret);
            Element partner= root.addElement("partner");
            partner.setText(Config.partner);
            Element partnerkey=root.addElement("partnerkey");
            partnerkey.setText(Config.partnerkey);
            Element notifyurl= root.addElement("notifyurl");
            notifyurl.setText(Config.notifyurl);
            Element notifyrechargeurl= root.addElement("notifyrechargeurl");
            notifyrechargeurl.setText(Config.notifyrechargeurl);
            Element hostip= root.addElement("hostip");
            hostip.setText(Config.hostip);
            Element url302= root.addElement("url302");
            url302.setText(Config.url302);
            Element time= root.addElement("time");
            time.setText(Integer.toString(Config.time));


            OutputFormat opf = new OutputFormat("\t", true, "UTF-8");
            opf.setTrimText(true);
            XMLWriter writer = new XMLWriter(new FileOutputStream(path), opf);
            writer.write(doc);
            writer.close();
        } catch (Exception error) {
            System.out.println("添加节点出错失败: " + error.getMessage());
        }
    }
}
