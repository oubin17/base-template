package com.odk.baseweb.user;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.user.UserLoginApi;
import com.odk.baseapi.request.UserLoginRequest;
import com.odk.baseapi.request.UserLogoutRequest;
import com.odk.baseapi.response.UserLoginResponse;
import com.odk.baseweb.interceptor.NoLoginCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * UserLoginController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/5
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserLoginController {


    private UserLoginApi userLoginApi;

    @NoLoginCondition
    @PostMapping("/login")
    public ServiceResponse<UserLoginResponse> userLogin(@RequestBody UserLoginRequest loginRequest) {
        return userLoginApi.userLogin(loginRequest);
    }

    @NoLoginCondition
    @PostMapping("logout")
    public ServiceResponse<Boolean> userLogout(@RequestBody UserLogoutRequest logoutRequest) {
        return userLoginApi.userLogout(logoutRequest);
    }

    @Autowired
    public void setUserLoginApi(UserLoginApi userLoginApi) {
        this.userLoginApi = userLoginApi;
    }
}
