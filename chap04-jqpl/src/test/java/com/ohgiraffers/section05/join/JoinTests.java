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
        // 위에 문단 풀어서 쓰면
        for (Object[] objs: menuList){
            for (Object obj: objs){
                System.out.println(obj);
                System.out.println();
            }
            System.out.println();
        }
    }

    @Test
    public void 세타조인을_이용한_조회_테스트(){
        // 세타조인은 조건이 없다. 그냥 N개의 테이블과 N개의 테이블을 합침. 그래서 쿼리 개수가 n개 x n개 나옴
        // 그래서 성능이 떨어지기 때문에 잘 안쓰기도 하고 사용자에게 이렇게 쓸 일이 없다....

        // 세타 조인은 조인 되는 모든 경우의 수를 다 반환하는 크로스 조인과 같다.
        // 엔티티들에 대한 조인을 말함
        String jpql = "SELECT c.categoryName, m.menuName FROM category_section05 c, menu_section05 m";

        List<Object[]> categoryList = entityManager.createQuery(jpql, Object[].class).getResultList();

        Assertions.assertNotNull(categoryList);

        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.println(col + " "));
            System.out.println();
        });

        for (Object[] objs: categoryList){
            for (Object obj: objs){
                System.out.println(obj);
                System.out.println();
            }
            System.out.println();
        }
    }

    @Test
    public void 페치조인을_이용한_조회_테스트(){
        // 페치 조인을 하면 처음 sql 실행 후 로딩할 때 조인 결과를 다 조회한 뒤에 사용하는 방식이기 때문에 쿼리 실행 횟수가 줄어들게 된다.
        // 대부분의 경우 성능이 향상된다. ->항상 그런게 아님... 먼저 조인이 진짜 필요한지에 대한 고민부터 해야됨
        String jpql = "SELECT m FROM menu_section05 m JOIN FETCH m.categoryCode c";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        Assertions.assertNotNull(menuList);
        menuList.forEach(System.out::println);

        for (Menu m: menuList){
            System.out.println(m);
        }
    }
}
