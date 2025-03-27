package com.eto.server.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eto.domain.TbLamp;
import com.eto.mapper.TbLampMapper;
import com.eto.server.TbLampService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
* @author LOKI
* @description 针对表【tb_lamp】的数据库操作Service实现
* @createDate 2025-03-26 20:38:48
*/
@Service
public class TbLampServiceImpl extends ServiceImpl<TbLampMapper, TbLamp>
    implements TbLampService {
    @Override
    public void updateLampOnlineStatus(String jsonInfo) {
        //这个实现类如何被调用的？MQTT 消息监听机制触发，即ReceiverMessageHandler.handleMessage调用
        // 数据流：设备 → EMQX → ReceiverMessageHandler → TbLampServiceImpl → 数据库
        // 解析消息获取设备id和上线状态
        Map<String ,  Object> map = JSON.parseObject(jsonInfo, Map.class);
        String deviceId = map.get("deviceId").toString();
        Integer status = Integer.parseInt(map.get("online").toString());

        // 根据设备的id查询设备数据
        LambdaQueryWrapper<TbLamp> lambdaQueryWrapper = new LambdaQueryWrapper<>() ;
        lambdaQueryWrapper.eq(TbLamp::getDeviceid , deviceId) ;
        TbLamp tbLamp = this.getOne(lambdaQueryWrapper);
        if(tbLamp == null) {        // 设备不存在，新增设备
            tbLamp = new TbLamp() ;
            tbLamp.setDeviceid(deviceId);
            tbLamp.setStatus(status);
            this.save(tbLamp) ;
        }else {     // 设备已经存在，修改设备的状态
            tbLamp.setStatus(status);
            tbLamp.setUpdateTime(new Date());
            this.updateById(tbLamp) ;
        }
    }

}





