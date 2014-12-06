package com.crawler;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 

import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mortbay.util.ajax.JSON;


/**
 * Servlet implementation class Query
 */
public class ReadPrintJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static List<String> revList = new ArrayList<String>();
	static List<String> dateList = new ArrayList<String>();
	static List<String> titleList = new ArrayList<String>();
	static List<String> starList = new ArrayList<String>();   
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadPrintJSON() {
        super();
        
        // TODO Auto-generated constructor stub
    }

    	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		JSONParser parser = new JSONParser();
		URL url = ReadPrintJSON.class.getResource("samplejson.txt");
        String filepath = url.getPath();
        System.out.println(filepath);
        out.println("FILE PATh ="+filepath);
		Object obj;
		try {
			obj = parser.parse(new FileReader(filepath));
			
			JSONObject jsonObject = (JSONObject) obj;
			
		
			
			JSONArray dateArray= (JSONArray) jsonObject.get("Review-Date");
			JSONArray titleArray= (JSONArray) jsonObject.get("Review-Title");
			JSONArray ratingArray= (JSONArray) jsonObject.get("RatingStars-List");
			JSONArray reviewsArray= (JSONArray) jsonObject.get("Reviews");
			out.print("<table border='2'>");
			for(int h=0;h<dateArray.size();h++)
			{
			out.println("<tr>");
			out.println("<td>"+dateArray.get(h)+"</td><td>"+ratingArray.get(h)+"</td><td>"+titleArray.get(h)+"</td><td>"+reviewsArray.get(h)+"</td>");
			out.println("</tr>");
			}
			out.print("</table>");
			out.println("-----------------------------------------------------------");
			out.println("JSON is\n"+jsonObject);
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 


	}
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		
			}

	public static String DataFunnel(String link) {
	    org.jsoup.nodes.Document doc;
	    Elements reviews = null;
	    //String urlNextPage = new String();
	    try {
//	    	PrintWriter writer = new PrintWriter(new FileWriter("note2.txt", true));
	        
	    	// need http protocol
	    	System.out.println("Amazon Reviews");
	    	
	        doc = Jsoup.connect(link).timeout(25000).get();
	        reviews = doc.select("div[class=reviewText]");
	        Elements date = doc.select("nobr[class=date line fk-font-small]");
	        Elements reviewHeading = doc.select("div[class=line fk-font-normal bmargin5 dark-gray]");
	        String title = doc.title();
	        Elements s = doc.select("span[class=swSprite s_star_5_0 ]");
	        Element tempelement;
	        for(int i = 0;i<s.size();i++)
	        {
	            tempelement = s.get(i);
	            starList.add(tempelement.attr("title").replaceAll(" stars", ""));
	            dateList.add(((Element) date.toArray()[i]).text());
	            revList.add(((Element) reviews.toArray()[i]).text());
	            titleList.add(((Element) reviewHeading.toArray()[i]).text());
	        }
	        
	        
//	        System.out.println("-----------------------Star Lists --------" + starList);
//	        
//	        Elements datess = doc.select("div[class=date line fk-font-small]");
//	        dateList.add(((Element) datess.toArray()[2]).text());
//	        System.out.println(((Element) datess.toArray()[2]).text());
	        
	        //System.out.println("-----------------------Reviews --------" + reviews);
	        //System.out.println("-----------------------Date --------" + date);
	        //System.out.println("-----------------------ReviewHeading --------" + reviewHeading);
	        
//	        writer.println("Page Title : " + title);
//	        writer.println("PresentPageURL = "+ link);
//	        writer.println("-----------------------Reviews --------" + reviews);
//	        writer.println("-----------------------Date --------" + date);
//	        writer.println("-----------------------ReviewHeading --------" + reviewHeading);
	        
	        
	        
	        //urlNextPage = doc.select("a.nav_bar_next_prev").first().attr("abs:href");
	        //This is creating some issues, the loop ends coz of this
	        
	        
	        //System.out.println("NextPageURL = "+ urlNextPage);
	        
	        
	        
	        
	        //writer.println("NextPageURL = "+ urlNextPage);
	        
	        //Elements productList = doc.select("div[class=date line fk-font-small]");
	        //System.out.println("-----------------------Date --------" + productList);
	        // get page title
	        //System.out.println("title : " + title);

//	        writer.close();
	        
	        // get all links
//	        Elements links = doc.select("a[href]");
//	        for (Element link : links) {
//	            // get the value from href attribute
//	            System.out.println("\nlink : " + link.attr("href"));
//	            System.out.println("text : " + link.text());
//	        }

	    } 
//	    catch(NullPointerException n)
//        {
//        	System.out.println("No More pages to crawl");
//        	System.out.println("Last Page Crawled = "+link);
//        	System.exit(0);
//        }
	    
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return (reviews.toString());
        
	}

	
}

