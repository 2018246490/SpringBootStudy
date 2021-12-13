package com.study.app.sys.dao;

import com.study.app.sys.model.SysOperatingLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysOperatingLogDao extends JpaRepositoryImplementation<SysOperatingLog, Long> {
    @Query(nativeQuery = true, value = "select module from sys_operating_log group by module")
    List<Object> findAllType();
}
