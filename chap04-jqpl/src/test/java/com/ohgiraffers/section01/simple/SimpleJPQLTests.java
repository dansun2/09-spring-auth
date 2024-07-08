package com.ohgiraffers.section01.simple;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class SimpleJPQLTests {
    /*
     * jpql(java persistence Query Language)
     * jpql 은 엔티티 객체를 중심으로 개발할 수 있는 객체 지향 쿼리이다.
     * sql보다 간결하며 특정 DBMS에 의존하지 않는다.
     * 방언을 통해 해당 DBMS에 맞는 SQL을 실행하게 된다.
     * jpql은 find() 메소드를 통한 조회와 다르게 항상 데이터베이스에 SQL을 실행해서 결과를 조회한다.
     * */
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory(){
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest"); //데이터베이스에 커넥션 맺을 수 있는 공장을 만듦
    }
    @BeforeEach
    public void initManager(){
        entityManager = entityManagerFactory.createEntityManager();
    }
    @AfterEach
    public void closeManager(){
        entityManager.close();
    }
    @AfterAll
    public static void closeFactory(){
        entityManagerFactory.close();
    }

}
