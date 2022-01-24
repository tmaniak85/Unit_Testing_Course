package pl.devfoundry.testing.account;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import pl.devfoundry.testing.account.Account;
import pl.devfoundry.testing.account.Address;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class AccountTest {

    @Test
    void newAccountshouldNotBeActiveAfterCreation() {
//        given
//        when
        Account newAccount = new Account();
//        then
        assertFalse(newAccount.isActive(), "Check if new account is not active");
        assertThat(newAccount.isActive(), equalTo(false));
        assertThat(newAccount.isActive(), is(false));
    }

    @Test
    void newAccountShouldBeActiveAfterActivation() {
//        given
        Account newAccount = new Account();
        assertFalse(newAccount.isActive());
//        activate
        newAccount.activate();
//        then
        assertTrue(newAccount.isActive());
        assertThat(newAccount.isActive(), equalTo(true));
    }

    @Test
    void newlyCreatedAccountShouldNotHaveDefaultAddessSet() {

//        given
        Account account = new Account();

//        when
        Address address = account.getDefaultDeliveryAddress();

//        then
        assertNull(address);
        assertThat(address, is(nullValue()));
    }

    @Test
    void defaultDeliveryAddressShouldNotBeNullAfterBeingSet() {

//        when
        Address address = new Address("Krakowska", "67c");
        Account account = new Account();
        account.setDefaultDeliveryAddress(address);

//        given
        Address defaultAddress = account.getDefaultDeliveryAddress();

//        then
        assertNotNull(defaultAddress);
        assertThat(defaultAddress, is(notNullValue()));

    }

    @RepeatedTest(5)
    void newAccountWithNotNullAddressShouldBeActive() {

//        given
        Address address = new Address("Puławska", "46/6");

//        when
        Account account = new Account(address);

//        then

        assumingThat(address != null, () -> {
            assertTrue(account.isActive());
        });
    }

    @Test
    void invalidEmailShouldThrowException() {

//        given
        Account account = new Account();

//        when
//        then
        assertThrows(IllegalArgumentException.class, () -> account.setEmail("wrong email"));

    }

    @Test
    void validEmailShouldBeSet() {

//        given
        Account account = new Account();

//        when
        account.setEmail("azo8@interia.pl");
//        then
        assertThat(account.getEmail(), is("azo8@interia.pl"));

    }

}
