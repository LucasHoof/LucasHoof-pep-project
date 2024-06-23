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
        if(account.getPassword().length() > 4 && account.getUsername() != ""){
            return accountDAO.Registration(account);
        }
        else{
            return null;
        }
    }

    public Account Login(Account account){
        return accountDAO.Login(account);
    }

    public Boolean Verify(int account_id){
        return accountDAO.Verify(account_id);
    }
}
