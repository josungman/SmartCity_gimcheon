package com.gis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indigo.cep.epl.generated.CepEPL2GrammarParser.arrayExpression_return;

public class GisCore extends QuartzJobBean {

	private Log log = LogFactory.getLog(GisCore.class);
	
	private String ttspath;
	
	private dao Dao = new dao();

	private String beforeEPSG;
	
	private String afterEPSG;
	
	private String targetTable;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub

		try {
			onSignal();
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	List<HashMap<String, Object>> rowList = null;

	// job(onSignal) 메소드
	@SuppressWarnings("null")
	private void onSignal() throws Exception {

		
		long start = System.currentTimeMillis();

		
		
		// Select 쿼리에 들어갈 HashMap 타입
		HashMap<String, Object> targetmap = new HashMap<String, Object>();
		
		// DB Select
		targetmap.put("targetTable", targetTable);
		rowList = Dao.select("SQL",targetmap);

		String[] LON = null;
		String[] LAT = null;
		String[] FCLT_ID = null;

		
		
		// Select된 데이터 Jason 변환
		if (!rowList.isEmpty()) {

			JSONArray array = new JSONArray();
			for (HashMap<String, Object> map : rowList) {
				array.put(new JSONObject(map));
			}
			String msg = array.toString();

			log.info("JSON : " + msg);

			
			
			// Jason을 다시 분리하여 각각의 배열변수에 다시 대입
			List<HashMap<String, Object>> AddList = new ArrayList<HashMap<String, Object>>();

			LON = new String[array.length()];
			LAT = new String[array.length()];
			FCLT_ID = new String[array.length()];

			for (int i = 0; i < array.length(); i++) {
				
				
				// jsonStringToMap메서드 이용하여 맵 형식으로 변환
				AddList.add(jsonStringToMap(array.getJSONObject(i)));

				
				
				// 배열 변수에 대입
				LON[i] = AddList.get(i).get("LON").toString();
				LAT[i] = AddList.get(i).get("LAT").toString();
				FCLT_ID[i] = AddList.get(i).get("FCLT_ID").toString();

			}

			
			
			// 좌표변환 후 쿼리 업데이트
			for (int i = 0; i < array.length(); i++) {

				double X = 0;
				double Y = 0;

				// 좌표변환 메서드X
				X = Dao.Change_GIS_X(beforeEPSG,afterEPSG, LON[i], LAT[i]);

				// 좌표변환 메서드Y
				Y = Dao.Change_GIS_Y(beforeEPSG,afterEPSG, LON[i], LAT[i]);

				// Update 쿼리에 들어갈 HashMap 타입
				HashMap<String, Object> Datamap = new HashMap<String, Object>();

				Datamap.put("FCLT_ID", FCLT_ID[i]);
				Datamap.put("LAT", X);
				Datamap.put("LON", Y);
				
				//EPSG: 는 제외
				Datamap.put("afterEPSG", afterEPSG.substring(5));
				Datamap.put("targetTable", targetTable);
			
				Dao.update("SQL", Datamap);

			}
			long end = System.currentTimeMillis();

			log.info("");
			log.info("===================================================");
			log.info("================= GIS JOb Finish ==================");
			log.info("========= 동작시간 : " + ( end - start )/1000.0 + " =========");
			log.info("");
		}

	}

	
	public HashMap<String, Object> jsonStringToMap(JSONObject jsonMsg) throws IOException {
		return new ObjectMapper().readValue(jsonMsg.toString(), new TypeReference<Map<String, Object>>() {
		});
	}

	
	
	
	
	
	
//	=============================================
	
	
	
	
	
	
	
	public String getBeforeEPSG() {
		return beforeEPSG;
	}


	public void setBeforeEPSG(String beforeEPSG) {
		this.beforeEPSG = beforeEPSG;
	}


	public String getAfterEPSG() {
		return afterEPSG;
	}


	public void setAfterEPSG(String afterEPSG) {
		this.afterEPSG = afterEPSG;
	}


	public String getTargetTable() {
		return targetTable;
	}


	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}

}
