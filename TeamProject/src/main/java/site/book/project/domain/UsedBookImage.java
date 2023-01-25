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
@Entity(name = "USEDBOOKIMAGE")
@SequenceGenerator(name = "USEDBOOKIMAGE_SEQ_GEN", sequenceName = "USEDBOOKIMAGE_SEQ", allocationSize = 1)
public class UsedBookImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USEDBOOKIMAGE_SEQ_GEN")
    private Integer id;
    
    @Column(nullable = false)
    private Integer usedBookId;
    
    @Column(nullable = false)
    private String origFileName;
    
    @Column(nullable = false)
    private String fileName;
    
    @Column(nullable = false)
    private String filePath;

}
