package com.spring.shopping.repository.impl;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.spring.shopping.model.Customer;
import com.spring.shopping.repository.CustomerRepository;
import com.spring.shopping.util.CustomerMapper;

@Repository
public class CustomerRepositoryJdbcImpl implements CustomerRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public Customer validateUsers(String userName, String password) {
		try {
			String sql = "select * from eshopper.customer c where c.User_Name=:userName and c.Pass=:password";
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
					.addValue("userName", userName).addValue("password",
							password);
			Customer customer = namedParameterJdbcTemplate.queryForObject(sql,
					sqlParameterSource, new CustomerMapper());
			return customer;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Integer registerUser(Customer customer) {
		String sql = "INSERT INTO eshopper.customer (User_Name,Pass,Email_Address) VALUES(:userName,:password,:emailAddress)";
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(
				customer);

		return namedParameterJdbcTemplate.update(sql, sqlParameterSource);
	}

}