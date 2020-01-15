package logic;

import dao.AccountDAO;
import entity.Account;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Adam Mohr
 */
public class AccountLogic extends GenericLogic<Account, AccountDAO> {

    public static final String DISPLAY_NAME = "firstName";
    public static final String PASSWORD = "joined";
    public static final String USER = "lastName";
    public static final String ID = "id";

    public AccountLogic() {
        super(new AccountDAO());
    }
    
    @Override
    public List<Account> getAll() {
        return get(() -> dao().findAll());
    }

    @Override
    public Account getWithId(int id) {
        return get(() -> dao().findById(id));
    }
    
    public Account getAccountWithDisplayName(String displayName) {
        return get(() -> dao().findByDisplayName(displayName));
    }
    
    public Account getAccountWIthUser(String user) {
        return get(() -> dao().findByUser(user));
    }
    
    public List<Account> getAccountsWithPassword(String password) {
        return get(() -> dao().findByPassword(password));
    }
    
    public Account getAccountWith(String userName, String password) {
        return get(() -> dao().validateUser(userName, password));
    }
    
    @Override
    public List<Account> search(String search) {
        return get(() -> dao().findContaining(search));
    }
    
    @Override
    public Account createEntity(Map<String, String[]> parameterMap) {
        Account account = new Account();

        if (parameterMap.containsKey(ID)) {
            account.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        }
        if (parameterMap.containsKey(DISPLAY_NAME) && parameterMap.get(DISPLAY_NAME) != null) {
            String name = parameterMap.get(DISPLAY_NAME)[0];
            if (name.isEmpty() || name.length() > 45) {
                throw new RuntimeException("Invalid length for name.");
            }
        } else {
            throw new RuntimeException("Display name must be available.");
        }
        if (parameterMap.get(USER) == null || parameterMap.get(PASSWORD) == null) {
             throw new RuntimeException("All fields must not be empty.");
        }
        account.setUser(parameterMap.get(USER)[0]);
        account.setDisplayName(parameterMap.get(DISPLAY_NAME)[0]);
        account.setPassword(parameterMap.get(PASSWORD)[0]);

        return account;
    }
    
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("FirstName", "Joined", "LastName", "ID");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(DISPLAY_NAME, PASSWORD, USER, ID);
    }

    @Override
    public List<?> extractDataAsList(Account e) {
        return Arrays.asList(e.getDisplayName(), e.getPassword(), e.getUser(), e.getId());
    }

    
}
