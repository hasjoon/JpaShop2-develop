package jpabook.jpashop.domain.repository;

import java.util.List;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {

        String jpql = "select o from Order o join o.member m";


        return em.createQuery(jpql,Order.class)
            //            .setFirstResult(100)//페이징 100부터 시작됨
            .setMaxResults(1000)//최대 1000건
            .getResultList();
    }

}
