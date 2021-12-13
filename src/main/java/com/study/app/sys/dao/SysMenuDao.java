package com.study.app.sys.dao;

import com.study.app.sys.model.SysMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuDao extends JpaRepositoryImplementation<SysMenu, Long> {
    List findAllByBdeleteOrderByOrderNum(boolean bdelete);

    @Query(nativeQuery = true, value = "select a.* from sys_menu a where EXISTS(select 1 from sys_role_button b where b.menu_code = a.code and b.role_id = ?1 )")
    List<SysMenu> findUserMenu(Long roleId);
}
