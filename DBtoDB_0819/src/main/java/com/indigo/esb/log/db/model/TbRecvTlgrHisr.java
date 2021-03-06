package com.indigo.esb.log.db.model;

import java.math.BigDecimal;
import java.util.Date;

public class TbRecvTlgrHisr extends TbRecvTlgrHisrKey{

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.TRNSC_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private String trnscId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.FCLT_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private String fcltId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.RECV_DT
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private Date recvDt;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.RECV_FILE_NM
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private String recvFileNm;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.TLGR_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private String tlgrId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.INTN_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private String intnId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.RECV_TLGR_DVSN
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private String recvTlgrDvsn;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.OPER_SVR_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private String operSvrId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.RECV_SEQ
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private BigDecimal recvSeq;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.FRST_REGISTER_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private String frstRegisterId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.FRST_RGST_DT
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private Date frstRgstDt;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.FRST_RGST_PRG_NM
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private String frstRgstPrgNm;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.LAST_UPDUSR_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private String lastUpdusrId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.LAST_CRCT_DT
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private Date lastCrctDt;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TB_RECV_TLGR_HISR.LAST_CRCT_PRG_NM
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    private String lastCrctPrgNm;

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.TRNSC_ID
     * @return  the value of TB_RECV_TLGR_HISR.TRNSC_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public String getTrnscId() {
        return trnscId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.TRNSC_ID
     * @param trnscId  the value for TB_RECV_TLGR_HISR.TRNSC_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setTrnscId(String trnscId) {
        this.trnscId = trnscId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.FCLT_ID
     * @return  the value of TB_RECV_TLGR_HISR.FCLT_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public String getFcltId() {
        return fcltId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.FCLT_ID
     * @param fcltId  the value for TB_RECV_TLGR_HISR.FCLT_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setFcltId(String fcltId) {
        this.fcltId = fcltId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.RECV_DT
     * @return  the value of TB_RECV_TLGR_HISR.RECV_DT
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public Date getRecvDt() {
        return recvDt;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.RECV_DT
     * @param recvDt  the value for TB_RECV_TLGR_HISR.RECV_DT
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setRecvDt(Date recvDt) {
        this.recvDt = recvDt;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.RECV_FILE_NM
     * @return  the value of TB_RECV_TLGR_HISR.RECV_FILE_NM
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public String getRecvFileNm() {
        return recvFileNm;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.RECV_FILE_NM
     * @param recvFileNm  the value for TB_RECV_TLGR_HISR.RECV_FILE_NM
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setRecvFileNm(String recvFileNm) {
        this.recvFileNm = recvFileNm;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.TLGR_ID
     * @return  the value of TB_RECV_TLGR_HISR.TLGR_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public String getTlgrId() {
        return tlgrId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.TLGR_ID
     * @param tlgrId  the value for TB_RECV_TLGR_HISR.TLGR_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setTlgrId(String tlgrId) {
        this.tlgrId = tlgrId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.INTN_ID
     * @return  the value of TB_RECV_TLGR_HISR.INTN_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public String getIntnId() {
        return intnId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.INTN_ID
     * @param intnId  the value for TB_RECV_TLGR_HISR.INTN_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setIntnId(String intnId) {
        this.intnId = intnId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.RECV_TLGR_DVSN
     * @return  the value of TB_RECV_TLGR_HISR.RECV_TLGR_DVSN
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public String getRecvTlgrDvsn() {
        return recvTlgrDvsn;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.RECV_TLGR_DVSN
     * @param recvTlgrDvsn  the value for TB_RECV_TLGR_HISR.RECV_TLGR_DVSN
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setRecvTlgrDvsn(String recvTlgrDvsn) {
        this.recvTlgrDvsn = recvTlgrDvsn;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.OPER_SVR_ID
     * @return  the value of TB_RECV_TLGR_HISR.OPER_SVR_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public String getOperSvrId() {
        return operSvrId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.OPER_SVR_ID
     * @param operSvrId  the value for TB_RECV_TLGR_HISR.OPER_SVR_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setOperSvrId(String operSvrId) {
        this.operSvrId = operSvrId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.RECV_SEQ
     * @return  the value of TB_RECV_TLGR_HISR.RECV_SEQ
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public BigDecimal getRecvSeq() {
        return recvSeq;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.RECV_SEQ
     * @param recvSeq  the value for TB_RECV_TLGR_HISR.RECV_SEQ
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setRecvSeq(BigDecimal recvSeq) {
        this.recvSeq = recvSeq;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.FRST_REGISTER_ID
     * @return  the value of TB_RECV_TLGR_HISR.FRST_REGISTER_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public String getFrstRegisterId() {
        return frstRegisterId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.FRST_REGISTER_ID
     * @param frstRegisterId  the value for TB_RECV_TLGR_HISR.FRST_REGISTER_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setFrstRegisterId(String frstRegisterId) {
        this.frstRegisterId = frstRegisterId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.FRST_RGST_DT
     * @return  the value of TB_RECV_TLGR_HISR.FRST_RGST_DT
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public Date getFrstRgstDt() {
        return frstRgstDt;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.FRST_RGST_DT
     * @param frstRgstDt  the value for TB_RECV_TLGR_HISR.FRST_RGST_DT
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setFrstRgstDt(Date frstRgstDt) {
        this.frstRgstDt = frstRgstDt;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.FRST_RGST_PRG_NM
     * @return  the value of TB_RECV_TLGR_HISR.FRST_RGST_PRG_NM
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public String getFrstRgstPrgNm() {
        return frstRgstPrgNm;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.FRST_RGST_PRG_NM
     * @param frstRgstPrgNm  the value for TB_RECV_TLGR_HISR.FRST_RGST_PRG_NM
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setFrstRgstPrgNm(String frstRgstPrgNm) {
        this.frstRgstPrgNm = frstRgstPrgNm;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.LAST_UPDUSR_ID
     * @return  the value of TB_RECV_TLGR_HISR.LAST_UPDUSR_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public String getLastUpdusrId() {
        return lastUpdusrId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.LAST_UPDUSR_ID
     * @param lastUpdusrId  the value for TB_RECV_TLGR_HISR.LAST_UPDUSR_ID
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setLastUpdusrId(String lastUpdusrId) {
        this.lastUpdusrId = lastUpdusrId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.LAST_CRCT_DT
     * @return  the value of TB_RECV_TLGR_HISR.LAST_CRCT_DT
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public Date getLastCrctDt() {
        return lastCrctDt;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.LAST_CRCT_DT
     * @param lastCrctDt  the value for TB_RECV_TLGR_HISR.LAST_CRCT_DT
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setLastCrctDt(Date lastCrctDt) {
        this.lastCrctDt = lastCrctDt;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TB_RECV_TLGR_HISR.LAST_CRCT_PRG_NM
     * @return  the value of TB_RECV_TLGR_HISR.LAST_CRCT_PRG_NM
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public String getLastCrctPrgNm() {
        return lastCrctPrgNm;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TB_RECV_TLGR_HISR.LAST_CRCT_PRG_NM
     * @param lastCrctPrgNm  the value for TB_RECV_TLGR_HISR.LAST_CRCT_PRG_NM
     * @mbggenerated  Tue Sep 06 13:43:03 KST 2016
     */
    public void setLastCrctPrgNm(String lastCrctPrgNm) {
        this.lastCrctPrgNm = lastCrctPrgNm;
    }
}