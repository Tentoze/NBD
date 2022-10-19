package library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book")
@NamedQueries({
        @NamedQuery(name = "Book.findBySerialNumber", query = "select b from Book b where b.serialNumber = :serialNumber"),
        @NamedQuery(name = "Book.findAll", query = "select b from Book b"),
        @NamedQuery(name = "Book.updateIsArchiveBySerialNumber", query = "update Book b set b.isArchive = :isArchive where b.serialNumber = :serialNumber")
})
@Access(AccessType.FIELD)
@Getter
@NoArgsConstructor
public class Book extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "client_id")
    private long id;
    @Column(unique = true)
    private String serialNumber;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String genre;

    @Column
    @Setter
    private boolean isArchive;

    public Book(String title, String author, String serialNumber, String genre) {
        this.title = title;
        this.author = author;
        this.serialNumber = serialNumber;
        this.genre = genre;
        this.isArchive = false;
    }

}
