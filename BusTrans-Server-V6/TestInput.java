package webmarket;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

public class TestInput extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	
	String driver = "com.mysql.jdbc.Driver";
    String dbName = "sbs";
    String dbPwd = "2CBEpBKZhw9q4sfV";
    String dbUserName = "sbs_user";
    String url = "jdbc:mysql://128.235.90.197:3306/" + dbName;

    String sqli = "insert into sbs_simpax_od (fid, arrTime,p_ort_no,p_odir,p_osp_id,p_drt_no,p_tdir,p_dsp_id) values ('%1$s','%2$s','%3$s','%4$s','%5$s','%6$s','%7$s','%8$s')";
    String sql = "select * from sbs_simpax_od  where fid='%1$s'";
    String sqlU ="update sbs.sbs_simpax_od set sbs_simpax_od.arrTime='%1$s',sbs_simpax_od.p_ort_no='%2$s',sbs_simpax_od.p_odir='%3$s',sbs_simpax_od.p_osp_id='%4$s',sbs_simpax_od.p_drt_no='%5$s',sbs_simpax_od.p_tdir='%6$s',sbs_simpax_od.p_dsp_id='%7$s' where fid = '%8$s' ";
    
    
    String ostop=null;
	String dstop=null;
    String orn = null;
    String drn = null;
    String poDir = null;
    String ptDir = null;
    String fid = null;
    
    
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
		poDir = request.getParameter("poDir");
		ptDir = request.getParameter("ptDir");
		
//		out.println(doQuery());
		if(doQuery() == "0"){
			out.println(doInsert());
		}else{
			out.println(doUpdate());
			out.println("update!!!");
		}
		
		out.flush();
		out.close();
	}
	
	
	
	
	private String doQuery(){
		
		JSONArray ja = new JSONArray();
		try {
			//
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        //
	        
	        PreparedStatement ps = conn.prepareStatement(String.format(sql, fid));
	        ResultSet rs = ps.executeQuery();
	        while(rs.next()){
	        	Map<String,String> pds = new HashMap<String, String>();
	        	pds.put("p_ort_no", rs.getString("p_ort_no"));
	        	ja.put(pds);
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

	private String doInsert(){
		String result = "1";
		try {
			//
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        Statement stmt = conn.createStatement();
	        stmt.executeUpdate(String.format(sqli, fid, dateFormat.format(date),orn,poDir,ostop,drn,ptDir,dstop));
	   
	        if (stmt != null) {
	            try {
	                stmt.close();
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
	        result = "0";
	        return result;
	        //e.printStackTrace();
	    }
		return result;
	}

	private String doUpdate(){
		String result = "1";
		try {
			//
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        Statement stmt = conn.createStatement();
	        stmt.executeUpdate(String.format(sqlU,dateFormat.format(date),orn,poDir,ostop,drn,ptDir,dstop,fid));
	        //System.out.println(String.format(sqlU, fid, dateFormat.format(date),orn,poDir,ostop,drn,ptDir,dstop));
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	             
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
