import com.bonitasoft.engine.api.APIAccessor;
import com.bonitasoft.engine.api.IdentityAPI;


def static void deleteUser(APIAccessor apiAccessor, String username){
	
		IdentityAPI identityAPI = apiAccessor.getIdentityAPI();
		identityAPI.deleteUser(username);
		
		return;
	
}