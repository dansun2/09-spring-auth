package com.ohgiraffers.section02.parameter;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParameterBindingTests {

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

    @Test
    public void 이름_기준_파라미터_바인딩_메뉴_목록_조회_테스트(){
        String menuName = "한우딸기국밥";
        String jpql = "SELECT m FROM menu_section02 m WHERE m.menuName = :menuName";
//        "SELECT m FROM menu_section02 m WHERE m.menuName = '한우딸기국밥'"; -> entityManager -> DB
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter("menuName", menuName)
                .getResultList();

        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    public void 위치_기준_파라미터_바인딩_메뉴_목록_조회_테스트() {

        //given
        String menuNameParameter = "한우딸기국밥";

        //when
        String jpql = "SELECT m FROM menu_section02 m WHERE m.menuName = ?1"; // ?1이 뭐야 파라미터값인가

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter(1, menuNameParameter)
                .getResultList();

        //then
        Assertions.assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}