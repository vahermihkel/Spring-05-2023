package ee.mihkel.nordpool;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Person {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

}
