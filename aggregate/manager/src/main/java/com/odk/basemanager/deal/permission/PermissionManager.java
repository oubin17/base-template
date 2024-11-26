package com.odk.basemanager.deal.permission;

import com.odk.base.enums.common.CommonStatusEnum;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.basedomain.domain.permission.PermissionDO;
import com.odk.basedomain.domain.permission.UserRoleDO;
import com.odk.basedomain.domain.permission.UserRoleRelDO;
import com.odk.basedomain.domain.user.UserBaseDO;
import com.odk.basedomain.repository.permission.PermissionRepository;
import com.odk.basedomain.repository.permission.UserRoleRelRepository;
import com.odk.basedomain.repository.permission.UserRoleRepository;
import com.odk.basedomain.repository.user.UserBaseRepository;
import com.odk.basemanager.entity.PermissionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PermissionManager
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/8
 */
@Slf4j
@Service
public class PermissionManager {

    private UserBaseRepository userBaseRepository;

    private UserRoleRepository userRoleRepository;

    private PermissionRepository permissionRepository;

    private UserRoleRelRepository relRepository;

    /**
     * 查找用户权限
     *
     * @param userId
     * @return
     */
    public PermissionEntity getAllPermissions(String userId) {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setUserId(userId);
        List<UserRoleDO> userRoleDOS = userRoleRepository.findAllUserRole(userId);
        permissionEntity.setRoles(userRoleDOS);
        if (!CollectionUtils.isEmpty(userRoleDOS)) {
            List<String> roleIds = userRoleDOS.stream().map(UserRoleDO::getRoleId).collect(Collectors.toList());
            List<PermissionDO> allRolePermission = permissionRepository.findAllRolePermission(roleIds);
            permissionEntity.setPermissions(allRolePermission);
        }
        return permissionEntity;
    }

    /**
     * 添加角色
     *
     * @param roleCode
     * @param roleName
     * @return
     */
    public String addRole(String roleCode, String roleName) {
        UserRoleDO userRoleDO = userRoleRepository.findByRoleCode(roleCode);
        AssertUtil.isNull(userRoleDO, BizErrorCode.PARAM_ILLEGAL, "角色码重复，添加角色失败");
        UserRoleDO addRole = new UserRoleDO();
        addRole.setRoleCode(roleCode);
        addRole.setRoleName(roleName);
        addRole.setStatus(CommonStatusEnum.NORMAL.getCode());
        UserRoleDO save = userRoleRepository.save(addRole);
        return save.getRoleId();
    }

    public String addUserRoleRela(String roleId, String userId) {
        UserRoleRelDO userRoleRelDO = relRepository.findByUserIdAndRoleId(userId, roleId);
        AssertUtil.isNull(userRoleRelDO, BizErrorCode.PARAM_ILLEGAL, "用户已具备该权限");

        UserRoleDO userRoleDO = userRoleRepository.findByRoleId(roleId);
        AssertUtil.notNull(userRoleDO, BizErrorCode.PARAM_ILLEGAL, "角色不存在");
        UserBaseDO userBaseDO = userBaseRepository.findByUserId(userId);
        AssertUtil.notNull(userBaseDO, BizErrorCode.PARAM_ILLEGAL, "用户不存在");

        UserRoleRelDO roleRelDO = new UserRoleRelDO();
        roleRelDO.setUserId(userId);
        roleRelDO.setRoleId(roleId);
        UserRoleRelDO save = relRepository.save(roleRelDO);
        return String.valueOf(save.getId());

    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Autowired
    public void setPermissionRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Autowired
    public void setUserBaseRepository(UserBaseRepository userBaseRepository) {
        this.userBaseRepository = userBaseRepository;
    }

    @Autowired
    public void setRelRepository(UserRoleRelRepository relRepository) {
        this.relRepository = relRepository;
    }
}
