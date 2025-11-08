package tech.ynfy.module.user.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ynfy.frame.util.QueryWrapperUtil;
import tech.ynfy.module.user.bean.SysUserListDTO;
import tech.ynfy.module.user.bean.SysUserListVO;
import tech.ynfy.module.user.bean.SysUserSaveDTO;
import tech.ynfy.module.user.bean.SysUserUpdateDTO;
import tech.ynfy.module.user.entity.SysUserPO;
import tech.ynfy.module.user.mapper.SysUserMapper;
import tech.ynfy.module.user.service.SysUserService;

import java.util.List;

/**
 * 〈〉
 *
 * @author Hsiong
 * @version 1.0.0
 * @since 2022/8/17
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserPO> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SysUserSaveDTO dto) {
        String name = dto.getName();
        List<SysUserPO> sysUserList = this.list(
            new LambdaQueryWrapper<SysUserPO>().eq(SysUserPO::getName, name)
        );
        if (ObjectUtil.isNotEmpty(sysUserList)) {
            throw new IllegalArgumentException("用户已存在!" + name);
        }
        SysUserPO sysUser = new SysUserPO();
        BeanUtil.copyProperties(dto, sysUser);
        sysUser.setPassword(DigestUtils.sha256Hex(dto.getPassword()));
        this.save(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(SysUserUpdateDTO dto) {
        Long id = dto.getId();
        SysUserPO entity = this.getById(id);
        if (ObjectUtil.isEmpty(entity)) {
            throw new IllegalArgumentException("用户表不存在! id: " + id);
        }
        BeanUtil.copyProperties(dto, entity);
        entity.setPassword(DigestUtils.sha256Hex(dto.getPassword()));
        this.updateById(entity);
    }
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        this.removeById(id);
    }
    
    @Override
    public IPage<SysUserListVO> list(SysUserListDTO dto) {
        Page<SysUserPO> page = new Page<>(dto.getPage(), dto.getRows());
        
        // 使用 QueryWrapperUtil 自动构建查询条件
        QueryWrapper<SysUserPO> wrapper = QueryWrapperUtil.buildQueryWrapper(dto);
        
        // 添加排序
        wrapper.orderByDesc("create_time");
        
        // 执行分页查询
        IPage<SysUserPO> result = sysUserMapper.selectPage(page, wrapper);
        
        // 转换为 VO
        Page<SysUserListVO> voPage =
            new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<SysUserListVO> voList = BeanUtil.copyToList(result.getRecords(), SysUserListVO.class);
        voPage.setRecords(voList);
        
        return voPage;
    }


}
