package com.avatech.edi.mdm.businessone.masterdata;

import com.avatech.edi.mdm.businessone.B1Exception;
import com.avatech.edi.mdm.businessone.BORepositoryBusinessOne;
import com.avatech.edi.mdm.bo.IAccount;
import com.avatech.edi.mdm.config.B1Connection;
import com.avatech.edi.mdm.config.DataTemple;
import com.sap.smb.sbo.api.*;
import org.springframework.stereotype.Component;

import java.security.acl.Group;
import java.util.List;

/**
 * 科目生成服务
 */
@Component
public class B1AccountServiceImp implements B1AccountService {

    private static final String YES = "Y";
    private static final String NO = "N";
    private static final String EXPRESS = "E";
    private static final String REVENUES = "I";
    private static final String OTHER = "N";

    private static final String ACCTTYPE = "GroupType";


    @Override
    public String syncAccount(IAccount account, B1Connection b1Connection,List<DataTemple> dataTempleList){
        BORepositoryBusinessOne boRepositoryBusinessOne = null;
        ICompany company = null;
        try {
            //get company info
            boRepositoryBusinessOne = BORepositoryBusinessOne.getInstance(b1Connection);
            company = boRepositoryBusinessOne.getCompany();

            IChartOfAccounts chartOfAccounts = SBOCOMUtil.newChartOfAccounts(company);
            chartOfAccounts.setCode(account.getAcctCode());
            chartOfAccounts.setName(account.getAcctName());

            for (DataTemple temple:dataTempleList) {
                if(temple.getFieldName().toUpperCase().equals(ACCTTYPE)){
                    //chartOfAccounts.set
                }
            }
            chartOfAccounts.setAcctCurrency(account.getActCur());
            if(account.getFinase()!= null && account.getFinase().equals(YES)){
                chartOfAccounts.setCashAccount(SBOCOMConstants.BoYesNoEnum_tYES);
            }else {
                chartOfAccounts.setCashAccount(SBOCOMConstants.BoYesNoEnum_tNO);
            }
            if(account.getPostable() != null && account.getPostable().equals(YES)){
                chartOfAccounts.setActiveAccount(SBOCOMConstants.BoYesNoEnum_tYES);
            }else {
                chartOfAccounts.setActiveAccount(SBOCOMConstants.BoYesNoEnum_tNO);
            }

            if(account.getFatherAccountKey() != null && !account.getFatherAccountKey().isEmpty()){
                chartOfAccounts.setFatherAccountKey(account.getFatherAccountKey());
            }

            if(account.getActType() != null) {
                chartOfAccounts.setAccountType(getActType(account.getActType()));
            }

            int rstCode;
            if(chartOfAccounts.getByKey(account.getAcctCode())){
                rstCode = chartOfAccounts.update();
            }else {
                rstCode = chartOfAccounts.add();
            }

            if(rstCode == 0){
                return company.getNewObjectKey();
            }else {
                throw new B1Exception(company.getLastErrorCode() + ":" + company.getLastErrorDescription());
            }
        }catch (SBOCOMException e){
            throw new B1Exception(e);
        }catch (Exception e){
            throw e;
        }
    }

    private int getActType(String actType){
        switch (actType){
            case EXPRESS:return SBOCOMConstants.BoAccountTypes_at_Expenses;
            case REVENUES:return SBOCOMConstants.BoAccountTypes_at_Revenues;
            case OTHER:return SBOCOMConstants.BoAccountTypes_at_Other;
            default: return -1;
        }
    }
}
