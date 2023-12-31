package com.flyme.bigenvent.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Integer id;//主键ID
    @NotEmpty
    private String categoryName;//分类名称
@NotEmpty
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
