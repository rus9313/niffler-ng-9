package guru.qa.niffler.data.entity.auth;

import guru.qa.niffler.model.auth.Authority;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AuthAuthorityEntity {
    private UUID id;
    private UUID userId;
    private Authority authority;
}
