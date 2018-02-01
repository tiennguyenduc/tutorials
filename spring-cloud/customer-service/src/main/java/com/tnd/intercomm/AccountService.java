package com.tnd.intercomm;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tnd.entity.Account;

@FeignClient("ACCOUNT-SERVICE")
public interface AccountService {

    @RequestMapping(method = RequestMethod.GET, value = "/customer/{customerId}")
    List<Account> getAccount(@PathVariable("customerId") Integer customerId);

}
