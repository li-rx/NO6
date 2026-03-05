package com.cl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import java.io.IOException;

@Configuration
public class AutoRunWeb {
    // 这里是注入你yml配置的端口号
    @Value("${server.port}")
    private String appPort;

    /**
     * 监听事件（当项目启动后），启动浏览器
     */
    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
        System.out.println("应用已经准备就绪 ...");
        // 需要启动的url（appPort是端口号， "/springbootil5n0/admin/dist/idnex.html"是项目的具体页面）
        String url1 = "http://localhost:" + appPort + "/"+"cl515882190/client/index.html";
        String url2 = "http://localhost:" + appPort + "/"+"cl515882190/manage/index.html#/login";
        
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();
        
        try {
            if (os.contains("win")) {
                // Windows系统
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url1);
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url2);
            } else if (os.contains("mac")) {
                // macOS系统
                runtime.exec("open " + url1);
                runtime.exec("open " + url2);
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux系统
                runtime.exec("xdg-open " + url1);
                runtime.exec("xdg-open " + url2);
            }
            System.out.println("请在浏览器中访问:");
            System.out.println("前台: " + url1);
            System.out.println("后台: " + url2);
        } catch (IOException e) {
            System.err.println("自动打开浏览器失败，请手动访问以下地址:");
            System.err.println("前台: " + url1);
            System.err.println("后台: " + url2);
        }
    }
}
