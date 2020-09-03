package com.DBtoDB.snd;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.DBtoDB.cmn.BigLog;
import com.DBtoDB.cmn.SvcProp;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indigo.esb.common.IndigoUtil;
import com.indigo.esb.date.DateUtils;
import com.indigo.esb.message.MessageHandler;

public class SndMsgDB extends QuartzJobBean {

	private String ttspath; // tts경로
	
    private String serviceName; // 서비스명

    private String trsmId; // 송신기관 ID

    private String recvId; // 수신기관 ID

    private String tlgrId; // 전문 ID

    private String dummyYn; // 더미데이터 사용 여부

    private String updateCnctYn; // cnctYn 사용 여부

    private String methodNm; // dao method name

    private List<HashMap<String, Object>> dummyDataList;

    // properties
    private JmsTemplate jmsTemplate;

    // snd
    private String sendqname;

    private Log log = LogFactory.getLog( SndMsgDB.class );

    private HashMap<String, String> headerMap = null;

    SndMsgDao dao = new SndMsgDao();

    // 스케줄러 호출 메소드
    @Override
    protected void executeInternal( JobExecutionContext arg0 ) throws JobExecutionException {
        try {
            // make header
            makeHeader( "IF_ID" );
            // ping service 처리
            if ( SvcProp.PING.getSvcNm().equals( serviceName ) ) {
                sendPing();
                return;
            }
            // data 전달
            onSignal( serviceName );
        }
        catch ( Exception e ) {
            log.info( "[ERROR-MSG]==>" + e.getMessage(), e );
        }
    }

    // 스케줄러 호출 메소드
    private void onSignal( String serviceName ) throws Exception {
        log.info( "== onSignal start ==" );
        
        log.info(ttspath);
        
        
        List<HashMap<String, Object>> rowList = null;
        // 더미 데이터를 사용할 것인가?
        if ( "Y".equalsIgnoreCase( dummyYn ) ) {
            rowList = dummyDataList;
        } else {
            rowList = dao.select( methodNm );
        
            log.info(" ======== 01. select result ========= ");
            log.info(rowList);
            log.info(" ======== 01. select result ========= ");
            
            
        }
       
        
        
        //jason 변환 및 큐로 이동
        if ( !rowList.isEmpty() ) {
        	
        	JSONArray array = new JSONArray();
            for ( HashMap<String, Object> map : rowList ) {
                array.put( new JSONObject( map ) );
            }
            String msg = array.toString();
            	

            
      //  ++ 추가코드 KIND컬럼이 V인것은 음원파일로 경로 넣기(09.01)
          //** 대시민 서비스 요청은 한번밖에 안오니까 row는 0으로 입력
            JSONArray Vcheckarr = new JSONArray( msg );
            List<HashMap<String, Object>> VcheckList = new ArrayList<HashMap<String, Object>>();
            
            	VcheckList.add( jsonStringToMap( Vcheckarr.getJSONObject( 0 ) ) );
            	
            	String Kind = VcheckList.get(0).get("KIND").toString();
            	String ttstext = VcheckList.get(0).get("MSG_CONTENT").toString();
            	
            	
            	
            	if(Kind.equals("V") || Kind =="V") {
            	
            		
            	// [년월일시분초]_[밀리세컨드] 파일이름 (해더에 집어 넣기)
                  Date dat = new Date();
                  SimpleDateFormat datformat = new SimpleDateFormat("yyyyMMddHHmmss_SSS", Locale.KOREA);
                                               
                  String tempname = "gimcheon_U_" + datformat.format(dat);
                  
                  log.info("msg_filename ================");
                  log.info("msg_filecreate : " + tempname);
                  log.info("msg_filename ================");
            		
            		//합성 파일명 해더에 집어넣기
            		headerMap.put("msg_filename", tempname);
            		
            	}else {
            		
            		headerMap.put("msg_filename", null);
            		
            	}
            
    //  ++ 추가코드 KIND컬럼이 V인것은 음원파일로 만들기(08.27)
            
            
            sendMessage( msg );
            
            log.info(" ======== 02. JSON result ========= ");
            log.info(msg);
            log.info(" ======== 02. JSON result ========= ");
            
            
            
            if ( "Y".equalsIgnoreCase( updateCnctYn ) ) {
                for ( HashMap<String, Object> paramMap : rowList ) {
                    dao.updateCnctYn( methodNm, paramMap );
                }
            }
        } else {
            log.info( "== Data is not exist ==" );
        }
        log.info( "== onSignal end ==" );
    }


