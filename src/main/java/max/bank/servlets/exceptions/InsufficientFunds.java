package max.bank.servlets.exceptions;

public class InsufficientFunds extends RuntimeException {

    String message;
    Integer code;

    public InsufficientFunds(String message, Integer code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
