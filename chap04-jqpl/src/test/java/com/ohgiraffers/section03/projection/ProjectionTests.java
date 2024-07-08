package com.ohgiraffers.section03.projection;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class ProjectionTests {

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

    /*
    * 프로젝션(projection)
    * select 절에 조회할 대상을 지정하는 것을 프로젝션이라고 한다.
    * (select {프로젝션 대상} from)
    *
    * 프로젝션 대상은 4가지 방식이 있다.
    * 1. 엔티티 프로젝션
    *   원하는 객체를 바로 조회할 수 있다.
    *   조회된 엔티티는 영속성 컨텍스트에서 관리한다.
    * */
}