    // make header
    private void makeHeader( String if_id ) {
        String send_time = DateUtils.getCurrentTime();
        String tx_id = IndigoUtil.getTransactionId( if_id, send_time );
        HashMap<String, String> headMap = new HashMap<String, String>();
        headMap.put( "if_id", if_id );
        headMap.put( "tx_id", tx_id );
        headMap.put( "init_time", send_time );
        headMap.put( "trsmId", trsmId );
        headMap.put( "recvId", recvId );
        headMap.put( "tlgrId", tlgrId );
        headMap.put( "serviceName", serviceName );
        headMap.put( "methodNm", methodNm );
        headerMap = headMap;
    }

    private void sendMessage( String msg ) throws Exception {
        BigLog.loggingBigStr( log, "[SEND-MSG]==>", msg );
        MessageHandler.sendMessageToQueue( getJmsTemplate(), msg, getSendqname(), headerMap );
    }

    // Exception 발생시 ERROR 내용 INSERT
    public void sndErrorMsg( String errorMsg ) {
        try {
            Document doc = new Document();
            // xml 형태로 변환
            Element root = new Element( "response" );
            Element data = new Element( "data" );
            root.addContent( data );
            doc.setRootElement( root );
            XMLOutputter serializer = new XMLOutputter();
            Format f = serializer.getFormat();
            f.setEncoding( "UTF-8" );
            f.setIndent( " " );
            f.setLineSeparator( "\r\n" );
            f.setTextMode( Format.TextMode.TRIM );
            serializer.setFormat( f );
            XMLOutputter outputter = new XMLOutputter( Format.getPrettyFormat().setEncoding( "UTF-8" ) );
            String xmlResult = outputter.outputString( doc );
            headerMap.put( "errorMsg", errorMsg );
            MessageHandler.sendMessageToQueue( getJmsTemplate(), xmlResult, getSendqname(), headerMap );
        }
        catch ( Exception e ) {
            log.info( e.getMessage() );
        }
    }

    /**
     * snd와 rcv 사이에 오랜 시간동안 데이터 전달이 없는 경우 rcv단에서 데이터를 못받는 문제를 방지하기 위해 핑을 날리는 함수
     * @throws Exception
     */
    private void sendPing() throws Exception {
        MessageHandler.sendMessageToQueue( getJmsTemplate(), serviceName, getSendqname(), headerMap );
        log.info( "== ping send ==" );
    }

    /*
     * Getter / Setter
     */

    // properties
    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate( JmsTemplate jmsTemplate ) {
        this.jmsTemplate = jmsTemplate;
    }

    public String getSendqname() {
        return sendqname;
    }

    public void setSendqname( String sendqname ) {
        this.sendqname = sendqname;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName( String serviceName ) {
        this.serviceName = serviceName;
    }

    public String getTrsmId() {
        return trsmId;
    }

    public void setTrsmId( String trsmId ) {
        this.trsmId = trsmId;
    }

    public String getRecvId() {
        return recvId;
    }

    public String getTtspath() {
		return ttspath;
	}

	public void setTtspath(String ttspath) {
		this.ttspath = ttspath;
	}

	public void setRecvId( String recvId ) {
        this.recvId = recvId;
    }

    public String getTlgrId() {
        return tlgrId;
    }

    public void setTlgrId( String tlgrId ) {
        this.tlgrId = tlgrId;
    }

    public List<HashMap<String, Object>> getDummyDataList() {
        return dummyDataList;
    }

    public void setDummyDataList( List<HashMap<String, Object>> dummyDataList ) {
        this.dummyDataList = dummyDataList;
    }

    /**
     * @return the dummyYn
     */
    public String getDummyYn() {
        return dummyYn;
    }

    /**
     * @param dummyYn the dummyYn to set
     */
    public void setDummyYn( String dummyYn ) {
        this.dummyYn = dummyYn;
    }

    /**
     * @return the methodNm
     */
    public String getMethodNm() {
        return methodNm;
    }

    /**
     * @param methodNm the methodNm to set
     */
    public void setMethodNm( String methodNm ) {
        this.methodNm = methodNm;
    }

    /**
     * @param updateCnctYn the updateCnctYn to set
     */
    public void setUpdateCnctYn( String updateCnctYn ) {
        this.updateCnctYn = updateCnctYn;
    }

    
    public HashMap<String, Object> jsonStringToMap( JSONObject jsonMsg ) throws IOException {
        return new ObjectMapper().readValue( jsonMsg.toString(), new TypeReference<Map<String, Object>>() {
        } );
    }
    
}
