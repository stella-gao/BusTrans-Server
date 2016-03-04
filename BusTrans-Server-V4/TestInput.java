package webmarket;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestInput extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	
	String driver = "com.mysql.jdbc.Driver";
    String dbName = "sbs";
    String dbPwd = "2CBEpBKZhw9q4sfV";
    String dbUserName = "sbs_user";
    String url = "jdbc:mysql://128.235.90.197:3306/" + dbName;

    String sqli = "insert into sbs_simpassx (fid, arrTime,p_ort_no,p_osp_id,p_drt_no,p_dsp_id) values ('%1$s','%2$s','%3$s','%4$s','%5$s','%6$s')";
    String ostop=null;
	String dstop=null;
    String orn = null;
    String drn = null;
	public static String fid = null;
    
    
	/**
	 * Constructor of the object.
	 */
	public TestInput() {
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
		//System.out.println(route);
		ostop=request.getParameter("ostop");
		dstop=request.getParameter("dstop");
		orn = request.getParameter("orn");
		drn = request.getParameter("drn");
		out.println(doInsert());
		out.flush();
		out.close();
	}

	private String doInsert(){
		String result = "1";
		try {
			//
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        Statement stmt = conn.createStatement();
	        stmt.executeUpdate(String.format(sqli, fid, dateFormat.format(date),orn,ostop,drn,dstop));
	   
	        if (stmt != null) {
	            try {
	                stmt.close();
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
	        result = "0";
	        return result;
	        //e.printStackTrace();
	    }
		return result;
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
