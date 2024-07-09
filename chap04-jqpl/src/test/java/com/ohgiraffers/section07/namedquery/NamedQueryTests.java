package com.ohgiraffers.section07.namedquery;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

import java.util.List;

public class NamedQueryTests {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory(){ entityManagerFactory = Persistence.createEntityManagerFactory("jpatest"); }
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

    @Test // 정적쿼리
    public void 네임드쿼리를_이용한_조회_테스트(){
        List<Menu> menuList = entityManager.createNamedQuery("menu_section07.selectMenuList", Menu.class).getResultList();

        Assertions.assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    public void 동적쿼리를_이용한_조회_테스트() {

        //given
        String searchName = "한우";
        int searchCategoryCode = 4;

        //when
        StringBuilder jpql = new StringBuilder("SELECT m FROM menu_section07 m ");
        if(searchName != null && !searchName.isEmpty() && searchCategoryCode > 0) {
            jpql.append("WHERE ");
            jpql.append("m.menuName LIKE '%' || :menuName || '%' ");
            jpql.append("AND ");
            jpql.append("m.categoryCode = :categoryCode ");
        } else {
            if(searchName != null && !searchName.isEmpty()) {
                jpql.append("WHERE ");
                jpql.append("m.menuName LIKE '%' || :menuName || '%' ");
            } else if(searchCategoryCode > 0) {
                jpql.append("WHERE ");
                jpql.append("m.categoryCode = :categoryCode ");
            }
        }
        // 근데 이렇게 쓰는게 불편해서 마이바티스를 쓴다.


        TypedQuery<Menu> query = entityManager.createQuery(jpql.toString(), Menu.class);

        if(searchName != null && !searchName.isEmpty() && searchCategoryCode > 0) {
            query.setParameter("menuName", searchName);
            query.setParameter("categoryCode", searchCategoryCode);
        } else {
            if(searchName != null && !searchName.isEmpty()) {
                query.setParameter("menuName", searchName);
            } else if(searchCategoryCode > 0) {
                query.setParameter("categoryCode", searchCategoryCode);
            }
        }

        List<Menu> menuList = query.getResultList();

        //then
        Assertions.assertNotNull(menuList);
        menuList.forEach(System.out::println);

    }
}
