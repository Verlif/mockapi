package idea.verlif.mockapi.anno;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MockResult {

    /**
     * 自定义路径，有值时会跳过路径生成器生成
     */
    String path() default "";

    /**
     * 使用缓存。在第一次构建后，后续请求会直接返回第一次构建的数据。
     */
    boolean cacheable() default false;

    /**
     * 是否打印mock日志
     */
    boolean log() default false;

    /**
     * 配置名称
     */
    String config() default "";

    /**
     * 直返数据，不进行mock，直接返回参数值
     */
    String result() default "";

    /**
     * 直返数据的类型
     */
    Class<?> resultType() default String.class;

}
