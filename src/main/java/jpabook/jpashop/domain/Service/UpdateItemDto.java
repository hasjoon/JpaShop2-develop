package jpabook.jpashop.domain.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateItemDto {

    String name;
    int price;
    String stockQuantity;
}
