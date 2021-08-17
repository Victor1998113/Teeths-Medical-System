package com.oral.utils;

import java.io.FileWriter;
import java.io.IOException;

public class AppUtil {
    //应用ID,您的APPID,收款账号即是你的APPID对应支付宝账号
    public static String app_id = "2021000117657641";
    //商户私钥,你的PKCS8格式RSA2私钥
    //应用私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCgCB+YU+fueL3lARY2+vI9jgrPzc6X+4BVdQ4s3Yid0dSDm2Och3kv96o1fpAZHSvPU9vW+8jDjheCLuoxl5sPu458dAX3wKXEqDguqe3H/MAKq2oPOn3zJrgD+NMK/OGIkmvRJQuLGLG4E6AxpPGSBoECpFa481C32iGRklbP4xXGfMcLJaKLodpFewjNHaNn436sG9ppOnnWe0tOPU7Eh+ukJ/33ofG4egZmbxeElnHyqrlJTGo+lqxqXoJ0auFC4VxrQaL4PipLScAh6lS3hzGnnvzauquHMXHJUZjW5zC/fAigRhhBh1yNKQkdiRtuUMLHjJhp6fC9jMTAfRD7AgMBAAECggEAebqUJe+1HW2UrKxt9HZgazgvuzAGtQ7m6Jo4pSICOGIS7+T0GW3nXfha7YQPIjtRRsy4DcjKRw4/jZQYKo7ysT3uvXYX0AQnU/bNfZleuN/mSHdpHppUke9izAqqpdID0Dnob1dpd23aSXYMRZb89iGiYpsVKGxUevUe0bdp94jDl39ghV0TDSx23Q5IHweHlDON72S+VYMXidPvsJ7W37dISRefXbj6V/1Xmpx5HAG9HBUI+T+1uhg2JyoDexywpj+yGmLIe6vX73vf6cK3FmhRwx14Yn6vWV/RcrcbrlTkx9uvQ3Osbc6KFZej+uMCzpCCinpQ5/87C7cZtL5n+QKBgQDk3seAV+TB70X8YXb7qI3mGoBNFX26d0c2fUUrG8kn+lIZs1fhFh9yt+BjVgN69XRikTIDAWTFhPCtY3JmfGZiXk8uG3brnIi+Dx8QJTNn/7fZiTRD0pYlihB5CthBHBcqlJAaAXfIeK0t0+KU56bPzosMfwoTatfRoanFlPSy3QKBgQCzAGTHuy3lKh3CnUqrzoLQ39M5iw9MSYKW0W/MNiNdk1lXSYP4JXQDoxpxVthzWyoghfemvB+fnyfckoZQX7qxPAtDsIVhSxl0lAXB5/b2cuMOyiKVqPbwJ957DLPNCyGEASDMp5z/eert5m5smOXVz+oF8n/XkLexmU+DidY5twKBgB1k41h/cVjbAzELz304w6i25J2B0hj9sr6zzgtgUTB6PMsiNnopHjbcmMwJQv8qwRzbqwGpzyhzD3olWI8DER83H5I7YsWV4BYQ2Rt4u6ZyuEkPDxBa3n/JwJ3bH7UVB4Cnfe5pG9o6bXuCSAp5yfnMfB1Jv6CIy1kYGzbOD7NdAoGAYuUkyPSxNps+NfeQ+bruq1f96xzZJty2OX0ct0Zx45E1GvSS5NZMcnX6dgP1MkZ7z9w0JvpovlfAPiz16BH7o0/5KTytdEN5nN7rD2aJGz/+En26/dSMGcLtOC6cZ/xuV/AHnwo7wyookadt+rG+yZJFSWPhHcfmtUhulc3hfaECgYEAp+629fHsZsnWBT6VHv3YnezRcB7zjSW4QayUTYXkQciASRekXmJDvPvyvoa0VNufuBneRk/UDe36Ap+xSAW7xbfjCCIT/HUlCo2f9+LsSToLOhaWCBbIKguRWXF6oaeixHqsFOHtZonA2H7wrtkIozQ/qB7GzAJe38NSNkJuSvM=";
    //支付宝公钥
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxyCpfVUotE6bgP5e+cNezFtvWF/6lnmWl5T098oNYHQBoVDTGVl189JGODOm+rkQJI6GLdbyXzfBpUY4rYCWcbT7n7vG+4yYkO9/AVVJ26YhFL+vov2ahe/0WalUtG9vYrq0STGvURwyRAijFxPuhGxrXOZViE+cNDhlpxKkQiQcntAGKGGaEj7EDaF0zdEVMF2wkXZx8A4xGBf1IoXo1r27orGCgf39SbGf9D5bx6vSSNGGK3jUlvwYglamnoQa/4U40f6yP7kml8kHg1g3QSl145sIYZ/dy5qfwUOKy93RTyjE3l6xMSzv9DyfXId5Dz3S1Baeo2SvCdCPxwFpawIDAQAB";
    //服务器异步通知页面路径
    //需http://格式的完整路径,不能加?id=123这类自定义参数,必须外网可以正常访问
    //此地址根据你的服务器 域名更改    /getnotify这个是控制台地址
    public static String notify_url = "http://localhost:9000/sys/member/getnotify";
    public static String notify_urlTo = "http://localhost:9000/sys/medical/getnotify";
    //页面跳转同步通知页面路径
    //需http://格式的完整路径,不能加?id=123这类自定义参数,必须外网可以正常访问
    //此地址根据你的服务器 域名更改   /getreturn这个是控制台地址
    public static String return_url = "http://localhost:9000/sys/member/getreturn";
    public static String return_urlTo = "http://localhost:9000/sys/medical/getreturn";
    //签名方式
    public static String sign_type = "RSA2";
    //字符编码格式
    public static String charset = "utf-8";
    //支付宝网关,注意这些使用的是沙箱的支付宝网关,与正常网关的区别是多了dev
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
    //支付宝网关
    public static String log_path = "C:\\";

    //写日志,方便测试(看网站需求,也可以改成把记录存入数据库)
    //要写入日志里的文本内容
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
            System.out.println("sWord:" + sWord);
            System.out.println("writer:" + writer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

