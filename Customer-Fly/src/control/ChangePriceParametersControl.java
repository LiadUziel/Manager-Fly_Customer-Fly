package control;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import util.Consts;

public class ChangePriceParametersControl {

	private static ChangePriceParametersControl instance;
	public static ChangePriceParametersControl getInstance() 
	{
		if (instance == null)
			instance = new ChangePriceParametersControl();
		return instance;
	}
	
	public static LocalDate maxDate;
	// index 0 - duration, index 1 - meal, index 2 - class
    public ArrayList<Integer> getOldParamsDetails() {
		ArrayList<Integer> params = new ArrayList<Integer>();
		boolean startFlag = true;

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_parameters.*\r\n"+ "FROM tbl_parameters");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					if (startFlag) {
						maxDate = rs.getDate(1).toLocalDate();
						params.add(0, rs.getInt(2));
						params.add(1, rs.getInt(3));
						params.add(2, rs.getInt(4));

					}
					// updated date
					else if (rs.getDate(1).toLocalDate().isAfter(maxDate)) {
						params.add(0, rs.getInt(2));
						params.add(1, rs.getInt(3));
						params.add(2, rs.getInt(4));
						maxDate = rs.getDate(1).toLocalDate();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return params;
	}
    
    
    public void addParamsToDB(Integer duration, Integer meal, Integer clas) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_PARAMETERS)){
			int i = 1;
			stmt.setDate(i++, Date.valueOf(LocalDate.now()));
			stmt.setInt(i++, duration);
			stmt.setInt(i++, meal);
			stmt.setInt(i++, clas);
			stmt.executeUpdate();
		}
		return;	
    }



}
