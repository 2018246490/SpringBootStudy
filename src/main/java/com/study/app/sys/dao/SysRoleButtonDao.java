package com.study.app.sys.dao;

import com.study.app.sys.model.SysRoleButton;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysRoleButtonDao extends JpaRepositoryImplementation<SysRoleButton, Long> {
    List findAllByRoleIdAndBdelete(Long roleId, boolean bdelete);

    @Query(nativeQuery = true, value = "select " +
            "a.id,a.parent_id as parentId,a.code,a.name,b.id as roleMenuId,b.create_flag as createFlag,b.update_flag as updateFlag,b.del_flag as delFlag,b.query_flag as queryFlag,b.upload_flag as uploadFlag,b.download_flag as downloadFlag " +
            "from sys_menu a left join sys_role_button b on a.code = b.menu_code and b.role_id = ?1 order by a.order_num")
    List<Map<String, Object>> queryRoleButtonByRoleId(Long roleId);
}
