package com.goorm.BITA;

import com.goorm.BITA.config.MySQLContainerTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class MysqlTest extends MySQLContainerTest {

    @Test
    void test1() {
        log.info("로그 getJdbcDriverInstance {} ", mysql.getJdbcDriverInstance());
        log.info("로그 getJdbcUrl {} ", mysql.getJdbcUrl());
        log.info("로그 getMappedPort {} ", mysql.getMappedPort(3306));
        log.info("로그 getHost {} ", mysql.getHost());
        log.info("로그 getUsername {} ", mysql.getUsername());
        log.info("로그 getPassword {} ", mysql.getPassword());
    }
}