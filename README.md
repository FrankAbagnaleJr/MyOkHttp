中科昊音面试题
---
接口1

    URL：http://192.168.8.127:8899/wav/enrol
    请求方式：POST
    Content-Type：multipart/form-data
    参数	        类型	      是否必传	示例	        参数说明
    accessToken	String	  是	    “123456”	用户唯一标识(必须为数字)
    groupId	    String	  是	    “888888”	分组号,必传
    myfiles   	File[]	  是		            需要至少3个短声纹

    成功调用接口并将接口调用结果写入到txt文件中

接口2

    URL：http://192.168.8.127:8899/wav/verify
    请求方式：POST
    Content-Type：multipart/form-data
    参数	         类型	    是否必传	 示例	    参数说明
    accessToken	 String	    是	     “123456”	用户唯一标识(必须为数字)
    groupId	     String	    是	     “888888”	分组号,必传
    myfiles    	 File	    是		            需要至少1个短声纹
 
    成功调用接口并将接口调用结果写入到txt文件中

整理文件方法

    遍历整个目录的音频文件，在音频文件夹中分别将前缀相同的文件分为一组，分组的顺序以首字母排序

