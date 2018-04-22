package com.xxx.homework.Service;

import com.xxx.homework.model.TimeSeriesData;
import com.xxx.homework.dao.Mapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class TimeSeriesDataService {

    @Resource
    private Mapper mapper;

    public boolean insertValues(List<TimeSeriesData> datas) {
        return mapper.executeInsert(datas);
    }
}
