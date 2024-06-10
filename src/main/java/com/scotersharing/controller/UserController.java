package com.scotersharing.controller;

import com.scotersharing.dto.response.UserProfileResponse;
import com.scotersharing.entity.User;
import com.scotersharing.entity.UserDetailsImpl;
import com.scotersharing.mapper.UserProfileMapper;
import com.scotersharing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scootersharing/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public UserProfileResponse profile() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        return UserProfileMapper.mapToUserProfile(user);
    }




}