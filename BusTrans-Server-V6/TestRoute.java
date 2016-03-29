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

import java.util.Calendar;

import java.util.Date;

import java.util.HashMap;

import java.util.Map;




import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;




import org.json.JSONArray;




public class TestRoute extends HttpServlet {




	 private static final long serialVersionUID = 1L;

		

		String driver = "com.mysql.jdbc.Driver";

	    String dbName = "sbs";

	    String dbPwd = "2CBEpBKZhw9q4sfV";

	    String dbUserName = "sbs_user";

	    String url = "jdbc:mysql://128.235.90.197:3306/" + dbName;

	    String sqls = "select p_transfer,p_tarrived,p_oarrived from sbs_simpax_od where fid='%s' ";

	    String sqls1 = "select p_oarrived,p_obus_id,p_tbus_id from sbs_simpax_od where fid='%s'"; 

	    String sqls2 = "select b_class from sbs_simtrajs where b_traj_id in (select Max(b_traj_id)from sbs_simtrajs where b_vissim_id = '%s')";

	    

	    

	    public static String fid = null;

	    public static String res = null;

	    public static String busId = null;

	

	public TestRoute() {

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

		//out.println(doQuery());

		

		String res = doQuery();

		if (res == "1") {

			String newBusId = doQuery1();

			System.out.println(newBusId);

			//out.println(doQuery1());

			out.println(doQuery2(newBusId));

//		

		}else {

			out.println("arrive");

		}

		

		

		

		

		out.flush();

		out.close();

	}




	/**

	 * Initialization of the servlet. <br>

	 *

	 * @throws ServletException if an error occurs

	 */

	public void init() throws ServletException {

		// Put your code here

	}

	

private String doQuery(){

		

		String res = "1";

		try {

			//

	        Class.forName(driver);

	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);

	        //

	        PreparedStatement ps = conn.prepareStatement(String.format(sqls, fid));

	        System.out.println(String.format(sqls, fid));

	        ResultSet rs = ps.executeQuery();

	        String p_transfer = null;

	        String p_tarrived = null;

	        String p_oarrived = null;

	      

	        while(rs.next()){

	        	

	        	p_transfer = rs.getString("p_transfer");

				p_tarrived = rs.getString("p_tarrived");

				p_oarrived = rs.getString("p_oarrived");

				

				int pt = Integer.parseInt(p_transfer);

		        int pta = Integer.parseInt(p_tarrived);

		        int poa = Integer.parseInt(p_oarrived);

		        

		        	//System.out.println(pt);

		        	//System.out.println(poa);

		        	//System.out.println((pt==1) && (pta==1) ||(pt==0)&&(poa==1));

		        if ((pt==1) && (pta==1) ||(pt==0)&&(poa==1)) {

		        	res = "0";

		        } else {

		        	res = "1";

		        }

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

			return res;

		

	}

	

private String doQuery1(){

		

		//String res = "1";

		try {

			//

	        Class.forName(driver);

	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);

	        //

	        PreparedStatement ps = conn.prepareStatement(String.format(sqls1, fid));

	        System.out.println(String.format(sqls, fid));

	        ResultSet rs = ps.executeQuery(); 

	        String p_oarrived = null;

	        String p_obus_id = null;

	        String p_tbus_id = null;

	          while(rs.next()){

	    	   p_oarrived = rs.getString("p_oarrived");

	    	   p_obus_id = rs.getString("p_obus_id");

	    	   p_tbus_id = rs.getString("p_tbus_id");

	    	   

	    	   if(Integer.parseInt(p_oarrived)==0){

	    		   busId = p_obus_id;

	    	   }

	    	   if(Integer.parseInt(p_oarrived)==1){

	    		   busId = p_tbus_id;

	    	   }

	    	   

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

			//return res;

		return busId;

		

	}




private String doQuery2(String busId){

	String result = "route not change";

	try {

		//

		

        Class.forName(driver);

        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);

        Statement stmt1 = conn.createStatement();

//        Statement stmt2 = conn.createStatement();

//        Statement stmt3 = conn.createStatement();

        

        

        ResultSet rs = stmt1.executeQuery(String.format(sqls2,busId));

        

        //System.out.println(String.format(sqls2, busId));

        //ResultSet rs = stmt1.executeQuery(String.format(sqls3));

        

        String b_class = null;

        while(rs.next()){

        	b_class = rs.getString("b_class");

        	int bc = Integer.parseInt(b_class);

        	if(bc != 5000 && bc != 20000 && bc != 21000 ){

        		result = "route change";

        	}

        }

        

        

        if (rs != null) {

            try {

                rs.close();

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }

        if (stmt1 != null) {

            try {

                stmt1.close();

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

//        result = "0";

//        return result;

        //e.printStackTrace();

    	e.printStackTrace();

    }

	return result;

}



}
