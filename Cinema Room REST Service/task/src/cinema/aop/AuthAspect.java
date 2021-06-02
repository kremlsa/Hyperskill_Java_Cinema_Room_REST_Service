package cinema.aop;

import cinema.exception_handling.CinemaExceptions;
import cinema.exception_handling.UnauthorizedError;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AuthAspect {

    private final String PASSWORD = "super_secret";

    @Before("@annotation(cinema.aop.Authenticate) && execution(public * *(..))")
    public void checkAuth(final JoinPoint joinPoint) throws UnauthorizedError {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Authenticate annotation = signature.getMethod().getAnnotation(Authenticate.class);
        Object[] arguments = joinPoint.getArgs();
        String password = "";
        for (Object obj : arguments) {
            if (obj instanceof String) {
                password = (String) obj;
            }
        }
        if (annotation.isAuth() && !password.equals(PASSWORD)) {
            throw new UnauthorizedError("The password is wrong!");
        }
    }
}