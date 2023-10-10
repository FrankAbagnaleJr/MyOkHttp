package com.kyrie.main;

import com.kyrie.utils.OkHttpClientInstenceL;
import com.kyrie.utils.Utils;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.util.Random;

public class ToJob {

    static final String ENROLL_REQUEST_PATH = "http://47.108.228.186:8899/wav/enroll";
    static final String VERIFY_REQUEST_PATH = "http://47.108.228.186:8899/wav/verify";
    //音频文件路径
    static final String FILE_PATH = "J:/deskfile-temp/wav";
    //执行结果输出文件
    static final String RESULT_TXT = "J:/1.txt";

    public static void main(String[] args) throws Exception {
        //请求注册接口，输出文件路径
        System.out.println(enroll(RESULT_TXT));
        //请求验证接口
        System.out.println(verify(RESULT_TXT));
        //整理文件夹，参数是文件夹的路径
        System.out.println(Classify(FILE_PATH));
    }


    /**
     *
     * @param resultPath 结果输出文件路径
     * @return
     * @throws IOException
     */
    public static String enroll(String resultPath) throws IOException {
        if (!new File(resultPath).isFile()) return "文件路径错误，不是文件";

        //获得OkHttp客户端
        OkHttpClient client = OkHttpClientInstenceL.getClient();
        //通过工具类获取 MediaType
        MediaType mediaType = Utils.getMediaType("multipart/form-data");

        Random random = new Random();
        random.ints(0, 1);

//        File file1 = new File("/C:/Users/Administrator/Desktop/wav/hc_1.wav");
//        File file2 = new File("/C:/Users/Administrator/Desktop/wav/ht_1.wav");
//        File file3 = new File("/C:/Users/Administrator/Desktop/wav/long_30s.wav");
        File file1 = new File("wav/hc_1.wav");
        File file2 = new File("wav/ht_2.wav");
        File file3 = new File("wav/long_30s.wav");



        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("accessToken", "123456")
                .addFormDataPart("groupId", "888888")
                .addFormDataPart("myfiles", file1.getName(), RequestBody.create(mediaType, file1))
                .addFormDataPart("myfiles", file2.getName(), RequestBody.create(mediaType, file2))
                .addFormDataPart("myfiles", file3.getName(), RequestBody.create(mediaType, file3))
                .build();

        Request request = new Request.Builder()
                .url(ENROLL_REQUEST_PATH)
                .method("POST", body)
                .addHeader("Content-Type", mediaType.toString())
                .build();
        //发请求
        Response response = client.newCall(request).execute();

        //响应体
        String responseBody = response.body().string();

        //写文件到txt中
        resultToFile(responseBody,new File(resultPath),true);

        return responseBody;
    }

    /**
     *
     * @param resultPath 结果输出文件路径
     * @return
     * @throws IOException
     */
    public static String verify(String resultPath) throws IOException {
        if (!new File(resultPath).isFile()) return "文件路径错误，不是文件";

        //获得OkHttp客户端
        OkHttpClient client = OkHttpClientInstenceL.getClient();
        //通过工具类获取 MediaType
        MediaType mediaType = Utils.getMediaType("multipart/form-data");

        //请求文件
        File file = new File("wav/ht_2.wav");

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("accessToken", "123456")
                .addFormDataPart("myfiles", file.getName(), RequestBody.create(mediaType, file))
                .build();

        Request request = new Request.Builder()
                .url(VERIFY_REQUEST_PATH)
                .method("POST", body)
                .addHeader("Content-Type", "multipart/form-data")
                .build();

        Response response = client.newCall(request).execute();

        //响应体
        String responseBody = response.body().string();

        //写文件到txt中
        resultToFile(responseBody,new File(resultPath),true);

        return responseBody;
    }

    /**
     *
     * @param filePath 整理文件夹路径
     * @return
     */
    public static String Classify(String filePath) {
        if (filePath==null) return null;

        //得到文件目录
        File dom = new File(filePath);
        //获取目录的所有文件
        File[] files = dom.listFiles();
        //循环每一个文件
        for (File file : files) {
            //不是文件跳过循环
            if (!file.isFile()) continue;
            //得到文件名前缀
            String prfix = StringUtils.substringBefore(file.getName(), "_");
            //得到新的目录
            File newDom = new File(file.getParent() + File.separator + prfix);
            //新建文件夹
            newDom.mkdir();
            //得到移动后的文件
            File proFile = new File(newDom.getPath() + File.separator + file.getName());
            //文件移动
            file.renameTo(proFile);
        }
        return "文件归类处理完成";
    }

    /**
     *
     * @param content 写入文件内容
     * @param file 写入文件
     * @param flag 覆盖写入 / 追加写入
     * @return
     */
    public static boolean resultToFile(String content, File file, boolean flag) {
        if (!file.isFile()) return false;

        //true 追加写入
        if (flag) return resultToFileAppend(content, file);

        //false覆盖写入
        //创建文件写入器
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            //写入
            fileWriter.write(content);
        } catch (IOException e) {
            System.out.println("写入失败：" + e.getMessage());
            return false;
        }
        return true;
    }


    public static boolean resultToFileAppend(String content, File file) {
        //追加写入
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, true));
            //整行读取
            bw.newLine();
            //追加写入
            bw.write(content);
            //刷新流
            bw.flush();
        } catch (Exception e) {
            System.out.println("追加写入失败：" + e.getMessage());
            return false;
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}


