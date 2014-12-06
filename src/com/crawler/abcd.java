package com.crawler;

public class abcd {
	
	public static void main(String args[])
	{
		int c=0,j,i,lm,ln,m= 15, n=20;
		//System.out.println(m.);
		String mm = Integer.toBinaryString(m);
		String nn = Integer.toBinaryString(n);
		lm=mm.length();
		ln = nn.length();
		if(lm<ln)
			i = lm;
		else
			i = ln;
		
		
		String mmr = new StringBuffer(mm).reverse().toString();
		String nnr = new StringBuffer(nn).reverse().toString();
		
		for(j=0;j<i;j++)
		{
			
			if((mmr.charAt(j) != (nnr.charAt(j))))
			{
				c++;
				System.out.println(mm.charAt(j)+"  "+(nn.charAt(j))+"  "+c);
			}
				
				
		}
			
		System.out.println("Answer="+(c+Math.abs(lm-ln)));
	}
	
}