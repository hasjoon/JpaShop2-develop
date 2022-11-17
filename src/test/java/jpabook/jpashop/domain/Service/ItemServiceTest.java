package jpabook.jpashop.domain.Service;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;
    @Autowired
    EntityManager em;

   @Test
   @DisplayName("수량확인 TEST")
   void 수량확인() throws Exception{
     //given
       Item item = new Item();
       item.setStockQuantity(3);

     //when
       item.addStock(3);

     //then
       em.flush();
       assertEquals(item.getStockQuantity(),6);
   }
}