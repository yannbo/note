## Spring、Spring MVC、Spring boot 的区别
1. Spring是一个开发框架
2. Spring MVC是基于Spring实现的MVC框架
3. Spring boot 事基于spring4条件注册的一套整合开发包
	- Spring boot的特色是**自动配置**.
	- 工作原理：
	    + 自动注入通过 `@EnableAutoConfiguration` 注解实现
	    + 该注解通过子注解`@Import(AutoConfigurationImportSelector.class)`来实现
	    + `AutoConfigurationImportSelector.class` 根据条件来选择需要导入的配置类
	    + 配置类中声明了各种**条件注解**，当条件满足时自动注入配置类Bean
	- 对比Spring 4: 
	    + 在Spring4中如果要注入一个bean需要添加注解配置或者Java配置，
	    + 而在Spring boot中只要引入spring boot相关的start pom.xml文件，spring boot会自动扫描相关相关jar包，
	    + Spring boot根据路径下的spring.factories文件(此文件是一个配置文件)，定义需要引入的配置文件(Java配置文件)，
	    + 配置文件(`@Configuration`)中定义了bean自动注入需要满足的条件，当条件满足时，bean会被自动注入，而不需要像Spring 4 手动去为需要注入的bean加注解配置和java配置）