package com.csz.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.net.URLEncoder;
public class Tools {

    private static String SQL_ESCAPE = "/";
    public static int PageSize = 10;
    public Tools() {
    }

    /**
     * @Name:  replace
     * @funcation:  �� szFrom ���� szTo
     * @param :  szSour Դ��
     * @param : szFrom ���滻���Ӵ�
     * @param : szTo �滻����Ӵ�
     * @return : String �´�
     * @exception:
     * Memo:
     */
    public static String replace(String szSource, String szFrom, String szTo)
    {
        if(szSource == null ) return null;
        if(szFrom == null || szFrom.equals("")) return szSource;
        if(szTo == null)  szTo = "";

        String szDest = "";
        int intFromLen = szFrom.length();
        int intPos;
        while((intPos=szSource.indexOf(szFrom))!=-1)
        {
            szDest = szDest + szSource.substring(0,intPos);
            szDest = szDest + szTo;
            szSource = szSource.substring(intPos+intFromLen);
        }
        szDest = szDest + szSource;
        return szDest;
    }

    /**
     *  Method trim
     *  Function: ɾ���ո�
     *  @Param: Object o
     *  @Return: String
     */
    public static String trim(Object o)
    {
        if(o==null)
            return "";
        else
            return String.valueOf(o).trim() ;
    }

    /**
     * ���ã�  ����ת��
     * Parameter ;
     * String aUnicode
     * @Return: String
     * Update:
     */
    public static String encode(String aUnicode){
        String gbCode="";
        if (aUnicode==null) return "";

        aUnicode=aUnicode+"@";
        if(aUnicode.length()==1)
           return "";
        else
           aUnicode=aUnicode.substring(0,aUnicode.length()-1);
        try
        {
           gbCode = new String( aUnicode.getBytes( "ISO8859_1" ), "utf-8" );
        // 	gbCode=aUnicode;
        }
       // catch (UnsupportedEncodingException e )
        catch (Exception e )
        {
            //throw new C20_Exception("ת��ʧ��!",e);
        }
        
        return gbCode;
    }

    
    /**
     * ���ã�  ����ת��
     * Parameter ;
     * String aUnicode
     * @Return: String
     * Update:
     */
    public static String code(String aUnicode){
        String gbCode="";
        if (aUnicode==null) return "";

        aUnicode=aUnicode+"@";
        if(aUnicode.length()==1)
           return "";
        else
           aUnicode=aUnicode.substring(0,aUnicode.length()-1);
        try
        {
           gbCode = new String( aUnicode.getBytes( "ISO8859_1" ), "GB2312" );
//         gbCode=aUnicode;
        }
       // catch (UnsupportedEncodingException e )
        catch (Exception e )
        {
            //throw new C20_Exception("ת��ʧ��!",e);
        }
        return gbCode;
    }

    /**
     * ���ã�  ��ʾ���������� 20031111
     * Parameter ;
     * String
     * @Return: String
     * Update:
     */
    public static String getNoFormatDate(){
        Calendar  d1 = Calendar.getInstance();
        String sYear=String.valueOf(d1.get(Calendar.YEAR));
        String sMonth = String.valueOf(d1.get(Calendar.MONTH)+1);
        String sDay = String.valueOf(d1.get(Calendar.DAY_OF_MONTH));

        if(sMonth.length()==1){
            sMonth = "0" + sMonth;
        }

        if(sDay.length()==1){
            sDay = "0" + sDay;
        }

        String s = sYear + sMonth + sDay ;
        return s;
    }

    /***************************************************************************
        * @Name:  getSqlEscapeQuery
        * @funcation:  ȡ����SQLʹ��ת���ַ�����䡣�磺 ESCAPE '/'
        * @return : String
        * @exception:
        * Memo:
        */
    public static String getSqlEscapeQuery()
    {
        return " ESCAPE  '" + SQL_ESCAPE +"'";
    }

