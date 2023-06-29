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

    private static final String createUsersQL = "insert into public.myusers(firstName, lastName,age) values";
    private static final String updateUsersQL = "update public.myusers set";
    private static final String deleteUsersQL = "delete from public.myusers where id =";
    private static final String findUserByIdSQL = "select * from public.myusers where id =";
    private static final String findUserByNameSQL = "select * from public.myusers where firstName like ";
    private static final String findAllUsersQL = "select * from public.myusers";

    public Long createUser(User user) {

        long id=0;
        try {
            connection = CustomDataSource.getInstance().getConnection();
            st = connection.createStatement();
            st.executeUpdate(createUsersQL+"('"+user.getFirstName()+"',"
                    +"'"+user.getLastName()+"',"+user.getAge()+")");
            ResultSet rs = st.executeQuery("SELECT * FROM public.myusers ORDER BY ID DESC LIMIT 1");
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
        List <User> Users = new ArrayList<>();
        try {
            connection = CustomDataSource.getInstance().getConnection();
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(findAllUsersQL);
            while(rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getInt("age"));
                Users.add(user);
            }
            rs.close();
            st.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Users;
    }

    public User updateUser(User user) {

        try {
            connection = CustomDataSource.getInstance().getConnection();
            st = connection.createStatement();
            st.executeUpdate(updateUsersQL + "first_name='"+user.getFirstName()+"',"
            +"lastName='"+user.getLastName()+"', age ="+user.getAge()+" where id ="+user.getId());
            ResultSet rs = st.executeQuery("SELECT * FROM public.myusers ORDER BY ID DESC LIMIT 1");
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
            st.executeUpdate(deleteUsersQL+userId);
            st.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
