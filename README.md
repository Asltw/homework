# homework
#### homework
在做数据集成的时候，需要读取第三方的数据并集成到本地的数据库中

第三方数据商为我们提供的是一个时间序列的数据，文件格式是csv，数据格式如下：

股票代码 | 数据项1 | 数据项2 | 数据项3
---|---|---|---|
000001 | 0.01 | 1.02 | -1.01 
000002 | 0.03 | 0.22 | 0.01
00000x | 0.11 | 2.11 | 0.21


文件名称是 tradingDate.csv，例如 2017-09-19.csv。
请将上述数据商提供的数据写入到本地数据库中，本地数据库的表结构如下:

CREATE TABLE  time_series_data (

    item_id uuid,

    trading_date date,

    stock_code text,

    item_value double,

    PRIMARY KEY (item_id, trading_date, stock_code)

)
#### 开发工具：

```
MySQL：数据库
Git：版本管理
IntelliJ IDE：开发IDE
Navicat for MySQL：数据库客户端
```

#### 开发环境：

```
JDK8+
MySQL5.7+
```

#### 后端技术：

```
SpirngBoot
Druid
lombok
jooq
gson
joda-tim
junit
```
#### 最终测试类(FinalTest)
> 测试方法：testFinally()
