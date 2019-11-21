## Spring、Spring MVC、Spring boot 的区别
1. Spring是一个开发框架
2. Spring MVC是基于Spring实现的MVC框架
3. Spring boot 是基于spring4条件注册的一套整合开发包，整合框架，不是一个技术框架。
	- Spring boot的特色是**自动配置**.
	- Spring boot = Spring4 + 自动配置
	- 工作原理：
	    + 自动注入通过 `@EnableAutoConfiguration` 注解实现
	    + 该注解通过子注解`@Import(AutoConfigurationImportSelector.class)`来实现
	    + `AutoConfigurationImportSelector.class` 根据条件来选择需要导入的配置类
	    + 配置类中声明了各种**条件注解**，当条件满足时自动注入配置类Bean
	- 对比Spring 4: 
	    + Spring boot的技术实现是基于Spring4，没有新的技术实现。
	    + 在Spring4中如果要注入一个bean需要添加注解配置或者Java配置，
	    + 而在Spring boot中只要引入spring boot相关的start pom.xml文件，spring boot会自动扫描相关相关jar包，
	    + Spring boot根据路径下的spring.factories文件(此文件是一个配置文件)，定义需要引入的配置文件(Java配置文件)，
	    + 配置文件(`@Configuration`)中定义了bean自动注入需要满足的条件，当条件满足时，bean会被自动注入，而不需要像Spring 4 手动去为需要注入的bean加注解配置和java配置）
	- 对功能的支持(如支持MongoDB), Spring boot和Spring4 的**做法有什么不同**？
	    + 做法的不同：
	        - Spring4 需要**手动注入/配置**.
	            + 手动注入(注解配置/Java配置)功能需要的相关Bean(MongoTemplate)
	            + 添加`@EnableMongoRepositories`到 Java配置文件。
	        - Spring boot只需要**引入相应的starter pom文件,<u>无需任何配置</u>**.
	            + 通过自动配置了注入了MongoTemplate，自动配置了不用要开发者去注入。
	            + 自动配置了`@EnableMongoRepositories`.
	    + Spring boot没有通过新的技术实现来支持MongoDB。
	    
## Spring boot 工作原理
1. `@SpringBootApplication`
    - 是Spring boot定义的注解
    - 是一个组合注解(`@Configuration`,`@EnableAutoConfiguration`, `@ComponentScan`)
        + `@Configuration`  Spring 注解
        + `@ComponentScan`  Spring注解
        + `@EnableAutoConfiguration` Spring boot注解
            ```
            ...
            @Import({AutoConfigurationImportSelector.class}) // 根据条件来选择需要导入的配置类
            public @interface EnableAutoConfiguration {
            ...
            ```
        