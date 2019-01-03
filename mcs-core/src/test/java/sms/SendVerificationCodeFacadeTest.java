package sms;

import com.google.common.collect.Maps;
import com.msjf.finance.mcs.common.test.SpringTestCase;
import com.msjf.finance.mcs.facade.sms.SendVerificationCodeFacade;
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
    @Autowired
    protected ApplicationContext ctx;
    @Test
    public  void  queryOrganInfoList(){
        HashMap map= Maps.newHashMap();
        map.put("verificatetype","1");
        map.put("templateId","2031012026749");
        map.put("mobile","18565641675");
        System.out.println("--------------------------"+sendVerificationCodeFacade.SendRegisterVerificationCode(map));
        while (true){

        }
    }
}

