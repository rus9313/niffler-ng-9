package guru.qa.niffler.data.mapper;

import guru.qa.niffler.data.entity.auth.AuthAuthorityEntity;
import guru.qa.niffler.model.auth.Authority;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AuthAuthorityRowMapper implements RowMapper<AuthAuthorityEntity> {
    public static final AuthAuthorityRowMapper instance = new AuthAuthorityRowMapper();

    @Override
    public AuthAuthorityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        AuthAuthorityEntity ae = new AuthAuthorityEntity();
        ae.setId(rs.getObject("id", UUID.class));
        ae.setUserId(rs.getObject("user_id", UUID.class));
        ae.setAuthority(Authority.valueOf(rs.getString("authority")));
        return null;
    }
}
