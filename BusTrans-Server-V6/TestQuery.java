package webmarket;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

public class TestQuery extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
	
	String driver = "com.mysql.jdbc.Driver";
    String dbName = "sbs";
    String dbPwd = "2CBEpBKZhw9q4sfV";
    String dbUserName = "sbs_user";
    String url = "jdbc:mysql://128.235.90.197:3306/" + dbName;
    String sqls = "select ptoBusArrT,podBusArrT from sbs_simpax_od where fid='%s' ";
    public static String fid = null;
	/**
	 * Constructor of the object.
	 */
	public TestQuery() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("in doGet");
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		System.out.println("in doPost");
		PrintWriter out = response.getWriter();
		fid = request.getParameter("fid");
		out.println(doQuery());
		out.flush();
		out.close();
	}
	
	
	private String doQuery(){
		String result = "0";
		JSONArray ja = new JSONArray();
		try {
			//
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        //
	        PreparedStatement ps = conn.prepareStatement(String.format(sqls, fid));
	        System.out.println(String.format(sqls, fid));
	        ResultSet rs = ps.executeQuery();
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        while(rs.next()){
	        	Date d = dateFormat.parse(rs.getString("podBusArrT"));
	        	
				//int hours = d.getHours();
	        	//int minute = d.getMinutes();
	        	//int seconds = d.getSeconds();
	        	
	        	Calendar cal = Calendar.getInstance();
	        	cal.setTime(d);  
	        	int hours = cal.get(Calendar.HOUR_OF_DAY);
	        	int minute = cal.get(Calendar.MINUTE);
	        	int seconds = cal.get(Calendar.SECOND);
	        	
	        	Map<String,String> ptimes = new HashMap<String, String>();
	        	//ptimes.put("warn", null);
	        	
	        	if(hours == 0 && minute == 0 && seconds == 0){
	        		//ja = null;
	        		//Map<String,String> ptimes = new HashMap<String, String>();
		        	//ptimes.put("podBusArrT", rs.getString("podBusArrT"));
		        	ptimes.put("warn", "No such bus in system.");

	        	}else{
	        		//Map<String,String> ptimes = new HashMap<String, String>();
	        		
	        		ptimes.put("ptoBusArrT", rs.getString("ptoBusArrT"));
		        	ptimes.put("podBusArrT", rs.getString("podBusArrT"));
		        	ptimes.put("warn", null);
		        	//ptimes.put("podBusArrT", rs.getString("podBusArrT"));

		        	//ptimes.put("ptoBusArrT", rs.getString("ptoBusArrT"));
		        	//ptimes.put("p_next_delay", rs.getString("p_next_delay"));      			        	

	        	}   
	        	ja.put(ptimes);
//	        	
	        }
	        
	        
	        // 关闭记录集
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        // 关闭声明
	        if (ps != null) {
	            try {
	                ps.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        // 关闭链接对象
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		if(ja.length()!=0){
			return ja.toString();
		}else{
			return "0";
		}
	}
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
