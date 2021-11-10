package database;

import java.util.ArrayList;

public interface UserConvDao {
    void createConv(UserConv userConv);
    ArrayList<UserConv> getUserConv(int id);
}
