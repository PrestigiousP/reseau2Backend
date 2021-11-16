package database;

import java.util.ArrayList;

public interface UserConvDao {
    ArrayList<Integer> createConv(UserConv userConv);
    ArrayList<UserConv> getUserConv(int id);
}
