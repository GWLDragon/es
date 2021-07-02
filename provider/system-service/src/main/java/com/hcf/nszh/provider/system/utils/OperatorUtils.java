package com.hcf.nszh.provider.system.utils;

import com.google.common.collect.Lists;
import com.hcf.nszh.common.security.shiro.utils.UserUtils;
import com.hcf.nszh.provider.system.api.vo.OfficeDetailVO;
import com.hcf.nszh.provider.system.api.vo.OfficeVo;
import com.hcf.nszh.provider.system.api.vo.OperatorMenuVo;
import com.hcf.nszh.provider.system.api.vo.UserVo;
import com.hcf.nszh.provider.system.mapper.MenuMapper;
import com.hcf.nszh.provider.system.mapper.OfficeMapper;
import com.hcf.nszh.provider.system.mapper.RoleMapper;
import com.hcf.nszh.provider.system.entity.MenuEntity;
import com.hcf.nszh.provider.system.entity.RoleEntity;
import com.hcf.nszh.provider.system.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by Furant
 * 2019/7/7 19:21
 */
@Component
public class OperatorUtils {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private OfficeMapper officeMapper;

    // 静态初使化当前类
    private static OperatorUtils operatorUtils;

    /**
     * 在方法上加上注解@PostConstruct，这样方法就会在Bean初始化之后被Spring容器执行
     */
    @PostConstruct
    public void init() {
        operatorUtils = this;
        operatorUtils.roleMapper = this.roleMapper;
        operatorUtils.officeMapper = this.officeMapper;
        operatorUtils.menuMapper = this.menuMapper;
    }

    public OperatorMenuVo getMenu(String id) {
        return menuMapper.selectById(id);
    }

    public OfficeDetailVO getOffice(String id) {
        OfficeVo officeById = operatorUtils.officeMapper.getOfficeById(id);
        if (officeById != null) {
            OfficeDetailVO officeDetailVO = ObjConvert.newInstance(officeById, OfficeDetailVO.class);
            return officeDetailVO;
        } else {
            return null;
        }

    }


    /**
     * 数据范围过滤
     *
     * @param user        当前用户对象，通过“entity.getCurrentUser()”获取
     * @param officeAlias 机构表别名，多个用“,”逗号隔开。
     * @param userAlias   用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
     * @return 标准连接条件对象
     */
    public static String dataScopeFilter(UserEntity user, String officeAlias, String userAlias) {

        StringBuilder sqlString = new StringBuilder();

        // 进行权限过滤，多个角色权限范围之间为或者关系。
        List<String> dataScope = Lists.newArrayList();

        // 超级管理员，跳过权限过滤
        if (!user.isAdmin()) {
            boolean isDataScopeAll = false;
            for (RoleEntity r : user.getRoleList()) {
                for (String oa : StringUtils.split(officeAlias, ",")) {
                    if (!dataScope.contains(r.getDataScope()) && StringUtils.isNotBlank(oa)) {
                        if (RoleEntity.DATA_SCOPE_ALL.equals(r.getDataScope())) {
                            isDataScopeAll = true;
                        } else if (RoleEntity.DATA_SCOPE_COMPANY_AND_CHILD.equals(r.getDataScope())) {
                            sqlString.append(" OR " + oa + ".id = '" + user.getCompany().getId() + "'");
                            sqlString.append(" OR " + oa + ".parent_ids LIKE '" + user.getCompany().getParentIds() + user.getCompany().getId() + ",%'");
                        } else if (RoleEntity.DATA_SCOPE_COMPANY.equals(r.getDataScope())) {
                            sqlString.append(" OR " + oa + ".id = '" + user.getCompany().getId() + "'");
                            // 包括本公司下的部门 （type=1:公司；type=2：部门）
                            sqlString.append(" OR (" + oa + ".parent_id = '" + user.getCompany().getId() + "' AND " + oa + ".type = '2')");
                        } else if (RoleEntity.DATA_SCOPE_OFFICE_AND_CHILD.equals(r.getDataScope())) {
                            sqlString.append(" OR " + oa + ".id = '" + user.getOffice().getId() + "'");
                            sqlString.append(" OR " + oa + ".parent_ids LIKE '" + user.getOffice().getParentIds() + user.getOffice().getId() + ",%'");
                        } else if (RoleEntity.DATA_SCOPE_OFFICE.equals(r.getDataScope())) {
                            sqlString.append(" OR " + oa + ".id = '" + user.getOffice().getId() + "'");
                        } else if (RoleEntity.DATA_SCOPE_CUSTOM.equals(r.getDataScope())) {
                            sqlString.append(" OR EXISTS (SELECT 1 FROM sys_role_office WHERE role_id = '" + r.getRoleId() + "'");
                            sqlString.append(" AND office_id = " + oa + ".id)");
                        }
                        dataScope.add(r.getDataScope());
                    }
                }
            }
            // 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
            if (!isDataScopeAll) {
                if (StringUtils.isNotBlank(userAlias)) {
                    for (String ua : StringUtils.split(userAlias, ",")) {
                        sqlString.append(" OR " + ua + ".id = '" + user.getUserId() + "'");
                    }
                } else {
                    for (String oa : StringUtils.split(officeAlias, ",")) {
                        sqlString.append(" OR " + oa + ".id IS NULL");
                    }
                }
            } else {
                // 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
                sqlString = new StringBuilder();
            }
        }
        if (StringUtils.isNotBlank(sqlString.toString())) {
            return " AND (" + sqlString.substring(4) + ")";
        }
        return "";
    }


