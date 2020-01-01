使用原生JDBC方式对数据库进行操作，包括六个步骤：

1.加载JDBC驱动程序

    在连接数据库之前，首先要加载想要连接的数据库的驱动到JVM。可以通过java.lang.Class类的静态方法forName(String className)实现，成功加载后，会将Driver类的实例注册到DriverManager类中。

    代码如下：

Class.forName("com.mysql.jdbc.Driver");
    Class.forName()方法内部实现包括两部分：类加载、类初始化。

    1）类加载

     当使用一个类的时候（比如 new 一个类的实例），会检查此类是否被加载到内存，如果没有，则会执行加载操作。读取类对应的 class 文件数据，解析此数据，构造一个此类对应的 Class 类的实例。类加载好了以后，JVM就可以使用该类了，比如实例化此类，或者调用此类的静态方法。

     Java 也提供了手动加载类的接口，class.forName()方法就是其中之一。

    2）类初始化

    类被加载之后，jvm 已经获得了一个描述类结构的 Class 实例。但是还需要进行类初始化操作之后才能正常使用此类，类初始化操作就是执行一遍类的静态语句，包括静态变量的声明还有静态代码块。

    MySQL 的 Driver 类中，在静态代码块中，就通过 JDBC 的 DriverManager 注册了驱动。代码如下：

复制代码
public class Driver extends NonRegisteringDriver implements java.sql.Driver {
  
static {
    try {
        java.sql.DriverManager.registerDriver(new Driver());
    } catch (SQLException E) {
        throw new RuntimeException("Can't register driver!");
    }
}
  
    public Driver() throws SQLException {
    // Required for Class.forName().newInstance()
    }
}
复制代码
 

2.创建数据库的连接

JDBC连接的URL书写形式：协议：子协议：数据源标识 。

其中：

     协议：在JDBC中总是以jdbc开始 。子协议：是桥连接的驱动程序或是数据库管理系统名称。  数据源标识：标记找到数据库来源的地址与连接端口。

String URL = "jdbc:mysql://127.0.0.1:3306/mybatis?characterEncoding=utf-8";
String USER = "root";
String PASSWORD = "123456";
创建数据库连接的代码：

Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
 

3.创建Statement

要执行SQL语句，必须获得java.sql.Statement实例，Statement实例分为以下3 种类型：

    执行静态SQL语句。通常通过Statement实例实现。
    执行动态SQL语句。通常通过PreparedStatement实例实现。
    执行数据库存储过程。通常通过CallableStatement实例实现。
代码如下：

String name = "中国";
//预编译
String sql = "select * from country where countryname=?";
PreparedStatement statement = conn.prepareStatement(sql);
statement.setString(1, name);
 

4.执行SQL语句

Statement接口提供了三种执行SQL语句的方法：executeQuery 、executeUpdate   和execute  

    ResultSet executeQuery(String sqlString)：执行查询数据库的SQL语句   ，返回一个结果集（ResultSet）对象。
    int executeUpdate(String sqlString)：用于执行INSERT、UPDATE或   DELETE语句以及SQL DDL语句，如：CREATE TABLE和DROP TABLE等  
    execute(sqlString):用于执行返回多个结果集、多个更新计数或二者组合的   语句。
代码如下：

ResultSet rs = statement.executeQuery();
 

5.处理结果 

两种情况：  

    执行更新返回的是本次操作影响到的记录数。  
    执行查询返回的结果是一个ResultSet对象。  
     ResultSet包含符合SQL语句中条件的所有行，并且它通过一套get方法提供了对这些行中数据的访问。

    使用结果集（ResultSet）对象的访问方法获取数据，代码如下：

while (rs.next()) {
    logger.info(rs.getString("countryname") + " " + rs.getString("countrycode"));
}
 

6.关闭JDBC对象

操作完成以后要把所有使用的JDBC对象全都关闭，以释放JDBC资源，关闭顺序和声明顺序相反：

    关闭记录集
    关闭声明  
    关闭连接对象
代码如下：

rs.close();
statement.close();
conn.close();
 
