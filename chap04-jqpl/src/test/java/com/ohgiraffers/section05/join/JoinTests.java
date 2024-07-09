package com.ohgiraffers.section05.join;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class JoinTests {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory(){ entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");}
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
    * 조인의 종류
    * 1. 일반 조인 : 일반적인 sql 조인을 의미 (내부 조인, 외부 조인, 컬렉션 조인, 세타 조인)
    * 2. 페치 조인 : Jpql에서 성능 최적화를 위해 제공하는 기능으로 연관된 엔티티나 컬렉션을 한 번에 조회할 수 있다.
    *               지연 로딩이 아닌 즉시 로딩을 수행하며 join fetch 명령어를 사용한다.
    * */

    @Test
    public void 내부조인을_이용한_조회_테스트(){
        // 연관관계가 맺어진 inner 조인을 의미함
        String jpql = "SELECT m FROM menu_section05 m JOIN m.categoryCode c";

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        Assertions.assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    public void 외부조인을_이용한_조회_테스트(){
        // 연관관계가 맺어진 엔티티들에 대한 Left or Right Outer 조인을 말한다.
        String jpql = "SELECT m.menuName, c.categoryName FROM menu_section05 m RIGHT JOIN m.categoryCode c" +
                " ORDER BY m.categoryCode.categoryCode";

        List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class).getResultList();

        Assertions.assertNotNull(menuList);
        menuList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.println(col + " "));
            System.out.println();
        });
    }
}
