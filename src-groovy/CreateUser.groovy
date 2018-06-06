import org.bonitasoft.engine.identity.User;
import org.bonitasoft.engine.identity.ContactDataCreator;
import org.bonitasoft.engine.identity.Group;
import org.bonitasoft.engine.identity.Role;
import org.bonitasoft.engine.identity.RoleCriterion;
import org.bonitasoft.engine.identity.User;

import org.bonitasoft.engine.identity.UserCreator;
import org.bonitasoft.engine.profile.Profile;
import org.bonitasoft.engine.profile.ProfileMemberCreator;
import org.bonitasoft.engine.profile.ProfileSearchDescriptor;
import org.bonitasoft.engine.search.SearchOptionsBuilder;
import org.bonitasoft.engine.search.SearchResult;

import com.bonitasoft.engine.api.APIAccessor;
import com.bonitasoft.engine.api.IdentityAPI;
import com.bonitasoft.engine.api.ProfileAPI;

def static User createUser(APIAccessor apiAccessor, String username, String password, String firstName, String lastName, String email){
	
		IdentityAPI identityAPI = apiAccessor.getIdentityAPI();
		UserCreator userCreator = new UserCreator(username, password);
		userCreator.setFirstName(firstName);
		userCreator.setLastName(lastName);
				 
		ContactDataCreator contactDataCreator = new ContactDataCreator();
		contactDataCreator.setEmail(email);
		userCreator.setProfessionalContactData(contactDataCreator);
					   
		User user = identityAPI.createUser(userCreator);
			
		Group group = identityAPI.getGroupByPath("/acme");
		Role role =  identityAPI.getRoles(0,1, RoleCriterion.NAME_ASC).get(0);
		
		identityAPI.addUserMembership(user.getId(), group.getId(), role.getId() );
		
		ProfileAPI orgProfileAPI = apiAccessor.getProfileAPI();
		SearchOptionsBuilder searchOptionsBuilder = new SearchOptionsBuilder(0,10);
		searchOptionsBuilder.filter(ProfileSearchDescriptor.NAME, "user");
		
		SearchResult<Profile> searchResultProfile =
			 orgProfileAPI.searchProfiles(searchOptionsBuilder.done());
		
		Profile profile = searchResultProfile.getResult().get(0);
		ProfileMemberCreator profileMemberCreator = new ProfileMemberCreator( profile.getId());
		profileMemberCreator.setUserId(user.getId());
		orgProfileAPI.createProfileMember(profileMemberCreator);
		
		return user;
	
}