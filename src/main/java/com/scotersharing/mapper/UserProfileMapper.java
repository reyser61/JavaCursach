package com.scotersharing.mapper;

import com.scotersharing.dto.response.UserProfileResponse;
import com.scotersharing.entity.User;

public class UserProfileMapper {
    public static UserProfileResponse mapToUserProfile(User user) {
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setEmail(user.getEmail());
        userProfileResponse.setPhone(user.getPhone());
        userProfileResponse.setFirstName(user.getFirstName());
        userProfileResponse.setLastName(user.getLastName());
        return userProfileResponse;
    }
}
