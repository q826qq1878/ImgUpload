简单说明



在开发过程中。难免有图片上传的业务逻辑

而每次开发都需要写一份又显得浪费时间

这里把图片上传功能，单独拆分成一个服务。







#后台


技术架构： springMVC + Mybatis + Spring

请创建数据库，导入sql文件。 后修改jdbc配置文件


提供2个接口

1、上传到临时区（有一个定时器任务。每天会清空临时区的数据）

http://localhost:8080/ImgUpload/upLoad/fileUpload

2、上传到正式区（根据临时区接口返回的加密字符串作为参数调用接口）

http://localhost:8080/ImgUpload/upLoad/uploadSuccess


将文件上传到临时区。 如果用户确认提交的时候。

在调用正式区方法。 将临时区的文件复制到正式区进行储存

而图片的名称使用加密类进行加密。 存储起来实际就是一个字符串。
 
通过本项目中的工具进行解析。 形成http的img地址。直接展示。





#前台


这里采用的是webuploader。简单封装了一下。可以直接调用后台，解决了跨域问题



#图片上传使用方法


1、启动

使用maven 或者 tomcat的方式。都可以


2、访问

http://localhost:8080/ImgUpload/upLoad/demo


3、

参考index.html中的代码。


4、方法介绍

JS方法（调用）：
imgUpload("imgId1");

HTML：
<!--用来存放item-->
<img src="" id="imgId2_img">
<div id="imgId2_btn">选择图片2</div>

JS：自己根据名称自定义   
function imgId1_callBack(data){
  console.log(data);
  alert(data.ret);
}


这里我封装好了一个方法。大家使用思路为：

1、初始化imgUpload("imgId1");
2、imgId1：看HTML的代码。  命名按照规范来命名，  自动进行回显示
3、imgId1_callBack 方法为钩子函数。  如果你的ID等于 imgId100  那么方法名应当为imgId100_callBack

      
     
5、使用步骤

5.1、上传的时候会调用
http://localhost:8080/ImgUpload/upLoad/fileUpload


成功返回：
{
"ret":"200",
"httpUrl":"http://localhost:8080//imgup/upload_bak/images/2017/09/18/FAZmZ7tQ0nqaLhE9PoWRUu2mqDdizaHrDAIsPAmGNvBe89w9GXbonEvB6zINpqWg.jpg",
"name":"FAZmZ7tQ0nqaLhE9PoWRUu2mqDdizaHrDAIsPAmGNvBe89w9GXbonEvB6zINpqWg.jpg",
"url":"upload_bak/images/2017/09/18/FAZmZ7tQ0nqaLhE9PoWRUu2mqDdizaHrDAIsPAmGNvBe89w9GXbonEvB6zINpqWg.jpg"
}

失败返回：
{
"ret":"400"
}

根据钩子函数自己进行处理。、 如果失败。则给用户提示重新调用。
然后将name 拿到。 传递到后台。入库前。调用5.2


5.2、成功上传图片接口 

调用地址：http://localhost:8080/ImgUpload/upLoad/uploadSuccess
调用方法：POST
调用方法体：fileName=QxREMt3zu25pXh1EtTF7bRm4B92FIkTtbTBqZ%2FiHqlt9UukqTbFgsFm8NIjTUbv2.jpg

name为刚才上传成功的name。

成功返回：

{"fileName":"qvm9heAagy2ng%2B0pkTKoHQn5PRzWl%2BE5Fi42ZMFUtr9wyLH3BBMothY6B8KLp6du.jpg","ret":"200"}

失败返回：
{ret:400}

然后将fileName入库即可







