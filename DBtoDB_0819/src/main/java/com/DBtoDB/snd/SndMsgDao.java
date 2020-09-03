package com.DBtoDB.snd;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SndMsgDao {

	private Log log = LogFactory.getLog( SndMsgDao.class );
	
	static SqlSessionFactory sqlSessionFactory;

    final String MAPPER_NAME = "SndMsg";

    private StringBuffer stringBuffer = new StringBuffer();

    static {
        // DB 접속 정보
        try {
            String resource = "Snd-mybatis-config.xml";
            InputStream reader = Resources.getResourceAsStream( resource );
            if ( sqlSessionFactory == null ) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build( reader );
            }
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public List<HashMap<String, Object>> select( String methodName ) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        stringBuffer.append( MAPPER_NAME ).append( ".select" ).append( methodName );
       
        log.info("====== 0. stringBuffer check ======");
        	log.info(stringBuffer.toString());
        log.info("====== 0. stringBuffer check ======");
        
        try {
            return session.selectList( stringBuffer.toString() );
        }
        finally {
            session.close();
            stringBuffer.setLength( 0 );
        }
    }

    /**
     *
     * @param methodName
     * @param paramMap
     * @throws Exception
     */
    public void updateCnctYn( String methodName, HashMap<String, Object> paramMap ) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        stringBuffer.append( MAPPER_NAME ).append( ".updateCnctYn" ).append( methodName );
        try {
        	 log.info(" ===== 03. Y update result =====");
        	
        	session.update( stringBuffer.toString(), paramMap );
            session.commit();
            
            log.info(" ===== 03. Y update result =====");
        
        
        }
        finally {
            session.close();
            stringBuffer.setLength( 0 );
        }
    }

    // ERROR INSERT
    public void insertErrorMsg( HashMap<String, Object> paramMap ) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        stringBuffer.append( MAPPER_NAME ).append( ".insertErrorMsg" );
        try {
            session.insert( stringBuffer.toString(), paramMap );
            session.commit();
        }
        finally {
            session.close();
            stringBuffer.setLength( 0 );
        }
    }

    // 텍스트 합성 API(08.27)
//    public void TTS (String ttstext, String ttspath) {
//    	
//    		//Naver 클라우드 플랫폼 API 사용 (CSS) 1000글자당 6.4원씩 나감...
//    	
//    	  String clientId = "74390c32gz";//애플리케이션 클라이언트 아이디값";
//          String clientSecret = "Iw3ASNzeUfHp4RbSnRFN2Aol7mdqhNHQYzT8gWtt";//애플리케이션 클라이언트 시크릿값";
//          try {
//              String text = URLEncoder.encode(ttstext, "UTF-8"); // 13자
//              String apiURL = "https://naveropenapi.apigw.ntruss.com/voice/v1/tts";
//              URL url = new URL(apiURL);
//              HttpURLConnection con = (HttpURLConnection)url.openConnection();
//              con.setRequestMethod("POST");
//              con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
//              con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
//              // post request
//              String postParams = "speaker=mijin&speed=0&text=" + text;
//              con.setDoOutput(true);
//              DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//              
//              System.out.println(wr.toString());
//              
//              wr.writeBytes(postParams);
//              wr.flush();
//              wr.close();
//              int responseCode = con.getResponseCode();
//              
//              System.out.println(responseCode);
//              
//              BufferedReader br;
//              if(responseCode==200) { // 정상 호출
//                  InputStream is = con.getInputStream();
//                  int read = 0;
//                  byte[] bytes = new byte[1024];
//                  
//               
//                  // [년월일시분초]_[밀리세컨드] 파일이름
//                  Date dat = new Date();
//                  SimpleDateFormat datformat = new SimpleDateFormat("yyyyMMddHHmmss_SSS", Locale.KOREA);
//                                               
//                  String tempname = datformat.format(dat);
//                  //System.out.println(tempname);
//                  
//                  //파일 경로 및 디렉터리 만들기[년월]
//                  SimpleDateFormat dirformat = new SimpleDateFormat("yyyy_MM", Locale.KOREA);
//                  
//                  String path = ttspath;                       
//       
//                  File dir = new File(path + dirformat.format(dat));
//                  dir.mkdir();
//                  //System.out.println(dir.toString());
//                
//                  //디렉터리 안에 텍스트합성파일 넣기
//                  String e = "/";
//                  File f = new File(dir.toString() + e + tempname + ".wav");
//                  f.createNewFile();
//                  OutputStream outputStream = new FileOutputStream(f);
//                  while ((read =is.read(bytes)) != -1) {
//                      outputStream.write(bytes, 0, read);
//                  }
//                  
//                  is.close();
//              
//                  log.info("path : " + dir);
//                  log.info("filename : " + tempname);
//                  log.info("!! TTS JOB COMPLETE !!");
//              
//              } else {  // 오류 발생
//                  br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
//                  String inputLine;
//                  StringBuffer response = new StringBuffer();
//                  while ((inputLine = br.readLine()) != null) {
//                      response.append(inputLine);
//                  }
//                  br.close();
//                  //System.out.println(response.toString());
//              }
//          } catch (Exception e) {
//              System.out.println(e);
//          }
    	
//    }
        
}
