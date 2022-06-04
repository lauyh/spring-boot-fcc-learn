package com.example.fcc.demo.dao;

import com.example.fcc.demo.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao{
    private final JdbcTemplate jdbcTemplate;

    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        return jdbcTemplate.update("INSERT INTO person values (?,?)", id, person.getName());
    }

    @Override
    public List<Person> selectAllPeople() {
        final String sql = "SELECT id, name from person;";
        return jdbcTemplate.query(sql, (resultSet,i)->{
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(id, name);
        });
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sql = "SELECT id, name from person WHERE id = ?;";
        Person person = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet,i)->{
                UUID personId = UUID.fromString(resultSet.getString("id"));
                String name = resultSet.getString("name");
                return new Person(id, name);
        });

        return Optional.ofNullable(person);
    }

    @Override
    public int deletePersonById(UUID id) {
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return 0;
    }


}