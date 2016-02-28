package cl.gov.subdere.chileindica.sgd;

import cl.gov.subdere.chileindica.sgd.adm.User;

public class Control {
	
	public Version createVersion(Version parent, User user){
		return new Version(parent);
	}

}
