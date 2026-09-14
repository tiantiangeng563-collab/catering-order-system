 catering-order-system
基于SpringBoot+MySQL的餐饮订单核销管理系统，实现订单流转、菜品库存管理，使用Redis解决并发下单超卖问题
餐饮订单核销管理系统
Java后端实习项目，聚焦业务闭环完整性与并发场景下的数据一致性。

用户浏览菜品、下单并获取唯一核销码，到店由管理员核验完成订单；超时未支付自动取消并回滚库存。

技术栈
后端
1.Spring Boot 2.7、MyBatis-Plus 3.5
2.MySQL 8.0、Redis 7.x
3.JWT（Hutool）+ 拦截器、BCrypt 密码加密

前端
 原生 HTML/CSS/JS、jQuery（AJAX）、ECharts

 部署
 云服务器、Nginx 反向代理

核心功能
用户端
 1.注册登录、按分类浏览菜品
 2.购物车下单、生成核销码
 3.我的订单（状态筛选、取消订单）

管理端
 1.分类管理、菜品管理（上下架、图片上传、限量库存）
 2.订单核销、数据统计（今日销售额、销量 TOP10）

技术优势
 限量菜并发防超卖
 库存判断写入 UPDATE 的 WHERE 条件，利用 MySQL 行锁保证原子性，通过影响行数判断成败
 Redis "decrement" 预扣库存，拦截大部分无效请求
 Redis "setIfAbsent" 做接口幂等，防止重复提交
 效果：JMeter 500 并发下无超卖，订单数 = 初始库存；相比悲观锁 FOR UPDATE，不阻塞读，读多写少场景性能更好

2. Redis 缓存与一致性
  菜品/分类列表走 Redis 缓存
  穿透：空值缓存；击穿：setIfAbsent 互斥锁；雪崩：过期时间加随机值
  写操作后主动失效缓存，先更新数据库再删缓存，避免脏数据回写

3. 订单状态机与核销幂等
  状态流转：待支付 → 待核销 → 已完成 / 已取消，集中在 Service 层管控
  核销码用 UUID 生成（不可枚举）+ 唯一索引
  核销时状态判断写入 WHERE 条件，靠影响行数杜绝重复核销

4. 工程规范
  Result 统一响应、@RestControllerAdvice 全局异常
  Validation 参数校验
  @Scheduled 定时任务取消超时订单并回滚库存

数据库设计
共 6 张表：user、admin、category、dish、orders、order_detail

设计要点
  主表与明细表分离，避免订单维度信息冗余，符合第二范式
  明细冗余 dish_name / price 快照，菜品改价后历史订单仍能还原真实信息
  金额用 DECIMAL(10,2)，避免 double 精度丢失
  不建物理外键，应用层保证关联，便于后续扩展

核心索引
orders.verify_code 唯一 | 核销定位 + 防重复核销 
orders.user_id + status  联合 | 「我的订单」按状态筛选 
orders.status + create_time  联合 | 管理端分页 + 超时订单扫描 
dish.category_id   按分类浏览（最高频） 

快速启动
1. 导入表结构
sql
CREATE DATABASE dining DEFAULT CHARSET utf8mb4
导入 sql/dining.sql
