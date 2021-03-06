package it.polito.tdp.iscritticorsi.db;

import it.polito.tdp.iscritticorsi.model.Corso;
import it.polito.tdp.iscritticorsi.model.Studente;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IscrittiDAO {
	
	private Studente createStudente(ResultSet rs) throws SQLException {
		return new Studente(rs.getInt("matricola"),
				rs.getString("cognome"), rs.getString("nome"),
				rs.getString("cds")) ;
	}
	
	private Corso createCorso(ResultSet rs) throws SQLException {
		return new Corso(rs.getString("codins"),
				rs.getInt("crediti"), rs.getString("nome"),
				rs.getInt("pd")) ;
	}

	
	public List<Corso> getAllCorso() {
		final String sql = "SELECT codins, crediti, nome, pd FROM corso";

		List<Corso> result = new ArrayList<Corso>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Corso c = createCorso(rs);
				result.add(c);
			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public List<Studente> getAllStudente() {
		final String sql = "SELECT matricola, cognome, nome, cds FROM studente";

		List<Studente> result = new ArrayList<Studente>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Studente s = createStudente(rs) ;
				result.add(s);
			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public List<ItemIscritto> getAllIscritti() {
		
		final String sql = "SELECT matricola, codins FROM iscrizione";

		List<ItemIscritto> result = new ArrayList<ItemIscritto>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				ItemIscritto iscr = new ItemIscritto(rs.getInt("matricola"),
						rs.getString("codins"));
				result.add(iscr);
			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public List<Studente> getStudentiByCorso(Corso c) {
		
		final String sql = "SELECT studente.matricola, cognome, nome, cds " +
				"FROM iscrizione, studente " +
				"WHERE studente.matricola = iscrizione.matricola " +
				"AND iscrizione.codins = ?" ;
		
		List<Studente> result = new ArrayList<Studente>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, c.getCodins()) ;
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Studente s = createStudente(rs) ;
				result.add(s);
			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public List<Corso> getCorsiByStudente(Studente  s) {
		final String sql = "SELECT iscrizione.codins, crediti, nome, pd " +
				"FROM iscrizione, corso " +
				"WHERE corso.codins = iscrizione.codins " +
				"AND iscrizione.matricola= ? ";

		List<Corso> result = new ArrayList<Corso>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, s.getMatricola()) ;

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Corso c = createCorso(rs);
				result.add(c);
			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	
}
