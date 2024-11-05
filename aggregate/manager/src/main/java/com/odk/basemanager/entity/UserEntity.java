package com.odk.basemanager.entity;

import com.odk.base.enums.user.UserStatusEnum;
import com.odk.base.enums.user.UserTypeEnum;
import lombok.Data;

/**
 * UserEntity
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/4
 */
@Data
public class UserEntity {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户类型
     * {@link com.odk.base.enums.user.UserTypeEnum}
     */
    private UserTypeEnum userType;

    /**
     * 用户状态
     * {@link com.odk.base.enums.user.UserStatusEnum}
     */
    private UserStatusEnum userStatus;


}
