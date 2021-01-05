package com.example.demo.dao;

import com.example.demo.entity.Group;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import static java.sql.Types.BLOB;

public interface GroupsRepository extends JpaRepository<Group, Integer> {

    @Modifying
    @Transactional
    @Query("update Group set image=:blob where id_group=:id")
    void setBlob(Integer id, Blob blob);

    @Query(value="select * from rozrachunki.groups order by id_group desc limit 1", nativeQuery = true)
    Group findMaxId();
}
