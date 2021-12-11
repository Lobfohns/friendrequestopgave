package friendrequestopgave.controllers;

import friendrequestopgave.models.Friend;
import friendrequestopgave.repositories.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Friends {

    @Autowired
    FriendRepository friends;


    @PostMapping("/friendship")
    public ResponseEntity<String> addFriendPost(@RequestBody String req) {
        System.out.println(req);
        String version = "1";
        String statusCode;
        String phrase;
        List<Friend> friendsList = friends.findAll();
        String[] splitRequest = req.split(" ");

        if (req.contains("add")) { // ADD
            for (int i = 0; i < friends.count() ;i++) {
                if (friendsList.get(i).getRequest().equals(req)) {
                    statusCode = "530";
                    phrase = "Access Denied Friend Request already sent";
                    return ResponseEntity.ok("{\"response\":\""+version+" "+statusCode+" "+phrase+"\r\n\"}");
                }
            }
            Friend friendRequest = new Friend();
            friendRequest.setRequest(req);
            friendRequest.setStatus("pending");
            friends.save(friendRequest);
            statusCode = "200";
            phrase = "Success friend added";

        } else if (req.contains("accept")) { //ACCEPT
            for (int i = 0; i < friends.count() ;i++) {
                if(friendsList.get(i).getStatus().equals("pending")) {
                    String[] splitFriend = friendsList.get(i).getRequest().split(" ");
                    if (splitFriend[3].equals(splitRequest[1]) && splitFriend[4].equals(splitRequest[2]) || splitRequest[3].equals(splitFriend[1]) && splitRequest[4].equals(splitFriend[2])) {
                        Friend friendAccepted = new Friend();
                        friendAccepted.setStatus("accepted");
                        friendAccepted.setRequest(req);
                        friends.save(friendAccepted);
                        friends.delete(friendsList.get(i));
                        statusCode = "200";
                        phrase = "Success friend accepted";
                        return ResponseEntity.ok("{\"response\":\""+version+" "+statusCode+" "+phrase+"\r\n\"}");
                    }
                }
            }
            statusCode = "530";
            phrase = "Access Denied No Friend Request from Destination";

        } else if (req.contains("deny")) { //DENY
            for (int i = 0; i < friends.count() ;i++) {
                if (friendsList.get(i).getStatus().equals("pending")) {
                    String[] splitFriend = friendsList.get(i).getRequest().split(" ");
                    if (splitFriend[3].equals(splitRequest[1]) && splitFriend[4].equals(splitRequest[2]) || splitRequest[3].equals(splitFriend[1]) && splitRequest[4].equals(splitFriend[2])) {
                        Friend friendDenied = new Friend();
                        friendDenied.setStatus("denied");
                        friendDenied.setRequest(req);
                        friends.save(friendDenied);
                        friends.delete(friendsList.get(i));
                        statusCode = "200";
                        phrase = "Success friend request denied";
                        return ResponseEntity.ok("{\"response\":\""+version+" "+statusCode+" "+phrase+"\r\n\"}");
                    }
                }
            }
            statusCode = "530";
            phrase = "Access Denied No Friend Request from Destination";

        } else if (req.contains("remove")) { //REMOVE
            for (int i = 0; i < friends.count() ;i++) {
                if(friendsList.get(i).getStatus().equals("accepted")) {
                    String[] splitFriend = friendsList.get(i).getRequest().split(" ");
                    if (splitFriend[3].equals(splitRequest[1]) && splitFriend[4].equals(splitRequest[2]) || splitRequest[3].equals(splitFriend[1]) && splitRequest[4].equals(splitFriend[2])) {
                        friends.delete(friendsList.get(i));
                        statusCode = "200";
                        phrase = "Success friend removed";
                        return ResponseEntity.ok("{\"response\":\""+version+" "+statusCode+" "+phrase+"\r\n\"}");
                    }
                }
            }
            statusCode = "530";
            phrase = "Access Denied No Friends from Destination";

        } else if (req.contains("block")) { //BLOCK
            for (int i = 0; i < friends.count() ;i++) {
                if (friendsList.get(i).getRequest().equals(req)) {
                    statusCode = "530";
                    phrase = "Access Denied Friend already Blocked";
                    return ResponseEntity.ok("{\"response\":\""+version+" "+statusCode+" "+phrase+"\r\n\"}");
                }
            }
            Friend friendRequest = new Friend();
            friendRequest.setRequest(req);
            friendRequest.setStatus("blocked");
            friends.save(friendRequest);
            statusCode = "200";
            phrase = "Success friend blocked";

        } else { //WRONG METHOD
            statusCode = "500";
            phrase = "Error, command - Method not found";

        }
        return ResponseEntity.ok("{\"response\":\""+version+" "+statusCode+" "+phrase+"\r\n\"}");
    }
}
