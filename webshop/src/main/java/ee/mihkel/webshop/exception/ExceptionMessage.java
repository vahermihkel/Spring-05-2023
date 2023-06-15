package ee.mihkel.webshop.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ExceptionMessage {
    private String message;
    private Date timestamp;
    private int status;
}
