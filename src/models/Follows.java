package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follows")
@NamedQueries({
    @NamedQuery(
            name = "isFollow",
            query = "SELECT count(f) FROM Follows AS f WHERE f.follower_id = :followerId AND f.followed_id = :followedId"
            ),
    @NamedQuery(
            name = "getFollowList",
            query = "SELECT f FROM Follows AS f WHERE f.follower_id = :followerId"
            ),
    @NamedQuery(
            name = "getFollowId",
            query = "SELECT f FROM Follows AS f WHERE f.follower_id = :followerId AND f.followed_id = :followedId"
            )
})
@Entity
public class Follows {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "follower_id", nullable = false)
    private Integer follower_id;

    @Column(name = "followed_id", nullable = false)
    private Integer followed_id;

    public Follows(){
    }

    public Follows(Integer follower_id, Integer followed_id){
        this.follower_id = follower_id;
        this.followed_id = followed_id;
    }

}