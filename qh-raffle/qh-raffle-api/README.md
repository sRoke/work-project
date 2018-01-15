


# API 定义要求：

* java.util.Optional + jackson-modules-java8

* 分页的请求参数。
  
  参考 spring-data-rest 的 [Paging and Sorting](https://docs.spring.io/spring-data/rest/docs/2.6.6.RELEASE/reference/html/#paging-and-sorting).
  统一要求有以下参数：
    
    ```text
    @ApiParam(value = "每页多少个记录,最大100")
    @QueryParam("size")
    @DefaultValue("10")
            int size,
    
    @ApiParam(value = "页码。从0开始")
    @QueryParam("page")
    @DefaultValue("0")
            int page,
    
    @ApiParam(
            value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、 '+'、'desc'、'-'。可以传递多个 sort 参数",
            allowMultiple = true,
            example = "age,asc"
    )
    @QueryParam("sort")
            String sort,
    ```

* 分页的响应结果 统一格式为 `UniResp<Page<Xxx>>`。

* MongoDb 的全文搜索 vs. ElasticSearch 的全文搜索.

    * MongoDb 仅限文本搜索，搜索权重在创建索引时就已经指定。
    * ElasticSearch 支持复杂搜索，比如价格区间。

    如果都需要事件触发更新统计表/聚合表的话，应该只用 MongoDb 也够用。
    但看过下面文章，还是不要使用 Mongodb 的全文检索了：
    [MongoDB vs. Elasticsearch: The Quest of the Holy Performances ](https://blog.quarkslab.com/mongodb-vs-elasticsearch-the-quest-of-the-holy-performances.html)
    

* 全文检索的请求参数。 参考 ElasticSearch 的： 
    
    * [URL Search](https://www.elastic.co/guide/en/elasticsearch/reference/5.4/search-uri-request.html)
    * [Query String Query](https://www.elastic.co/guide/en/elasticsearch/reference/5.4/query-dsl-query-string-query.html#query-string-syntax)
    * [elasticsearch-js](https://github.com/elastic/elasticsearch-js)
    * [Securing Elasticsearch](https://brudtkuhl.com/securing-elasticsearch/)

   统一要求有以下参数：
   
    ```text
    @ApiParam(
            value = "查询语句"
    )
    @QueryParam("q")
            String q
    ```


* 因为 Jax-RS 注解的限制，相关注解需要全部写在 API 中。
实现类上只允许才 class 级别上写 `@PATH` 注解。
且该注解的之必须与 API 上的定义一直。
详情请参考[这里](https://stackoverflow.com/questions/25916796/inheritance-with-jax-rs).







