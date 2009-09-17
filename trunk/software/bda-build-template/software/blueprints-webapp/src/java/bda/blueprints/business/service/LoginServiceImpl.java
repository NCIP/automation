package bda.blueprints.business.service;

import bda.blueprints.business.data.InvalidLoginException;
import bda.blueprints.business.data.LoginDao;
import bda.blueprints.business.data.LoginDaoImpl;

public class LoginServiceImpl implements LoginService {

	public void login(String username, String password) throws UserException {
		System.out.println("Username is: " + username + " Password is: " + password);
		LoginDao loginData = new LoginDaoImpl();
		try {
			loginData.login(LoginDao.VERIFY_LOGIN, username, password);
		} catch (InvalidLoginException e) {
			throw new UserException();
		}
	}
}
