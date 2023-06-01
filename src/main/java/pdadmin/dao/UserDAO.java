package pdadmin.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDAO {
    private Long id;
    private String role;

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserDAO{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
