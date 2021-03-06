package com.sys.util;

import com.sys.bean.UserBean;
import com.sys.shiro.MyShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.mgt.RealmSecurityManager;

import java.util.Collection;

/**
 * Created by Administrator on 2017/4/26.
 */
public class ShiroUtil {
    /**
     * 清除当前权限信息
     */
    public static void clearAuthz(){
        RealmSecurityManager rsm= (RealmSecurityManager) SecurityUtils.getSecurityManager();
        MyShiroRealm realm=  (MyShiroRealm)rsm.getRealms().iterator().next();
        realm.clearAuthz();
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public static UserBean getCurrentUser(){
        UserBean user;
        try{
            user=(UserBean)SecurityUtils.getSubject().getSession().getAttribute("user");
        }catch (UnavailableSecurityManagerException e){
            user=null;
        }
        return user;
    }

    /**
     * 获取当前登录用户的所有角色
     * @return
     */
    public static Collection<String> getCurrentUserRoles(){
        RealmSecurityManager rsm= (RealmSecurityManager) SecurityUtils.getSecurityManager();
        MyShiroRealm realm=  (MyShiroRealm)rsm.getRealms().iterator().next();
        return realm.getAllRoles();
    }

    /**
     * 获取当前登录用户的所有权限
     * @return
     */
    public static Collection<String> getCurrentUserPermissions(){
        RealmSecurityManager rsm= (RealmSecurityManager) SecurityUtils.getSecurityManager();
        MyShiroRealm realm=  (MyShiroRealm)rsm.getRealms().iterator().next();
        return realm.getAllPermissions();
    }

}
