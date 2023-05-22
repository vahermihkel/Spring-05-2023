package ee.mihkel.webshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders") // @Table(name = "users")
@SequenceGenerator(name="seq", initialValue = 41312313, allocationSize = 1)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;
    private boolean paid;
    private double totalSum;
    private Date creationDate;
    @ManyToMany
    private List<Product> products;
    @ManyToOne
    private Person person;
}
