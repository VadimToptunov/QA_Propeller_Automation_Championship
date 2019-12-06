package enums;

public enum Constants {
    LOGINPAGE_URL("http://localhost:8080/index.html"),
    PROFILEPAGE_URL("http://localhost:8080/profile.html"),
    MAINPAGE_URL("http://localhost:8080/main.html"),
    ERRORPAGE_URL("http://localhost:8080/loginError.html"),
    LOGIN("test"),
    PASSWORD("test"),
    FIRSTNAME("Ivan"),
    LASTNAME("Berdyaev"),
    CARDNUMBER("35454656464456645"),
    PAYMENT_METHOD_VISA("Visa"),
    PAYMENT_METHOD_MASTERCARD("MasterCard"),
    PAYMENT_METHOD_APPLE("Apple Card"),
    EMPTY(""),
    INVALID_VALUE("@##$@$");


    private final String constant;

    Constants(String constant) {
        this.constant = constant;
    }

    public String getValue() {
        return constant;
    }

    
}
