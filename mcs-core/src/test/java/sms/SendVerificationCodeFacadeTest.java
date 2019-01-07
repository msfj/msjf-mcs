package sms;

import com.google.common.collect.Maps;
import com.msjf.finance.mcs.common.response.Response;
import com.msjf.finance.mcs.common.test.SpringTestCase;
import com.msjf.finance.mcs.facade.sms.SendVerificationCodeFacade;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;
import com.msjf.finance.mcs.modules.utils.SpringContextUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.HashMap;

public class SendVerificationCodeFacadeTest extends SpringTestCase {
    //    @Resource
//    CustInfoFacade custInfoFacade;
    @Resource
    SendVerificationCodeFacade sendVerificationCodeFacade;
    @Test
    public  void  queryOrganInfoList(){
        HashMap map= Maps.newHashMap();
        map.put("verificateType","1");
        //map.put("templateId","2031012026749");
        map.put("mobile","18565641675");
        map.put("msgCode","6098");
        Response<VerificationCodeDomain> rs=sendVerificationCodeFacade.SendRegisterVerificationCode(map);
//        System.out.println("--------------------------"+rs);
//        rs.getData().getSeqNum();
        System.out.println("--------------------------"+sendVerificationCodeFacade.checkVerificationCode(map));

        while (true){

        }
    }
}

