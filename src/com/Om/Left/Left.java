package com.Om.Left;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.ws.rs.*;
import java.lang.Object;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.*;

@Path("bson/client")
public class Left {
	static Connection conn;
	static Statement stmt;
	
	@GET
	@Path("/read/{varA}/{varB}/{varC}")
	@Produces("application/json")
	public static String read(@PathParam("varA") int a,@PathParam("varB") int b,@PathParam("varC") int c) throws ClassNotFoundException, SQLException
	{
		String g="";
		ResultSet rt;
		String column="";
		Class.forName("com.mysql.jdbc.Driver");
		 conn=DriverManager.getConnection("jdbc:mysql://localhost/DeviceManagement","root","");
		 stmt=conn.createStatement();
	
		if((a!=0)&&(b!=0))
	rt=stmt.executeQuery("select * from AccessControl where ObjectID="+a+" and ObjectInstanceID="+b);
		else
			rt=stmt.executeQuery("select * from AccessControl");
	
	
	while(rt.next())
	{
	if(c==0)	
	g=g+"ObjectID: "+rt.getInt("ObjectID")+"\nObjectInstanceID:"+rt.getInt("ObjectInstanceID")+"\nACL: "+rt.getInt("ACL")+"\nAccessControlOwner:"+rt.getInt("AccessControlOwner")+" ";
	if(c==1)
		g=g+"ACL: "+rt.getInt("ACL");
	if(c==2)
		g=g+"AccessControlOwner: "+rt.getInt("AccessControlOwner");
	}
	return g+"\nSuccess";
	}
	
	@POST
	@Path("/write/{varX}/{varY}/{varZ}/{varN}")
	//@Consumes("application/json")
	public static String write(@PathParam("varX") int x,@PathParam("varY") int y,@PathParam("varZ") int z,@PathParam("varN") int n) throws SQLException, ClassNotFoundException
	{
		
		Class.forName("com.mysql.jdbc.Driver");
		conn=DriverManager.getConnection("jdbc:mysql://localhost/DeviceManagement","root","");
		stmt=conn.createStatement();
		if(z==2)
		stmt.executeUpdate("update AccessControl set ACL="+n+" where ObjectID="+x+" and ObjectInstanceID="+y);
		if(z==3)
			stmt.executeUpdate("update AccessControl set AccessControlOwner="+n+" where ObjectID="+x+" and ObjectInstanceID="+y);
		
	return "Updated Object Instance: "+y+"\nSuccess";
	}
	
