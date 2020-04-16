/**
 * https://mp.weixin.qq.com/s/qde6zvqbE84gDcw_zEo62g
 *
 * 注解处理工具apt(Annotation Processing Tool)，这是Sun为了
 * 帮助注解的处理过程而提供的工具，apt被设计为操作Java源文件，
 * 而不是编译后的类。
 *
 * 它是javac的一个工具，中文意思为编译时注解处理器。APT可以用来
 * 在编译时扫描和处理注解。通过APT可以获取到注解和被注解对象的相
 * 关信息，在拿到这些信息后我们可以根据需求来自动的生成一些代码，
 * 省去了手动编写。注意，获取注解及生成代码都是在代码编译时候完成
 * 的，相比反射在运行时处理注解大大提高了程序性能。APT的核心是
 * AbstractProcessor类。
 *
 * 正常情况下使用APT工具只是能够生成一些文件(不仅仅是我们想象的
 * class文件，还包括xml文件等等之类的)，并不能修改原有的文件信息。
 *
 * 但是此时估计会有疑问，那么Lombok不就是在我们原有的文件中新增
 * 了一些信息吗？我在后面会有详细的解释，这里简单介绍一下，其实
 * Lombok是修改了Java中的**抽象语法树AST**才做到了修改其原有类的信息。
 *
 *
 *
 * 在TestAno上加@MyGetter注解在编译期就能生成文件了，接下来演示一下
 * 在编译时生成文件，此时不要着急直接进行javac编译，MyGetter类是注解
 * 类没错，而MyGetterProcessor是注解类的处理器，那么我们在编译TestAno
 * Java的时候就会触发处理器。因此这两个类是无法一起编译的。
 *
 * 先将注解类和注解处理器类进行编译
 * javac com/wen/apt/MyGetter*
 *
 * 接下来进行编译我们的测试类，此时在编译时需要加上processor参数，
 * 用来指定相关的注解处理类。就会触发MyGetterProcessor中processor方法
 * javac -processor com.wen.apt.MyGetterProcessor com/wen/apt/TestAno.Java
 *
 */
package com.wen.apt;