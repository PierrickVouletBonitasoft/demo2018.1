import java.sql.ResultSet;
import java.sql.SQLException;

import org.bonitasoft.onboarding.Employee;
import org.bonitasoft.onboarding.HardwareItemDefinition;
import org.bonitasoft.onboarding.Person;
import org.bonitasoft.onboarding.SoftwareItemDefinition;


def static Employee mapEmployee(ResultSet resultSet){
	Employee employee = new Employee();
	while(resultSet.next()){
		employee.setBonitaUserId(resultSet.getLong("bonita_user_id"));
		employee.setId(resultSet.getLong("id"));
		employee.setContractType(resultSet.getString("contract_type"));
		employee.setFixedSalary(resultSet.getInt("fixed_salary"));
		employee.setDepartment(resultSet.getString("department"));
		employee.setFirstPaymentDate(getDate(resultSet, "first_payment_date"));
		employee.setHiringManagerId(resultSet.getString("hiring_manager_id"));
		employee.setHomeOffice(resultSet.getString("home_office"));
		employee.setPersonId(resultSet.getLong("person_id"));
		employee.setPosition(resultSet.getString("position"));
		employee.setPositionId(resultSet.getLong("position_id"));
		employee.setStartDate(getDate(resultSet, "start_date"));
		employee.setWorkspaceLocation(resultSet.getString("workspace_location"));
		employee.setVariableSalary(resultSet.getInt("variable_salary"));
		employee.setIsWorkspaceNeeded(getBoolean(resultSet, "is_workspace_needed"));
		String benefits = resultSet.getString("benefits");
		employee.setBenefits(benefits == null || benefits.isEmpty() ? new ArrayList() : Arrays.asList(benefits.split(",")));
		employee.setOnBoardingCompletionDate(getDate(resultSet, "on_boarding_complete"));
		}
	return employee;
}

def static Person mapPerson(ResultSet resultSet){
	Person person = new Person();
	while(resultSet.next()){
		person.setId(resultSet.getLong("id"));
		person.setAddress(resultSet.getString("address"));
		person.setCity(resultSet.getString("city"));
		person.setDateOfBirth(getDate(resultSet, "date_of_birth"));
		person.setEmail(resultSet.getString("email"));
		person.setProfessionalMail(resultSet.getString("professional_email"));
		person.setGender(resultSet.getString("gender"));
		person.setFirstName(resultSet.getString("first_name"));
		person.setLastName(resultSet.getString("last_name"));
		person.setPhone(resultSet.getString("phone"));
		person.setWorkPhone(resultSet.getString("work_phone"));
		person.setZipCode(resultSet.getInt("zip_code"));
	}
	return person;
}

def static Map<String, List> mapDepartmentData(ResultSet resultSet){
	Map departmentMap = new HashMap();
	while(resultSet.next()){
		String department = resultSet.getString("department");
		String position = resultSet.getString("position");
		if(departmentMap.containsKey(department)){
			departmentMap.get(department).add(position);
		} else{
		departmentMap.put(department. position);
		}
	}
}

def static 	List<SoftwareItemDefinition> mapSoftwareOptions(ResultSet resultSet){
	List<SoftwareItemDefinition> items  = new ArrayList<SoftwareItemDefinition>();
	while(resultSet.next()){
		SoftwareItemDefinition item  = new SoftwareItemDefinition();
		item.setId(resultSet.getLong("id"));
		item.setLabel(resultSet.getString("label"));
		items.add(item);
	}
	return items;
}


def static List<HardwareItemDefinition> mapHardwareOptions(ResultSet resultSet){
	List<HardwareItemDefinition> items  = new ArrayList<HardwareItemDefinition>();
	while(resultSet.next()){
		HardwareItemDefinition item  = new HardwareItemDefinition();
		item.setId(resultSet.getLong("id"));
		item.setLabel(resultSet.getString("label"));
		items.add(item);
	}
	return items;
}


def static Boolean getBoolean(ResultSet resultset, String parameterName){
	return "true".equalsIgnoreCase(resultset.getString(parameterName));
}

def static Date getDate(ResultSet result, String parameterName)
{
	   try {
			return result.getDate(parameterName) == null ? null :
					new Date(result.getDate(parameterName).getTime()) ;
		} catch (SQLException e) {
		   throw new RuntimeException(e);
		}
}
