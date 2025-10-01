package overwitch.are_you_serious.enchanted.Util;

import overwitch.are_you_serious.enchanted.Util.EventHate.OverrideEvent;

import java.lang.reflect.Method;

public class EventOverrideChecker {
    public static void checkOverride(Object eventHandler) {
        Class<?> clazz = eventHandler.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(OverrideEvent.class)) {
                boolean isOverriding = false;

                // 遍历父类的方法，检查是否有相同方法签名
                for (Method parentMethod : clazz.getSuperclass().getDeclaredMethods()) {
                    if (isSameSignature(method, parentMethod)) {
                        isOverriding = true;
                        break;
                    }
                }

                if (!isOverriding) {
                    throw new IllegalStateException("方法 " + method.getName() + " 使用了 @OverrideEvent，但它没有覆盖父类的方法！");
                }
            }
        }
    }

    private static boolean isSameSignature(Method method1, Method method2) {
        return method1.getName().equals(method2.getName()) &&
                method1.getReturnType().equals(method2.getReturnType()) &&
                java.util.Arrays.equals(method1.getParameterTypes(), method2.getParameterTypes());
    }
}
