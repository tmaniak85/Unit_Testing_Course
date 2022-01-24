package pl.devfoundry.testing.account;

public class Account {

    private boolean active;
    private Address defaultDeliveryAddress;
    private String email;

    public Account(Address defaultDeliveryAddress) {
        this.defaultDeliveryAddress = defaultDeliveryAddress;
        if(defaultDeliveryAddress != null) {
            activate();
        } else {
            this.active = false;
        }
    }

    public Address getDefaultDeliveryAddress() {
        return defaultDeliveryAddress;
    }

    public void setDefaultDeliveryAddress(Address defaultDeliveryAddress) {
        this.defaultDeliveryAddress = defaultDeliveryAddress;
    }

    public Account() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setEmail(String email) {

        if(email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Wrong email format");
        }

    }

    public String getEmail() {
        return email;
    }
}