    /**************************************************************************************
    * @Name:  filterToSSqlForUpd
    * @param :  szSour Դ��
    * @return : String �´�
    * @exception:
    * ����˵�������û�¼�벿�ֵ��������漰�ĵ����š�б���ַ�����ת��( '\'  => '\\'; "'"=>"''")��ʹ�� insert , update �����Ч��
    * ʹ��˵����
    *     ʹ�÷�Χ��ֻ��SQL������ insert values���֣�update set xxx = ���ֵġ���������Ҫ���ø÷����� where �Ӿ��У����convertSelectSql()������
    * ʹ��������
    *     //�����û��ֹ�������ַ������ݵĶ�����Ϊ szModule_name
    *     szSql = " insert into table_test ( module_name ) values ('"+ Tools.convertUpdSql(szModule_name) + "')" ;
    *  ע�⣺��ORACLE�У�\����Ҫת�������������ԣ��������а����У�Pgsql
    *      ��Ժ�̨SQL��ƴд���ܽ����£�
    *��1�� ���� insert into xxx values ()  �е� values ���֣�һ��Ҫ���ã�convertUpdSql()����ת����
    *��2�� ���� update xxx set xxx = value  �е� value ���֣�һ��Ҫ���ã�convertUpdSql()����ת����
    *��3�� ���� where  xxx = value  �е� value ���֣�Ҳһ��Ҫ���ã�convertUpdSql()����ת����
    *��4�� ���� where  xxx like value  �е� value ���֣�Ҳһ��Ҫ���ã�convertSelectSql()������getSqlEscapeQuery����ת����
    * �磺 szSql = " select * from table_test where col_name like '"+ Tools.convertUpdSql(szModule_name) + "'  " + Tools.getSqlEscapeQuery() ;
    *��5�����ڣ�oracle ��Sqlserver ���Ʋ�һ����������ת��������� DAO��ʵ�֡�
    */
   public static String convertUpdSql( String szSour){
       if( (szSour==null)||(szSour.length() ==0) ){
           return szSour ;
       }
       String szNew = szSour;
       szNew = Tools.replace(szNew,"\\","\\\\");
       szNew = Tools.replace(szNew,"'","''");
       return szNew;
    }

    /**************************************************************************************
     * @Name:  filterToSqlStr
     * @funcation:  �� SQL����Ҫ ��ѯ�� '%'  => '/%'; ,'_' => '/_' ,"'"=>"''"�������ת���ַ�Ӧ�ü� SQL_ESCAPE���塣
     * @param :  szSour Դ��
     * @return : String �´�
     * @exception:
     * ����˵�������û���ѯ���ֵ��������漰�ٷֺ�% ,�»��� _ ���ַ�����ת����ʹ�ò�ѯ������԰��� % �� _ ��
     * ʹ��˵����
     *     ��1��ʹ�÷�Χ��ֻ��SQL��ʹ����where ... like  ... ʱ������Ҫ���ø÷�����
     *     ��2����ƴдSQL��ʱ��Ҫ���û��ֹ�����Ĳ��֣�������Ϊ like �Ĳ�ѯ���ʱ����ת����
     *     ��3����like�����󣬻�Ҫ���� ESCAPE '/'��ͨ�� Tools.getSqlEscapeQuery()����ȡ�á���Ҳ�Ҫд����
     *     ��4������ж��like ������Ҫ�ظ�
     * ʹ��������
     *     //�����û��ֹ�������ַ������ݵĶ�����Ϊ szModule_name
     *     szSql = " select * from table_test where col_name like '"+ Tools.convertSelectSql(szModule_name) + "'  " + Tools.getSqlEscapeQuery() ;
     * ���ؽ���� select * from table_test where col_name like 'abc/%efg'  ESCAPE '/'
     */
    public static String convertSelectSql( String szSour){
        if( (szSour==null)||(szSour.length() ==0) ){
            return szSour ;
        }
        String szNew = szSour;
        //�������滻ת���ַ������� oracle�У�ת���ַ��������Ҫת��Ϊ�������� postgre sql �У�ת����񶼿��ԡ�
        szNew = Tools.replace(szNew,SQL_ESCAPE,SQL_ESCAPE+SQL_ESCAPE);
        szNew = Tools.replace(szNew,"%",SQL_ESCAPE+"%");
        szNew = Tools.replace(szNew,"_",SQL_ESCAPE+"_");
        szNew = Tools.replace(szNew,"'","''");
        return szNew;
    }

    /**
     * @Name: convertHtml
     * @funcation:  �� HTML��Ҫ��ʾ�� '<'  => &lt; ,'>' => &gt; ˫���Ż��� &quot;   \r\n => '<br>'  �ո� => &nbsp;
     *              2002-11-11 13:00 javaScript�����У����������ŵĴ�������Ϊ�����������Ż��� \u0027
     *              ������ html ��ʾ�������У����ܻ��� \u0027 ����ȡ����
     *              2002-12-07 �ո���ת������Ϊ���ύ��ȡ��Ϊ���롣
     *              2003-01-14 �ո�Ҫת������Ϊ�����ﲻ���ύ�����Ҫ�ύ�ģ�ʹ��filterToHtmlValue()
     *              2002-12-18 ���� TextArea �ı������ʾ������ת���س���(��filterToHtmlValue)������HTML��ʾ������Ҫת���س�����
     * @param :  szSour Դ��
     * @return : String �´�
     * @exception:
     * Memo:
     */
    public static String convertHtml( String szSour){
        String szNew = szSour;
        szNew = Tools.replace(szNew,"&","&amp;");
        //szNew = Tools.replace(szNew,"<","&lt;");
        //szNew = Tools.replace(szNew,">","&gt;");
        szNew = Tools.replace(szNew,"\"","&quot;");
        //szNew = Tools.replace(szNew," ","&nbsp;");
        szNew = Tools.replace(szNew,"'","&apos;");
        //szNew = Tools.replace(szNew,"'","\u0027"); see  replace()
        //szNew = Tools.replace(szNew,"\r\n","<br>");
        //szNew = Tools.convertWrap(szNew);
        if(trim(szNew).equals("") )
            szNew="&nbsp;";
        return szNew;
    }

