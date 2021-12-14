package com.study.app.sys.service.impl;

import com.study.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DateFileService {
    public List getDateFile(String bid, String weixinName){
        List result = new ArrayList();
        if(StringUtils.isEmpty(bid) || StringUtils.isEmpty(weixinName)){
            return result;
        }
        String pahtPrefix = FileUtil.getWebAppsPath();
        File file = new File(pahtPrefix + "/beaconData/" + bid + "/"+ weixinName);

        if(!file.exists()){
            return result;
        }

        for(File f : file.listFiles()){
            Map fp = new HashMap();
            fp.put("name", f.getName());
            Date d = new Date(f.lastModified());
            DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
            fp.put("time", df.format(d));
            result.add(fp);
        }

        return result;
    }

}
