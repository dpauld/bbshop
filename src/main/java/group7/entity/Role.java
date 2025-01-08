//package group7.entity;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToMany;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Deprecated
//@NoArgsConstructor
//@Data
//@Entity(name = "role")
//public class Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//
//    @Column(unique = true, nullable = false)
//    private String name;
//
//    //this is optional, if we want to have unidirectional ManyToMany mapping where owning side is the User.
//    //Also, unidirectional mapping is suitable when only want to have a Collection of one Entity, in this case we are having a Set of Role in the User side
//
//    //bidirectional ManyToMany mapping
//    @ManyToMany(mappedBy = "roles")
//    private Set<User_> users = new HashSet<>();
//
//    //other options to achieve bidirectional ManyToMany mapping, note: If a different table name is used here @JoinTable(name = "user_role",...), Two table will be created, so be careful.
////	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) //with fetch type lazy impl Graph Entity
////	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name="role_id", referencedColumnName="id"), inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"))
////	private Set<User> users = new HashSet<>();
//
//    public void addUser(User_ user) {
//        if (user != null) {
//            this.users.add(user);
//        }
//    }
//
//    public void removeUser(User_ user) {
//        if (user != null) {
//            this.users.remove(user);
//        }
//    }
//}
