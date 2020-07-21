package com.csw.user.util;

import com.csw.data.util.DeleteDataUtil;
import com.csw.data.util.InsertDataUtil;
import com.csw.data.util.QueryDataUtil;
import com.csw.privileage.util.*;
import com.csw.util.GetSystemInfoUtil;
import java.sql.Timestamp;
import java.util.List;

public class ActionManager {

    /*****************************注册插入权限数据************************************/
    public static boolean userRegistration(String masterName, String password, String trueName,
                                        String sex, String dept, String position,
                                        String phone, String email, int masterId2, String masterName2) {
        boolean flag = false;
        Timestamp createDate = GetSystemInfoUtil.getTimeStamp();
        Master master = new Master();
        master.setMasterName(masterName);
        master.setPassword(password);
        master.setTrueName(trueName);
        master.setSex(sex);
        master.setDept(dept);
        master.setPosition(position);
        master.setPhone(phone);
        master.setEmail(email);
        master.setMasterId2(masterId2);
        master.setMasterName2(masterName2);
        master.setCreateDate(createDate);
        if (InsertDataUtil.insertMasterInfo(master)>0) {
            flag = true;
        }
        return flag;
    }

    public static boolean actionRegistration(String action, String actionName) {
        Action action1 = new Action();
        action1.setAction(action);
        action1.setActionName(actionName);
        boolean flag = false;
        if (InsertDataUtil.insertActionInfo(action1)>0) {
            flag = true;
        }
        return flag;
    }

    public static boolean groupRegistration(String groupName, String groupInfo,
                                            int masterId, String masterName) {
        Group group = new Group();
        group.setGroupName(groupName);
        group.setGroupInfo(groupInfo);
        group.setMasterId(masterId);
        group.setMasterName(masterName);
        group.setCreateDate(GetSystemInfoUtil.getTimeStamp());
        boolean flag = false;
        if (InsertDataUtil.insertGroupInfo(group)>0) {
            flag = true;
        }
        return flag;
    }

    public static boolean actionGroupRegistration(int actionId, int groupId, int masterId,
                                                  String masterName) {
        ActionGroup actionGroup = new ActionGroup();
        actionGroup.setActionId(actionId);
        actionGroup.setGroupId(groupId);
        actionGroup.setMasterId(masterId);
        actionGroup.setMasterName(masterName);
        actionGroup.setCreateDate(GetSystemInfoUtil.getTimeStamp());
        boolean flag = false;
        if (InsertDataUtil.insertActionGroupInfo(actionGroup)>0) {
            flag = true;
        }
        return flag;
    }

    public static boolean masterGroupRegistration(int masterId, String masterName, int groupId,
                                                  int masterId2, String masterName2) {
        MasterGroup masterGroup = new MasterGroup();
        masterGroup.setMasterId(masterId);
        masterGroup.setMasterName(masterName);
        masterGroup.setGroupId(groupId);
        masterGroup.setMasterId2(masterId2);
        masterGroup.setMasterName2(masterName2);
        masterGroup.setCreateDate(GetSystemInfoUtil.getTimeStamp());
        boolean flag = false;
        if (InsertDataUtil.insertMasterGroupInfo(masterGroup)>0) {
            flag = true;
        }
        return flag;
    }
    /******************************************************************************/

    /*****************************查询权限数据************************************/
    public static List<Action> queryAllActions() {
        return QueryDataUtil.selectAllActions();
    }

    public static Action queryActionById(int id) {
        return QueryDataUtil.selectActionById(id);
    }

    public static List<ActionGroup> queryAllActionGroups() {
        return QueryDataUtil.selectAllActionGroups();
    }

    public static ActionGroup queryActionGroupById(int id) {
        return QueryDataUtil.selectActionGroupById(id);
    }

    public static List<ActionGroup> queryActionGroupByGroupId(int groupId) {
        return QueryDataUtil.selectActionGroupByGroupId(groupId);
    }

    public static List<Group> queryAllGroups() {
        return QueryDataUtil.selectAllGroups();
    }

    public static Group queryGroupById(int id) {
        return QueryDataUtil.selectGroupById(id);
    }

    public static List<Master> queryAllMasters() {
        return QueryDataUtil.selectAllMasters();
    }

    public static Master queryMasterByName(String masterName) {
        return QueryDataUtil.selectMasterByName(masterName);
    }

    public static Master queryMasterById(int id) {
        return QueryDataUtil.selectMasterById(id);
    }

    public static List<MasterGroup> queryAllMasterGroups() {
        return QueryDataUtil.selectAllMasterGroups();
    }

    public static MasterGroup queryMasterGroupById(int id) {
        return QueryDataUtil.selectMasterGroupById(id);
    }

    public static MasterGroup queryMasterGroupByMasterId(int masterId) {
        return QueryDataUtil.selectMasterGroupByMasterId(masterId);
    }
    /******************************************************************************/

    /*****************************删除权限数据************************************/
    public static boolean deleteActionInfoById(int id) {
        boolean flag = false;
        if (DeleteDataUtil.deleteActionById(id)>0) {
            flag = true;
        }
        return flag;
    }

    public static boolean deleteActionGroupById(int id) {
        boolean flag = false;
        if (DeleteDataUtil.deleteActionGroupById(id)>0) {
            flag = true;
        }
        return flag;
    }

    public static boolean deleteGroupById(int id) {
        boolean flag = false;
        if (DeleteDataUtil.deleteGroupById(id)>0) {
            flag = true;
        }
        return flag;
    }

    public static boolean deleteMasterById(int id) {
        boolean flag = false;
        if (DeleteDataUtil.deleteMasterById(id)>0) {
            flag = true;
        }
        return flag;
    }

    public static boolean deleteMasterGroupById(int id) {
        boolean flag = false;
        if (DeleteDataUtil.deleteMasterGroupById(id)>0) {
            flag = true;
        }
        return flag;
    }
    /******************************************************************************/
}
