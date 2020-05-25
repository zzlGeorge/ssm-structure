# ssm-structure
SSM基础框架及相关组件扩展（学习用）

### 技术栈
#### 基本框架
- Spring、Spring MVC、MyBatis
- Alibaba Druid
- Dubbo

#### 中间件
- Redis
- MySQL
- Zookeeper
- RocketMQ

## 整合常用功能
整合Thymeleaf模板引擎制作的基于MyBatis的逆向代码生成（从前端到后端代码）

## 经典业务场景
- 秒杀下单
- 分布式锁
- 分布式事务

## 大事件更新记录
- 统一tomcat启动模块(ssm-demo)：新增的模块工程，配置好pom直接移动到ssm-demo里跑tomcat
使用方法：
    1. 以ssm-user模块为例，首先将user模块所有资源文件放在user-web子模块中，注意资源路径名；
    2. 其次在ssm-demo模块中pom.xml引入user-web模块依赖，并在构建节点引入user-web的资源：
    ```xml
    <!-- 引入user-web模块配置 -->
    <artifactItem>
        <groupId>com.vct</groupId>
        <artifactId>user-web</artifactId>
        <version>1.0.0</version>
        <type>jar</type>
        <overWrite>true</overWrite>
        <outputDirectory>${project.build.directory}/classes/user</outputDirectory>
        <includes>**/prop/*.properties,**/spring/*.xml,**/mybatis/*.xml</includes>
    </artifactItem>
    ```
    3. 注意每次更新user-web资源文件需要重新maven install，再maven clean以及maven install ssm-demo模块，以成功引入user-web资源。
    切换其他模块本地运行操作也是一致的。

## 持续更新中...
