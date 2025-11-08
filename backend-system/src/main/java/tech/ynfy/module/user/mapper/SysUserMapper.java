package tech.ynfy.module.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import tech.ynfy.module.user.entity.SysUserPO;

/**
 * SysUser Mapper 接口
 * <p>
 * 注意：如果需要使用连表查询，可以改为继承 MPJBaseMapper
 * import com.github.yulichang.base.MPJBaseMapper;
 * public interface SysUserMapper extends MPJBaseMapper<SysUserPO>
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUserPO> {
	// BaseMapper 已提供基础的 CRUD 方法
	// 如需连表查询，改为继承 MPJBaseMapper
}
