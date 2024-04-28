package com.example.demo.annotation.crossparameter;

import com.example.demo.port.TokenInformation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

//@PermissionControl é definido no nível do método, mas as restrições devem ser aplicadas aos parâmetros do método e não ao valor de retorno do método.
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class PermissionControlValidator implements ConstraintValidator<PermissionControl, Object[]> {

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private List<PartnerDataBy> partnerDataBy;
    @Autowired
    private TokenInformation tokenInformation;

    private TypePermission typePermission;
    private String methodName;
    private Class<?> typeClass;
    private String parameterNameValidation;

    private final Logger log = LoggerFactory.getLogger(PermissionControlValidator.class);

    @Override
    public void initialize(PermissionControl constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.typePermission = constraintAnnotation.value();
        this.methodName = constraintAnnotation.methodName();
        this.typeClass = constraintAnnotation.typeClass();
        this.parameterNameValidation = constraintAnnotation.parameterNameValidation();
        log.info("method=initialize, message=typeControl {}, typeClass {}, methodName {}, parameterNameValidation {}"
                , typePermission, typeClass.getSimpleName(), methodName, parameterNameValidation);
    }

    @Override
    public boolean isValid(Object[] objects, ConstraintValidatorContext constraintValidatorContext) {
        log.info("method=isValid, message=o array de parametros do metodo anotado possui {} parametros que são {}", objects.length, Arrays.toString(objects));

        if (TypePermission.NONE.equals(this.typePermission)) {
            log.error("method=isValid, message=ao utilizar a @PermissionControl você deve informar o tipo de validação no atributo value.");
            return false;
        }

        PartnerDataBy partnerDataBy = PartnerDataBy.select(this.partnerDataBy, this.typePermission);
        PartnerData partnerData = partnerDataBy.get(getParameterToObtainPartnersCnpj(objects));

        String accessTokenCnpj = getAccessTokenCnpj();

        return isAccessPermitted(accessTokenCnpj, partnerData.integrationCnpj(), partnerData.automationCnpj());
    }

    private <T> T getParameterToObtainPartnersCnpj(Object[] objects ) {
        //verifica se o metodo informado existe na classe informada
        Optional<Method> annotatedMethod = Arrays.stream(typeClass.getMethods()).filter(method -> method.getName().equalsIgnoreCase(methodName)).findFirst();

        if (annotatedMethod.isEmpty()) {
            String errorMessage = "o método " + methodName + " não existe na classe " + typeClass.getSimpleName() + ".";
            log.error("method=isValid, message={}.", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        log.info("method=isValid, message=assinatura dos parametros do metodo[{}]", Arrays.toString(annotatedMethod.get().getParameters()));

        //varifica se o parametro informado, existe no metodo
        Optional<Parameter> methodParameter = Arrays.stream(annotatedMethod.get().getParameters())
                .filter(parameter -> parameter.getName().contains(parameterNameValidation))
                .findFirst();

        if (methodParameter.isEmpty()) {
            String errorMessage = "o método "+methodName+" da classe "+typeClass.getSimpleName()+" não possui o parâmetro "+parameterNameValidation+", conforme informado em @AccessControl.";
            log.error("method=isValid, message={}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        //obtem o indice do parametro do metodo, independente da posicao
        int indexPositionOfMethodParameter = IntStream.range(0, annotatedMethod.get().getParameters().length)
                .filter(index -> annotatedMethod.get().getParameters()[index].equals(methodParameter.get()))
                .findFirst().getAsInt();

        log.info("method=isValid, message=o parametro {} existe na posição {} do metodo {}.",parameterNameValidation, indexPositionOfMethodParameter, methodName);

        return (T) objects[indexPositionOfMethodParameter];
    }

    private String getAccessTokenCnpj() {
        String headerAuthorizationValue = httpServletRequest.getHeader("Authorization");
        String accessToken = headerAuthorizationValue.substring(7);
        String accessTokenCnpj = tokenInformation.get(accessToken);
        return accessTokenCnpj;
    }

    private Boolean isAccessPermitted(String accessTokenCnpj, String integrationCnpj, String automationCnpj) {
        log.info("method=isAccessPermitted, message=accessTokenCnpj[{}], integrationCnpj[{}], automationCnpj[{}]", accessTokenCnpj, integrationCnpj, automationCnpj);
        return accessTokenCnpj.equals(integrationCnpj) || accessTokenCnpj.equals(automationCnpj);
    }
}