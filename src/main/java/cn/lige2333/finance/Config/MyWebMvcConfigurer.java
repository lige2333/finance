package cn.lige2333.finance.Config;

import cn.lige2333.finance.Interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Override
       public void  addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new MyInterceptor())
                        .addPathPatterns("/**")
                .excludePathPatterns("/").excludePathPatterns("/moon/**").excludePathPatterns("/icon/**").excludePathPatterns("/favicon.ico").excludePathPatterns("/bs/**");
        }
}
