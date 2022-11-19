package study.mockito;

import javax.servlet.http.HttpServletRequest;

public class AccountLoginController {
    private AccountDao accountDao;

    public AccountLoginController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public String login(HttpServletRequest request) {
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");
        try
        {
            final Account account = accountDao.findAccount(username, password);
            if (account == null) {
                return "/login";
            } else {
                return "/index.jsp";
            }
        }catch (Exception e){
            return "/505";
        }
    }
}
