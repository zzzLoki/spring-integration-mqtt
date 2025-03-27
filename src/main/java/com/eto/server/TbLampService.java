package com.eto.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eto.domain.TbLamp;

/**
* @author LOKI
* @description 针对表【tb_lamp】的数据库操作Service
* @createDate 2025-03-26 20:38:48
*/
public interface TbLampService extends IService<TbLamp> {

    void updateLampOnlineStatus(String jsonInfo);
}
