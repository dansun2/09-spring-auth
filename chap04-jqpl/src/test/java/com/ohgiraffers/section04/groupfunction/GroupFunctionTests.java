package com.ohgiraffers.section04.groupfunction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GroupFunctionTests {

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
    * JPQL의 그룹함수는 COUNT, MAX, MIN, SUM, AVG로 SQL의 그룹함수와 별반 차이가 없다.
    * 단, 몇가지 주의사항이 있다.
    * 1. 그룹함수의 반환 타입은 결과 값이 정수면 Long, 실수면 Double로 반환된다.
    * 2. 값이 없는 상태에서는 count를 제외한 그룹 함수는 null이 되고, count만 0이 된다.
    *   따라서 반환 값을 담기 위해 선언하는 변수 타입을 기본자료형으로 하게 되면, 조회 결과를 언박싱 할 때 npe가 발생한다.
    * 3. 그룹 함수의 반환 자료형은 Long or Double형이기 떄문에 having절에서 그룹 함수 결과값을 비교하기 위한
    *   파라미터 타입은 Long or Double로 해야한다.
    * */

    @Test
    public void 특정_카테고리의_등록된_메뉴_수_조회(){
        int categoryCodeParam = 4;
        String jpql = "SELECT COUNT(m.menuPrice) FROM menu_section04 m WHERE m.categoryCode = :categoryCode";
        long countOfMenu = entityManager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categoryCodeParam)
                .getSingleResult();
        Assertions.assertTrue(countOfMenu >= 0);
        System.out.println(countOfMenu);
    }

    @Test
    public void count를_제외한_다른_그룹함수의_조회결과가_없는_경우_테스트() {

        //given
        int categoryCodeParameter = 2;

        //when
        String jpql = "SELECT SUM(m.menuPrice) FROM menu_section04 m WHERE m.categoryCode = :categoryCode";
        long sum = entityManager.createQuery(jpql, Long.class).setParameter("categoryCode", categoryCodeParameter).getSingleResult();
        System.out.println(sum);

        //then
        Assertions.assertThrows(NullPointerException.class, () -> {
            /* 반환 값을 담을 변수의 타입을 기본 자료형으로 하는 경우 Wrapper 타입을 언박싱 하는 과정에서 NPE이 발생하게 된다. */
            long sumOfPrice = entityManager.createQuery(jpql, Long.class)
                    .setParameter("categoryCode", categoryCodeParameter)
                    .getSingleResult();
        });

        Assertions.assertDoesNotThrow(() -> {
            /* 반환 값을 담는 변수를 Wrapper 타입으로 선언해야 null 값이 반환 되어도 NPE가 발생하지 않는다. */
            Long sumOfPrice = entityManager.createQuery(jpql, Long.class)
                    .setParameter("categoryCode", categoryCodeParameter)
                    .getSingleResult();
        });


    }
}
