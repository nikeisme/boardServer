package com.board.boardserver.aop;

import com.board.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE) // AOP 순서 지정
@Log4j2
public class LoginCheckAspect {

    // LoginCheck 동작을 위한 어노테이션
    @Around("@annotation(com.board.boardserver.aop.LoginCheck) && @ annotation(loginCheck)")
    // Aop 기능 중 하나 - proceedingJoinPoint : 시점 지정한 것을 한번에 모아서 controller 로 전달
    // 어노테이션이 만들어준 loginCheck
    public Object adminLoginCheck(ProceedingJoinPoint proceedingJoinPoint, LoginCheck loginCheck) throws Throwable {

        // 세션 생성
        HttpSession session = (HttpSession) ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getSession();

        String id = null;

        // 몇번째 argument에 저장할 것인지 index 설정
        int idIndex = 0;

        // loginCheck 인터페이스에서 userType
        String userType = loginCheck.type().toString();

        switch (userType) {
            case "ADMIN": { // 아이디가 admin일떄
                // 세션 로그인 정보
                id = SessionUtil.getLoginAdminId(session);
                break;
            }

            case "USER": {// 아이디가 일반 사용자일때
                id = SessionUtil.getLoginMemberId(session);
                break;
            }
        }

        if (id == null) {
            log.debug(proceedingJoinPoint.toString() + "accountName :" + id);
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인한 ID 값을 확인해주세요.") {};
        }

        Object[] modefiedArgs = proceedingJoinPoint.getArgs();

        if (proceedingJoinPoint != null)
            modefiedArgs[idIndex] = id;

            // controller 에 최종적으로 전달
            return proceedingJoinPoint.proceed(modefiedArgs);
        }
}
