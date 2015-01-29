package webContent;
//			else urlName = "http://baidu.com";
//			else urlName = "http://www.tuicool.com/assets/application-e24a1938a0be681881c7f219301f0961.css";
//			else urlName = "http://www.tuicool.com/assets/application-750e7e45d44953caf9148dbf5ba0b752.js";
//else urlName = "http://www.jb51.net/article/57203.htm";
//else urlName = "http://tomfish88.iteye.com/";
//else urlName = "http://www.baidu.com/s?wd=java爬虫保存html&pn=0&oq=java爬虫保存html&ie=utf-8&rsv_pq=dc9bccde00000aef&rsv_t=9ede1Xt3efpyk8XPporisaNFhr6f4M8JJRZPVUCRLen1vmvUFDVhdyBBKGk&f=8&rsv_bp=1&tn=baidu";
//else urlName = "http://outofmemory.cn/code-snippet/2332/java-usage-BufferedInputStream-duqu-wenbenwenjian";
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebContent {
	static int intWebIndex = 0;
	public static void main(String[] args)
	{
//		String urlName;
//		if(args.length>0) urlName = args[0];
//		else urlName = "http://www.jb51.net/article/57203.htm";
		String urlName1 = "http://developer.51cto.com/art/201103/248141.htm";
		String urlName2 = "http://www.jb51.net/article/57203.htm";
		String urlName3 = "http://www.tuicool.com/assets/application-e24a1938a0be681881c7f219301f0961.css";
		crawl(urlName1);	
		crawl(urlName2);
		crawl(urlName3);
	}	
	
	public static void crawl(String urlName)
	{
		try
		{
			URL url = new URL(urlName);				
			HttpURLConnection con = (HttpURLConnection) url.openConnection(); 				
			con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			System.getProperties().setProperty("proxySet", "true");
		      // //如果不设置，只要代理IP和代理端口正确,此项不设置也可以

		    System.getProperties().setProperty("http.proxyHost", "183.207.228.9");

		    System.getProperties().setProperty("http.proxyPort", "9988");

			JudgePageType(con);

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}

	public static   int getIntWebIndex()
	 {
	  intWebIndex++;
	  return intWebIndex;
	 }
	public static void JudgePageType(HttpURLConnection con)throws IOException{
		String type = con.getContentType().substring(0, 8);
		if (type.equals("text/htm") )
		   {
			savefile(con.getInputStream());
//			System.out.println("getContentType: " + con.getContentType());
			System.out.println("getContentType: " + con.getContentType());
			System.out.println("这是一个规范的Html网页");
			}
		else{
			System.out.println("getContentType: " + con.getContentType());
			JudgePageType(con , 1);
		}
	
	}
	
	
	public static void JudgePageType(HttpURLConnection con , int i) throws IOException{
		String sb = con.getInputStream().toString();
		boolean zztype = parseHtmlLable(sb);
			if(zztype == true){
				savefile(con.getInputStream());
				System.out.println("这是一个不规范的HTML网页");}
			else
				System.out.println("这才不是一个HTML网页");
			 
		}
	
		public static String savefile(InputStream is) throws IOException{
		
				Path currentRelativePath = Paths.get("");
				String currentPath = currentRelativePath.toAbsolutePath().toString();
				int a = getIntWebIndex();
				String filename = "web"+a+".htm";
				BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
				BufferedInputStream bis = new BufferedInputStream(is);
				byte bytes[] = new byte[1024 * 1000];
				int index = 0;
				int count = bis.read(bytes, index, 1024 * 100);
				while (count != -1) {
				index += count;
				count = bis.read(bytes, index, 1);
				}	
				String sb = bytes.toString();
//				String filePath = "D:/web1.htm";	
				String filePath = currentPath+"/web/"+filename;
				PrintWriter pw  =null;				
				FileOutputStream fos = new FileOutputStream(filePath);				
				OutputStreamWriter writer = new OutputStreamWriter(fos);				
				pw = new PrintWriter(writer);
				System.out.println(index);
				fos.write(bytes, 0, index);
				is.close();
				bis.close();
				fos.close();
				System.out.println(sb.toString());
			    return sb.toString();
			
		}
		
		public static boolean parseHtmlLable(String context) {
		    String regex = "<meta>.*?</meta>";
			Pattern pt = Pattern.compile(regex);
			boolean mt = pt.matcher(context).find();
			
			if (mt == true){
				return true;}
			else
				return false;
			
			}
}
