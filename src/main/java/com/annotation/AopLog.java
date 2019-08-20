package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Xiaok
 * @Date: 2019/8/20 14:46
 * @version: 1.0
 * @Description:
 */

/**@Target(ElementType.TYPE)
 * 1.ElementType.CONSTRUCTOR:用于描述构造器
 * 2.ElementType.FIELD:用于描述域（类的成员变量）
 * 3.ElementType.LOCAL_VARIABLE:用于描述局部变量（方法内部变量）
 * 4.ElementType.METHOD:用于描述方法
 * 5.ElementType.PACKAGE:用于描述包
 * 6.ElementType.PARAMETER:用于描述参数
 * 7.ElementType.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 */

/**@Retention(RetentionPolicy.RUNTIME)
 * 1.RetentionPoicy.SOURCE:在源文件中有效（即源文件保留）
 * 2.RetentionPoicy.CLASS:在class文件中有效（即class保留）
 * 3.RetentionPoicy.RUNTIME:在运行时有效（即运行时保留）
 */

/**
 * @Documented：它是一个标记注解，即没有成员的注解，用于描述其它类型的annotation应该被作为被标注的程序成员的公共API，
 *  因此可以被例如javadoc此类的工具文档化
 *
 *  @Inherited：它也是一个标记注解，它的作用是，被它标注的类型是可被继承的，比如一个class被@Inherited标记，那么一个子类继承该class后，
 *  则这个annotation将被用于该class的子类
 *  注意：一个类型被@Inherited修饰后，类并不从它所实现的接口继承annotation，方法并不从它所重载的方法继承annotation
 */

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AopLog {

    String name() default "";

    String value() default "";

    AopLogType type() default AopLogType.GET;
}
