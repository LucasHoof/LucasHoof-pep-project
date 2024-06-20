package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account Registration(Account account){
        return accountDAO.Registration(account);
    }

    public Account Login(Account account){
        return accountDAO.Login(account);
    }

    public Boolean Verify(int account_id){
        return accountDAO.Verify(account_id);
    }
}
