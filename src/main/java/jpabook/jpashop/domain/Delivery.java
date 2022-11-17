package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Delivery {

    @Id@GeneratedValue
    @Column(name = "delivery_id")

    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // EnumType.ordinary면 컬럼이 1234 숫자로 들어감 ex ready면 1 camp는2 중간에 다른 상태가 생기면 망하는것 = 절대 쓰면 안됨 EnumType.STRING 쓰자
    private DeliveryStatus status; //READY, CAMP
    //enum 조심해야할것
}


