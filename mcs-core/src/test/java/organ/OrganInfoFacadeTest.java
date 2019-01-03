package organ;

import com.msjf.finance.cas.facade.organ.OrganInfoFacade;
import com.msjf.finance.cas.facade.organ.domain.OrganInfoDomain;
import com.msjf.finance.mcs.common.test.SpringTestCase;
import com.msjf.finance.mcs.modules.utils.SpringContextUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

/**
 * Created by 11509 on 2018/12/18.
 */

public class OrganInfoFacadeTest   extends SpringTestCase{
//    @Resource
//    CustInfoFacade custInfoFacade;
//    @Resource
//CifCustFacade cifCustFacade;
    @Resource
    OrganInfoFacade organInfoFacade ;
    @Test
    public  void  queryOrganInfoList(){
        System.out.println("--------------------------"+organInfoFacade.queryOrganInfoList(new OrganInfoDomain()));
//        while (true){
//
//        }
    }
}
