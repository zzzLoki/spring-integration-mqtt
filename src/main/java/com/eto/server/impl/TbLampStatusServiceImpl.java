package com.eto.server.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.eto.domain.TbLampStatus;
import com.eto.mapper.TbLampStatusMapper;
import com.eto.server.TbLampStatusService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author LOKI
* @description 针对表【tb_lamp_status】的数据库操作Service实现
* @createDate 2025-03-26 20:38:48
*/
@Service
public class TbLampStatusServiceImpl extends ServiceImpl<TbLampStatusMapper, TbLampStatus>
    implements TbLampStatusService {
    @Override
    public void saveDeviceStatus(String json) {

        // 获取消息内容
        Map<String , Object> map = JSON.parseObject(json, Map.class);
        String deviceId = map.get("deviceId").toString();
        Integer status = Integer.parseInt(map.get("status").toString());

        // 创建对象封装消息
        TbLampStatus tbLampStatus = new TbLampStatus() ;
        tbLampStatus.setDeviceid(deviceId);
        tbLampStatus.setStatus(status);
        this.save(tbLampStatus) ;

    }
}




