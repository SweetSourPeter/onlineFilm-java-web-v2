package edu.hitech.onlinefilm.controller;

import cn.hutool.core.util.RandomUtil;
import edu.hitech.onlinefilm.common.RespCode;
import edu.hitech.onlinefilm.common.ResultJSONObject;
import edu.hitech.onlinefilm.domain.Customer;
import edu.hitech.onlinefilm.exception.OnlineFilmException;
import edu.hitech.onlinefilm.utils.CacheUtils;
import edu.hitech.onlinefilm.utils.DataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
@Validated
public class CustomerController {

    @Autowired
    private DataHelper dataHelper;
    private static final String BASE_URL="ABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890";

    @PostMapping("/register")
    public ResultJSONObject  register(@RequestBody @Validated Customer registerInfo){
          ResultJSONObject result = ResultJSONObject.success();
          if (!doRegister(registerInfo)){
              result = new ResultJSONObject(RespCode.FAIL);
          }
          return result;
    }



    @GetMapping("/login")
    public ResultJSONObject login(@NotEmpty(message = "账号不能为空") String account,@NotEmpty String password){
            ResultJSONObject result = ResultJSONObject.success();
            String token = doLogin(account,password);
            if (token == null ){
                result = new ResultJSONObject(RespCode.USER_ACCOUNT_ERROR);
            }else{
                 Map<String,Object> data = new HashMap<>(1);
                 data.put("token",token);
                 data.put("customer",CacheUtils.getUser(token));
                 result.setData(data);
            }
            return result;
        }


    @GetMapping("/logout")
        public ResultJSONObject logout(){
            ResultJSONObject result = ResultJSONObject.success();
            doLogout();
            return result;
        }




    private String doLogin(String account, String password) {
            String token = null;
            Customer customer = isValidCustomer(account,password);
            if (customer!=null){
                //登陆成功，生成token 并缓存用户信息
                token = RandomUtil.randomString(BASE_URL,40);
                CacheUtils.cacheUser(token,customer);
            }
            return token;
        }

    private void doLogout() {
            CacheUtils.removeUser();
    }


        private boolean doRegister(Customer registerInfo) {
            Customer custom  = dataHelper.getCustomByAccount(registerInfo.getAccount());
            if (custom != null){
                throw new OnlineFilmException(RespCode.USER_ACCOUNT_DUPLICATE);
            }
            return  dataHelper.addCustom(registerInfo);
        }

        private Customer isValidCustomer(String account, String password){
            Customer result = dataHelper.getCustomByAccount(account);
            if (result!=null && !password.equals(result.getPassword())){
                    result = null;
            }
            return result;
        }
}
