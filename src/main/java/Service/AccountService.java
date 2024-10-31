package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;
    private static final int MIN_LENGTH_PASSWORD = 4;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account account) {
        if (account.getUsername().isBlank()) {
            System.out.println("Username cannot be blank");
            return null;
        }
        if (account.getPassword().length() < MIN_LENGTH_PASSWORD) {
            System.out.println("Password must be at least " + MIN_LENGTH_PASSWORD + " characters long");
            return null;
        }
        if (ifAccountUsernameExists(account)) {
            System.out.println("Username already exists");
            return null;
        }
        return accountDAO.insertAccount(account);
    }

    /**
     * Service method to login if a username exists within the account table.
     * @param account
     * @return an account if username and password matches
     */
    public Account login(Account account) {
        Account storedAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (storedAccount != null && storedAccount.getUsername().equals(account.getUsername()) && storedAccount.getPassword().equals(account.getPassword())) {
            return storedAccount;
        }
        return null;
    }
    
    /**
     * Helper method for addAccount.
     * @param account
     * @return true if account username exists within the database, false otherwise
     */
    private boolean ifAccountUsernameExists(Account account) {
        return accountDAO.getAccountByUsername(account.getUsername()) != null;
    }

}