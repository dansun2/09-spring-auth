package com.ohgiraffers.section03.projection;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Table;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    *
    * 2. 임베디드 타입 프로젝션
    *   엔티티와 거의 비슷하게 사용되며 조회의 시작점이 될 수 없다. from절에 사용 불가
    *   임베디드 타입은 영속성 컨텍스트에서 관리되지 않는다.
    *
    * 3. 스칼라 타입 프로젝션
    *   숫자, 문자, 날짜 같은 기본 데이터 타입이다.
    *   스칼라 타입은 영속성 컨텍스트에서 관리되지 읂는다.
    *
    * 4. new 명령어를 활용한 프로젝션
    *   다양한 종류의 단순 값들을 DTO로 바로 조회하는 방식으로 new 패키지명.DTO명을 쓰면 해당 DTO로 바로 반환받을 수 있다.
    *   new명령어를 사용한 클래스의 객체는 엔티티가 아니므로 영속성 컨텍스트에서 관리되지 않는다.
    * */


    // 1. 엔티티 프로젝션
    @Test
    public void 단일_엔티티_프로젝션_테스트(){
        String jpql = "SELECT m FROM menu_section03 m";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        System.out.println(menuList.get(1));
        assertNotNull(menuList);
    }

    @Test
    public void 양방향_연관관계_엔티티_프로젝션_테스트(){
        int menuCodeParameter = 3;
        String jpql = "SELECT m.categoryCode FROM bidirection_menu m WHERE m.menuCode = :menuCode";
        BiDirectionCategory categoryOfMenu = entityManager.createQuery(jpql, BiDirectionCategory.class)
                .setParameter("menuCode", menuCodeParameter).getSingleResult();
        assertNotNull(categoryOfMenu);
        System.out.println(categoryOfMenu);
        assertNotNull(categoryOfMenu.getMenuList());
        categoryOfMenu.getMenuList().forEach(System.out::println);
    }

    // 2. 임베디드 프로젝션
    @Test
    public void 임베디드_타입_프로젝션_테스트() {

        //when
        String jpql = "SELECT m.menuInfo FROM embedded_menu m";
        List<MenuInfo> menuInfoList = entityManager.createQuery(jpql, MenuInfo.class).getResultList();

        //then
        Assertions.assertNotNull(menuInfoList);
        menuInfoList.forEach(System.out::println);
    }

    // 3. 스칼라 타입 프로젝션
    @Test
    public void TypedQuery를_이용한_스칼라_타입_프로젝션_테스트(){
        String jpql = "SELECT m.categoryName FROM category_section03";

        List<String> categoryNameList = entityManager.createQuery(jpql, String.class).getResultList();

        Assertions.assertNotNull(categoryNameList);
        categoryNameList.forEach(System.out::println);
    }

}
