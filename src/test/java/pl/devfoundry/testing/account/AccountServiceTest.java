package pl.devfoundry.testing.account;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;

import java.util.Collections;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class AccountServiceTest {

    @Test
    void getAllActiveAccount() {

//        given
        List<Account> accounts = prepareAccountData();
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        when(accountRepository.getAllAccounts()).thenReturn(accounts);

//        when
        List<Account> accountList = accountService.getAllActiveAccounts();

//        then
        assertThat(accountList, hasSize(2));
    }

    private List<Account> prepareAccountData() {

        Address address1 = new Address("Kwiatowa", "33/5");
        Account account1 = new Account(address1);

        Account account2 = new Account();

        Address address2 = new Address("Piekarska", "12b");
        Account account3 = new Account(address2);

        return Arrays.asList(account1, account2, account3);
    }

    @Test
    void getAccountsByName() {

//        given
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        given(accountRepository.getByName("dawid")).willReturn(Collections.singletonList("Nowak"));

//        when
        List<String> accountNames = accountService.findByName("dawid");

//        then
        assertThat(accountNames, contains("Nowak"));
    }
}
