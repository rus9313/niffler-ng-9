package guru.qa.niffler.data.mapper;

import guru.qa.niffler.data.entity.userdata.FriendshipEntity;
import guru.qa.niffler.data.entity.userdata.FriendshipStatus;
import guru.qa.niffler.data.entity.userdata.UserEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ParametersAreNonnullByDefault
public class UserdataUserEntityResultSetExtractor implements ResultSetExtractor<List<UserEntity>> {
    public static final UserdataUserEntityResultSetExtractor instance = new UserdataUserEntityResultSetExtractor();

    private UserdataUserEntityResultSetExtractor() {
    }

    @Nullable
    @Override
    public List<UserEntity> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<UUID, UserEntity> userMap = new ConcurrentHashMap<>();
        while (rs.next()) {
            UserEntity user = userMap.getOrDefault(rs.getObject("id", UUID.class),
                    UserdataUserEntityRowMapper.instance.mapRow(rs, 1));

            FriendshipEntity friendship = new FriendshipEntity();
            if (rs.getObject("requester_id", UUID.class).equals(user.getId())) {
                friendship.setRequester(user);
                UserEntity u = new UserEntity();
                u.setId(rs.getObject("addressee_id", UUID.class));
                friendship.setAddressee(u);
                user.getFriendshipRequests().add(friendship);
            }
            if (rs.getObject("addressee_id", UUID.class).equals(user.getId())) {
                UserEntity u = new UserEntity();
                u.setId(rs.getObject("requester_id", UUID.class));
                friendship.setRequester(u);
                friendship.setAddressee(user);
                user.getFriendshipAddressees().add(friendship);
            }
            friendship.setStatus(Optional.ofNullable(rs.getString("status"))
                    .map(FriendshipStatus::valueOf)
                    .orElse(null));
            friendship.setCreatedDate(rs.getDate("created_date"));
            userMap.put(user.getId(), user);
        }
        return new ArrayList<>(userMap.values());
    }
}
