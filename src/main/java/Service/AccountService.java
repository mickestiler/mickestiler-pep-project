package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account account) {
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
    }
    
    private boolean ifAccountUsernameExists(Account account) {
        return accountDAO.getAccountByUsername(account.getUsername()) != null;
    }
}