    /***************************************************************************
     * ר���滻�ַ����еĻ᳷���з���
     * ע����1��������� TextArea����ʾ�����ܵ��øú���
     *     ��2����������˸÷����������ٵ��ã�filterToHtmlStr()
     */
    public static String replaceWrap( String szSour,String toStr){
        String szNew = szSour;
        szNew = Tools.replace(szNew,"\r\n",toStr);
        szNew = Tools.replace(szNew,"\n",toStr);
        szNew = Tools.replace(szNew,"\r",toStr);
        return szNew;
    }

    /***************************************************************************
     * ר��ת���س������� HTML ��ʾ�С�
     * ע����1��������� TextArea����ʾ�����ܵ��øú���
     *     ��2����������˸÷����������ٵ��ã�filterToHtmlStr()
     */
    public static String convertWrap( String szSour){
        String szNew = szSour;
        szNew = Tools.replace(szNew,"\r\n","<br>");
        szNew = Tools.replace(szNew,"\n","<br>");
        szNew = Tools.replace(szNew,"\r","<br>");
        return szNew;
    }

    public static String getSpace(int times){
        String s="";
        if(times==0){
            return "";
        }
        else{
            for(int i=0;i<times;i++){
                s = s + "&nbsp;&nbsp;";
            }
            return s;
        }
    }

    public static String[] split(String sourceStr, String splitter){
        Vector v=new Vector();
        String[] ss= new String[0];
        if(sourceStr == null || sourceStr.equals("")) return ss;
        if(splitter == null || splitter.equals("")) return ss;

        String szDest = "";
        int intLen = splitter.length();
        int intPos;
        while((intPos=sourceStr.indexOf(splitter))!=-1)
        {
            v.add(sourceStr.substring(0,intPos));
            //szDest = szDest + sourceStr.substring(0,intPos);
            sourceStr = sourceStr.substring(intPos+intLen);
        }
        if(v!=null){
            v.add(sourceStr);
        }

        int len = v.size();
        String[] s = new String[len];
        for(int i=0;i<len;i++){
            s[i] = (String)v.get(i);
        }

        return s;
    }

    public static String trim(String src){
        if(src==null){
            return "";
        }
        else{
            return src.trim();
        }
    }

    public static String killNull(Object src){
        if(src==null){
            return "";
        }
        else{
            return src.toString();
        }
    }

    public static String killLongString(String src,int len){
        if(src==null){
            return "";
        }
        else{
            if(src.length()<=len)
                return src;
            else
                return src.substring(0,len)+"��";
        }
    }

    public static String gb2iso(String str)
    {
        try
        {
            return new String(str.getBytes("gb2312"), "ISO-8859-1");
        }
        catch(Exception e)
        {
            return str;
        }
	}

    public static String encodeURL(String str){
        String[] s=Tools.split(str,"/");
        String rtnStr="";
        System.out.println(s.length);
        for(int i=0;i<3;i++){
            if(s[i]==null){
                s[i]="";
            }
            rtnStr=rtnStr+s[i]+"/";
        }
        System.out.println(rtnStr);
        for(int i=3;i<s.length-1;i++){
            if(rtnStr.equals("")){
                rtnStr=URLEncoder.encode(s[i])+"/";
            }
            else{
                rtnStr=rtnStr+URLEncoder.encode(s[i])+"/";
            }
        }
        if(s[s.length-1]==null){
            s[s.length-1]="";
        }
        System.out.println(rtnStr);
        rtnStr=rtnStr+URLEncoder.encode(s[s.length-1]);
        return rtnStr;
    }
    /**  
     * ɾ���ļ��������ǵ����ļ����ļ���  
     * @param   fileName    ��ɾ�����ļ���  
     * @return �ļ�ɾ���ɹ�����true,���򷵻�false  
     */  
    public static boolean delete(String fileName){   
        File file = new File(fileName);   
        if(!file.exists()){   
            System.out.println("ɾ���ļ�ʧ�ܣ�"+fileName+"�ļ�������");   
            return false;   
        }else{   
            if(file.isFile()){   
                   
                return deleteFile(fileName);   
            }else{   
                return deleteDirectory(fileName);   
            }   
        }   
    }   
       
