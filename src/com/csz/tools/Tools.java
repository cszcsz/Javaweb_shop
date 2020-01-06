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
     * @funcation:  把 szFrom 换成 szTo
     * @param :  szSour 源串
     * @param : szFrom 被替换的子串
     * @param : szTo 替换后的子串
     * @return : String 新串
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
     *  Function: 删除空格
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
     * 作用：  内码转换
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
            //throw new C20_Exception("转码失败!",e);
        }
        
        return gbCode;
    }

    
    /**
     * 作用：  内码转换
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
            //throw new C20_Exception("转码失败!",e);
        }
        return gbCode;
    }

    /**
     * 作用：  显示服务器日期 20031111
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
        * @funcation:  取出在SQL使用转义字符的语句。如： ESCAPE '/'
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
    * @param :  szSour 源串
    * @return : String 新串
    * @exception:
    * 功能说明：把用户录入部分的内容中涉及的单引号、斜杠字符进行转换( '\'  => '\\'; "'"=>"''")。使得 insert , update 语句有效。
    * 使用说明：
    *     使用范围：只有SQL中用作 insert values部分，update set xxx = 部分的　，　才需要调用该方法。 where 子句中，请见convertSelectSql()方法。
    * 使用样例：
    *     //假设用户手工输入的字符串内容的对象名为 szModule_name
    *     szSql = " insert into table_test ( module_name ) values ('"+ Tools.convertUpdSql(szModule_name) + "')" ;
    *  注意：在ORACLE中，\不需要转换成两个。所以，方法名中包含有：Pgsql
    *      针对后台SQL的拼写，总结如下：
    *（1） 对于 insert into xxx values ()  中的 values 部分，一定要调用：convertUpdSql()方法转换。
    *（2） 对于 update xxx set xxx = value  中的 value 部分，一定要调用：convertUpdSql()方法转换。
    *（3） 对于 where  xxx = value  中的 value 部分，也一定要调用：convertUpdSql()方法转换。
    *（4） 对于 where  xxx like value  中的 value 部分，也一定要调用：convertSelectSql()方法和getSqlEscapeQuery（）转换。
    * 如： szSql = " select * from table_test where col_name like '"+ Tools.convertUpdSql(szModule_name) + "'  " + Tools.getSqlEscapeQuery() ;
    *（5）由于，oracle ，Sqlserver 机制不一样，这样的转换，请放在 DAO中实现。
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
     * @funcation:  把 SQL串中要 查询的 '%'  => '/%'; ,'_' => '/_' ,"'"=>"''"。具体的转义字符应该见 SQL_ESCAPE定义。
     * @param :  szSour 源串
     * @return : String 新串
     * @exception:
     * 功能说明：把用户查询部分的内容中涉及百分号% ,下划线 _ 的字符进行转换。使得查询结果可以包含 % 或 _ 。
     * 使用说明：
     *     （1）使用范围：只有SQL中使用了where ... like  ... 时，才需要调用该方法。
     *     （2）在拼写SQL串时，要对用户手工输入的部分，并且作为 like 的查询语句时进行转换。
     *     （3）在like语句完后，还要加上 ESCAPE '/'。通过 Tools.getSqlEscapeQuery()方法取得。大家不要写死。
     *     （4）如果有多个like ，都得要重复
     * 使用样例：
     *     //假设用户手工输入的字符串内容的对象名为 szModule_name
     *     szSql = " select * from table_test where col_name like '"+ Tools.convertSelectSql(szModule_name) + "'  " + Tools.getSqlEscapeQuery() ;
     * 返回结果： select * from table_test where col_name like 'abc/%efg'  ESCAPE '/'
     */
    public static String convertSelectSql( String szSour){
        if( (szSour==null)||(szSour.length() ==0) ){
            return szSour ;
        }
        String szNew = szSour;
        //首先先替换转义字符本身。在 oracle中，转义字符本身必须要转换为两个。在 postgre sql 中，转换与否都可以。
        szNew = Tools.replace(szNew,SQL_ESCAPE,SQL_ESCAPE+SQL_ESCAPE);
        szNew = Tools.replace(szNew,"%",SQL_ESCAPE+"%");
        szNew = Tools.replace(szNew,"_",SQL_ESCAPE+"_");
        szNew = Tools.replace(szNew,"'","''");
        return szNew;
    }

    /**
     * @Name: convertHtml
     * @funcation:  把 HTML中要显示的 '<'  => &lt; ,'>' => &gt; 双引号换成 &quot;   \r\n => '<br>'  空格 => &nbsp;
     *              2002-11-11 13:00 javaScript方法中，包含单引号的串不能作为参数。单引号换成 \u0027
     *              但是在 html 显示的内容中，不能换成 \u0027 ，故取消。
     *              2002-12-07 空格不用转换，因为：提交后取得为乱码。
     *              2003-01-14 空格要转换，因为：这里不用提交。如果要提交的，使用filterToHtmlValue()
     *              2002-12-18 对于 TextArea 文本框的显示，不用转换回车符(见filterToHtmlValue)。对于HTML显示，则需要转换回车符。
     * @param :  szSour 源串
     * @return : String 新串
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
     * 专门替换字符串中的会撤换行符。
     * 注：（1）如果是在 TextArea中显示，则不能调用该函数
     *     （2）如果调用了该方法，则不能再调用：filterToHtmlStr()
     */
    public static String replaceWrap( String szSour,String toStr){
        String szNew = szSour;
        szNew = Tools.replace(szNew,"\r\n",toStr);
        szNew = Tools.replace(szNew,"\n",toStr);
        szNew = Tools.replace(szNew,"\r",toStr);
        return szNew;
    }

    /***************************************************************************
     * 专门转换回车符，在 HTML 显示中。
     * 注：（1）如果是在 TextArea中显示，则不能调用该函数
     *     （2）如果调用了该方法，则不能再调用：filterToHtmlStr()
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
                return src.substring(0,len)+"…";
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
     * 删除文件，可以是单个文件或文件夹  
     * @param   fileName    待删除的文件名  
     * @return 文件删除成功返回true,否则返回false  
     */  
    public static boolean delete(String fileName){   
        File file = new File(fileName);   
        if(!file.exists()){   
            System.out.println("删除文件失败："+fileName+"文件不存在");   
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
     * 删除单个文件  
     * @param   fileName    被删除文件的文件名  
     * @return 单个文件删除成功返回true,否则返回false  
     */  
    public static boolean deleteFile(String fileName){   
        File file = new File(fileName);   
        if(file.isFile() && file.exists()){   
            file.delete();   
            System.out.println("删除单个文件"+fileName+"成功！");   
            return true;   
        }else{   
            System.out.println("删除单个文件"+fileName+"失败！");   
            return false;   
        }   
    }   
       
    /**  
     * 删除目录（文件夹）以及目录下的文件  
     * @param   dir 被删除目录的文件路径  
     * @return  目录删除成功返回true,否则返回false  
     */  
    public static boolean deleteDirectory(String dir){   
        //如果dir不以文件分隔符结尾，自动添加文件分隔符   
        if(!dir.endsWith(File.separator)){   
            dir = dir+File.separator;   
        }   
        File dirFile = new File(dir);   
        //如果dir对应的文件不存在，或者不是一个目录，则退出   
        if(!dirFile.exists() || !dirFile.isDirectory()){   
            System.out.println("删除目录失败"+dir+"目录不存在！");   
            return false;   
        }   
        boolean flag = true;   
        //删除文件夹下的所有文件(包括子目录)   
        File[] files = dirFile.listFiles();   
        for(int i=0;i<files.length;i++){   
            //删除子文件   
            if(files[i].isFile()){   
                flag = deleteFile(files[i].getAbsolutePath());   
                if(!flag){   
                    break;   
                }   
            }   
            //删除子目录   
            else{   
                flag = deleteDirectory(files[i].getAbsolutePath());   
                if(!flag){   
                    break;   
                }   
            }   
        }   
           
        if(!flag){   
            System.out.println("删除目录失败");   
            return false;   
        }   
           
        //删除当前目录   
        if(dirFile.delete()){   
            System.out.println("删除目录"+dir+"成功！");   
            return true;   
        }else{   
            System.out.println("删除目录"+dir+"失败！");   
            return false;   
        }   
    } 
    
    public static Date StrToDate(String str, String formatString) {
		formatString = (formatString == null || formatString.equals("")) ? "yyyy-MM-dd HH:mm:ss"
				: formatString;
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//获取中国的时区
		format.setTimeZone(timeZoneChina);//设置系统时区
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
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//获取中国的时区
		bartDateFormat.setTimeZone(timeZoneChina);//设置系统时区
		String dateString = bartDateFormat.format(date);
		return dateString;
	}

    public static String FormatDate(String str, String formatString) throws ParseException{
		formatString = (formatString == null || formatString.equals("")) ? "yyyy-MM-dd HH:mm:ss"
				: formatString;

		SimpleDateFormat bartDateFormat = new SimpleDateFormat(formatString);
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//获取中国的时区
		bartDateFormat.setTimeZone(timeZoneChina);//设置系统时区
		Date date = null;
		date = bartDateFormat.parse(str);
		String dateString = bartDateFormat.format(date);
		return dateString;
	}
    
    public static String FormatDate(String formatString) {
		formatString = (formatString == null || formatString.equals("")) ? "yyyy-MM-dd HH:mm:ss"
				: formatString;
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(formatString);
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//获取中国的时区
		bartDateFormat.setTimeZone(timeZoneChina);//设置系统时区
		Date date = new Date();
		String dateString = bartDateFormat.format(date);
		return dateString;
	}

}