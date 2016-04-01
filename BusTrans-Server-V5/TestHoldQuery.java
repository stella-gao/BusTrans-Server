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

import com.mysql.jdbc.Driver;





public class TestHoldQuery extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	String driver = "com.mysql.jdbc.Driver";
    String dbName = "sbs";
    String dbPwd = "*************";
    String dbUserName = "*******";
    String url = "jdbc:mysql://***.***.***.***:3306/" + dbName;
    String sqli1 = "update sbs.sbs_simpax_od set sbs_simpax_od.p_hold_request = '%s' where fid = '%1s'";
    String sqls = "select p_hold_success from sbs_simpax_od where fid='%s'";
    public static String fid=null;
	String hr = null;
	
	
	
	
	
    
    /**
     * 
     */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("in doGet");
		doPost(request, response);
	}

	/**
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		
		PrintWriter out = response.getWriter();
		fid = request.getParameter("fid");		
		hr = request.getParameter("p_hold_requset");
		
		
		System.out.println(fid);
		System.out.println(hr);
		//if(fid != null){
			//System.out.println(sqli1);
			//out.println(doUpdate());
			out.println(doQuery());
		//}
		
		out.flush();
		out.close();
	}
	
	
	/**
	 * 
	 * @param username
	 * @return result code
	 */
	private String doQuery(){
		String result = "0";
		JSONArray ja = new JSONArray();
		try {
			
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        
	        PreparedStatement ps = conn.prepareStatement(String.format(sqls, fid));
            ResultSet rs = ps.executeQuery();
       
	        
	        while(rs.next()){
	        	Map<String,String> pds = new HashMap<String, String>();
	        	pds.put("p_hold_success", rs.getString("p_hold_success"));
	        	ja.put(pds);
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
	

private String doUpdate(){
	String result = "1";
	try {
		//
		
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(String.format(sqli1,hr,fid));
        //stmt.executeUpdate(String.format(sqli, id, dateFormat.format(date),orn,ostop,drn,dstop));
        //System.out.println(sqli1);
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


}
