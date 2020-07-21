package com.csw.transform.util;

import com.csw.file.util.FileOperationUtil;
import org.junit.Test;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * 	该类主要提供了将sensorML转换为ebRIM文档内容的方法
 * */
public class TransformSensorMLToebRIMAction {
	private static final String TRANSFORM_FILE = "src/main/resources/transform/SensorMLToEbRIM.xsl";

	/**
	 *	用户将参数sensorML内容转换为ebRIM内容，并且将转换结果传递回来
	 * @param sensorML:需要转换的sensorML内容
	 * @return 返回转换后的ebRIM内容
	 * 
	 * */
	public String parseSensorMLToEbRIM(String sensorML) {
		String result = "";
		String webPath = FileOperationUtil.getWebPath();
		String xslFilePath = webPath+"transform/SensorMLToEbRIM.xsl";
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslFilePath));
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			StreamResult streamResult = new StreamResult(byteArrayOutputStream);
			InputStream inputStream = new ByteArrayInputStream(sensorML.getBytes());//将sensorML字符串转换为流
			StreamSource streamSource = new StreamSource(inputStream);
			transformer.transform(streamSource, streamResult);
			result = byteArrayOutputStream.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
