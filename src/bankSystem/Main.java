package bankSystem;

import controller.AccountController;
import repository.AccountRepository;
import service.AccountService;
import view.AccountView;

public class Main {
	public static void main(String[] args) {
		AccountRepository repository = new AccountRepository();
		AccountService service = new AccountService(repository);
		AccountView view = new AccountView();
		AccountController controller = new AccountController(service, view);
		
		controller.run();
	}
}