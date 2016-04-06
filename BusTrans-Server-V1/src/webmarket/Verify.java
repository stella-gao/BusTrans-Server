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





public class Verify extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	String driver = "com.mysql.jdbc.Driver";
    String dbName = "sbs";
    String dbPwd = "*************";
    String dbUserName = "sbs_user";
    String url = "jdbc:mysql://***.***.***.***:3306/" + dbName;
	
    String sqls = "select p_next_delay from sbs_simpassx where fid='%s'and arrTime = '%2s' ";
    
   // String sqls1 = "select p_hold_success from sbs_simpassx where fid='%s'and arrTime = '%2s'";
    
    String sqli = "insert into sbs_simpassx (fid, arrTime,p_ort_no,p_osp_id,p_drt_no,p_dsp_id) values ('%1$s','%2$s','%3$s','%4$s','%5$s','%6$s')";
    
    String sqli1 = "update sbs_simpassx set p_hold_request = '%s' where fid = '%2s'and arrTime = '%3s' ";
    String fid=null;
    String delay;
    
//    Random rand = new Random();
//    int  id = rand.nextInt(50) + 41;
    
    
//	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	Date date = new Date();
	
	String ostop=null;
	String dstop=null;
    String orn = null;
    String drn = null;
	public static String id = null;
	String hr = null;
	public static String dateI = null;
	
	
	int os=0;
	int ds=0;
	int hold_request = 0;
    
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
		
		System.out.println("in doPost");

		PrintWriter out = response.getWriter();
		
		fid = request.getParameter("fid");		
		ostop=request.getParameter("ostop");
		dstop=request.getParameter("dstop");
		orn = request.getParameter("orn");
		drn = request.getParameter("drn");
		id = request.getParameter("fid");
//		hr = request.getParameter("p_hold_requset");
		
		if(ostop != null && dstop !=null){
			os = Integer.parseInt(ostop);
			ds = Integer.parseInt(dstop);
		}
//		if(hr != null){
//			out.println(doUpdate());
//		}
		
		//hold_request = Integer.parseInt(hr);
		
		
		//out.println(doQuery());
		//out.println(doInsert());
		doInsert();
		if(id != null){
			out.println(doQuery());
			
		}
		
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
			//
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        //
	        PreparedStatement ps = conn.prepareStatement(String.format(sqls, fid,dateI));
	        ResultSet rs = ps.executeQuery();
	        //
	        
//	        if(rs.next()) {
//	        	result = "P_delay"+  rs.getString(1);
//	            //result = route+" is in the route list";	            
//	        } else {
//	        	//result = route+" is not in the route list";
//	        	result = "P_delay does not exist";
//	        }
	        /*
	        while(rs.next()){
	        	Map<String,String> pds = new HashMap<String, String>();
	        	pds.put("p_next_delay", rs.getString("p_next_delay"));
	        	ja.put(pds);
	        }
	        */
	        
	        while(rs.next()){
	        	Map<String,String> pds = new HashMap<String, String>();
	        	delay = rs.getString("p_next_delay");
	        	pds.put("p_next_delay", delay);
	        	
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
	





//private String doUpdate(){
//	String result = "1";
//	try {
//		//
//		
//        Class.forName(driver);
//        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
//        Statement stmt = conn.createStatement();
//        stmt.executeUpdate(String.format(sqli1,fid,dateI));
//   
//        if (stmt != null) {
//            try {
//                stmt.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        // 关闭链接对象	        
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    } catch (Exception e) {
//        result = "0";
//        return result;
//        //e.printStackTrace();
//    }
//	return result;
//}
	
private String doInsert(){
	String result = "1";
	try {
		//
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		dateI = dateFormat.format(date);
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(String.format(sqli, id, dateFormat.format(date),orn,ostop,drn,dstop));
   
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
