package ir.assignment.three.searching;

import java.util.ArrayList;

public class Result {
private ArrayList<String> url = new ArrayList<String>();
private ArrayList<String> discreption = new ArrayList<String>();
private ArrayList<String> title = new ArrayList<String>();
private int total = 0;
public void add(String title, String url, String discreption)
{
	this.title.add(title);
	this.url.add(url);
	if(discreption.length()<100)
		this.discreption.add(discreption);
	else
		this.discreption.add(discreption.substring(0,99));
	
}
public void add(int nub)
{
	this.total = nub;
}
public void clean()
{
	this.title.clear();
	this.url.clear();
	this.discreption.clear();
}

public String returnresult()
{
	StringBuffer sb = new StringBuffer();
	sb.append(String.valueOf(this.total)+"!");
	for(int i = 0; i < title.size(); ++i)
	{
		sb.append(this.title.get(i)+"|");
		sb.append(this.url.get(i)+"|");
		sb.append(this.discreption.get(i)+"!");
		
	}
	return sb.toString();
}
}
