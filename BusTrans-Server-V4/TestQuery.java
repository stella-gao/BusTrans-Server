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
    String dbPwd = "*************";
    String dbUserName = "*******";
    String url = "jdbc:mysql://***.***.***.***:3306/" + dbName;
    String sqls = "select poBusArrT,podBusArrT,p_next_delay from sbs_simpassx where fid='%s' ";
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
	        ResultSet rs = ps.executeQuery();
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        while(rs.next()){
	        	Map<String,String> ptimes = new HashMap<String, String>();
	        	ptimes.put("Bus Schedule Arrival Time at origin Stop", rs.getString("poBusArrT"));
	        	ptimes.put("First Bus Schedule Arrival Time at Transfer Stop", rs.getString("podBusArrT"));
	        	ptimes.put("Estimated Delay to Next Stop", rs.getString("p_next_delay"));
	        	ja.put(ptimes);
	        }
	        
	        
	        // 鍏抽棴璁板綍闆�	        
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        // 鍏抽棴澹版槑
	        if (ps != null) {
	            try {
	                ps.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        // 鍏抽棴閾炬帴瀵硅薄
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
