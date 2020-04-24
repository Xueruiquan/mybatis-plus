## springboot & mybatis plus 

#### 代码地址

- https://github.com/Xueruiquan/mybatis-plus

#### 简介

- 官网地址：https://mp.baomidou.com/guide/

#### 使用

- 根据**代码配置**进行配置
- 运行：**代码配置**中的 **MysqlGenerator** 代码生成器（自动生成**bean.java**、**mapper.xml**、**mapper.java**、**service.java**、**serviceImpl.java**）。使用时，**注入serviceImpl**对象即可使用。简单示例如：**代码配置-测试**

#### 代码配置

- pom.xml

  mybatis plus 使用的依赖：mybatis-plus 启动器；mybatis-plus 代码生成器依赖；代码生成器模板

```xml
<!-- mybatis plus 使用的依赖：mybatis-plus 启动器；mybatis-plus 代码生成器依赖；代码生成器模板  -->
<!-- fastjson -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.36</version>
</dependency>
<!-- lombox -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
<!-- mysql -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
<!-- druid -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.10</version>
</dependency>
<!-- mybatis-plus 启动器 -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.1.0</version>
</dependency>
<!-- mybatis-plus 代码生成器依赖 -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>3.1.0</version>
</dependency>
<!-- 代码生成器模板 -->
<dependency>
    <groupId>org.freemarker</groupId>
    <artifactId>freemarker</artifactId>
</dependency>
```

- application.yml

  官网配置地址：https://mp.baomidou.com/config/ 

```yml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
    url: jdbc:mysql://localhost:3306/mybatis-plus?characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=UTC
    username: root
    password: root

# https://mp.baomidou.com/config/ 官网配置解析
mybatis-plus:
  global-config:
    db-config:
      id-type: auto
      field-strategy: not_empty
      table-underline: true
      db-type: mysql
      logic-delete-value: 1 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
  mapper-locations: classpath:/mapper/*.xml
```

- MyBatisPlusConfig 插件配置

```java
@Configuration
@Slf4j
public class MyBatisPlusConfig {
    /**
     * 配置分页插件
     * @return com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        log.info("注册分页插件");
        return new PaginationInterceptor();
    }

    /**
     * SQL执行效率插件
     * @return com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor
     */
    @Bean
    @Profile({"dev"}) // 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor(){
        return new PerformanceInterceptor();
    }

    /**
     * 逻辑删除用，3.1.1之后的版本可不需要配置该bean，但项目这里用的是3.1.0的
     * @return com.baomidou.mybatisplus.core.injector.ISqlInjector
     */
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }
}
```

- MysqlGenerator 代码生成器

```java
public class MysqlGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");

        // TODO 设置用户名
        gc.setAuthor("xue rui quan");
        gc.setOpen(true);
        // service 命名方式
        gc.setServiceName("%sService");
        // service impl 命名方式
        gc.setServiceImplName("%sServiceImpl");
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);
        mpg.setGlobalConfig(gc);

        // TODO 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/mybatis-plus?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // TODO 包配置
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName(scanner("模块名"));
        pc.setParent("com.huiyuanai.mybatisplus");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        mpg.setPackageInfo(pc);

        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        //如 每张表都有一个创建时间、修改时间
        //而且这基本上就是通用的了，新增时，创建时间和修改时间同时修改
        //修改时，修改时间会修改，
        //虽然像Mysql数据库有自动更新几只，但像ORACLE的数据库就没有了，
        //使用公共字段填充功能，就可以实现，自动按场景更新了。
        //如下是配置
        //TableFill createField = new TableFill("gmt_create", FieldFill.INSERT);
        //TableFill modifiedField = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        //tableFillList.add(createField);
        //tableFillList.add(modifiedField);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        // 设置逻辑删除键
        strategy.setLogicDeleteFieldName("deleted");
        // TODO 指定生成的bean的数据库表名
        strategy.setInclude("user");
//        strategy.setInclude("manager");
        //strategy.setSuperEntityColumns("id");
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
```

- 主启动类

```java
@SpringBootApplication
@MapperScan("com.huiyuanai.mybatisplus.mapper") // 扫描数据访问接口
public class MybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
        System.out.println("MybatisPlusApplication 启动成功，｡:.ﾟヽ(｡◕‿◕｡)ﾉﾟ.:｡+ﾟ");
    }

}
```

- 建表sql

```sql
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `address` varchar(64) DEFAULT NULL,
  `openid` varchar(32) DEFAULT NULL,
  `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(3) unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
```

- 测试

```java
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping(value = "/test")
    public String test(){
        User user = new User();
        user.setId(Long.valueOf(1));
        user.setOpenid("openid");
        user.setUsername("灰原哀");
        user.setAddress("名侦探柯南");
        List<User> list = new ArrayList<>();
        for (int i = 0; i <3 ; i++) {
            list.add(user);
        }

        // 新增
//        boolean flag = userService.save(user);
//        log.info("新增结果：" + flag);

        // 批量新增
//        boolean flag = userService.saveBatch(list);
//        log.info("新增信息：" + flag);

        // 批量新增 第二个参数：分批处理时，一次性处理的数据条数
//        boolean flag = userService.saveBatch(list,2);
//        log.info("新增信息：" + flag);

        // 新增或者更新 也有批处理，与新增一样
//        boolean flag = userService.saveOrUpdate(user);
//        log.info("新增或者更新：" + flag);

        // 通过主键id查询
//        User user1 = userService.getById("1");
//        log.info("通过主键id查询结果：" + user1);

        // 批量查询 （查询出列表全部信息）
//        List<User> list = userService.list();
//        log.info("批量查询结果：" + list);

        // 条件查询 select * from user where id = '1' and username = 'sherry'
//        User user2 = userService.getOne(new QueryWrapper<User>().eq("id", "1").eq("username","sherry"));
//        log.info("条件查询结果：" + user2);

        // 分页查询
//        int pageNum = 1;
//        int pageSize = 3;
//        IPage<User> userIPage = userService.page(new Page<>(pageNum,pageSize), new QueryWrapper<User>().eq("username","灰原哀"));
//        List<User> list = userIPage.getRecords(); // 数据库查询记录
//        log.info("数据库查询记录结果：" + list);
//        long allPageNum = userIPage.getPages(); // 总页数
//        log.info("总页数：" + allPageNum);

        // 根据id 删除 实现逻辑删除，数据库中的记录不真的删除，只修改 逻辑删除键设定的字段 的状态。
//        boolean flag = userService.removeById(2);
//        log.info("删除信息：" + flag);

        // 根据条件 删除 实现逻辑删除，数据库中的记录不真的删除，只修改 逻辑删除键设定的字段 的状态。
        boolean flag = userService.remove(new QueryWrapper<User>().eq("id",3));
        log.info("删除信息：" + flag);

        return "测试结束";
    }
}
```

