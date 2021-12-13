package com.study.app.sys.service.impl;

import com.study.app.sys.dao.SysOperatingLogDao;
import com.study.app.sys.model.SysOperatingLog;
import com.study.app.sys.service.SysOperatingLogService;
import com.study.tool.login.LoginCacheManage;
import com.study.tool.login.LoginInfo;
import com.study.tool.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service("sysOperatingLogService")
public class SysOperatingLogServiceImpl implements SysOperatingLogService {

    @Autowired
    private SysOperatingLogDao sysOperatingLogDao;

    @Override
    public PageModel findByPage(Map<String, Object> map, PageModel page) {
        //页码：前端从1开始，jpa从0开始，做个转换
        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
        Page<SysOperatingLog> hpPage = sysOperatingLogDao.findAll(new Specification<SysOperatingLog>() {
            @Override
            public Predicate toPredicate(Root<SysOperatingLog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

                List<Predicate> preds = new ArrayList<>();

                if (!StringUtils.isEmpty(map.get("userCode"))) {
                    Predicate p = builder.like(root.get("userCode").as(String.class), "%" + map.get("userCode") + "%");
                    preds.add(p);
                }

                if (!StringUtils.isEmpty(map.get("type"))) {
                    Predicate p = builder.equal(root.get("type").as(String.class), map.get("type"));
                    preds.add(p);
                }

                if (!StringUtils.isEmpty(map.get("module"))) {
                    Predicate p = builder.equal(root.get("module").as(String.class), map.get("module"));
                    preds.add(p);
                }

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date sdate = null,edate = null;
                try {
                    if(map.get("stime") != null){
                        sdate = df.parse(String.valueOf(map.get("stime")));
                    }
                    if(map.get("etime") != null){
                        edate = df.parse(String.valueOf(map.get("etime")));
                    }
                } catch (ParseException e) {
//                    e.printStackTrace();
                }
                if (sdate != null) {
                    Predicate p = builder.greaterThanOrEqualTo(root.get("time"), sdate);
                    preds.add(p);
                }

                if (edate != null) {
                    Predicate p = builder.lessThanOrEqualTo(root.get("time"), edate);
                    preds.add(p);
                }

                return builder.and(preds.toArray(new Predicate[0]));
            }
        }, pageable);
        page.setData(hpPage.getContent());
        page.setTatalPageNum(hpPage.getTotalPages());
        page.setTotalNum(hpPage.getTotalElements());
        return page;
    }
    public List findAllType() {
        return sysOperatingLogDao.findAllType();
    }

    public void saveLog(Integer type, String module, String rmk) {
        SysOperatingLog log = new SysOperatingLog();
        log.setType(type);
        log.setModule(module);
        log.setRmk(rmk);
        log.setTime(new Date());
        LoginInfo info = LoginCacheManage.getLoginUser();
        if (info != null) {
            log.setUserCode(info.getUser().getUserCode());
        } else {
            log.setUserCode("");
        }
        sysOperatingLogDao.save(log);
    }

    public void saveLog(Integer type, String module, String rmk, String userCode) {
        SysOperatingLog log = new SysOperatingLog();
        log.setType(type);
        log.setModule(module);
        log.setRmk(rmk);
        log.setTime(new Date());
        log.setUserCode(userCode);
        sysOperatingLogDao.save(log);
    }
}
