package com.avatech.edi.mdm.businessone.masterdata;

import com.avatech.edi.mdm.bo.IProfitCenter;
import com.avatech.edi.mdm.config.B1Connection;
import com.avatech.edi.mdm.config.DataTemple;

import java.util.List;

public interface B1ProfitCenterService {

    String syncProfitCenter(IProfitCenter profitCenter, B1Connection b1Connection,List<DataTemple> dataTempleList);
}
