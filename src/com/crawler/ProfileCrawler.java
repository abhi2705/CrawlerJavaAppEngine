package com.crawler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Servlet implementation class ProfileCrawler
 */
public class ProfileCrawler extends HttpServlet {
	public static String rdf = "<?xml version='1.0'?>\n" +
			"<rdf:RDF\n" +
			"xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#'\n" + 
			"xmlns:person='http://www.personal_data.fake/person#'>\n";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfileCrawler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();
		String[] a1 = {"Tushar","Abhinav","Rahul","Kartik","Mohit","Anuj","Akshatha","Varghese","Vijay","Vinay","Nikhil"};
		String[] a2 = {"Makkar","Pathak","Das","Sreenivasan","Tahiliani","Bansal","Shetty","George","Desai","Raveendran","Thakkar"};
		String[] a3 = {"nitk","nitk","nitk","nitk","nitk","nitk","nitk","nitk","nitk","nitk","nitk"};

		int i;
		for (i=0;i<7;i++)
		{

			String fname = a1[i];
			String lname = a2[i];
			String key = a3[i];

			System.out.println(fname+"+"+lname+"+"+key);
			String link = "https://www.google.co.in/search?q="+fname+"+"+lname+"+"+key+"+"+"linkedin"+"&newwindow=1&biw=1366&bih=667&ei=EhlhVNjUEpKvyATYk4GwCQ&start=0&sa=N";
			DataFunnel(link,out,fname,lname,key);		
		}
		//LOOP ENDS HERE!!!
		rdf += "</rdf:RDF>"; //end of the rdf
		out.print(rdf);
	}


	public static void DataFunnel(String link,PrintWriter out, String fname, String lname, String key) {
		org.jsoup.nodes.Document doc,doc2;
		try {        
			// need http protocol
			doc = Jsoup.connect(link).timeout(25000).get();
			Elements l= doc.select("h3[class=r]");
			String finalLink = l.get(0).select("a").attr("abs:href");
			finalLink = finalLink.replaceAll("https://www.google.co.in/url", "").replaceAll("\\?q=", "");
			finalLink = finalLink.substring(0, finalLink.indexOf("&"));
			doc2 = Jsoup.connect(finalLink).timeout(25000).get();
			Elements name = doc2.select("span[class=full-name]");
			Elements location = doc2.select("span[class=locality]");
			Elements edu = doc2.select("div[class=content]");
			Elements workex = doc2.select("div[id=background-experience-container]");
			Elements skills = doc2.select("span[class=skill-pill]");
			try
			{
				//single entity in the ontology is being printed here
				rdf += "<rdf:Description\n" + 
						"rdf:about='http://www.personal_data.fake/person/"+name.first().text()+"'>\n";
				rdf += "<person:first_name>" + fname+ "</person:first_name>\n";
				rdf += "<person:last_name>" + lname + "</person:last_name>\n";
				rdf += "<person:address>" + location.first().text() + "</person:address>\n";
				rdf += "<person:education>" + edu.first().text() + "</person:education>\n";
				rdf += "<person:work_experience>" + workex.first().text() + "</person:work_experience>\n";
				rdf += "<person:technical_skills>" + skills.first().text() + "</person:technical_skills>\n";

				rdf += "</rdf:Description>\n"; //end of one entity
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}


	}
}
