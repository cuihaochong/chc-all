package com.chc.conf.validation;

/**
 * Description: validation分组
 *
 * @author cuihaochong
 * @date 2020/3/3
 */
public class ValidateGroups {

    /**
     * 通用分组
     */
    public interface Common {
    }

    /**
     * 新增分组
     */
    public interface Insert extends Common {
    }

    /**
     * 更新分组
     */
    public interface Update extends Common {
    }

    /**
     * 删除分组
     */
    public interface Delete {
    }

    /**
     * 其它分组
     */
    public interface Other {
    }
}
