package com.taskgpb.test.Configs;

import com.taskgpb.test.apiVkIntegration.Exceptions.ApiResponseException;
import com.taskgpb.test.apiVkIntegration.UserInfo;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GeneralConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public RouteBuilder vkApiRoutes() {
//        return new RouteBuilder() {
//            @Override
//            public void configure() {
//                from("direct:vkUserInfo")
//                        .toD("${header.uri}")
//                        .choice()
//                        .when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(404))
//                            .throwException(new ApiResponseException("User not found", 404))
//                        .when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(500))
//                            .throwException(new ApiResponseException("Internal VK API server Error", 500))
//                        .otherwise()
//                            .unmarshal().json(JsonLibrary.Jackson, UserInfo.class);
//                from("direct:vkMembershipCheck")
//                        .toD("${header.uri}")
//                        .choice()
//                        .when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(404))
//                        .   throwException(new ApiResponseException("Group or user not found", 404))
//                        .when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(500))
//                            .throwException(new ApiResponseException("VK API internal error", 500))
//                        .otherwise()
//                            .unmarshal().json(JsonLibrary.Jackson, Boolean.class);;
//            }
//        };
//    }
}

