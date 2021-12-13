package com.study.app.sys.dao;

import com.study.app.sys.model.SysParam;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysParamDao extends JpaRepositoryImplementation<SysParam, String> {
    List findAllByBdelete(boolean bdelete);

    @Modifying
    @Query(nativeQuery = true, value = "update sys_param set value=?2 where code=?1 ")
    void updateValueByCode(String code, String value);
}
