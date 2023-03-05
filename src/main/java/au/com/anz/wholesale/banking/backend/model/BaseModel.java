package au.com.anz.wholesale.banking.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base entity which holds common attributes.
 */
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(value = { "dateCreated", "lastUpdated" })
public class BaseModel implements Serializable {

    @CreatedDate
    @Column(name = "date_created", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(name = "last_updated", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdated;
}
