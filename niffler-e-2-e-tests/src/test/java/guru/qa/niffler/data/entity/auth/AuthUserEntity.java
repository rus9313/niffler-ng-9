package guru.qa.niffler.data.entity.auth;

import guru.qa.niffler.model.CurrencyValues;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class AuthUserEntity implements Serializable {

    private UUID id;
    private String username;
    private String password;
    private boolean enabled;
    private boolean account_non_expired;
    private boolean account_non_locked;
    private boolean credentials_non_expired;
}
