package xyz.anfun.customer_service.aspect;

//
//@Aspect
//@Component
//@Slf4j
public class LogAspect {
//
//    //统一切点,对com.kzj.kzj_rabbitmq.controller及其子包中所有的类的所有方法切面
//    @Pointcut("execution(public * com.bai_ye_hui.bai_ye_tong_hui.controller..*.*(..))")
//    public void Pointcut() { }
//
//    //前置通知
//    @Before("Pointcut()")
//    public void beforeMethod(JoinPoint joinPoint){
//        log.info("调用了前置通知");
//        System.out.println(joinPoint.toLongString());
//
//    }
//
//    //@After: 后置通知
//    @After("Pointcut()")
//    public void afterMethod(JoinPoint joinPoint){
//        log.info("调用了后置通知");
//        System.out.println(joinPoint.getSignature().getDeclaringType().getSimpleName() + " " + joinPoint.getSignature().getName());
//        System.out.println(SecurityUtils.getSubject().getPrincipal());
//    }
//
//    //@AfterRunning: 返回通知 rsult为返回内容
//    @AfterReturning(value="Pointcut()",returning="result")
//    public void afterReturningMethod(JoinPoint joinPoint,Object result){
//        log.info("调用了返回通知");
//    }
//
//    //@AfterThrowing: 异常通知
//    @AfterThrowing(value="Pointcut()",throwing="e")
//    public void afterReturningMethod(JoinPoint joinPoint, Exception e){
//        log.info("调用了异常通知");
////        System.out.println();
//    }
//
//    //@Around：环绕通知
//    @Around("Pointcut()")
//    public Object around(ProceedingJoinPoint pjp) throws Throwable {
//        log.info("around执行方法之前");
//        Object object = pjp.proceed();
//        log.info("around执行方法之后--返回值：" +object);
//        return object;
//    }

}
