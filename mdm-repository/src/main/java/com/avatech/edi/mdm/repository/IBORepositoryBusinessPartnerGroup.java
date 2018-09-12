package com.avatech.edi.mdm.repository;

import com.avatech.edi.mdm.bo.BusinessPartnerGroup;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Fancy
 * @date 2018/9/6
 */
public interface IBORepositoryBusinessPartnerGroup extends CrudRepository<BusinessPartnerGroup,Integer> {

    /**
     * 查找业务伙伴组
     * @param code 组代码
     * @return
     */
    BusinessPartnerGroup findByGrpCode(Integer code);

}
