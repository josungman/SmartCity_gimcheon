package com.DBtoDB.rcv;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.jms.core.JmsTemplate;

import com.DBtoDB.cmn.BigLog;
import com.DBtoDB.cmn.SeqValueGenerator;
import com.DBtoDB.cmn.SvcProp;
import com.DBtoDB.snd.SndMsgDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indigo.esb.common.IndigoUtil;
import com.indigo.esb.log.db.model.TbRecvTlgrHisr;
import com.mb.mci.common.jms.MessageSendTemplate;
import com.mb.mci.common.message.MCIMessage;

public class RcvMsgDB implements MessageListener {
    private Log log = LogFactory.getLog( RcvMsgDB.class );

    HashMap<String, String> headerMap = null;

    public String filename;
    
    String serviceName = "";

    RcvMsgDao rcvDao = new RcvMsgDao();

    private String WEB_CHANNEL_ID;

    private String WEB_SOCKET_QUE;

    // properties
    private JmsTemplate jmsTemplate;

    //voiceware api setting
    private String serverip_vm;
    

	RcvMsgDao dao = new RcvMsgDao();
    
    public void init() {
        log.debug( "@@@@@@@@ init start @@@@@@@@" );
    }

    public void onMessage( Message message ) {
        try {
            log.info( "== onMessage start ==" );
            String jsonMsg = IndigoUtil.getMessage( message );
            headerMap = getHeaderMap( message );
            
            
            log.info("===================hearermap=================");
            
            log.info(headerMap);
            
            log.info("===================hearermap=================");
            
            serviceName = headerMap.get( "serviceName" );
            // 핑 메시지 받기
            if ( SvcProp.PING.getSvcNm().equals( serviceName ) ) {
                log.info( "== rcvd ping message ==" );
                return;
            }
            BigLog.loggingBigStr( log, "== rcvd msg : ", jsonMsg );
            JSONArray array = new JSONArray( jsonMsg );
            List<HashMap<String, Object>> rowList = new ArrayList<HashMap<String, Object>>();
           
            
//          
            
            
            for ( int i = 0; i < array.length(); i++ ) {
                rowList.add( jsonStringToMap( array.getJSONObject( i ) ) );
            }
            	
            
            //api 설정 추가 부분================================
            
            if ( !rowList.isEmpty() ) {
            	
              	String Kind = rowList.get(0).get("KIND").toString();
              	String ttstext = rowList.get(0).get("MSG_CONTENT").toString();
              	
              	log.info("kind check : " + Kind);
              	
              	
              	//voice 음성
              	if(Kind.equals("V") || Kind == "V") {
              		
              		log.info("==== TTS Job START ====");

              		//filename 해더에서 가져오기
              		headerMap = getHeaderMap( message );
                    filename = headerMap.get( "msg_filename" );
              		
                            
              		//voiceware api
              		dao.VW_API(ttstext, serverip_vm,filename);
              		
              		 insert( rowList );
              		 
              	}else {
              		
      
              		 filename = null; //V코드가 아닐시 기존에 들어있는값 초기화	
              		 insert( rowList );
              		
              	}
            	
            	
            }
           
        }
        catch ( Exception e ) {
            log.info( e.getMessage(), e );
        }
    }

    // DATA INSERT
    public void insert( List<HashMap<String, Object>> rowList ) throws Exception {
        String methodNm = headerMap.get( "methodNm" );
        String serviceName = headerMap.get( "serviceName" );
        log.info( "===  serviceName : " + serviceName + ", methodNm : " + methodNm );
        // make trnscId
        String trnscId = null;
        String trsmId = headerMap.get( "trsmId" );
        // insert
        log.info( "[INSERT-MSG] ==> " + rowList );
        
     
        
        for ( HashMap<String, Object> cnct : rowList ) {
            trnscId = trsmId + SeqValueGenerator.create();
            
            cnct.put( "trnscId", trnscId );
            
            //mybatis filename추가 (확장자 포함)
            cnct.put("MSG_FILENAME", filename + ".wav");
            
            rcvDao.insert( methodNm, cnct );
          
        }
        log.info( "===  INSERT END ===" );
    }

