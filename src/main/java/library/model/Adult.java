package library.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("Adult")
@NoArgsConstructor
public class Adult extends Client{
    public Adult(String firstName, String lastName, String personalID, Integer age) {
        super(firstName, lastName, personalID, age);
    }

    @Override
    public Float getPenalty() {
        return (float)(5*2);
    }

    @Override
    public Integer getMaxDays() {
        return 90;
    }

    @Override
    public Integer getMaxBooks() {
        return 5;
    }
}
