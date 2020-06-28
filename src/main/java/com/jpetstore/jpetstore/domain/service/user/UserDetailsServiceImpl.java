package com.jpetstore.jpetstore.domain.service.user;

import java.util.List;

import com.jpetstore.jpetstore.domain.model.Account;
import com.jpetstore.jpetstore.domain.model.Product;
import com.jpetstore.jpetstore.domain.service.account.AccountService;
import com.jpetstore.jpetstore.domain.service.catalog.CatalogService;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Inject
    protected AccountService accountService;

    @Inject
    protected CatalogService catalogService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.getAccount(username);
        if (account == null) {
            throw new UsernameNotFoundException(username + " is not found.");
        }
        List<Product> myList = catalogService.getProductListByCategory(account
                .getFavouriteCategoryId());
        return new com.jpetstore.jpetstore.domain.model.UserDetails(account, myList);
    }

}
