package com.DBtoDB.cmn;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public final class SeqValueGenerator {

    static int seq = 0;

    private SeqValueGenerator() {
    }

    /**
     * @return yyyyMMddHHmmssSSS + seq( %06d )
     */
    public static String create() {
        if ( seq > 999999 ) {
            seq = 0;
        }
        String initTime = DateFormatUtils.format( new Date(), "yyyyMMddHHmmssSSS" );

        return initTime + String.format( "%06d", seq++ );
    }
}
