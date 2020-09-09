package com.gis;

import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.codehaus.jettison.json.JSONObject;
import org.osgeo.proj4j.BasicCoordinateTransform;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.ProjCoordinate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.ibatis.session.SqlSession;

public class dao {

	private Log log = LogFactory.getLog(dao.class);

	static SqlSessionFactory sqlSessionFactory;

	private StringBuffer stringBuffer = new StringBuffer();

	final String MAPPER_NAME = "Query";

	static {
		// DB 접속 정보
		try {
			String resource = "mybatis-config.xml";
			InputStream reader = Resources.getResourceAsStream(resource);
			if (sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<HashMap<String, Object>> select(String methodName, HashMap<String, Object> targetTable)
			throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		stringBuffer.append(MAPPER_NAME).append(".select").append(methodName);

		log.info("====== stringBuffer check ======");
		log.info(stringBuffer.toString());
		log.info("====== stringBuffer check ======");

		try {

			log.info("DB SELECT RESULT : " + session.selectList(stringBuffer.toString(), targetTable));
			return session.selectList(stringBuffer.toString(), targetTable);
		} finally {
			session.close();
			stringBuffer.setLength(0);
		}
	}

	public void update(String methodName, HashMap<String, Object> paramMap) throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		stringBuffer.append(MAPPER_NAME).append(".update").append(methodName);
		try {
			log.info(" ===== update result start =====");

			session.update(stringBuffer.toString(), paramMap);
			session.commit();

			log.info(" ===== update result end =====");

		} finally {
			session.close();
			stringBuffer.setLength(0);
		}
	}

	public Double Change_GIS_X(String beforeEPSG, String afterEPSG, String LON, String LAT) {
		// FD_CP_CTV LAT
		double point_x = Double.parseDouble(LAT);
		double point_y = Double.parseDouble(LON);

		CRSFactory factory = new CRSFactory();
		CoordinateReferenceSystem srcCrs = factory.createFromName(beforeEPSG);// 현재 좌표
		CoordinateReferenceSystem dstCrs = factory.createFromName(afterEPSG);// 변경할 좌표
		BasicCoordinateTransform transform = new BasicCoordinateTransform(srcCrs, dstCrs);

		log.info(srcCrs + " => " + dstCrs);

		double x = (point_x); // x좌표(경도 LAT)
		double y = (point_y);// y좌표(위도 LON)

		ProjCoordinate srcCoord = new ProjCoordinate(y, x); // 위,경도
		ProjCoordinate dstCoord = new ProjCoordinate();

		transform.transform(srcCoord, dstCoord);// 좌표변환

		// 변환된 좌표
		log.info("Complete(_X)" + srcCoord.x + " => " + dstCoord.x);

		return dstCoord.x;

	}

	public Double Change_GIS_Y(String beforeEPSG, String afterEPSG, String LON, String LAT) {
		// FD_CP_CTV LAT
		double point_x = Double.parseDouble(LAT);
		double point_y = Double.parseDouble(LON);

		CRSFactory factory = new CRSFactory();
		CoordinateReferenceSystem srcCrs = factory.createFromName(beforeEPSG);// 현재 좌표
		CoordinateReferenceSystem dstCrs = factory.createFromName(afterEPSG);// 변경할 좌표
		BasicCoordinateTransform transform = new BasicCoordinateTransform(srcCrs, dstCrs);

		log.info(srcCrs + " => " + dstCrs);

		double x = (point_x); // x좌표(경도 LAT)
		double y = (point_y);// y좌표(위도 LON)

		ProjCoordinate srcCoord = new ProjCoordinate(y, x); // 위,경도
		ProjCoordinate dstCoord = new ProjCoordinate();

		transform.transform(srcCoord, dstCoord);// 좌표변환

		// 변환된 좌표
		log.info("Complete(_Y)" + srcCoord.y + " => " + dstCoord.y);

		return dstCoord.y;

	}

}
