package com.study.app.sys.dao;

import com.study.app.sys.model.SysRole;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleDao extends JpaRepositoryImplementation<SysRole, Long> {
}
