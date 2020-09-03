/*
 * You are strictly prohibited to copy, disclose, distribute, modify, or use this program in part
 * or as a whole without the prior written consent of INCHEON FREE ECONOMIC ZONE AUTHORITY.
 * INCHEON FREE ECONOMIC ZONE AUTHORITY, owns the intellectual property rights in and to this
 * program.
 *
 * (COPYRIGHT 2014(c) INCHEON FREE ECONOMIC ZONE AUTHORITY. ALL RIGHTS RESERVED| Confidential)
 */
package com.DBtoDB.cmn;

/**
 * 각 서비스의 서비스명과 오퍼레이션 명을 정의하는 Enum
 * 
 * <pre>
 * 하드코딩된 서비스명을 Enum으로 변경하여
 * Enum한곳에서 관리
 * </pre>
 * 
 */
public enum SvcProp {

    PING( "pingSvc" ), XML_PATH( "/root/sndbizdata/row" );

    String svcNm;

    SvcProp( String svcNm ) {
        this.svcNm = svcNm;
    }

    public String getSvcNm() {
        return svcNm;
    }

}
