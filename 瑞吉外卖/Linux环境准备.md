# Linux环境准备

## 1 软件安装

### 1.1 安装方式介绍

- 二进制发布包安装
  - 软件已经针对具体平台编译打包发布，只要解压，修改配置即可
- rpm安装
  - 软件按照redhat的包管理规范进行打包，使用rpm命令进行安装，不能自行解决库依赖问题
- yum安装
  - 一种在线软件安装方式，本质上还是rpm安装，自动下载安装包并安装，安装过程中自动解决库依赖问题
- 源码编译安装
  - 软件以源码工程的形式发布，需要自己编译打包



### 1.2   jdk安装

操作步骤：

1. 使用上传工具将jdk的二进制发布包上传到Linux

2. 解压安装包

   ```shell
   tar -zxvf jdk-8u171-linux-x64.tar.gz -C /usr/local
   ```

3. 配置环境变量，使用vim命令修改/etc/profile文件，在文件末尾加入如下配置：

   ```shell
   JAVA_HOME=/usr/local/jdk1.8.0_171
   PATH=$JAVA_HOME/bin:$PATH
   ```

4. 重新加载profile文件，使更改的配置立即生效

   ```shel
   source /etc/profile
   ```

5. 检查安装是否成功

   ```shel
   java -version
   ```



### 1.3 Tomcat安装

1. 使用上传工具将Tomcat的二进制发布包上传到Linux
2. 解压安装包到/usr/local
3. 进入Tomcat的bin目录启动服务，命令为sh startup.sh或者./startup.sh

**验证服务是否启动成功**

- 查看启动日志
  - more /usr/local/apache-tomcat-7.0.57/logs/catalina.out
  - tail -50 /usr/local/apache-tomcat-7.0.57/logs/catalina.out
- 查看进程
  - ps -ef | grep tomcat
- 注意：
  - ps命令是linux下非常强大的进程查看命令，通过ps -ef 可以查看当前运行的所有进程的详细信息
  - | 在Linux中称为管道符，可以将前一个命令的结果输出给后一个命令作为输入
  - 使用ps命令查看进程时，经常配合管道符合查找命令grep一起使用，来查看特定进程

**停止Tomcat**

- 运行Tomcat的bin目录中提供的停止服务的脚本文件
  - sh shutdown.sh
  - ./shutdown.sh
- 结束Tomcat进程
  - 查看Tomcat进程，获得进程id
  - 执行命令结束进程 kill -9 -id  (-9 表示强制结束)

### 1.4 防火墙设置

防火墙操作：

- 查看防火墙状态 systemctl status firewalld 或 firewall-cmd --state
- 暂时关闭防火墙 systemctl stop firewalld
- 永久关闭防火墙 systemctl disable firewalld
- 开启防火墙 systemctl start firewalld
- 开放指定端口 firewall-cmd --zone=public --add-port=8080/tcp --permanent
- 关闭指定端口 firewall-cmd --zone=public --remove-port=8080/tcp --permanent
- 立即生效 firewall-cmd --reload
- 查看开放的端口 firewall-cmd --zone=public --list-ports

注意：

- systemctl是管理Linux中服务的命令，可以对服务进行启动，停止，重启，查看状态等操作
- firewall-cmd是Linux中专门用于控制防火墙的命令
- 为了保证系统的安全，服务器的防火墙不建议关闭

### 1.5  MySQL安装

①**检测当前系统中是否安装MySQL数据库**

- rpm -qa                         查询当前系统中安装的所有软件
- rpm -qa | grep mysql     查询当前系统中安装的名称带mysql的软件
- rpm -qa | grep mariadb  查询当前系统中安装的名称带mariadb的软件

*注意* ：如果当前系统中已经安装MySQL数据库，安装将失败。CentOS7自带mariadb，与MySQL数据库冲突

②**卸载已经安装的冲突软件**

- rpm -e --nodeps 软件名

③**将资料中提供的MySQL安装包上传到Linux并解压**

- mkdir /usr/local/mysql
- tar -zxvf mysql-5.7.25-1.el7.x86_64.rpm-bundle.tar.gz -C /usr/local/mysql

④**按照顺序安装rpm软件包**

- rpm -ivh mysql-community-common-5.7.25-1.el7.x86_64.rpm
- rpm -ivh mysql-community-libs-5.7.25-1.el7.x86_64.rpm
- rpm -ivh mysql-community-devel-5.7.25-1.el7.x86_64.rpm
- rpm -ivh mysql-community-libs-compat-5.7.25-1.el7.x86_64.rpm
- rpm -ivh mysql-community-client-5.7.25-1.el7.x86_64.rpm
- yum install net-tools
- rpm -ivh mysql-community-server-5.7.25-1.el7.x86_64.rpm

说明1：安装过程中提示缺少net-tools依赖，使用yum安装

说明2：可以通过指令升级现有软件及系统内核   yum update

⑤**启动MySQL**

- systemctl status mysqld    查看MySQL服务状态
- systemctl start mysqld    启动MySQL服务
- systemctl enable mysqld    开机启动MySQL服务
- netstat -tunlp   查看已经启动的服务
- netstat -tunlp | grep mysql    查看MySQL服务
- ps -ef | grep mysql    查看mysql进程



⑥**登录MySQL数据库，查阅临时密码**

- cat /var/log/mysqld.log | grep password  查看文件内容中包含password的行信息



⑦**登录MySQL，修改密码，开放访问权限**

- mysql -uroot -p		登录MySQL，使用临时密码

- 修改密码(以下为MySQL的命令)

  - set global validate_password_length=4;		设置密码长度最低位数

  - set global validate_password_policy=LOW;		设置密码安全等级低，便于密码可以修改为root
  - set password=password('root');		设置密码为root

- 开启访问权限
  - grant all on \*.* to 'root'@'%' identified by 'root';
  - flush privileges;

### 1.6 安装Irzsz

- 搜索lrzsz安装包，命令为yum list lrzsz
- 使用命令在线安装，命令为yum install lrzsz.x86_64



## 2 通过Shell脚本自动部署项目

### 2.1 Git

**Git安装**

- yum list git
- yum install git

**使用Git克隆代码**

- cd /usr/local/
- git clone 仓库地址

### 2.2 maven

**安装maven**

- tar -zxvf apache-maven-3.5.4-bin.tar.gz -C /usr/local
- vim /etc/profile    修改配置文件,加入一下内容
  - export MAVEN_HOME=/usr/local/apache-maven-3.5.4
  - export PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
- source /etc/profile     刷新
- mvn -version    查看是否安装成功
- mkdir /usr/local/repo
- vim /usr/local/apache-maven-3.5.4/conf/settings.xml    创建本地仓库，修改配置文件内容如下
  - <localRepository>/usr/local/repo/</localRepository>

### 2.3 编写Shell脚本（拉取代码、编译、打包、启动）

- 资料中的Shell脚本导入

### 2.4 为用户授予执行Shell脚本的权限

**使用chmod命令授权权限**

### 2.5 执行Shell脚本

### 2.7 设置静态ip

- 修改文件/etc/sysconfig/network-scripts/ifcfg-ens33

```
BOOTPROTO="static"		    # 使用静态ip地址，默认为dhcp
IPADDR="192.168.138.100"	# 设置的静态ip地址
NETMASK="255.255.255.0"     # 子网掩码 
GATEWAY="192.168.138.2"     # 网关地址
DNS1="192.168.138.2" 	    # DNS服务器
```

**重启网络服务**

```shell
systemctl restart network
```











