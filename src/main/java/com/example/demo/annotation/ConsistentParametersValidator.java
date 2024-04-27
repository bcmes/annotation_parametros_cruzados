package com.example.demo.annotation;

import com.example.demo.controller.AnnotationController;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

//A razão para esta anotação é porque @MyAnnotation é definido no nível do método, mas as restrições devem ser aplicadas aos parâmetros do método
// e não ao valor de retorno do método.
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ConsistentParametersValidator implements ConstraintValidator<ConsistentParameters, Object[]> {

    private final Logger log = LoggerFactory.getLogger(ConsistentParametersValidator.class);

    private Type type;

    @Override
    public void initialize(ConsistentParameters constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        log.info("passou pelo initialize." );
        type = constraintAnnotation.value();
//        log.info("no initialize = {}", Arrays.toString(constraintAnnotation.annotationType().getAnnotations())); retorna as anotacoes da interface annotation
    }

    @Override
    public boolean isValid(Object[] objects, ConstraintValidatorContext constraintValidatorContext) {
        log.info("passou pelo metodo isValid com {} parametros => {}", objects.length, Arrays.toString(objects));
        log.info("passou pelo metodo isValid com type {}", type);

//        log.info("=>{}", Arrays.toString(AnnotationController.class.getMethods()));
//        log.info("=>{}", Arrays.stream(AnnotationController.class.getMethods()).filter(
//                methodName -> methodName.getName().equalsIgnoreCase("myController1")).collect(Collectors.toSet())
//        );

        Set<Method> myController1 = Arrays.stream(AnnotationController.class.getMethods()).filter(
                methodName -> methodName.getName().equalsIgnoreCase("myController1")).collect(Collectors.toSet());

        return true;
    }
}
//ParameterNameProvider
/*
Parametros:
- Tipo da validacao = ACCOUNT_ID, CONCILIATION_ID, ONBOARDING, NONE
    - quando o campo não é um tipo reference, a propriedade a ser usada na validação vai ser anotada com @CheckPermission
- classe do controller = AnnotationController.class
- nome do metodo = myController1
 */

//// Método auxiliar para encontrar o nome do método que possui a anotação desejada
//private String findAnnotatedMethod() {
//    // Obter a pilha de chamadas (stack trace)
//    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//    // Iterar sobre a pilha de chamadas para encontrar o método que possui a anotação desejada
//    for (StackTraceElement stackTraceElement : stackTrace) {
//        try {
//            // Obter a classe associada ao elemento da pilha
//            Class<?> clazz = Class.forName(stackTraceElement.getClassName());
//            // Obter o método associado ao elemento da pilha
//            Method method = clazz.getMethod(stackTraceElement.getMethodName());
//            // Verificar se o método possui a anotação desejada
//            if (method.isAnnotationPresent(MyAnnotation.class)) {
//                return method.getName();
//            }
//        } catch (ClassNotFoundException | NoSuchMethodException e) {
//            // Lidar com exceções, se necessário
//        }
//    }
//    return null; // Se não encontrarmos o nome do método, retornamos null
//}