    /**  
     * ɾ�������ļ�  
     * @param   fileName    ��ɾ���ļ����ļ���  
     * @return �����ļ�ɾ���ɹ�����true,���򷵻�false  
     */  
    public static boolean deleteFile(String fileName){   
        File file = new File(fileName);   
        if(file.isFile() && file.exists()){   
            file.delete();   
            System.out.println("ɾ�������ļ�"+fileName+"�ɹ���");   
            return true;   
        }else{   
            System.out.println("ɾ�������ļ�"+fileName+"ʧ�ܣ�");   
            return false;   
        }   
    }   
       
    /**  
     * ɾ��Ŀ¼���ļ��У��Լ�Ŀ¼�µ��ļ�  
     * @param   dir ��ɾ��Ŀ¼���ļ�·��  
     * @return  Ŀ¼ɾ���ɹ�����true,���򷵻�false  
     */  
    public static boolean deleteDirectory(String dir){   
        //���dir�����ļ��ָ�����β���Զ�����ļ��ָ���   
        if(!dir.endsWith(File.separator)){   
            dir = dir+File.separator;   
        }   
        File dirFile = new File(dir);   
        //���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�   
        if(!dirFile.exists() || !dirFile.isDirectory()){   
            System.out.println("ɾ��Ŀ¼ʧ��"+dir+"Ŀ¼�����ڣ�");   
            return false;   
        }   
        boolean flag = true;   
        //ɾ���ļ����µ������ļ�(������Ŀ¼)   
        File[] files = dirFile.listFiles();   
        for(int i=0;i<files.length;i++){   
            //ɾ�����ļ�   
            if(files[i].isFile()){   
                flag = deleteFile(files[i].getAbsolutePath());   
                if(!flag){   
                    break;   
                }   
            }   
            //ɾ����Ŀ¼   
            else{   
                flag = deleteDirectory(files[i].getAbsolutePath());   
                if(!flag){   
                    break;   
                }   
            }   
        }   
           
        if(!flag){   
            System.out.println("ɾ��Ŀ¼ʧ��");   
            return false;   
        }   
           
        //ɾ����ǰĿ¼   
        if(dirFile.delete()){   
            System.out.println("ɾ��Ŀ¼"+dir+"�ɹ���");   
            return true;   
        }else{   
            System.out.println("ɾ��Ŀ¼"+dir+"ʧ�ܣ�");   
            return false;   
        }   
    } 
    
    public static Date StrToDate(String str, String formatString) {
		formatString = (formatString == null || formatString.equals("")) ? "yyyy-MM-dd HH:mm:ss"
				: formatString;
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//��ȡ�й���ʱ��
		format.setTimeZone(timeZoneChina);//����ϵͳʱ��
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
		}
		return date;
	}
    public static String DatetoStr(Date date, String formatString) {
		formatString = (formatString == null || formatString.equals("")) ? "yyyy-MM-dd HH:mm:ss"
				: formatString;

		SimpleDateFormat bartDateFormat = new SimpleDateFormat(formatString);
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//��ȡ�й���ʱ��
		bartDateFormat.setTimeZone(timeZoneChina);//����ϵͳʱ��
		String dateString = bartDateFormat.format(date);
		return dateString;
	}

    public static String FormatDate(String str, String formatString) throws ParseException{
		formatString = (formatString == null || formatString.equals("")) ? "yyyy-MM-dd HH:mm:ss"
				: formatString;

		SimpleDateFormat bartDateFormat = new SimpleDateFormat(formatString);
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//��ȡ�й���ʱ��
		bartDateFormat.setTimeZone(timeZoneChina);//����ϵͳʱ��
		Date date = null;
		date = bartDateFormat.parse(str);
		String dateString = bartDateFormat.format(date);
		return dateString;
	}
    
    public static String FormatDate(String formatString) {
		formatString = (formatString == null || formatString.equals("")) ? "yyyy-MM-dd HH:mm:ss"
				: formatString;
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(formatString);
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//��ȡ�й���ʱ��
		bartDateFormat.setTimeZone(timeZoneChina);//����ϵͳʱ��
		Date date = new Date();
		String dateString = bartDateFormat.format(date);
		return dateString;
	}

}