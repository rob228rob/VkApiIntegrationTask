package com.taskgpb.test.ApiVkIntegration;

import com.taskgpb.test.Common.DTOs.UserInfoResponse;
import com.taskgpb.test.Common.DTOs.VkUserInfoRequest;
import com.taskgpb.test.Common.Exceptions.GroupMembershipNotFoundException;
import com.taskgpb.test.Common.Exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkApiService {

    @Value("${app.vk.api.url}")
    private String VK_API_URL;

    @Value("${app.vk.api.version}")
    private String apiVersion;

    private final RestTemplate restTemplate;

    @Cacheable(cacheNames = "userInfoResponse", key = "#request.userId + '_' + accessToken" )
    public UserInfoResponse getUserInfoResponse(
            VkUserInfoRequest request,
            String accessToken) throws UserNotFoundException, GroupMembershipNotFoundException {
        log.error("token : {}", accessToken);
        UserInfo userInfo = callVkApiToGetUserInfo(request.getUserId(), accessToken);
        Boolean isMember = callVkApiToCheckMembership(request.getUserId(), request.getGroupId(), accessToken);

        return UserInfoResponse.builder()
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .middleName(userInfo.getMiddleName())
                .member(isMember)
                .build();
    }

    private UserInfo callVkApiToGetUserInfo(long userId, String accessToken)
            throws UserNotFoundException {
        String uri = getVkApiUri("/users.get")
                + "?user_ids=" + userId
                + "&fields=nickname&access_token=" + accessToken
                + "&v=" + apiVersion;
        ResponseEntity<Map<String, List<UserInfo>>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<UserInfo>>>() {});

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new UserNotFoundException("Error fetching user info: " + response.getStatusCode().value());
        }

        List<UserInfo> userInfoList = response.getBody().get("response");
        if (userInfoList == null || userInfoList.isEmpty()) {
            throw new UserNotFoundException("No user info found" + HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return userInfoList.get(0);
    }

    private Boolean callVkApiToCheckMembership(long userId, long groupId, String accessToken)
            throws GroupMembershipNotFoundException {
        String uri = getVkApiUri("/groups.isMember")
                + "?user_id=" + userId
                + "&group_id=" + groupId
                + "&access_token=" + accessToken
                + "&v=" + apiVersion;

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {});

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new GroupMembershipNotFoundException("Error checking membership: " +  response.getStatusCode().value());
        }

        Map<String, Object> body = response.getBody();
        if (body == null || !body.containsKey("response")) {
            throw new GroupMembershipNotFoundException("No response found in the membership check " + HttpStatus.NOT_FOUND.value());
        }
        Integer membershipStatus = (Integer) body.get("response");
        return membershipStatus == 1;
    }

    private String getVkApiUri(String uriPart) {
        return VK_API_URL + uriPart;
    }
}
