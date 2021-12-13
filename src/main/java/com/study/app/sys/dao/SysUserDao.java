package com.study.app.sys.dao;

import com.study.app.sys.model.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface SysUserDao extends JpaRepositoryImplementation<SysUser, Long> {
//    @Query(nativeQuery = true, value = "select * from dt_examine_ass_room where room_id=?1")
//    SysUsers loginCheck(String roomId);

    SysUser findSysUserByUserCodeAndPassword(String usercooe, String password);

    @Query(nativeQuery = true,
            value = "select a.id as id,a.user_code as userCode,a.nick_name as nickName,a.avatar as avatar,b.name as roleName,a.rmk as rmk,a.telephone as telephone from sys_user a left join sys_role b on a.role_id = b.id where if (?1 is not null,a.user_code like concat('%',?1,'%') or a.nick_name like concat('%',?1,'%'),1=1)",
            countQuery = "select count(*) from sys_user a left join sys_role b on a.role_id = b.id where if (?1 is not null,a.user_code like concat('%',?1,'%') or a.nick_name like concat('%',?1,'%'),1=1)")
    Page<Map> queryPage(String userName, Pageable pageable);

    SysUser findSysUserById(Long id);

    List<SysUser> findSysUserByTelephone(String telephone);

    SysUser findSysUserByUserCodeAndTelephone(String userCode, String telephone);
}
