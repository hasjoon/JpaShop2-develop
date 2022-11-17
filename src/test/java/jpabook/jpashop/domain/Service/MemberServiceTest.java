package jpabook.jpashop.domain.Service;

//import static org.junit.Assert.*;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
//import org.junit.runner.RunWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;


    @org.junit.jupiter.api.Test // vintage 에러남 - vintage는 unit3,4 기반
//    @Test //junit4 gradle에 4로 넣어 놨으므로 에러 없이
    @DisplayName("회원가입 정상작동 확인 TEST")
//    @Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("Kim");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @org.junit.Test(expected = IllegalStateException.class)
//    @DisplayName("중복회원 예외 TEST")
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("Kim");

        Member member2 = new Member();
        member2.setName("Kim");

        //when
        memberService.join(member1);
        memberService.join(member2); //예외가 발생해야함!

        //then
        fail("예외가 발생해야 한다.");
    }

}