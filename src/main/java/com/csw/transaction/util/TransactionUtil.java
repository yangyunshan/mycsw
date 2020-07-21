package com.csw.transaction.util;

import com.csw.data.util.DeleteDataUtil;
import com.csw.data.util.InsertDataUtil;

public class TransactionUtil {

    /**
     * 注册元数据到数据库中
     *
     * @param information:
     *            需注册的元数据内容
     * @param type:
     *            元数据类型
     * @param owner:
     *             所有者
     *
     * @return boolean: 注册成功返回true，失败返回false
     * */
    public boolean insertData(String information, String type, String owner) {
        return InsertDataUtil.parseAndSaveXMLDocumentByContent(information,type,owner);
    }

    /**
     * 更新数据库中的元数据
     *
     * 此处的更新相当于删除和注册两个过程的结合，即：先将目标数据删除，然后注册新的数据（数据标识不变）
     *
     * @param id :
     *          需要更新数据的唯一标识
     * @param information :
     *                    新的元数据内容（ebrim形式）
     * @param type :
     *             元数据类型
     * @param owner :
     *              所有者
     *
     * @return boolean: 更新成功返回true，失败返回false
     * */
    public boolean updateData(String id, String information, String type, String owner) {
        DeleteDataUtil.deleteRegistryPackageContent(id);
        return InsertDataUtil.parseAndSaveXMLDocumentByContent(information,type,owner);
    }

    /**
     * 通过资源id删除数据库中的元数据
     *
     * @param id:
     *          要删除资源的id
     *
     * @return boolean: 注册成功返回true，失败返回false
     * */
    public boolean deleteData(String id) {
        int status = DeleteDataUtil.deleteRegistryPackageContent(id);
        if (status>0) {
            return true;
        } else {
            return false;
        }
    }
}
