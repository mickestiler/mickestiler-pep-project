package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account account) {
        try {
            if (account.getUsername().isBlank()) {
                throw new IllegalArgumentException("Username cannot be blank");
            }
            if (account.getPassword().length() < 4) {
                throw new IllegalArgumentException("Password must be at least 4 characters long");
            }
            if (ifAccountUsernameExists(account)) {
                throw new IllegalArgumentException("Username already exists");
            }
            return accountDAO.insertAccount(account);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }

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