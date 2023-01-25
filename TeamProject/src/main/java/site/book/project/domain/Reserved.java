package site.book.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@Entity(name = "RESERVED")
@SequenceGenerator(name = "RESERVED_SEQ_GEN", sequenceName = "RESERVED_SEQ", allocationSize = 1)
public class Reserved {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESERVED_SEQ_GEN")
    private Integer id;
    
    @Column(nullable = false)
    private Integer userId;
    
    @Column(nullable = false)
    private Integer usedBookId;
    
}
