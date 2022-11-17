package jpabook.jpashop.domain.Service;


import javax.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class itemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTset() throws Exception {
        em.find(Book.class, 1L);

    }
}