    // 웹소켓으로 DATA 전송
    public void transport( HashMap<String, Object> trsmMap ) throws Exception {
        MCIMessage msg = new MCIMessage();
        JSONObject object = new JSONObject( trsmMap );
        msg.setTran_code( headerMap.get( "tlgrId" ).toString() );
        msg.setTran_seq( trsmMap.get( "trnscId" ).toString() );
        msg.setTran_channel( headerMap.get( "trsmId" ).toString() );
        // 원 데이터 json 형식으로 만들기
        object.remove( "trnscId" );
        msg.setDataObj( object.toString() );
        msg.setTran_dest( WEB_SOCKET_QUE );
        Map<String, String> propMap = new HashMap<String, String>();
        // 채널 id 설정
        propMap.put( "call", WEB_CHANNEL_ID );
        log.info( "== transport MCIMessage : " + object.toString() );
        MessageSendTemplate.sendMessageMCIM( jmsTemplate, msg, msg.getTran_dest(), propMap );
        log.info( "==MCIMessage end==" );
    }

    // 수신 전문 로그 저장
    public void receiveTelegram( String trnscId ) throws Exception {
        TbRecvTlgrHisr tbRecvTlgrHisr = new TbRecvTlgrHisr();
        Map<String, String> paramMap = new HashMap<String, String>();
        // 정해진 데이터셋
        paramMap.put( "loggingType", "receive" );
        tbRecvTlgrHisr.setTrnscId( trnscId );
        tbRecvTlgrHisr.setFcltId( headerMap.get( "trsmId" ).toString() );
        tbRecvTlgrHisr.setRecvSeq( new BigDecimal( 0 ) );
        tbRecvTlgrHisr.setRecvDt( new Timestamp( System.currentTimeMillis() ) );
        tbRecvTlgrHisr.setRecvFileNm( "" );
        tbRecvTlgrHisr.setTlgrId( headerMap.get( "tlgrId" ).toString() );
        tbRecvTlgrHisr.setIntnId( headerMap.get( "trsmId" ).toString() );
        tbRecvTlgrHisr.setRecvTlgrDvsn( "2" );
        tbRecvTlgrHisr.setOperSvrId( "SV00009" );
        MessageSendTemplate.sendMessageObj( jmsTemplate, tbRecvTlgrHisr, "QU.SD_MSG_LOGGER", paramMap );
        log.info( "== receiveTelegram end ==" );
    }

    // snd에서 보내온 메시지에서 Header 꺼내기
    private HashMap<String, String> getHeaderMap( Message message ) throws JMSException {
        HashMap<String, String> hM = new HashMap<String, String>();
        Enumeration<?> nameList = message.getPropertyNames();
        String name;
        // header에 무슨 property가 있는지 확인하려면 snd단에 가서 확인하기
        while ( nameList.hasMoreElements() ) {
            name = (String) nameList.nextElement();
            hM.put( name, message.getStringProperty( name ) );
        }
        return hM;
    }

    public HashMap<String, Object> jsonStringToMap( JSONObject jsonMsg ) throws IOException {
        return new ObjectMapper().readValue( jsonMsg.toString(), new TypeReference<Map<String, Object>>() {
        } );
    }

    // Exception 발생시 ERROR 내용 INSERT
    private void insertError( String errorMsg, String trsmId ) throws Exception {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put( "trsmId", trsmId );
        String trnscId = trsmId + SeqValueGenerator.create();
        param.put( "trnscId", trnscId );
        param.put( "serviceName", serviceName );
        param.put( "errorMsg", errorMsg );
        param.put( "errorCd", "E" ); // ERROR구분CODE
        rcvDao.insertErrorMsg( param );
    }

    /**
     * @param jmsTemplate the jmsTemplate to set
     */
    public void setJmsTemplate( JmsTemplate jmsTemplate ) {
        this.jmsTemplate = jmsTemplate;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    /**
     * @return the wEB_CHANNEL_ID
     */
    public String getWEB_CHANNEL_ID() {
        return WEB_CHANNEL_ID;
    }

    /**
     * @param wEB_CHANNEL_ID the wEB_CHANNEL_ID to set
     */
    public void setWEB_CHANNEL_ID( String wEB_CHANNEL_ID ) {
        WEB_CHANNEL_ID = wEB_CHANNEL_ID;
    }

    /**
     * @return the wEB_SOCKET_QUE
     */
    public String getWEB_SOCKET_QUE() {
        return WEB_SOCKET_QUE;
    }

    /**
     * @param wEB_SOCKET_QUE the wEB_SOCKET_QUE to set
     */
    public void setWEB_SOCKET_QUE( String wEB_SOCKET_QUE ) {
        WEB_SOCKET_QUE = wEB_SOCKET_QUE;
    }

    
    public String getServerip_vm() {
		return serverip_vm;
	}

	public void setServerip_vm(String serverip_vm) {
		this.serverip_vm = serverip_vm;
	}
    
}
