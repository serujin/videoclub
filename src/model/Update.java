package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Update {
	private Connection c;
	private Select select;
	
	public Update(Connection c) throws SQLException {
		this.c = c;
		select = new Select(c);
	}
	
	public void password(int id) throws SQLException {
		String newPassword = JOptionPane.showInputDialog("Introduce la nueva contraseña");
		Statement s = c.createStatement();
		String sql = "UPDATE USERS SET PASSWORD = '"+newPassword+"' WHERE ID = "+id;
		s.execute(sql);
		JOptionPane.showMessageDialog(null,"Contraseña cambiada con éxito", "Cambio de contraseña", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void article(int id) throws SQLException {
		int type = select.getType(id);
		if(type==1) {
			int response;
			String[] options = {"Nombre", "Director", "Año"};
			response = JOptionPane.showOptionDialog(null, "¿Qué deseas actualizar?", "Actualizar elemento", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			String field = "NAME";
			if(response == 1) {
				field = "DIRECTOR";
			}
			String newValue = JOptionPane.showInputDialog("¿Cuál es el nuevo "+options[response]+"?");
			String sql = "UPDATE ARTICLES SET "+field+" = '"+newValue+"' WHERE ID = "+id;
			if(response == 2) {
				int year = Integer.parseInt(newValue);
				sql = "UPDATE ARTICLES SET YEAR = "+year+" WHERE ID = "+id;
			}
			Statement s = c.createStatement();
			s.execute(sql);
			JOptionPane.showMessageDialog(null,"Artículo actualizado con éxito", "Actualizar artículo", JOptionPane.PLAIN_MESSAGE);
		} else {
			 JOptionPane.showMessageDialog(null,"Sólo se pueden actualizar películas", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void returnElement(int id, float valuation) throws SQLException {
		int type = select.getType(id);
		if(type==1) {
			returnMovies(id, valuation);
		} 
		if(type==2) {
			returnSeries(id, valuation);
		}
		if(type==3) {
			returnSeason(id, valuation);
		} 
		if(type==4) {
			returnEpisode(id, valuation);
		}
	}
	
	private void returnMovies(int id, float valuation) throws SQLException {
		if(id>0) {
			float oldValuation = select.valuation(id);
			float newValuation = (oldValuation + valuation)/2f;
			Statement s = c.createStatement();
			String sql = "UPDATE ARTICLES SET RENTED = RENTED - 1, STOCK = STOCK + 1, VALUATION = "+newValuation+" WHERE ID = "+id;
			s.execute(sql);
		} else {
			showUpdateError();
		}
	}
	
	private void returnSeries(int id, float valuation) throws SQLException {
		if(id>0) {
			float oldValuation = select.valuation(id);
			float newValuation = (oldValuation + valuation)/2;
			Statement s = c.createStatement();
			String sql = "UPDATE ARTICLES SET RENTED = RENTED - 1, STOCK = STOCK + 1, VALUATION = "+newValuation+" WHERE ID = "+id;
			s.execute(sql);
			ArrayList<Integer> seasonsID = select.getSeriesSeasonID(id);
			for(Integer i : seasonsID) {
				sql = "UPDATE ARTICLES SET RENTED = RENTED - 1, STOCK = STOCK + 1, VALUATION = "+newValuation+" WHERE ID = "+i;
				Statement s1 = c.createStatement();
				s1.execute(sql);
				s1.close();
				ArrayList<Integer> episodesID = select.getSeasonEpisodesID(i);
				for(Integer j : episodesID) {
					sql = "UPDATE ARTICLES SET RENTED = RENTED - 1, STOCK = STOCK + 1, VALUATION = "+newValuation+" WHERE ID = "+j;
					Statement s2 = c.createStatement();
					s2.execute(sql);
					s2.close();
				}
			}
		} else {
			showUpdateError();
		}
	}
	
	private void returnSeason(int id, float valuation) throws SQLException {
		if(id>0) {
			float oldValuation = select.valuation(id);
			float newValuation = (oldValuation + valuation)/2;
			Statement s = c.createStatement();
			String sql = "UPDATE ARTICLES SET RENTED = RENTED - 1, STOCK = STOCK + 1, VALUATION = "+newValuation+" WHERE ID = "+id;
			s.execute(sql);
			ArrayList<Integer> episodesID = select.getSeasonEpisodesID(id);
			for(Integer i : episodesID) {
				sql = "UPDATE ARTICLES SET RENTED = RENTED - 1, STOCK = STOCK + 1, VALUATION = "+newValuation+" WHERE ID = "+i;
				Statement s1 = c.createStatement();
				s1.execute(sql);
				s1.close();
			} 
			updateSeriesValuation(id); 
			updateSeriesStock(id);
		} else {
			showUpdateError();
		}
	}
	
	private void returnEpisode(int id, float valuation) throws SQLException {
		if(id>0) {
			float oldValuation = select.valuation(id);
			float newValuation = (oldValuation + valuation)/2f;
			Statement s = c.createStatement();
			String sql = "UPDATE ARTICLES SET RENTED = RENTED - 1, STOCK = STOCK + 1, VALUATION = "+newValuation+" WHERE ID = "+id;
			s.execute(sql);
			updateSeasonValuation(id); 		
			int modifiedSeason = select.getEpisodeParentID(id);
			updateSeriesValuation(modifiedSeason); 
			updateSeasonStock(id);
			updateSeriesStock(modifiedSeason);
		} else {
			showUpdateError();
		}
	}
	
	private void updateSeriesStock(int seasonID) throws SQLException {
		int seriesID = select.getSeasonParentID(seasonID);
		ArrayList<Integer> seasonsID = select.getSeriesSeasonID(seriesID);
		int minStock = 99999999;
		int stock = 0;
		for(Integer i : seasonsID) {
			stock = select.getStock(i);
			if(stock<minStock) {
				minStock = stock;
			}
			ArrayList<Integer> episodesID = select.getSeasonEpisodesID(seriesID);
			for(Integer j : episodesID) {
				stock = select.getStock(j);
				if(stock<minStock) {
					minStock = stock;
				}
			}
		}
		Statement s = c.createStatement();
		String sql = "UPDATE ARTICLES SET STOCK = "+minStock+" WHERE ID = "+seriesID;
		s.execute(sql);
	}
	
	private void updateSeasonStock(int episodeID) throws SQLException {
		int seasonID = select.getEpisodeParentID(episodeID);
		ArrayList<Integer> episodesID = select.getSeasonEpisodesID(seasonID);
		int minStock = 99999999;
		int stock = 0;
		for(Integer i : episodesID) {
			stock = select.getStock(i);
			if(stock<minStock) {
				minStock = stock;
			}
		}
		Statement s = c.createStatement();
		String sql = "UPDATE ARTICLES SET STOCK = "+minStock+" WHERE ID = "+seasonID;
		s.execute(sql);
	}
	
	private void updateSeriesValuation(int seasonID) throws SQLException {
		int seriesID = select.getSeasonParentID(seasonID);
		ArrayList<Integer> seasonsID = select.getSeriesSeasonID(seriesID);
		float valuationSum = 0;
		float seasonNumber = seasonsID.size();
		for(Integer i : seasonsID) {
			valuationSum+=select.getValuation(i);
		}
		float valuationAvg = valuationSum/seasonNumber; 
		Statement s = c.createStatement();
		String sql = "UPDATE ARTICLES SET VALUATION = "+valuationAvg+" WHERE ID = "+seriesID;
		s.execute(sql);
	}
	
	private void updateSeasonValuation(int episodeID) throws SQLException {
		int seasonID = select.getEpisodeParentID(episodeID);
		ArrayList<Integer> episodesID = select.getSeasonEpisodesID(seasonID);
		float valuationSum = 0;
		float seasonNumber = episodesID.size();
		for(Integer i : episodesID) {
			valuationSum+=select.getValuation(i);
		}
		float valuationAvg = valuationSum/seasonNumber; 
		Statement s = c.createStatement();
		String sql = "UPDATE ARTICLES SET VALUATION = "+valuationAvg+" WHERE ID = "+seasonID;
		s.execute(sql);
	}
	
	public void rentElement(int id) throws SQLException {
		int type = select.getType(id);
		if(type==1) {
			rentMovies(id);
		} 
		if(type==2) {
			rentSeries(id);
		}
		if(type==3) {
			rentSeason(id);
		} 
		if(type==4) {
			rentEpisode(id);
		}
	}

	private void rentMovies(int id) throws SQLException {
		if(id>0) {
			Statement s = c.createStatement();
			s.execute("UPDATE ARTICLES SET STOCK = STOCK - 1, RENTED = RENTED + 1 WHERE ID LIKE "+id);
		} else {
			showUpdateError();
		}
	}
	
	private void rentSeries(int id) throws SQLException {
		if(id>0) {
			Statement s = c.createStatement();
			String sql = "UPDATE ARTICLES SET STOCK = STOCK - 1, RENTED = RENTED + 1 WHERE ID = "+id;
			s.execute(sql);
			ArrayList<Integer> seasonsID = select.getSeriesSeasonID(id);
			for(Integer i : seasonsID) {
				sql = "UPDATE ARTICLES SET STOCK = STOCK - 1 WHERE ID = "+i;
				Statement s1 = c.createStatement();
				s1.execute(sql);
				s1.close();
				ArrayList<Integer> episodesID = select.getSeasonEpisodesID(i);
				for(Integer j : episodesID) {
					sql = "UPDATE ARTICLES SET STOCK = STOCK - 1 WHERE ID = "+j;
					Statement s2 = c.createStatement();
					s2.execute(sql);
					s2.close();
				}
			}
		} else {
			showUpdateError();
		}
	}

	private void rentSeason(int id) throws SQLException {
		if(id>0) {
			Statement s = c.createStatement();
			String sql = "UPDATE ARTICLES SET STOCK = STOCK - 1, RENTED = RENTED + 1 WHERE ID = "+id;
			s.execute(sql);
			ArrayList<Integer> episodesID = select.getSeasonEpisodesID(id);
			for(Integer i : episodesID) {
				sql = "UPDATE ARTICLES SET STOCK = STOCK - 1 WHERE ID = "+i;
				Statement s1 = c.createStatement();
				s1.execute(sql);
				s1.close();
			} 
			updateSeriesStock(id);
		} else {
			showUpdateError();
		}
	}
	
	private void rentEpisode(int id) throws SQLException {
		if(id>0) {
			Statement s = c.createStatement();
			String sql = "UPDATE ARTICLES SET STOCK = STOCK - 1, RENTED = RENTED + 1 WHERE ID = "+id;
			s.execute(sql);		
			updateSeasonStock(id);
			int modifiedSeason = select.getEpisodeParentID(id);
			updateSeriesStock(modifiedSeason);
		} else {
			showUpdateError();
		}
	}
	
	public void repairElement(int id) throws SQLException {
		int type = select.getType(id);
		if(type==1) {
			brokenMovies(id);
		} 
		if(type==2) {
			brokenSeries(id);
		}
		if(type==3) {
			brokenSeason(id);
		} 
		if(type==4) {
			brokenEpisode(id);
		}
	}
	
	private void brokenMovies(int id) throws SQLException {
		if(id>0) {
			Statement s = c.createStatement();
			s.execute("UPDATE ARTICLES SET STOCK = STOCK - 1, REPAIRING = REPAIRING + 1 WHERE ID LIKE "+id);
		} else {
			showUpdateError();
		}
	}
	
	private void brokenSeries(int id) throws SQLException {
		if(id>0) {
			Statement s = c.createStatement();
			String sql = "UPDATE ARTICLES SET STOCK = STOCK - 1, REPAIRING = REPAIRING + 1 WHERE ID = "+id;
			s.execute(sql);
			ArrayList<Integer> seasonsID = select.getSeriesSeasonID(id);
			for(Integer i : seasonsID) {
				sql = "UPDATE ARTICLES SET STOCK = STOCK - 1, REPAIRING = REPAIRING + 1 WHERE ID = "+i;
				Statement s1 = c.createStatement();
				s1.execute(sql);
				s1.close();
				ArrayList<Integer> episodesID = select.getSeasonEpisodesID(i);
				for(Integer j : episodesID) {
					sql = "UPDATE ARTICLES SET STOCK = STOCK - 1, REPAIRING = REPAIRING + 1 WHERE ID = "+j;
					Statement s2 = c.createStatement();
					s2.execute(sql);
					s2.close();
				}
			}
		} else {
			showUpdateError();
		}
	}
	
	private void brokenSeason(int id) throws SQLException {
		if(id>0) {
			ArrayList<Integer> episodesID = select.getSeasonEpisodesID(id);
			for(Integer i : episodesID) {
				String sql = "UPDATE ARTICLES SET STOCK = STOCK - 1, REPAIRING = REPAIRING + 1 WHERE ID = "+i;
				Statement s = c.createStatement();
				s.execute(sql);
				s.close();
			} 
			updateSeriesStock(id);
		} else {
			showUpdateError();
		}
	}
	
	private void brokenEpisode(int id) throws SQLException {
		if(id>0) {
			Statement s = c.createStatement();
			String sql = "UPDATE ARTICLES SET STOCK = STOCK - 1, REPAIRING = REPAIRING + 1 WHERE ID = "+id;
			s.execute(sql);		
			updateSeasonStock(id);
			int modifiedSeason = select.getEpisodeParentID(id);
			updateSeriesStock(modifiedSeason);
		} else {
			showUpdateError();
		}
	}
	
	public void restoreElement(int id) throws SQLException {
		int type = select.getType(id);
		if(type==1) {
			restoreMovies(id);
		} 
		if(type==2) {
			restoreSeries(id);
		}
		if(type==3) {
			restoreSeason(id);
		} 
		if(type==4) {
			restoreEpisode(id);
		}
	}
	
	private void restoreMovies(int id) throws SQLException {
		if(id>0) {
			Statement s = c.createStatement();
			s.execute("UPDATE ARTICLES SET REPAIRING = REPAIRING - 1, STOCK = STOCK + 1 WHERE ID LIKE "+id);
		} else {
			showUpdateError();
		}
	}
	
	private void restoreSeries(int id) throws SQLException {
		if(id>0) {
			Statement s = c.createStatement();
			String sql = "UPDATE ARTICLES SET REPAIRING = REPAIRING - 1, STOCK = STOCK + 1 WHERE ID = "+id;
			s.execute(sql);
			ArrayList<Integer> seasonsID = select.getSeriesSeasonID(id);
			for(Integer i : seasonsID) {
				sql = "UPDATE ARTICLES SET STOCK = STOCK + 1 WHERE ID = "+i;
				Statement s1 = c.createStatement();
				s1.execute(sql);
				s1.close();
				ArrayList<Integer> episodesID = select.getSeasonEpisodesID(i);
				for(Integer j : episodesID) {
					sql = "UPDATE ARTICLES SET STOCK = STOCK + 1 WHERE ID = "+j;
					Statement s2 = c.createStatement();
					s2.execute(sql);
					s2.close();
				}
			}
		} else {
			showUpdateError();
		}
	}
	
	private void restoreSeason(int id) throws SQLException {
		if(id>0) {
			Statement s = c.createStatement();
			String sql = "UPDATE ARTICLES SET REPAIRING = REPAIRING - 1, STOCK = STOCK + 1 WHERE ID = "+id;
			s.execute(sql);
			ArrayList<Integer> episodesID = select.getSeasonEpisodesID(id);
			for(Integer i : episodesID) {
				sql = "UPDATE ARTICLES SET STOCK = STOCK + 1 WHERE ID = "+i;
				Statement s1 = c.createStatement();
				s1.execute(sql);
				s1.close();
			} 
			updateSeriesStock(id);
		} else {
			showUpdateError();
		}
	}
	
	private void restoreEpisode(int id) throws SQLException {
		if(id>0) {
			Statement s = c.createStatement();
			String sql = "UPDATE ARTICLES SET REPAIRING = REPAIRING - 1, STOCK = STOCK + 1 WHERE ID = "+id;
			s.execute(sql);		
			updateSeasonStock(id);
			int modifiedSeason = select.getEpisodeParentID(id);
			updateSeriesStock(modifiedSeason);
		} else {
			showUpdateError();
		}
	}
	
	private void showUpdateError() {
		 JOptionPane.showMessageDialog(null,"No se puede hacer eso", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
