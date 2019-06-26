package it.polito.tdp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.model.District;
import it.polito.tdp.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	public List<Integer> caricaAnni(){
		String sql = "SELECT DISTINCT YEAR(reported_date) " + 
				"FROM EVENTS " + 
				"ORDER BY YEAR(reported_date) " 
				 ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(
							res.getInt("YEAR(reported_date)"));
				} catch (Throwable t) {
					t.printStackTrace();
					
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	public List<District> caricaDistretti(int year){
		String sql = "SELECT AVG(geo_lon), AVG(geo_lat), district_id "+
				"FROM EVENTS "+
				"WHERE YEAR(reported_date)=? "+
				"GROUP BY district_id ";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, year);
			
			List<District> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					LatLng centro=new LatLng(res.getDouble("AVG(geo_lat)"), res.getDouble("AVG(geo_lon)"));
					list.add(new District(res.getInt("district_id"), centro));
				} catch (Throwable t) {
					t.printStackTrace();
					
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	public District minorCriminalita(int anno, Graph<District, DefaultWeightedEdge> grafo) {
		String sql = "SELECT id "+
				"FROM (SELECT district_id AS id, COUNT(is_crime) AS cnt "+
					      "FROM EVENTS "+
					      "WHERE YEAR(reported_date)=? "+
					      "GROUP BY district_id) AS t1 "+
					"WHERE cnt = (SELECT MIN(cnt) "+
					             "FROM (SELECT COUNT(is_crime) AS cnt "+
					            "FROM EVENTS "+
					             "WHERE YEAR(reported_date)=? "+
					             "GROUP BY district_id) AS t2) ";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setInt(2, anno);
			
			ResultSet res = st.executeQuery() ;
			
			District centrale = null;
			
			while(res.next()) {
				try {
					int id=res.getInt("id");
					for(District d:grafo.vertexSet()){
					if(d.getId()==id) {
						centrale=d;
					}
					}
				} catch (Throwable t) {
					t.printStackTrace();
					
				}
			}
			
			conn.close();
			return centrale ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	public List<Event> eventiDATA(int anno, int mese, int giorno){
		String sql = "SELECT * "+
				"FROM EVENTS "+
				"WHERE YEAR(reported_date)=? "+
				"AND MONTH(reported_date)=? "+
				"AND DAY(reported_date)=? ";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setInt(2, mese);
			st.setInt(3, giorno);
			
			List<Event> list = new ArrayList<>() ;
			Event e;
			
			ResultSet res = st.executeQuery() ;
			System.out.println("Cerco gli eventi in quella data...");
			
			while(res.next()) {
				try {
					e=new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic"));
					System.out.println("\n"+e.toString());
					list.add(e);
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	


}
