package es.salesianos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import es.salesianos.model.User;

@Component
public class UserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;

	public void insert(User userFormulario) {
		String sql = "INSERT INTO USER (dni,nombre,apellido)" + "VALUES ( :dni, :nombre, :apellido)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("dni", userFormulario.getDni());
		params.addValue("nombre", userFormulario.getNombre());
		params.addValue("apellido", userFormulario.getApellido());
		namedJdbcTemplate.update(sql, params);
	}

	public Optional<User> search(User user) {
		String sql = "SELECT * FROM USER WHERE dni = ?";
		User person = null;
		try {
			person = (User) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(User.class), user.getDni());
		} catch (EmptyResultDataAccessException e) {
		}
		return Optional.ofNullable(person);

	}

	public void update(User user) {
			String sql = "UPDATE user SET " + "nombre = ?, apellido = ? WHERE dni = ?";
		jdbcTemplate.update(sql, user.getNombre(), user.getApellido(), user.getDni());
	}

	public List<User> listAllUsers() {
		String sql = "SELECT * FROM USER";
		List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
		return users;
	}

}
