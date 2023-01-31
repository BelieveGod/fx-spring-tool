package cn.nannar.tool.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author LTJ
 * @date 2023/1/10
 */
public class XmlHelper {
    private static final String CFG_FILE =System.getProperty("user.home")+"/.fx-tool/setting.xml";
    private Document document=null;

    public XmlHelper() throws DocumentException {
        File xml = FileUtil.file(CFG_FILE);
        boolean exist = FileUtil.exist(xml);
        if(!exist){
             document = DocumentHelper.createDocument();
            document.addElement("setting");
        }else{
            SAXReader saxReader = new SAXReader();
             document = saxReader.read(xml);

        }
    }

    public void setTrainNoFile(String path){
        Assert.notNull(document);
        Element rootElement = document.getRootElement();
        if(rootElement==null){
            rootElement = document.addElement("setting");
        }
        Element element = rootElement.element("train-no-file");
        if(element==null){
            element = rootElement.addElement("train-no-file");
        }
        element.setText(path);
    }

    public String getTrainNoFile(){
        Assert.notNull(document);
        Element rootElement = document.getRootElement();
        if(rootElement==null){
            rootElement = document.addElement("setting");
        }
        Element element = rootElement.element("train-no-file");
        if(element==null){
            element = rootElement.addElement("train-no-file");
        }
        return element.getText();
    }

    public String getStyleDirectory(){
        Assert.notNull(document);
        Element rootElement = document.getRootElement();
        if(rootElement==null){
            rootElement = document.addElement("setting");
        }
        Element element = rootElement.element("style-direcotry");
        if(element==null){
            element = rootElement.addElement("style-direcotry");
        }
        return element.getText();
    }

    public void setStyleDirectory(String path){
        Assert.notNull(document);
        Element rootElement = document.getRootElement();
        if(rootElement==null){
            rootElement = document.addElement("setting");
        }
        Element element = rootElement.element("style-direcotry");
        if(element==null){
            element = rootElement.addElement("style-direcotry");
        }
        element.setText(path);
    }

    public String getOutPutDirectory(){
        Assert.notNull(document);
        Element rootElement = document.getRootElement();
        if(rootElement==null){
            rootElement = document.addElement("setting");
        }
        Element element = rootElement.element("output-directory");
        if(element==null){
            element = rootElement.addElement("output-directory");
        }
        return element.getText();
    }

    public void setOutPutDirectory(String path){
        Assert.notNull(document);
        Element rootElement = document.getRootElement();
        if(rootElement==null){
            rootElement = document.addElement("setting");
        }
        Element element = rootElement.element("output-directory");
        if(element==null){
            element = rootElement.addElement("output-directory");
        }
        element.setText(path);
    }

    public void save(){
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        File xml = FileUtil.file(CFG_FILE);
        BufferedWriter writer = FileUtil.getWriter(xml, StandardCharsets.UTF_8, false);
        XMLWriter xmlWriter = new XMLWriter(writer, format);
        try {
            xmlWriter.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                xmlWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
