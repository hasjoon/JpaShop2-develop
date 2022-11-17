package jpabook.jpashop.domain.Service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.ItemRepository;
import jpabook.jpashop.domain.repository.MemberRepository;
import jpabook.jpashop.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.creatOrderItem(item, item.getPrice(), count);

//        어떤건 creatOrderIteam으로 관리하고 어떤건 아래와 같이 set으로 관리하면 퍼짐
//        해결법 = order에 들어가서 @NoArgsConstructor(access = Accesslevel.PROTECTED)로 잡아주면
//        orderItem이 protected라서 초기화해줄 수 없음
//        OrderItem orderItem1 = new OrderItem();
//        orderItem.setOrder();

        //주문 생성
        Order order = Order.creatOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 삭제
        order.cancel();

    }

    //검색
//    public List<Order> findOrders(OrderSaerch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }
}
