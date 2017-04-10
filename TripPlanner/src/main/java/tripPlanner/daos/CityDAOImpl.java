package tripPlanner.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import tripPlanner.interfaces.CityDAO;
import tripPlanner.models.Cities;


public class CityDAOImpl implements CityDAO {

	private JdbcTemplate jdbcTemplateObject;

	public CityDAOImpl(DataSource dataSource) {
	    this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void addCity(Cities city) {
		String sql = "INSERT INTO city (CITYNAME,CITYCOORDINATES,COVERAGEMINDAYS,COVERAGEMAXDAYS,STATEID,SCORE)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplateObject.update(sql, city.getCityname(),city.getCitycoordinates(),city.getCoveragemindays(),city.getCoveragemaxdays(),city.getStateid(),city.getScore());
	}

	@Override
	public void deleteCity(int cityId) {
		String sql = "DELETE FROM City WHERE cityid=?";
		jdbcTemplateObject.update(sql, cityId);		
	}


	@Override
	public Cities getCity(int cityId) {
	    String sql = "SELECT * FROM City WHERE cityId=" + cityId;
	    return jdbcTemplateObject.query(sql, new ResultSetExtractor<Cities>() {
	 
	        @Override
	        public Cities extractData(ResultSet rs) throws SQLException,
	                DataAccessException {
	            if (rs.next()) {
	            	Cities city = new Cities();
	                city.setCityId(rs.getInt("CITYID"));
	                city.setCityname(rs.getString("CITYNAME"));
	                city.setCitycoordinates(rs.getString("CITYCOORDINATES"));
	                city.setCoveragemindays(rs.getInt("COVERAGEMINDAYS"));
	                city.setCoveragemaxdays(rs.getInt("COVERAGEMAXDAYS"));
	                city.setStateid(rs.getInt("STATEID"));
	                city.setScore(rs.getInt("SCORE"));
	           
	                return city;
	            }
	 
	            return null;
	        }
	 
	    });
	}
	
	
	@Override
	public List<Cities> listAllCity() {
		String sql = "SELECT * FROM city";
		List<Cities> listCity = jdbcTemplateObject.query(sql, new RowMapper<Cities>() {
			
			@Override
			public Cities mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					Cities city = new Cities();
		
					city.setCityId(rs.getInt("CITYID"));
			        city.setCityname(rs.getString("CITYNAME"));
			        city.setCitycoordinates(rs.getString("CITYCOORDINATES"));
			        city.setCoveragemindays(rs.getInt("COVERAGEMINDAYS"));
			        city.setCoveragemaxdays(rs.getInt("COVERAGEMAXDAYS"));
			        city.setStateid(rs.getInt("STATEID"));
			        city.setScore(rs.getInt("SCORE"));
			        
			        
			        return city;
				}
			});
		
			return listCity;
		}

}
