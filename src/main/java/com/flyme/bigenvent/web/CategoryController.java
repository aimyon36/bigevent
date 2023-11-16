package com.flyme.bigenvent.web;

import com.flyme.bigenvent.pojo.Category;
import com.flyme.bigenvent.pojo.Result;
import com.flyme.bigenvent.service.CategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/category")
//分类接口
public class CategoryController {
    @Resource
    private CategoryService categoryService;
  @PostMapping
    public Result add(@RequestBody @Validated Category category){
      categoryService.add(category);
      return Result.success();
        }

  }

