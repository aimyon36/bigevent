package com.flyme.bigenvent.service.impl;

import com.flyme.bigenvent.mapper.CategoryMapper;
import com.flyme.bigenvent.pojo.Category;
import com.flyme.bigenvent.service.CategoryService;
import com.flyme.bigenvent.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    @Override
    public void add(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer UserId = (Integer) map.get("id");
        category.setCreateUser(UserId);
        categoryMapper.add(category);
    }
}
