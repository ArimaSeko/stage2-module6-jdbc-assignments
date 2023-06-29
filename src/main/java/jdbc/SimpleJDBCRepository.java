package jdbc;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleJDBCRepository {

    private Connection connection = null;
    private PreparedStatement ps = null;
    private Statement st = null;

    private static final String createUserSQL = "insert into users(firstName, lastName,age) values";
    private static final String updateUserSQL = "update Users set";
    private static final String deleteUserSQL = "delete from Users where id =";
    private static final String findUserByIdSQL = "select * from Users where id =";
    private static final String findUserByNameSQL = "select * from Users where name like ";
    private static final String findAllUserSQL = "select * from users";

    public Long createUser(User user) {

        long id=0;
        try {
            connection = CustomDataSource.getInstance().getConnection();
            st = connection.createStatement();
            st.executeUpdate(createUserSQL+"('"+user.getFirstName()+"',"
                    +"'"+user.getLastName()+"',"+user.getAge()+")");
            ResultSet rs = st.executeQuery("SELECT * FROM USERS ORDER BY ID DESC LIMIT 1");
            while(rs.next()){
                id =rs.getLong("id");
            }
            rs.close();
            st.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public User findUserById(Long userId) {
        User user = new User();
        try {
            connection = CustomDataSource.getInstance().getConnection();
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(findUserByIdSQL+userId);
            while(rs.next()) {
            user.setId(rs.getLong("id"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("firstName"));
            user.setAge(rs.getInt("age"));
            }
            rs.close();
            st.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User findUserByName(String userName) {
        User user = new User();
        try {
            connection = CustomDataSource.getInstance().getConnection();
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(findUserByNameSQL+"'%"+userName+"%'");
            while (rs.next()) {
            user.setId(rs.getLong("id"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("firstName"));
            user.setAge(rs.getInt("age"));
            }
            rs.close();
            st.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public List<User> findAllUser() {
        List <User> users = new ArrayList<>();
        try {
            connection = CustomDataSource.getInstance().getConnection();
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(findAllUserSQL);
            while(rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("firstName"));
                user.setAge(rs.getInt("age"));
                users.add(user);
            }
            rs.close();
            st.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public User updateUser(User user) {

        try {
            connection = CustomDataSource.getInstance().getConnection();
            st = connection.createStatement();
            st.executeUpdate(updateUserSQL + "firstName='"+user.getFirstName()+"',"
            +"lastName='"+user.getLastName()+"', age ="+user.getAge()+" where id ="+user.getId());
            ResultSet rs = st.executeQuery("SELECT * FROM USERS ORDER BY ID DESC LIMIT 1");
            while(rs.next()){
                 user = new User();
                user.setId(rs.getLong("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("firstName"));
                user.setAge(rs.getInt("age"));
            }
            rs.close();
            st.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void deleteUser(Long userId) {
        try {
            connection = CustomDataSource.getInstance().getConnection();
            st = connection.createStatement();
            st.executeUpdate(deleteUserSQL+userId);
            st.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
