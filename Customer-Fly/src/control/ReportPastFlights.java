package control;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.util.HashMap;

import javax.swing.JFrame;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import util.Consts;

public class ReportPastFlights {
	private static ReportPastFlights instance;
	public static ReportPastFlights getInstance() 
	{
		if (instance == null)
			instance = new ReportPastFlights();
		return instance;
	}
	
	public JFrame produceReport() // (Date start, Date end, int numFlightParameter)
	{

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR))
			{
				HashMap<String, Object> params = new HashMap<>();

//				params.put("agent_id", (Integer) agentNum.getId());
//				params.put("start",new java.sql.Date(start.getTime()));
//				params.put("end",new java.sql.Date(end.getTime()));
//				params.put("seatsEconomyOfBigFlight", numFlightParameter);
				JasperPrint print = JasperFillManager.fillReport(
						getClass().getResourceAsStream("/boundary/ReportPastFlights.jasper"),
//						getClass().getResourceAsStream(Consts.BIG_FLIGHTS_REPORT_PATH),
						params, conn);
				JFrame frame = new JFrame("ReportCustomerAndUpdatedOrders");
				frame.getContentPane().add(new JRViewer(print));
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.pack();
				return frame;
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	
	}


}