    /**
     * 获取当前用户授权菜单
     *
     * @return
     */
    public List<MenuEntity> getMenuList() {
        @SuppressWarnings("unchecked")
        List<MenuEntity> menuList = new ArrayList<>();
        List<MenuEntity> finalList = null;
        List<MenuEntity> finalList1 = null;
        MenuEntity m = new MenuEntity();
        StringBuffer parentIds = new StringBuffer();

        if (menuList == null) {
            UserVo user = UserUtils.getUser();
            if (user.isAdmin()) {
                menuList = operatorUtils.menuMapper.findAllList(m);
            } else {
                m.setUserId(user.getUserId());
                menuList = operatorUtils.menuMapper.findByUserId(m);
                List<String> toList = new ArrayList<String>();
                List<String> toList1 = new ArrayList<String>();
                String parentId = null;
                if (menuList != null && !menuList.isEmpty()) {
                    for (MenuEntity menu2 : menuList) {
                        if (menu2 != null && menu2.getParent() != null) {
                            if (org.apache.commons.lang.StringUtils.isNotBlank(menu2.getPermission())) {
                                parentId = menu2.getId();
                                toList1.add(parentId);
                            } else {
                                if (menu2.getParentIds() != null) {
                                    parentId = menu2.getParentIds();
                                    parentIds.append(parentId).append(",");
                                    String str2 = new String(parentIds);
                                    toList = stringToList(str2);
                                }
                            }
                        }
                    }
                    removeDuplicateWithList(toList);
                    finalList1 = operatorUtils.menuMapper.selectParentMenu(toList1);
                    finalList = operatorUtils.menuMapper.selectParentMenu(toList);
                    MenuEntity n = operatorUtils.menuMapper.selectAMenu();
                    if (!menuList.contains(finalList)) {
                        menuList.addAll(finalList);
                        menuList.addAll(finalList1);
                        if (menuList.contains(n)) {
                            menuList.remove(n);
                        }
                        removeDuplicateWithList(menuList);
                    }
                }
            }
        }
        return menuList;
    }

    public List<String> stringToList(String strs) {
        String str[] = strs.split(",");
        List<String> list = Arrays.asList(str);
        List arrList = new ArrayList(list);
        return arrList;
    }

    /**
     * list去重
     *
     * @param list
     * @return
     */
    public static void removeDuplicateWithList(List list) {
        HashSet h = new HashSet();
        h.addAll(list);
        list.clear();
        list.addAll(h);
    }

}
