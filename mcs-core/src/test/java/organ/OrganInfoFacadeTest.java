package organ;

import com.msjf.finance.mcs.common.test.SpringTestCase;
import com.msjf.finance.mcs.facade.organ.CifCustFacade;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by 11509 on 2018/12/18.
 */

public class OrganInfoFacadeTest   extends SpringTestCase{
//    @Resource
//    CustInfoFacade custInfoFacade;
    @Resource
    CifCustFacade cifCustFacade;
    @Test
    public  void  queryOrganInfoList(){
        System.out.println("--------------------------"+cifCustFacade.queryCifCustList());
//        while (true){
//
//        }
    }
}
