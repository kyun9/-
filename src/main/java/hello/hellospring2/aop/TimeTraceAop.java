package hello.hellospring2.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
// Component스캔으로 하는 것보다 AOP를 사용한다고 SpringConfig 파일에 Bean으로 명시해주는게 좋다.
@Aspect
public class TimeTraceAop {
	
	// * hello.hellospring2.service..*(..) 
	// hellospring2 하위에는 다 적용해
	@Around("execution(* hello.hellospring2..*(..))")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		System.out.println("START: " + joinPoint.toString());
	
		try {
			return joinPoint.proceed();
		} finally {
			long finish = System.currentTimeMillis();
			long timeMs = finish - start;
			
			System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
		}
	}
	
}