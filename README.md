# 说明
本工具提供文本自动翻页阅读和手动翻页阅读的功能:

使用前请在application.properties文件中

将filepath更改为所需要被读取的文件路径

如果想把被读取的文件放到resources目录下读取,可更改NovelReader下的run()方法,  
// Resource resource2 = new ClassPathResource("xxx.txt");

# 目前版本V1.5
 ##支持的功能: 
    # 1.自动翻页
    # 2.手动翻页(按enter键翻页)
    # 3.一次翻页的行数可选
    # 4.自动翻页的秒数可选
    # 5.自动去除文本中的空格,回车,以及换行
    # 6.从指定的页数开始阅读  ModuleChoose,startFrom()
    # 7.展示当前阅读的页数
    
### 指南
 运行程序后,在控制台按提示输入即可
 
    ModuleChoose中的模板read(int page, String type, InputStreamReader inputStreamReader)方法为主逻辑
    后续更新均在read方法中更新

### 修复了一些bug
    # 1. 打印每行时出现少字的bug
    # 2. 无法去除中文全角空格(unicode值12288)的bug

### 下一步工作
    1.提供登陆注册功能
    2.根据登陆的用户,自动读取上一次阅读的配置
    
    












* [xx](https://spring.io/guides/gs/rest-service/)
