package com.taskgpb.test;

import com.taskgpb.intern.Common.DTOs.UserInfoResponse;
import com.taskgpb.intern.Common.DTOs.VkUserInfoRequest;
import com.taskgpb.intern.Common.Exceptions.ApiResponseException;
import com.taskgpb.intern.Common.Exceptions.GroupMembershipNotFoundException;
import com.taskgpb.intern.Common.Exceptions.UserNotFoundException;
import com.taskgpb.intern.ApiVkIntegration.UserInfo;
import com.taskgpb.intern.ApiVkIntegration.VkApiService;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.config.name=application-test")
public class VkApiServiceTest {

    @InjectMocks
    private VkApiService vkApiService;

    @Mock
    private ProducerTemplate producerTemplate;

    @Value("${app.vk.test.token}")
    private String testValidVkToken;

    @Test
    public void testGetUserInfoResponse() throws UserNotFoundException, GroupMembershipNotFoundException {

        UserInfo mockUserInfo = new UserInfo("Иванов", "Иван", "Иванович");
        when(producerTemplate.requestBody(anyString(), isNull(), eq(UserInfo.class))).thenReturn(mockUserInfo);

        VkUserInfoRequest request = new VkUserInfoRequest(78385, 93559769);
        UserInfoResponse response = vkApiService.getUserInfoResponse(request, testValidVkToken);

        assertEquals("Иванов", response.getLastName());
        assertEquals("Иван", response.getFirstName());
        assertEquals("Иванович", response.getMiddleName());
    }

    @Test
    public void testCallVkApiToGetUserInfoThrowsApiException() {
        when(producerTemplate.requestBody(anyString(), isNull(), eq(UserInfo.class))).thenThrow(new RuntimeException("Ошибка"));

        VkUserInfoRequest request = new VkUserInfoRequest(78385, 93559769);

        ApiResponseException exception = assertThrows(ApiResponseException.class, () -> {
            vkApiService.getUserInfoResponse(request, testValidVkToken);
        });

        assertEquals(500, exception.getHttpStatusCode());
        assertEquals("Ошибка получения информации о пользователе", exception.getMessage());
    }
}
