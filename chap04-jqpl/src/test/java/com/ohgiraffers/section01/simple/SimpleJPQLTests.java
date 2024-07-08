package com.ohgiraffers.section01.simple;


import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.Objects;

public class SimpleJPQLTests {
    /*
     * jpql(java persistence Query Language)
     * jpql 은 엔티티 객체를 중심으로 개발할 수 있는 객체 지향 쿼리이다.
     * sql보다 간결하며 특정 DBMS에 의존하지 않는다.
     * 방언을 통해 해당 DBMS에 맞는 SQL을 실행하게 된다.
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

    /*
    * jpql의 기본 문법
    * select, update, delete 등의 키워드 사용은 sql과 동일하다.
    * insert는 persist() 메소드를 사용하면 된다.
    * 키워드는 대소문자를 구분하지 않지만, 엔티티와 속성은 대소문자를 구분함에 유의한다.
    * 엔티티 별칭은 필수로 작성해야 하며 별칭 없이 작성하면 에러가 발생한다.
    * */

    /*
    * jpql 사용방법은 다음과 같다.
    * 1. 작성한 jpql(문자열)을 'entityManager.createQuery()' 메소드를 통해 쿼리 객체로 만든다.
    * 2. 쿼리 객체는 'TypedQuery', 'Query' 두 가지가 있다.
    *   2-1. TypedQuery : 반환할 타입을 명확하게 지정하는 방식일 때 사용하면 쿼리 객체의 메소드 실행을 결과로 지정하는 타입이 반환된다.
    *   2-2. Query : 반환할 타입에서 명확하게 지정할 수 없을 때 사용하며 쿼리 객체 메소드의 실행 결과로 object 또는 Object[]이 반환된다.
    * 3. 쿼리 객체에서 제공하는 메소드 'getSingleResult()' 또는 'getResultList()'를 호출하여 쿼리를 실행하고 데이터베이스를 조회한다.
    *   3-1. getSingleResult() : 결과가 정확히 한 행일 경우 사용하며 없거나 많으면 예외가 발생한다.
    *   3-2. getResultList() : 결과가 2행 이상일 경우 사용하며 컬렉션을 반환한다. 결과가 없으면 빈 컬렉션을 반환한다.
    * */

    @Test
    public void TypedQuery_를_이용한_단일메뉴_조회_테스트(){
        String jpql = "SELECT m.menuName FROM menu_section01 as m WHERE m.menuCode=3";
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);

        String resultMenuName = query.getSingleResult();

        Assertions.assertEquals("민트미역국", resultMenuName);
    }

    @Test
    public void Query_를_이용한_단일메뉴_조회_테스트(){
        String jpql = "SELECT m.menuName FROM menu_section01 as m WHERE m.menuCode=7";

        Query query = entityManager.createQuery(jpql);
        Object resultMenuName = query.getSingleResult();
        Assertions.assertTrue(resultMenuName instanceof String);
        Assertions.assertEquals("민트미역국", resultMenuName);
    }
}
