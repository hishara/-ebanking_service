package au.com.anz.wholesale.banking.backend.model;

import au.com.anz.wholesale.banking.backend.enumerate.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * Transaction entity.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction extends BaseModel {

    @Id
    @SequenceGenerator(
            name = "transaction_id_sequence",
            sequenceName = "transaction_id_sequence",
            initialValue = 2000000
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_sequence")
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "value_date", columnDefinition = "DATE", nullable = false)
    private LocalDate valueDate;

    @Column(name = "debit_amount")
    private Double debitAmount;

    @Column(name = "credit_amount", nullable = false)
    private Double creditAmount;

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // denotes debitOrCredit

    @Column(name = "transaction_narrative")
    private String transactionNarrative;

    @ManyToOne
    @JoinColumn(name = "account_number")
    private Account account;
}
