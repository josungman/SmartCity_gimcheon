package com.DBtoDB.rcv;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import voiceware.libttsapi;

public class RcvMsgDao {

	 private Log log = LogFactory.getLog( RcvMsgDao.class );
	
	static SqlSessionFactory sqlSessionFactory;

    final String MAPPER_NAME = "RcvMsg";

    private StringBuffer stringBuffer = new StringBuffer();

    static {
        // DB 접속 정보
        try {
            String resource = "Rcv-mybatis-config.xml";
            InputStream reader = Resources.getResourceAsStream( resource );
            if ( sqlSessionFactory == null ) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build( reader );
            }
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    /**
     * DB INSERT, mapperId = insert + methodName
     * @param methodName
     * @param cnct
     * @throws Exception
     */
    public void insert( String methodName, HashMap<String, Object> cnct ) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        
      
        stringBuffer.append( MAPPER_NAME ).append( ".insert" ).append( methodName );
        
        
        try {
            
        	
        	
        	log.info("============ RCV SQL ============");
        	log.info("");
        	
        	session.insert( stringBuffer.toString(), cnct );
        	
        	log.info("");
        	log.info("============ RCV SQL ============");
           
            
            session.commit();
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

    
    
    //VoiceWare API 추가.(
    
    public void VW_API(String TTSText,String ServerIP,String filename) {
    	
    	int nReturn = 0;
		String szText = TTSText;
		libttsapi ttsapi = new libttsapi();
		
		//디렉터리 지정 gimcheon_U_YYYY_MM (yyyy_mm)
		String left = filename.substring(11,15);
		String right = filename.substring(15,17);
		
		try {
		nReturn = ttsapi.ttsRequestFileEx(ServerIP, 7000, szText, left + "_" + right,
		filename, libttsapi.TTS_HYERYUN_DB, libttsapi.FORMAT_AWAV,
		libttsapi.TEXT_NORMAL, 100, 100, 100, 0);
		} catch (IOException e) {
		nReturn = -9;
		}
		if (nReturn == libttsapi.TTS_RESULT_SUCCESS) {
		
			System.out.println("합성 성공!!!");
			log.info("음성 합성 성공");
		
			log.info("IP : " + ServerIP);
			log.info("TTS_CONTENT : " + TTSText);
			log.info("File : " + filename);
			log.info("Dir : " + left + "_" + right) ;
			
		} else {
		
			System.out.println("합성 실패(" + nReturn + ")!!!");
			log.info("음성 합성 실패 : " + nReturn );
			
			log.info("IP : " + ServerIP);
			log.info("TTS_CONTENT : " + TTSText);
			
		}
    	
    }
    
    
    
    
}
