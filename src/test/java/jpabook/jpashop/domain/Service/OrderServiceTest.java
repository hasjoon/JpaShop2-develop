package jpabook.jpashop.domain.Service;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.ItemRepository;
import jpabook.jpashop.domain.repository.MemberRepository;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
//테스트당 끝나면 롤백을 해준다
public class OrderServiceTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    EntityManager em;


    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = creatMember("Kim1", new Address("서울", "도봉구", "1111"));

        Book book = creatBook("서울 JPA", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격 = 가격 * 수량 이다.", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야한다.", 8, book.getStockQuantity());

    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = creatMember("Kim1", new Address("서울", "도봉구", "1111"));
        Book book = creatBook("서울 JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order order = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCLE 이다.", OrderStatus.CANCEL, order.getStatus());
        assertEquals("상품 수량 복구 확인", 10, book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
//    @DisplayName("주문수량초과 TEST")
    public void 주문수량초과() throws Exception {
        //given
        Item item = new Item();
        item.setStockQuantity(3);
        //when
        OrderItem.creatOrderItem(item, 1000, 4);

        //then
        fail("need more stock");
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = creatMember("kim2", new Address("인천", "계양구", "123-123"));
        Item item = creatBook("시골 JPA", 10000, 10);

        int orderCount = 11;
        //when
        orderService.order(member.getId(),item.getId(),orderCount);


        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }





    private Book creatBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member creatMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }
}