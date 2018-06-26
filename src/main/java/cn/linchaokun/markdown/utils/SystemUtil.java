package cn.linchaokun.markdown.utils;

public class SystemUtil {

    public static String getUserHome(){
        String home = System.getProperty("user.home") + "/FxMarkDown";
        return home;
    }
}
