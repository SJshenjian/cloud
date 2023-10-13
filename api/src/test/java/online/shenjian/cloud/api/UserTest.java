package online.shenjian.cloud.api;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.shenjian.cloud.api.mapper.UserMapper;
import online.shenjian.cloud.api.mapper.UserPlusMapper;
import online.shenjian.cloud.api.model.User;
import online.shenjian.cloud.client.cloud.dto.UserDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenjian
 * @since 2023/10/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApplication.class)
public class UserTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserPlusMapper userPlusMapper;

    /**
     * 批量插入
     */
    @Test
    public void testBatchSave() {
        List<User> users = new ArrayList<>();

        User userOne = new User();
        userOne.setId(IdUtil.getSnowflakeNextIdStr());
        userOne.setName("沈健");
        userOne.setUsername("shenjian");

        User userTwo = new User();
        userTwo.setId(IdUtil.getSnowflakeNextIdStr());
        userTwo.setName("算法小生");
        userTwo.setUsername("sfxs");

        users.add(userOne);
        users.add(userTwo);
        userMapper.insertBatchSomeColumn(users);
    }

    /**
     * 分页查询
     */
    @Test
    public void testPage() {
        IPage<UserDto> page = new Page<>(1, 10);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", "sfxs");
        Page<UserDto> resultPage = userPlusMapper.page(page, queryWrapper);
        Assert.assertNotNull(resultPage.getRecords());
        Assert.assertEquals(1, resultPage.getTotal());
    }
}
