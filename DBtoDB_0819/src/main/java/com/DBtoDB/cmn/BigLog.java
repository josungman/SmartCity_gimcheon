package com.DBtoDB.cmn;

import org.apache.commons.logging.Log;

/**
 *
 * 사이즈가 큰 String을 나눠서 로그로 출력하는 클래스
 *
 * <pre>
 * 사이즈가 큰 String을 그대로 IMC 로그에 남길 경우 크롬이 뻗기 때문에
 * 해당 클래스를 사용하여 지정된 사이즈만큼 표출
 * </pre>
 *
 * @author metabuild
 * @since 2020. 5. 12.
 * @version 1.0
 * @see
 *
 * <pre>
 *  == 개정이력(Modification Information) ==
 *
 *   수정일           수정자	            수정내용
 *  ---------------------------------------------------
 *   2020. 5. 12.    metabuild        최초생성
 * </pre>
 */
public final class BigLog {

    /**
     * 로그 한줄의 길이
     */
    static int oneLineLength = 2500;

    /**
     * 로그 최대 라인
     */
    static int maxLength = 4 * oneLineLength;

    static StringBuilder sb = new StringBuilder();

    private BigLog() {
    }

    /**
     * String 사이즈가 너무 커서 imc에서 랙먹는걸 방지하기 위해 만듬
     * @param log 해당 클래스의 로그
     * @param preStr bigString 앞에 붙일 string
     * @param bigString 로그 남길 메시지
     */
    public static void loggingBigStr( Log log, String preStr, String bigString ) {
        if ( bigString.length() <= oneLineLength ) {
            log.info( preStr + bigString );
            return;
        }
        int length = bigString.length() > maxLength ? maxLength : bigString.length();
        int i = oneLineLength;
        for ( ; i < length; i += oneLineLength ) {
            sb.append( preStr ).append( bigString.substring( i - oneLineLength, i ) );
            log.info( sb.toString() );
            sb.setLength( 0 );
        }
        if ( length % oneLineLength != 0 ) {
            sb.append( preStr ).append( bigString.substring( i - oneLineLength, length ) );
            log.info( sb.toString() );
            sb.setLength( 0 );
        }
    }
}
