package bda.blueprints.business.service;

public interface LoginService {

	public void login(String username, String password) throws UserException;

}
