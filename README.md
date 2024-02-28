# MockApi

模拟接口，能够在真实接口的基础上生成对应的模拟接口，不通过接口方法而直接返回模拟值。

例如你有一个这样的接口：

```java
@GetMapping
public BaseResult<User> getById(Integer id) {
    return null;
}
```

在访问时因为没有实际业务代码，只会返回`null`，而通过**MockApi**，则会返回如下内容：

```json
{
   "code": 201,
   "msg": "123",
   "data": {
      "userId": 681,
      "nickname": "小羊",
      "roleKey": "USER"
   }
}
```

**MockApi**不会访问您的方法代码，而是使用自己的数据生成逻辑，这对您的业务不会造成任何影响。

## 简单说明

只需要在接口上配置一个注解，即可生成此接口的**mock接口**用于返回测试数据：

- `@MockParams` - 生成入参数据模拟，访问此接口可返回接口入参随机数据。
- `@MockResult` - 生成出参数据模拟，访问此接口可返回方法返回值模拟数据。

例如前面提到的接口：

```java
@MockParams
@MockResult
@GetMapping("id")
public BaseResult<User> getById(Integer id) {
    return null;
}
```

此时访问`/id`则会访问方法而返回`null`，访问生成的`/params/id`则会返回一个随机数，访问`/mock/id`则会返回以下数据：

```json
{
   "code": 201,
   "msg": "123",
   "data": {
      "userId": 681,
      "nickname": "小羊",
      "roleKey": "USER"
   }
}
```

`MockApi`使用了**springdoc-openapi-ui**，因此你可以在[swagger-ui](http://127.0.0.1:8888/swagger-ui/index.html)页面上通过**mock**与**params**分组查看不同的模拟接口。

## 配置文件

配置文件说明请参考 [配置文件](docs/配置文件.md)

## 开发文档

需要自定义构造或是更高级的功能请参考 [开发文档](docs/开发文档.md)

## 使用

**！！！注意：目前的版本是可用性测试版本，仅适合学习和试验**

**！！！注意：目前的版本基于SpringBoot2.7.18版本开发**

1. 添加依赖

   添加**Jitpack**仓库，这里不做赘述。

2. 添加依赖

   ```xml
   <dependency>
       <groupId>com.github.Verlif</groupId>
       <artifactId>mock-api</artifactId>
       <version>${mockapi.version}</version>
   </dependency>
   ```

   **version**  [![](https://jitpack.io/v/Verlif/mock-api.svg)](https://jitpack.io/#Verlif/mock-api)

3. 添加`@EnableMockApi`注解启用

   ```java
   @EnableMockApi
   @SpringBootApplication
   public class Application extends SpringBootServletInitializer {
   
       public static void main(String[] args) {
           SpringApplication.run(Application.class, args);
       }
   }
   ```

4. 在需要**mock**的**controller类**上或是**接口方法**上添加`@MockResult`注解

   ```java
   @MockParams
   @MockResult
   @RestController
   @RequestMapping("user")
   public class UserController {
   
       @GetMapping
       public BaseResult<User> getById(Integer id) {
           return new OkResult<>(new User());
       }
   
       @GetMapping("list")
       public BaseResult<List<User>> getList() {
           return new OkResult<>(new ArrayList<>());
       }
   
       @MockResult(cacheable = true)
       @PutMapping
       public BaseResult<User> update(User user) {
           return new OkResult<>(user);
       }
   
       @DeleteMapping
       public BaseResult<String> deleteById(Integer id) {
           return new OkResult<>("OK");
       }
   
   }
   ```

5. 进行结果**mock**

访问`127.0.0.1:8080/mock/user`，则调用`getById`的**mock**方法，并返回以下结果：

```json
{
    "code": 201,
    "msg": "nk-ww",
    "data": {
        "userId": 4,
        "nickname": "小红",
        "roleKey": "VISITOR"
    }
}
```

**完成**

更多说明请参阅[使用说明](docs/使用说明.md)