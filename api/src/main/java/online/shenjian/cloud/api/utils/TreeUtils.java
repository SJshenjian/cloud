package online.shenjian.cloud.api.utils;


import online.shenjian.cloud.api.system.model.Module;
import online.shenjian.cloud.api.system.model.Org;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleTreeDto;
import online.shenjian.cloud.client.cloud.dto.system.org.OrgTreeDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 树结构组装工具类
 *
 * @author shenjian
 * @since 2023/8/11
 */
public class TreeUtils {

    /**
     * 获得组织机构树级结构
     *
     * @param list     原始数据列表
     * @param rootCode 根节点编码
     * @return
     * @throws IOException
     */
    public static List<OrgTreeDto> listOrgTree(List<Org> list, String rootCode) {
        // 存储根节点的菜单，即一级菜单
        List<OrgTreeDto> rootlist = new ArrayList<>();
        // 遍历所有数据，找到根节点菜单
        for (Org info : list) {
            if (info.getOrgCode().equals(rootCode)) {
                OrgTreeDto root = new OrgTreeDto();
                root.setLabel(info.getOrgName());
                root.setOrgCode(info.getOrgCode());
                // 找到根节点菜单的时候，寻找这个根节点菜单下的子节点菜单
                findOrgChilds(root, list);
                // 添加到根节点的列表中
                rootlist.add(root);
            }
        }
        return rootlist;
    }

    private static void findOrgChilds(OrgTreeDto root, List<Org> list) {
        List<OrgTreeDto> childlist = new ArrayList<>();
        // 遍历所有数据，找到是入参父节点的子节点的数据，然后加到childlist集合中。
        for (Org info : list) {
            if (root.getOrgCode().equals(info.getParentCode())) {
                OrgTreeDto child = new OrgTreeDto();
                child.setLabel(info.getOrgName());
                child.setOrgCode(info.getOrgCode());
                childlist.add(child);
            }
        }
        // 若子节点不存在，那么就不必再遍历子节点中的子节点了 直接返回。
        if (childlist.size() == 0) {
            return;
        }
        // 设置父节点的子节点列表
        root.setChildren(childlist);
        // 若子节点存在，接着递归调用该方法，寻找子节点的子节点。
        for (OrgTreeDto childs : childlist) {
            findOrgChilds(childs, list);
        }
    }

    /**
     * 获得菜单树级结构
     *
     * @param list     原始数据列表
     * @param parentCode 根节点编码
     * @return
     * @throws IOException
     */
    public static List<ModuleTreeDto> listModuleTree(List<Module> list, String parentCode) {
        // 存储根节点的菜单，即一级菜单
        List<ModuleTreeDto> rootlist = new ArrayList<>();
        // 遍历所有数据，找到根节点菜单
        for (Module info : list) {
            if (info.getParentId().equals(parentCode)) {
                ModuleTreeDto root = new ModuleTreeDto();
                root.setLabel(info.getModuleName());
                root.setModuleId(info.getModuleId());
                // 找到根节点菜单的时候，寻找这个根节点菜单下的子节点菜单
                findModuleChilds(root, list);
                // 添加到根节点的列表中
                rootlist.add(root);
            }
        }
        return rootlist;
    }

    private static void findModuleChilds(ModuleTreeDto root, List<Module> list) {
        List<ModuleTreeDto> childlist = new ArrayList<>();
        // 遍历所有数据，找到是入参父节点的子节点的数据，然后加到childlist集合中。
        for (Module info : list) {
            if (root.getModuleId().equals(info.getParentId())) {
                ModuleTreeDto child = new ModuleTreeDto();
                child.setLabel(info.getModuleName());
                child.setModuleId(info.getModuleId());
                childlist.add(child);
            }
        }
        // 若子节点不存在，那么就不必再遍历子节点中的子节点了 直接返回。
        if (childlist.size() == 0) {
            return;
        }
        // 设置父节点的子节点列表
        root.setChildren(childlist);
        // 若子节点存在，接着递归调用该方法，寻找子节点的子节点。
        for (ModuleTreeDto childs : childlist) {
            findModuleChilds(childs, list);
        }
    }
}