	@DELETE
	@Path("/delete/{varX}/{varY}")
	public static String delete(@PathParam("varX") int x,@PathParam("varY") int y) throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
	conn=DriverManager.getConnection("jdbc:mysql://localhost/DeviceManagement","root","");
	stmt=conn.createStatement();
		stmt.executeUpdate("delete from AccessControl where ObjectID="+x+" and ObjectInstanceID="+y);
		return "Deleted\nSuccess";
	}
	
	@POST
	@Path("/create/{varX}/{varY}/{varZ}/{varA}")
	@Consumes("application/json")
	public static String create(@PathParam("varX") int x,@PathParam("varY") int y,@PathParam("varZ") int z,@PathParam("varA") int a) throws SQLException, ClassNotFoundException
			//@PathParam("varX") int x,@PathParam("varY") int y,@PathParam("varZ") int z,@PathParam("varA") int a,
			
	{
		AccessControl d=new AccessControl(x,y,z,a);
//		d.setAccessControlOwner(a);
//		d.setObjectID(x);
//		d.setObjectInstanceID(y);
//		d.setACL(z);
		Class.forName("com.mysql.jdbc.Driver");
		conn=DriverManager.getConnection("jdbc:mysql://localhost/DeviceManagement","root","");
		stmt=conn.createStatement();
			stmt.executeUpdate("insert into AccessControl values("+d.getObjectInstanceID()+","+d.getObjectID()+","+d.getACL()+","+d.getAccessControlOwner()+")");
			return "Created Object Instance\nSuccess ";//+d.ObjectInstanceID+" "+d.getACL();
	}
	
	
	@GET
	@Path("/discover/{varX}/{varY}/{varZ}")
	public static String discover(@PathParam("varX") int x,@PathParam("varY") int y,@PathParam("varZ") int z) throws ClassNotFoundException, SQLException  
	{
		String g="";
		ResultSet rt;
		Class.forName("com.mysql.jdbc.Driver");
		conn=DriverManager.getConnection("jdbc:mysql://localhost/DeviceManagement","root","");
		stmt=conn.createStatement();
		rt = stmt.executeQuery("SELECT column_name FROM information_schema.columns WHERE table_name = 'AccessControl'");
		ArrayList<String> k=new ArrayList<String>();
		
	 while(rt.next())
	{
		g=g+rt.getString(1)+"\n";
	}
	return g;
	}
	
	
	@POST
	@Path("/writeattributes/{varA}/{varB}/{varC}/{varD}/{varE}/{varF}/{varG}/{varH}/{varI}")
	public static String writeAttributes(@PathParam("varA") int a,@PathParam("varB") int b,@PathParam("varC") int c,@PathParam("varD") int d
			,@PathParam("varE") int e,@PathParam("varF") int f,@PathParam("varG") int g,@PathParam("varH") int h,@PathParam("varI") int i) throws ClassNotFoundException, SQLException
	{
		 String gi="";
		 Class.forName("com.mysql.jdbc.Driver");
		 conn=DriverManager.getConnection("jdbc:mysql://localhost/DeviceManagement","root","");
		 stmt=conn.createStatement();
		 stmt.executeUpdate("insert into Attributes values("+a+","+b+","+c+","+d+","+e+","+f+","+g+","+h+","+i+")"); 
		 return "Attributes have been updated";
	}
	
	

	
	
	@GET
	@Path("/observe")
	public static String observe() throws ClassNotFoundException, SQLException
	{
		ResultSet rs;
		String gh="";
		int a,b,c,d,e,f,g,h;
		 Class.forName("com.mysql.jdbc.Driver");
		 
		 conn=DriverManager.getConnection("jdbc:mysql://localhost/DeviceManagement","root","");
		 stmt=conn.createStatement();
		 Statement st=conn.createStatement();
			ResultSet rt=stmt.executeQuery("select * from AccessControl");
			a=rt.getInt(1);
			b=rt.getInt(2);
			c=rt.getInt(3);
			d=rt.getInt(4);
			while(true)
			{
				rs=st.executeQuery("select * from AccessControl");
		
				while(rs.next())
				{
					e=rs.getInt(1);
					f=rs.getInt(2);
					g=rs.getInt(3);
					h=rs.getInt(4);
				if((a!=e)||(b!=f)||(c!=g)||(d!=h))
				{
					gh="Value Change Notification";
				}
				}
				if(gh.equals("Value Change Notification"))
					return gh;
			}
		
		
	}
	
	@POST
	@Path("/execute/{varX}/{varY}/{varZ}")
	public static String execute(@PathParam("varX") int x,@PathParam("varY") int y,@PathParam("varZ") int z) throws ClassNotFoundException, SQLException
	{
		ResultSet resultSet;
		Time time;
		double latitude,longitude,uncertainity,velocity;
	int altitude;
		double c=0.0;
		Class.forName("com.mysql.jdbc.Driver");		 
		conn = DriverManager .getConnection("jdbc:mysql://localhost/DeviceManagement","root","");
		stmt = conn.createStatement();			   
	
		resultSet = stmt.executeQuery("SELECT * from Location WHERE ObjectInstanceID= " + y);	     			      		      			      
		while (resultSet.next()) {					    	
		    	latitude = resultSet.getDouble(3);
		    	longitude = resultSet.getDouble(4);
		    	altitude = resultSet.getInt(5);
		    	uncertainity = resultSet.getDouble(6);
		    	velocity = resultSet.getDouble(5);
		    	 time = resultSet.getTime(8);
		    		switch(z)
		    		{
		    		case 6:
		    		{
		    			c=(altitude);
		    			c=c/1000;
		    		}break;
		    		}
		    		return "Altitude at "+time.toString()+" is "+altitude+"\nConverted value: "+ c;
		}			
		
	return "";
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
