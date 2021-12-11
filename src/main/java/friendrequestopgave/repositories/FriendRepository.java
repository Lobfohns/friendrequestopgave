package friendrequestopgave.repositories;


import friendrequestopgave.models.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Long> {
    @Query(value = "SELECT * FROM friends WHERE status = ? AND sender = ?;", nativeQuery = true)
    List<Friend> findFriendsByStatusForSender(String status, String name);

    @Query(value = "SELECT * FROM friends WHERE status = ? AND receiver = ?;", nativeQuery = true)
    List<Friend> findFriendsByStatusForReceiver(String status, String name);

}


