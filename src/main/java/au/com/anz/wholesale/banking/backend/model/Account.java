package au.com.anz.wholesale.banking.backend.model;

import au.com.anz.wholesale.banking.backend.enumerate.AccountType;
import au.com.anz.wholesale.banking.backend.enumerate.CurrencyType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Account entity.
 */
@SuperBuilder
@Data
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account extends BaseModel {

    @Id
    @SequenceGenerator(
            name = "account_number_sequence",
            sequenceName = "account_number_sequence",
            initialValue = 1000000
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_number_sequence")
    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "balance_date", columnDefinition = "DATE", nullable = false)
    private LocalDate balanceDate;

    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(name = "opening_available_balance", nullable = false)
    private Double openingAvailableBalance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.MERGE)
    private Set<Transaction> transactions;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

}
