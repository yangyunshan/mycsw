package com.csw.user.util;

import com.csw.data.util.GetSessionFactory;
import com.csw.data.util.QueryDataUtil;
import com.csw.privileage.util.*;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class VerifyUserActionUtil {
    /**
     * 根据用户名和密码判断该用户是否存在,存在返回true，否则返回false
     *
     * @param name :
     *             用户名
     * @param password :
     *                 密码
     * */
    public static boolean verifyUser(String name, String password) {
        boolean flag = false;
        Master user = ActionManager.queryMasterByName(name);
        if (user!=null) {
            if (user.getPassword().equals(password)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 根据用户名和密码查询该用户所隶属的组,如不存在返回空
     *
     * @param name :
     *             用户名
     * @param password :
     *                 密码
     * */
    public static Group getGroupOfUser(String name, String password) {
        Master user = ActionManager.queryMasterByName(name);
        Group group = null;
        if (user!=null) {
            if (user.getPassword().equals(password)) {
                MasterGroup masterGroup = ActionManager.queryMasterGroupByMasterId(user.getId());
                if (masterGroup != null) {
                    group = ActionManager.queryGroupById(masterGroup.getGroupId());
                }
            }
        }
        return group;
    }

    /**
     * 根据组信息查询该组对应的权限信息
     *
     * @param groupId :
     *                组id标识
     * */
    public static List<Action> getActionOfGroup(int groupId) {
        List<Action> actions = new ArrayList<Action>();
        List<ActionGroup> actionGroups = ActionManager.queryActionGroupByGroupId(groupId);
        if (actionGroups!=null) {
            for (ActionGroup actionGroup : actionGroups) {
                Action action = ActionManager.queryActionById(actionGroup.getActionId());
                actions.add(action);
            }

        }
        return actions;
    }

    /**
     * 查询用户的权限信息(权限信息包含在Action对象中)
     *
     * @param name :
     *             用户名
     * @param password :
     *                  密码
     * */
    public static List<Action> getActionOfUser(String name, String password) {
        Group group = getGroupOfUser(name,password);
        List<Action> actions = new ArrayList<Action>();
        if (group!=null) {
            actions = getActionOfGroup(group.getId());
        }
        return actions;
    }

    @Test
    public void test() throws Exception {
        long startTime = 0;
        long endTime = 0;
        SqlSession sqlSession = null;
        try {
            sqlSession = GetSessionFactory.sqlSessionFactory.openSession();
            Action action = QueryDataUtil.selectActionById(1);
            QueryDataUtil.queryRegistryPackageByType("sensorml");
            startTime = System.currentTimeMillis();
            QueryDataUtil.queryRegistryPackageById("urn:liesmars:remotesensor:platform:CBERS-3:package");
            endTime = System.currentTimeMillis();
            Master master = QueryDataUtil.selectMasterById(1);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        System.out.println("Time: "+(endTime-startTime)+"ms.");
    }
}
